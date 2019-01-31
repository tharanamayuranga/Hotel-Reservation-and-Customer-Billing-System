/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tharana
 */
@Entity
@Table(name = "restaurantbill")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Restaurantbill.findAll", query = "SELECT r FROM Restaurantbill r")
    , @NamedQuery(name = "Restaurantbill.findById", query = "SELECT r FROM Restaurantbill r WHERE r.id = :id")
    , @NamedQuery(name = "Restaurantbill.findByReserveddate", query = "SELECT r FROM Restaurantbill r WHERE r.reserveddate = :reserveddate")
    , @NamedQuery(name = "Restaurantbill.findByTotalprice", query = "SELECT r FROM Restaurantbill r WHERE r.totalprice = :totalprice")

    //to get last restaurantbill id
    , @NamedQuery(name = "Restaurantbill.getRestaurantBillId", query = "SELECT r FROM Restaurantbill r WHERE r.id = ( SELECT MAX(r.id) FROM Restaurantbill r )")
    
})
public class Restaurantbill implements Serializable {

    @Column(name = "paiddate")
    @Temporal(TemporalType.DATE)
    private Date paiddate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurantbillId", fetch = FetchType.EAGER)
    private List<Restaurantfooditemlist> restaurantfooditemlistList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "reserveddate")
    @Temporal(TemporalType.DATE)
    private Date reserveddate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "totalprice")
    private BigDecimal totalprice;
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Reservation reservationId;

    public Restaurantbill() {
    }

    public Restaurantbill(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReserveddate() {
        return reserveddate;
    }

    public void setReserveddate(Date reserveddate) {
        this.reserveddate = reserveddate;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public Reservation getReservationId() {
        return reservationId;
    }

    public void setReservationId(Reservation reservationId) {
        this.reservationId = reservationId;
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
        if (!(object instanceof Restaurantbill)) {
            return false;
        }
        Restaurantbill other = (Restaurantbill) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Restaurantbill[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Restaurantfooditemlist> getRestaurantfooditemlistList() {
        return restaurantfooditemlistList;
    }

    public void setRestaurantfooditemlistList(List<Restaurantfooditemlist> restaurantfooditemlistList) {
        this.restaurantfooditemlistList = restaurantfooditemlistList;
    }

    public Date getPaiddate() {
        return paiddate;
    }

    public void setPaiddate(Date paiddate) {
        this.paiddate = paiddate;
    }
    
}
