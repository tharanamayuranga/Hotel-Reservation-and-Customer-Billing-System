/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tharana
 */
@Entity
@Table(name = "reservation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r")
    , @NamedQuery(name = "Reservation.findById", query = "SELECT r FROM Reservation r WHERE r.id = :id")
    , @NamedQuery(name = "Reservation.findByReservationdate", query = "SELECT r FROM Reservation r WHERE r.reservationdate = :reservationdate")
    , @NamedQuery(name = "Reservation.findByArrival", query = "SELECT r FROM Reservation r WHERE r.arrival = :arrival")
    , @NamedQuery(name = "Reservation.findByDeparture", query = "SELECT r FROM Reservation r WHERE r.departure = :departure")
    , @NamedQuery(name = "Reservation.findByAdultscount", query = "SELECT r FROM Reservation r WHERE r.adultscount = :adultscount")
    , @NamedQuery(name = "Reservation.findByChildcount", query = "SELECT r FROM Reservation r WHERE r.childcount = :childcount")
    , @NamedQuery(name = "Reservation.findByNights", query = "SELECT r FROM Reservation r WHERE r.nights = :nights")

     //to get last reservation id
    , @NamedQuery(name = "Reservation.getReservationId", query = "SELECT r FROM Reservation r WHERE r.id = ( SELECT MAX(r.id) FROM Reservation r )"),
    
})
public class Reservation implements Serializable {

    @Size(max = 45)
    @Column(name = "comments")
    private String comments;

    @JoinColumn(name = "customerpackage_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Customerpackage customerpackageId;

    @JoinColumn(name = "reservationstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Reservationstatus reservationstatusId;

    @JoinColumn(name = "extrabed_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Extrabed extrabedId;

    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer customerId;

    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Room roomId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "reservationdate")
    @Temporal(TemporalType.DATE)
    private Date reservationdate;
    @Column(name = "arrival")
    @Temporal(TemporalType.DATE)
    private Date arrival;
    @Column(name = "departure")
    @Temporal(TemporalType.DATE)
    private Date departure;
    @Column(name = "adultscount")
    private Integer adultscount;
    @Column(name = "childcount")
    private Integer childcount;
    @Column(name = "nights")
    private Integer nights;

    public Reservation() {
    }

    public Reservation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReservationdate() {
        return reservationdate;
    }

    public void setReservationdate(Date reservationdate) {
        this.reservationdate = reservationdate;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public Integer getAdultscount() {
        return adultscount;
    }

    public void setAdultscount(Integer adultscount) {
        this.adultscount = adultscount;
    }

    public Integer getChildcount() {
        return childcount;
    }

    public void setChildcount(Integer childcount) {
        this.childcount = childcount;
    }

    public Integer getNights() {
        return nights;
    }

    public boolean  setNights(Integer nights) {
         boolean validity = nights != null;
        
        if(validity){
            
            this.nights = nights; 
            
        } else { 
            
            this.nights = null; 
        
        } 
        
        return validity; 
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
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Reservation[ id=" + id + " ]";
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Extrabed getExtrabedId() {
        return extrabedId;
    }

    public void setExtrabedId(Extrabed extrabedId) {
        this.extrabedId = extrabedId;
    }

    public Reservationstatus getReservationstatusId() {
        return reservationstatusId;
    }

    public void setReservationstatusId(Reservationstatus reservationstatusId) {
        this.reservationstatusId = reservationstatusId;
    }

    public Customerpackage getCustomerpackageId() {
        return customerpackageId;
    }

    public void setCustomerpackageId(Customerpackage customerpackageId) {
        this.customerpackageId = customerpackageId;
    }

    public String getComments() {
        return comments;
    }

    public boolean  setComments(String comments) {
               boolean validity = comments != null && comments.matches("^[_A-Za-z0-9-/.:,\\s]{5}[_A-Za-z0-9-/.:,\\s]*"); 
        
        if(validity){
            
            this.comments = comments; 
        
        } else { 
            
            this.comments=null;
        
        } 
        
        return validity;
    }
    
}
