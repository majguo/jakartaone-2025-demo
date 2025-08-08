package cafe.model;

import java.lang.invoke.MethodHandles;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cafe.model.entity.Coffee;
import cafe.model.entity.CoffeeSold;

@Stateless
public class CafeRepository {

    private static final Logger logger = LoggerFactory.getLogger(CafeRepository.class);

    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Coffee> getAllCoffees() {
        logger.info("Finding all coffees.");
        return this.entityManager.createNamedQuery("findAllCoffees", Coffee.class).getResultList();
    }

    public Coffee persistCoffee(Coffee coffee) {
        logger.info("Persisting the new coffee: {}", coffee);
        this.entityManager.persist(coffee);
        return coffee;
    }

    public void removeCoffeeById(Long coffeeId) {
        logger.info("Removing a coffee with ID: {}", coffeeId);
        
        // First, find and remove any related CoffeeSold records
        List<CoffeeSold> coffeeSoldList = findExistingCoffeeSoldByCoffeeId(coffeeId);
        if (!coffeeSoldList.isEmpty()) {
            CoffeeSold coffeeSold = coffeeSoldList.get(0);
            logger.info("Removing CoffeeSold record for coffee ID: {}", coffeeId);
            this.entityManager.remove(coffeeSold);
        }
        
        // Then remove the coffee
        Coffee coffee = entityManager.find(Coffee.class, coffeeId);
        if (coffee != null) {
            this.entityManager.remove(coffee);
            logger.info("Successfully removed coffee with ID: {}", coffeeId);
        } else {
            logger.warn("Coffee with ID {} not found", coffeeId);
        }
    }

    public Coffee findCoffeeById(Long coffeeId) {
        logger.info("Finding the coffee with ID: {}", coffeeId);
        return this.entityManager.find(Coffee.class, coffeeId);
    }

    public CoffeeSold findCoffeeSoldByCoffeeId(Long coffeeId) {
        logger.info("Finding coffee sold count for coffee ID: {}", coffeeId);
        List<CoffeeSold> results = findExistingCoffeeSoldByCoffeeId(coffeeId);
        if (results.isEmpty()) {
            // Create a new CoffeeSold entry with count 0 if doesn't exist
            Coffee coffee = findCoffeeById(coffeeId);
            if (coffee != null) {
                CoffeeSold coffeeSold = new CoffeeSold(coffee, 0L);
                this.entityManager.persist(coffeeSold);
                logger.info("Created new CoffeeSold entry for coffee ID: {} with count 0", coffeeId);
                return coffeeSold;
            }
            return null;
        }
        return results.get(0);
    }

    private List<CoffeeSold> findExistingCoffeeSoldByCoffeeId(Long coffeeId) {
        logger.info("Finding existing coffee sold records for coffee ID: {}", coffeeId);
        return this.entityManager.createNamedQuery("findCoffeeSoldByCoffeeId", CoffeeSold.class)
                                 .setParameter("coffeeId", coffeeId)
                                 .getResultList();
    }

    public CoffeeSold incrementSoldCount(Long coffeeId) {
        logger.info("Incrementing sold count for coffee ID: {}", coffeeId);
        CoffeeSold coffeeSold = findCoffeeSoldByCoffeeId(coffeeId);
        if (coffeeSold != null) {
            coffeeSold.setSoldCnt(coffeeSold.getSoldCnt() + 1);
            this.entityManager.merge(coffeeSold);
            logger.info("Incremented sold count to {} for coffee ID: {}", coffeeSold.getSoldCnt(), coffeeId);
        }
        return coffeeSold;
    }
}
