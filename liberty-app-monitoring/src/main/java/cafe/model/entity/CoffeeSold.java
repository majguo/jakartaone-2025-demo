package cafe.model.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name = "findCoffeeSoldByCoffeeId", query = "SELECT cs FROM CoffeeSold cs WHERE cs.coffee.id = :coffeeId")
@NamedQuery(name = "findAllCoffeeSold", query = "SELECT cs FROM CoffeeSold cs")
public class CoffeeSold implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "coffee_id", nullable = false, unique = true)
    private Coffee coffee;

    private Long soldCnt = 0L;

    public CoffeeSold() {
    }

    public CoffeeSold(Coffee coffee, Long soldCnt) {
        this.coffee = coffee;
        this.soldCnt = soldCnt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public Long getSoldCnt() {
        return soldCnt;
    }

    public void setSoldCnt(Long soldCnt) {
        this.soldCnt = soldCnt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CoffeeSold)) {
            return false;
        }
        CoffeeSold other = (CoffeeSold) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cafe.model.entity.CoffeeSold[id=" + id + ", coffeeId=" + (coffee != null ? coffee.getId() : null) + ", soldCnt=" + soldCnt + "]";
    }
}
