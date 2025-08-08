package cafe.web.rest;

import java.util.List;
import java.util.ArrayList;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import cafe.model.CafeRepository;
import cafe.model.entity.Coffee;
import cafe.model.entity.CoffeeSold;
import cafe.model.dto.CoffeeWithSoldCount;

import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.LongCounter;

@Path("coffees")
public class CafeResource {

    @Inject
    private CafeRepository cafeRepository;

    @Inject
    private Meter meter;

    private LongCounter getAllCoffeesCounter;

    @PostConstruct
    public void initMetrics() {
        this.getAllCoffeesCounter = meter.counterBuilder("getAllCoffees.invocations")
                                         .setDescription("Counts the number of times getAllCoffees is invoked")
                                         .setUnit("1")
                                         .build();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public List<CoffeeWithSoldCount> getAllCoffees() {
        this.getAllCoffeesCounter.add(1);
        
        List<Coffee> coffees = this.cafeRepository.getAllCoffees();
        List<CoffeeWithSoldCount> result = new ArrayList<>();
        
        for (Coffee coffee : coffees) {
            CoffeeSold coffeeSold = this.cafeRepository.findCoffeeSoldByCoffeeId(coffee.getId());
            Long soldCount = (coffeeSold != null) ? coffeeSold.getSoldCnt() : 0L;
            
            CoffeeWithSoldCount coffeeWithCount = new CoffeeWithSoldCount(
                coffee.getId(),
                coffee.getName(),
                coffee.getPrice(),
                soldCount
            );
            result.add(coffeeWithCount);
        }
        
        return result;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })	
    public Coffee createCoffee(Coffee coffee) {
        return this.cafeRepository.persistCoffee(coffee);
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Coffee getCoffeeById(@PathParam("id") Long coffeeId) {
        return this.cafeRepository.findCoffeeById(coffeeId);
    }

    @DELETE
    @Path("{id}")
    public void deleteCoffee(@PathParam("id") Long coffeeId) {
        this.cafeRepository.removeCoffeeById(coffeeId);
    }

    @PUT
    @Path("{id}/buy")
    @Produces({ MediaType.APPLICATION_JSON })
    public CoffeeSold buyCoffee(@PathParam("id") Long coffeeId) {
        return this.cafeRepository.incrementSoldCount(coffeeId);
    }
}
