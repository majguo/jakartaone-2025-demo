import httpx
import logging
from mcp.server.fastmcp import FastMCP

import os
import json
import subprocess
import sys

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger("azure-app-insights-mcp")

# Initialize FastMCP server
mcp = FastMCP("azure-app-insights")

def get_access_token():
    """Get Azure access token for Application Insights"""
    try:
        # Run Azure CLI command to get the token
        result = subprocess.run(
            ["az", "account", "get-access-token", "--resource", "https://api.applicationinsights.io/"],
            capture_output=True, text=True, check=True
        )
        token_data = json.loads(result.stdout)
        return token_data["accessToken"]
    except subprocess.CalledProcessError as e:
        logger.error(f"Error getting access token: {e}")
        logger.error(f"Error output: {e.stderr}")
        sys.exit(1)
    except json.JSONDecodeError as e:
        logger.error(f"Error parsing token response: {e}")
        sys.exit(1)

def get_app_insights_id(resource_group_name):
    """Get Application Insights app ID from the resource group"""
    try:
        result = subprocess.run(
            ["az", "monitor", "app-insights", "component", "show", 
             "--resource-group", resource_group_name, 
             "--query", "[0].appId", "-o", "tsv"],
            capture_output=True, text=True, check=True
        )
        return result.stdout.strip()
    except subprocess.CalledProcessError as e:
        logger.error(f"Error getting Application Insights app ID: {e}")
        logger.error(f"Error output: {e.stderr}")
        sys.exit(1)

def query_app_insights(app_id, token, query_body):
    """Make API call to Application Insights"""
    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Bearer {token}"
    }
    
    url = f"https://api.applicationinsights.io/v1/apps/{app_id}/query"
    params = {"timespan": "P1D"}  # Last 1 day of data
    
    try:
        with httpx.Client() as client:
            response = client.post(url, headers=headers, params=params, json=query_body)
            response.raise_for_status()
            return response.json()
    except httpx.RequestError as e:
        logger.error(f"Error querying Application Insights: {e}")
        sys.exit(1)
    except httpx.HTTPStatusError as e:
        logger.error(f"HTTP error querying Application Insights: {e}")
        logger.error(f"Response: {e.response.text}")
        sys.exit(1)

def format_results(response_data):
    """Process and return the results as a JSON array"""
    tables = response_data.get("tables", [])
    
    if not tables:
        logger.info("No results returned")
        return []
    
    all_results = []
    
    for i, table in enumerate(tables):
        table_name = table.get("name", f"Table {i}")
        logger.info(f"Processing table: {table_name}")
        
        # Get column names
        columns = [col["name"] for col in table.get("columns", [])]
        
        # Get rows
        rows = table.get("rows", [])
        
        if rows:
            # Convert each row to a dictionary with column names as keys
            for row in rows:
                row_dict = {}
                for j, value in enumerate(row):
                    if j < len(columns):
                        row_dict[columns[j]] = value
                all_results.append(row_dict)
    
    return all_results

@mcp.tool()
async def get_requests(resource_group_name: str, query: str):
    """Get response time of requests from the Azure Application Insights

    Args:
        resource_group_name: The name of the Azure resource group where the Azure Application Insights 
            resource is located.
        query: The query string to send to Application Insights. It's a Kusto Query Language (KQL) query 
            that queries requests table and should only return columns timestamp, name and duration. 
            You should infer the structure of the query from the context. 
            A simple example to query the last 10 requests would be "requests | take 10 | project timestamp, name, duration".
            Use it as default query if none is provided or you can't infer the query finally.
    """
    
    # Get token
    token = get_access_token()
    
    # Create query body
    query_body = {
        "query": query
    }
    
    # Get Application Insights app ID
    app_insights_app_id = get_app_insights_id(resource_group_name)
    
    # Query Application Insights
    response_data = query_app_insights(app_insights_app_id, token, query_body)

    # Format the results
    results = format_results(response_data)
    
    # Log the JSON results
    logger.info(f"Query results: {json.dumps(results, indent=2)}")
    
    return results

if __name__ == "__main__":
    # Initialize and run the server
    logger.info("Starting azure-app-insights MCP server")
    mcp.run(transport='stdio')
