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
@Table(name = "spabill")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spabill.findAll", query = "SELECT s FROM Spabill s")
    , @NamedQuery(name = "Spabill.findById", query = "SELECT s FROM Spabill s WHERE s.id = :id")
    , @NamedQuery(name = "Spabill.findByReserveddate", query = "SELECT s FROM Spabill s WHERE s.reserveddate = :reserveddate")
    , @NamedQuery(name = "Spabill.findByTotalprice", query = "SELECT s FROM Spabill s WHERE s.totalprice = :totalprice")

    //to get last spabill id
    , @NamedQuery(name = "Spabill.getSpaBillId", query = "SELECT s FROM Spabill s WHERE s.id = ( SELECT MAX(s.id) FROM Spabill s )")
    

})
public class Spabill implements Serializable {

    @Column(name = "paiddate")
    @Temporal(TemporalType.DATE)
    private Date paiddate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spabillId", fetch = FetchType.LAZY)
    private List<Spapackagelist> spapackagelistList;

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

    public Spabill() {
    }

    public Spabill(Integer id) {
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
        if (!(object instanceof Spabill)) {
            return false;
        }
        Spabill other = (Spabill) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Spabill[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Spapackagelist> getSpapackagelistList() {
        return spapackagelistList;
    }

    public void setSpapackagelistList(List<Spapackagelist> spapackagelistList) {
        this.spapackagelistList = spapackagelistList;
    }

    public Date getPaiddate() {
        return paiddate;
    }

    public void setPaiddate(Date paiddate) {
        this.paiddate = paiddate;
    }
    
}
