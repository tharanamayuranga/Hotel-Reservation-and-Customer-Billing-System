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
@Table(name = "fooditem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fooditem.findAll", query = "SELECT f FROM Fooditem f")
    , @NamedQuery(name = "Fooditem.findById", query = "SELECT f FROM Fooditem f WHERE f.id = :id")
    , @NamedQuery(name = "Fooditem.findByCode", query = "SELECT f FROM Fooditem f WHERE f.code = :code")
    , @NamedQuery(name = "Fooditem.findByName", query = "SELECT f FROM Fooditem f WHERE f.name = :name")
    , @NamedQuery(name = "Fooditem.findByUnitprice", query = "SELECT f FROM Fooditem f WHERE f.unitprice = :unitprice")


    
})
public class Fooditem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 5)
    @Column(name = "code")
    private String code;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "unitprice")
    private BigDecimal unitprice;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "fooditemcategory_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Fooditemcategory fooditemcategoryId;

    public Fooditem() {
    }

    public Fooditem(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public boolean  setCode(String code) {
          boolean validity = code != null && code.matches("^[_A-Za-z0-9-/.:,\\s]{5}"); 
        
        if(validity){
            
            this.code = code; 
            
        } else { 
            
            this.code = null;
        
        } 
        
        return validity;
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

    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public boolean  setUnitprice(BigDecimal unitprice) {
            boolean validity=unitprice.compareTo(new BigDecimal("0.0"))>0;
        
       
        
        if(validity){
            
            this.unitprice = unitprice; 
        
        } else {
            
            this.unitprice = null; 
        
        } 
        
        return validity; 
    }

    public String getDescription() {
        return description;
    }

    public boolean setDescription(String description) {
         boolean validity = description != null && description.matches("[A-Z][_A-Za-z. ]{3}[_A-Za-z]*([_A-Za-z]*\\s[_A-Za-z]*)*"); 
        
        if(validity){
            
            this.description = description; 
            
        } else { 
            
            this.description = null;
        
        } 
        
        return validity;
    }

    public Fooditemcategory getFooditemcategoryId() {
        return fooditemcategoryId;
    }

    public void setFooditemcategoryId(Fooditemcategory fooditemcategoryId) {
        this.fooditemcategoryId = fooditemcategoryId;
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
        if (!(object instanceof Fooditem)) {
            return false;
        }
        Fooditem other = (Fooditem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Fooditem[ id=" + id + " ]";
    }
    
}
