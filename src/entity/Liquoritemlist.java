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
@Table(name = "liquoritemlist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Liquoritemlist.findAll", query = "SELECT l FROM Liquoritemlist l")
    , @NamedQuery(name = "Liquoritemlist.findById", query = "SELECT l FROM Liquoritemlist l WHERE l.id = :id")
    , @NamedQuery(name = "Liquoritemlist.findByQty", query = "SELECT l FROM Liquoritemlist l WHERE l.qty = :qty")
    , @NamedQuery(name = "Liquoritemlist.findByAmount", query = "SELECT l FROM Liquoritemlist l WHERE l.amount = :amount")

    // get all list by using 
    , @NamedQuery(name = "Liquoritemlist.findByLiquorbillId", query = "SELECT l FROM Liquoritemlist l WHERE l.liquorbillId = :liquorbillId")

})
public class Liquoritemlist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "qty")
    private Integer qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private BigDecimal amount;
    @JoinColumn(name = "liquorbill_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Liquorbill liquorbillId;
    @JoinColumn(name = "liquoritem_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Liquoritem liquoritemId;

    public Liquoritemlist() {
    }

    public Liquoritemlist(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Liquorbill getLiquorbillId() {
        return liquorbillId;
    }

    public void setLiquorbillId(Liquorbill liquorbillId) {
        this.liquorbillId = liquorbillId;
    }

    public Liquoritem getLiquoritemId() {
        return liquoritemId;
    }

    public void setLiquoritemId(Liquoritem liquoritemId) {
        this.liquoritemId = liquoritemId;
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
        if (!(object instanceof Liquoritemlist)) {
            return false;
        }
        Liquoritemlist other = (Liquoritemlist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Liquoritemlist[ id=" + id + " ]";
    }
    
}
