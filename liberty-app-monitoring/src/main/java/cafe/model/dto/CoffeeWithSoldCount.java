package cafe.model.dto;

import java.io.Serializable;

public class CoffeeWithSoldCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Double price;
    private Long soldCount;

    public CoffeeWithSoldCount() {
    }

    public CoffeeWithSoldCount(Long id, String name, Double price, Long soldCount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.soldCount = soldCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(Long soldCount) {
        this.soldCount = soldCount;
    }

    @Override
    public String toString() {
        return "CoffeeWithSoldCount[id=" + id + ", name=" + name + ", price=" + price + ", soldCount=" + soldCount + "]";
    }
}
