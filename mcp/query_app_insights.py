#!/usr/bin/env python3
import os
import json
import subprocess
import requests
import sys

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
        print(f"Error getting access token: {e}")
        print(f"Error output: {e.stderr}")
        sys.exit(1)
    except json.JSONDecodeError as e:
        print(f"Error parsing token response: {e}")
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
        print(f"Error getting Application Insights app ID: {e}")
        print(f"Error output: {e.stderr}")
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
        response = requests.post(url, headers=headers, params=params, json=query_body)
        response.raise_for_status()
        return response.json()
    except requests.exceptions.RequestException as e:
        print(f"Error querying Application Insights: {e}")
        if hasattr(e, 'response') and e.response:
            print(f"Response: {e.response.text}")
        sys.exit(1)

def display_results(response_data):
    """Process and display the results"""
    tables = response_data.get("tables", [])
    
    if not tables:
        print("No results returned")
        return
    
    for i, table in enumerate(tables):
        table_name = table.get("name", f"Table {i}")
        print(f"Table Name: {table_name}")
        
        # Get column names
        columns = [col["name"] for col in table.get("columns", [])]
        
        # Get rows
        rows = table.get("rows", [])
        
        if rows:
            # Print column headers
            print("\t".join(columns))
            print("-" * 53)
            
            # Print each row
            for row in rows:
                print("\t".join(str(cell) for cell in row))
        
        print()

def main():
    # Get resource group name from environment or user input
    resource_group_name = os.environ.get("RESOURCE_GROUP_NAME")
    if not resource_group_name:
        resource_group_name = input("Enter resource group name: ")
    
    # Get token
    token = get_access_token()
    
    # Create query body
    query_body = {
        "query": "requests | take 10 | project timestamp, name, duration"
    }
    
    # Get Application Insights app ID
    app_insights_app_id = get_app_insights_id(resource_group_name)
    
    # Query Application Insights
    response_data = query_app_insights(app_insights_app_id, token, query_body)
    
    # Display the results
    display_results(response_data)

if __name__ == "__main__":
    main()