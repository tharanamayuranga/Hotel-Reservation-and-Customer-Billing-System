/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tharana
 */
@Entity
@Table(name = "roomtype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roomtype.findAll", query = "SELECT r FROM Roomtype r")
    , @NamedQuery(name = "Roomtype.findById", query = "SELECT r FROM Roomtype r WHERE r.id = :id")
    , @NamedQuery(name = "Roomtype.findByName", query = "SELECT r FROM Roomtype r WHERE r.name = :name")
    , @NamedQuery(name = "Roomtype.findByArea", query = "SELECT r FROM Roomtype r WHERE r.area = :area")
    , @NamedQuery(name = "Roomtype.findByUnitprice", query = "SELECT r FROM Roomtype r WHERE r.unitprice = :unitprice")
    , @NamedQuery(name = "Roomtype.findByFun1", query = "SELECT r FROM Roomtype r WHERE r.fun1 = :fun1")
    , @NamedQuery(name = "Roomtype.findByFun2", query = "SELECT r FROM Roomtype r WHERE r.fun2 = :fun2")
    , @NamedQuery(name = "Roomtype.findByFun3", query = "SELECT r FROM Roomtype r WHERE r.fun3 = :fun3")
    , @NamedQuery(name = "Roomtype.findByFun4", query = "SELECT r FROM Roomtype r WHERE r.fun4 = :fun4")
    , @NamedQuery(name = "Roomtype.findByFun5", query = "SELECT r FROM Roomtype r WHERE r.fun5 = :fun5")
    , @NamedQuery(name = "Roomtype.findByFun6", query = "SELECT r FROM Roomtype r WHERE r.fun6 = :fun6")
    , @NamedQuery(name = "Roomtype.findByFun7", query = "SELECT r FROM Roomtype r WHERE r.fun7 = :fun7")
    , @NamedQuery(name = "Roomtype.findByFun8", query = "SELECT r FROM Roomtype r WHERE r.fun8 = :fun8")
    , @NamedQuery(name = "Roomtype.findByFun9", query = "SELECT r FROM Roomtype r WHERE r.fun9 = :fun9")
    , @NamedQuery(name = "Roomtype.findByFun10", query = "SELECT r FROM Roomtype r WHERE r.fun10 = :fun10")


    //Serach Querys
    
    ,  @NamedQuery(name = "Roomtype.findAllByName", query = "SELECT r FROM Roomtype r WHERE r.name LIKE :name ")
})
public class Roomtype implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "area")
    private BigDecimal area;
    @Column(name = "unitprice")
    private BigDecimal unitprice;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Column(name = "fun1")
    private Integer fun1;
    @Column(name = "fun2")
    private Integer fun2;
    @Column(name = "fun3")
    private Integer fun3;
    @Column(name = "fun4")
    private Integer fun4;
    @Column(name = "fun5")
    private Integer fun5;
    @Column(name = "fun6")
    private Integer fun6;
    @Column(name = "fun7")
    private Integer fun7;
    @Column(name = "fun8")
    private Integer fun8;
    @Column(name = "fun9")
    private Integer fun9;
    @Column(name = "fun10")
    private Integer fun10;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomtypeId", fetch = FetchType.EAGER)
    private List<Room> roomList;

    public Roomtype() {
    }

    public Roomtype(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean  setName(String name) {
         boolean validity = name != null && name.matches("^[_A-Za-z0-9-/.:,\\s]{5}[_A-Za-z0-9-/.:,\\s]*"); 
        
        if(validity){
            
            this.name = name; 
            
        } else { 
            
            this.name = null;
        
        } 
        
        return validity;
    }

    public BigDecimal getArea() {
        return area;
    }

    public boolean  setArea(BigDecimal area) {
         boolean validity=area.compareTo(new BigDecimal("0.0"))>0;
        
        if (validity) {
            this.area=area;
        }else{
        this.area=area;
        }
        return validity;
    }

    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    public String getDescription() {
        return description;
    }

    public boolean  setDescription(String description) {
         boolean validity = description != null && description.matches("[A-Z][_A-Za-z. ]{3}[_A-Za-z]*([_A-Za-z]*\\s[_A-Za-z]*)*"); 
        
        if(validity){
            
            this.description = description; 
            
        } else { 
            
            this.description = null;
        
        } 
        
        return validity;
    }

    public Integer getFun1() {
        return fun1;
    }

    public void setFun1(Integer fun1) {
        this.fun1 = fun1;
    }

    public Integer getFun2() {
        return fun2;
    }

    public void setFun2(Integer fun2) {
        this.fun2 = fun2;
    }

    public Integer getFun3() {
        return fun3;
    }

    public void setFun3(Integer fun3) {
        this.fun3 = fun3;
    }

    public Integer getFun4() {
        return fun4;
    }

    public void setFun4(Integer fun4) {
        this.fun4 = fun4;
    }

    public Integer getFun5() {
        return fun5;
    }

    public void setFun5(Integer fun5) {
        this.fun5 = fun5;
    }

    public Integer getFun6() {
        return fun6;
    }

    public void setFun6(Integer fun6) {
        this.fun6 = fun6;
    }

    public Integer getFun7() {
        return fun7;
    }

    public void setFun7(Integer fun7) {
        this.fun7 = fun7;
    }

    public Integer getFun8() {
        return fun8;
    }

    public void setFun8(Integer fun8) {
        this.fun8 = fun8;
    }

    public Integer getFun9() {
        return fun9;
    }

    public void setFun9(Integer fun9) {
        this.fun9 = fun9;
    }

    public Integer getFun10() {
        return fun10;
    }

    public void setFun10(Integer fun10) {
        this.fun10 = fun10;
    }

    @XmlTransient
    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
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
        if (!(object instanceof Roomtype)) {
            return false;
        }
        Roomtype other = (Roomtype) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
