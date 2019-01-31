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
@Table(name = "spapackagelist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spapackagelist.findAll", query = "SELECT s FROM Spapackagelist s")
    , @NamedQuery(name = "Spapackagelist.findById", query = "SELECT s FROM Spapackagelist s WHERE s.id = :id")
    , @NamedQuery(name = "Spapackagelist.findByQty", query = "SELECT s FROM Spapackagelist s WHERE s.qty = :qty")
    , @NamedQuery(name = "Spapackagelist.findByAmount", query = "SELECT s FROM Spapackagelist s WHERE s.amount = :amount")

     // get all list by using 
    ,@NamedQuery(name = "Spapackagelist.findBySpabillId", query = "SELECT s FROM Spapackagelist s WHERE s.spabillId = :spabillId")
    
})
public class Spapackagelist implements Serializable {

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
    @JoinColumn(name = "spabill_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Spabill spabillId;
    @JoinColumn(name = "spapackage_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Spapackage spapackageId;

    public Spapackagelist() {
    }

    public Spapackagelist(Integer id) {
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

    public Spabill getSpabillId() {
        return spabillId;
    }

    public void setSpabillId(Spabill spabillId) {
        this.spabillId = spabillId;
    }

    public Spapackage getSpapackageId() {
        return spapackageId;
    }

    public void setSpapackageId(Spapackage spapackageId) {
        this.spapackageId = spapackageId;
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
        if (!(object instanceof Spapackagelist)) {
            return false;
        }
        Spapackagelist other = (Spapackagelist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Spapackagelist[ id=" + id + " ]";
    }
    
}
