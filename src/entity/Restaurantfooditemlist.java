/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tharana
 */
@Entity
@Table(name = "restaurantfooditemlist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Restaurantfooditemlist.findAll", query = "SELECT r FROM Restaurantfooditemlist r")
    , @NamedQuery(name = "Restaurantfooditemlist.findById", query = "SELECT r FROM Restaurantfooditemlist r WHERE r.id = :id"),

    // get all list by using 
    @NamedQuery(name = "Restaurantfooditemlist.findByRestaurantbillId", query = "SELECT r FROM Restaurantfooditemlist r WHERE r.restaurantbillId = :restaurantbillId")

})
public class Restaurantfooditemlist implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "qty")
    private Integer qty;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "fooditem_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Fooditem fooditemId;
    @JoinColumn(name = "restaurantbill_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Restaurantbill restaurantbillId;

    public Restaurantfooditemlist() {
    }

    public Restaurantfooditemlist(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Fooditem getFooditemId() {
        return fooditemId;
    }

    public void setFooditemId(Fooditem fooditemId) {
        this.fooditemId = fooditemId;
    }

    public Restaurantbill getRestaurantbillId() {
        return restaurantbillId;
    }

    public void setRestaurantbillId(Restaurantbill restaurantbillId) {
        this.restaurantbillId = restaurantbillId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Restaurantfooditemlist)) {
            return false;
        }
        Restaurantfooditemlist other = (Restaurantfooditemlist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Restaurantfooditemlist[ id=" + id + " ]";
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
}
