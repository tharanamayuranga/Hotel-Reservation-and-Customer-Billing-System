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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tharana
 */
@Entity
@Table(name = "liquoritem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Liquoritem.findAll", query = "SELECT l FROM Liquoritem l")
    , @NamedQuery(name = "Liquoritem.findById", query = "SELECT l FROM Liquoritem l WHERE l.id = :id")
    , @NamedQuery(name = "Liquoritem.findByCode", query = "SELECT l FROM Liquoritem l WHERE l.code = :code")
    , @NamedQuery(name = "Liquoritem.findByName", query = "SELECT l FROM Liquoritem l WHERE l.name = :name")
    , @NamedQuery(name = "Liquoritem.findByUnitprice", query = "SELECT l FROM Liquoritem l WHERE l.unitprice = :unitprice")
    , @NamedQuery(name = "Liquoritem.findByDescription", query = "SELECT l FROM Liquoritem l WHERE l.description = :description")

 
})
public class Liquoritem implements Serializable {

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
    @Size(max = 45)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "liquoritemcategory_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Liquoritemcategory liquoritemcategoryId;

    public Liquoritem() {
    }

    public Liquoritem(Integer id) {
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

    public Liquoritemcategory getLiquoritemcategoryId() {
        return liquoritemcategoryId;
    }

    public void setLiquoritemcategoryId(Liquoritemcategory liquoritemcategoryId) {
        this.liquoritemcategoryId = liquoritemcategoryId;
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
        if (!(object instanceof Liquoritem)) {
            return false;
        }
        Liquoritem other = (Liquoritem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Liquoritem[ id=" + id + " ]";
    }
    
}
