/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
    , @NamedQuery(name = "Reservation.findBySubtotal", query = "SELECT r FROM Reservation r WHERE r.subtotal = :subtotal")
    , @NamedQuery(name = "Reservation.findByVatpresentage", query = "SELECT r FROM Reservation r WHERE r.vatpresentage = :vatpresentage")
    , @NamedQuery(name = "Reservation.findByDispresentage", query = "SELECT r FROM Reservation r WHERE r.dispresentage = :dispresentage")
    , @NamedQuery(name = "Reservation.findByVatamount", query = "SELECT r FROM Reservation r WHERE r.vatamount = :vatamount")
    , @NamedQuery(name = "Reservation.findByDisamaount", query = "SELECT r FROM Reservation r WHERE r.disamaount = :disamaount")
    , @NamedQuery(name = "Reservation.findByServicechargepresentage", query = "SELECT r FROM Reservation r WHERE r.servicechargepresentage = :servicechargepresentage")
    , @NamedQuery(name = "Reservation.findByServicechargeamount", query = "SELECT r FROM Reservation r WHERE r.servicechargeamount = :servicechargeamount")

    
     // get last id
    
    , @NamedQuery(name = "Reservation.getReservationId", query = "SELECT r FROM Reservation r WHERE r.id = ( SELECT MAX(r.id) FROM Reservation r )"),
    
    
    

})
public class Reservation implements Serializable {

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
    @Size(max = 45)
    @Column(name = "nights")
    private String nights;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Column(name = "vatpresentage")
    private Integer vatpresentage;
    @Column(name = "dispresentage")
    private Integer dispresentage;
    @Column(name = "vatamount")
    private BigDecimal vatamount;
    @Column(name = "disamaount")
    private BigDecimal disamaount;
    @Column(name = "servicechargepresentage")
    private Integer servicechargepresentage;
    @Column(name = "servicechargeamount")
    private BigDecimal servicechargeamount;
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Customer customerId;
    @JoinColumn(name = "extrabed_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Extrabed extrabedId;
    @JoinColumn(name = "package_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Package packageId;
    @JoinColumn(name = "reservationstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Reservationstatus reservationstatusId;
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Room roomId;

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

    public String getNights() {
        return nights;
    }

    public void setNights(String nights) {
        this.nights = nights;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getVatpresentage() {
        return vatpresentage;
    }

    public void setVatpresentage(Integer vatpresentage) {
        this.vatpresentage = vatpresentage;
    }

    public Integer getDispresentage() {
        return dispresentage;
    }

    public void setDispresentage(Integer dispresentage) {
        this.dispresentage = dispresentage;
    }

    public BigDecimal getVatamount() {
        return vatamount;
    }

    public void setVatamount(BigDecimal vatamount) {
        this.vatamount = vatamount;
    }

    public BigDecimal getDisamaount() {
        return disamaount;
    }

    public void setDisamaount(BigDecimal disamaount) {
        this.disamaount = disamaount;
    }

    public Integer getServicechargepresentage() {
        return servicechargepresentage;
    }

    public void setServicechargepresentage(Integer servicechargepresentage) {
        this.servicechargepresentage = servicechargepresentage;
    }

    public BigDecimal getServicechargeamount() {
        return servicechargeamount;
    }

    public void setServicechargeamount(BigDecimal servicechargeamount) {
        this.servicechargeamount = servicechargeamount;
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

    public Package getPackageId() {
        return packageId;
    }

    public void setPackageId(Package packageId) {
        this.packageId = packageId;
    }

    public Reservationstatus getReservationstatusId() {
        return reservationstatusId;
    }

    public void setReservationstatusId(Reservationstatus reservationstatusId) {
        this.reservationstatusId = reservationstatusId;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
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
    
}
