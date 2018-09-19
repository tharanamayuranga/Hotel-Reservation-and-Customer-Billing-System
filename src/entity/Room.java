/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tharana
 */
@Entity
@Table(name = "room")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r")
    , @NamedQuery(name = "Room.findById", query = "SELECT r FROM Room r WHERE r.id = :id")
    , @NamedQuery(name = "Room.findByNo", query = "SELECT r FROM Room r WHERE r.no = :no")


    //Serach Querys
    
    , @NamedQuery(name = "Room.findAllByNo", query = "SELECT r FROM Room r WHERE r.no LIKE :no "),
    @NamedQuery(name = "Room.findAllByType", query = "SELECT r FROM Room r WHERE r.roomtypeId = :type "),
    @NamedQuery(name = "Room.findAllByStatus", query = "SELECT r FROM Room r WHERE r.roomstatusId = :status"),
    @NamedQuery(name = "Room.findAllByNoStatus", query = "SELECT r FROM Room r WHERE r.roomstatusId = :status AND r.no LIKE :no "),
    @NamedQuery(name = "Room.findAllByNoType", query = "SELECT r FROM Room r WHERE r.roomtypeId = :type AND r.no LIKE :no "),
    @NamedQuery(name = "Room.findAllByStatusType", query = "SELECT r FROM Room r WHERE r.roomstatusId = :status AND r.roomtypeId = :type "),
    @NamedQuery(name = "Room.findAllByNoStatusType", query = "SELECT r FROM Room r WHERE r.roomstatusId = :status AND r.roomtypeId = :type AND r.no LIKE :no"),
     


})
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 3)
    @Column(name = "no")
    private String no;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "floor_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Floor floorId;
    @JoinColumn(name = "roomstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Roomstatus roomstatusId;
    @JoinColumn(name = "roomtype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Roomtype roomtypeId;

    public Room() {
    }

    public Room(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public boolean  setNo(String no) {
         boolean validity = no != null ; 
        
        if(validity){
            
            this.no = no; 
            
        } else { 
            
            this.no = null;
        
        } 
        
        return validity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Floor getFloorId() {
        return floorId;
    }

    public void setFloorId(Floor floorId) {
        this.floorId = floorId;
    }

    public Roomstatus getRoomstatusId() {
        return roomstatusId;
    }

    public void setRoomstatusId(Roomstatus roomstatusId) {
        this.roomstatusId = roomstatusId;
    }

    public Roomtype getRoomtypeId() {
        return roomtypeId;
    }

    public void setRoomtypeId(Roomtype roomtypeId) {
        this.roomtypeId = roomtypeId;
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
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Room[ id=" + id + " ]";
    }
    
}
