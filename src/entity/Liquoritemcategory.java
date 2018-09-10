/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "liquoritemcategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Liquoritemcategory.findAll", query = "SELECT l FROM Liquoritemcategory l")
    , @NamedQuery(name = "Liquoritemcategory.findById", query = "SELECT l FROM Liquoritemcategory l WHERE l.id = :id")
    , @NamedQuery(name = "Liquoritemcategory.findByName", query = "SELECT l FROM Liquoritemcategory l WHERE l.name = :name")

//Binding 
    , @NamedQuery(name = "Liquoritemcategory.findAllByName", query = "SELECT l FROM Liquoritemcategory l WHERE l.name = :name")

})
public class Liquoritemcategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "liquoritemcategoryId", fetch = FetchType.EAGER)
    private List<Liquoritem> liquoritemList;

    public Liquoritemcategory() {
    }

    public Liquoritemcategory(Integer id) {
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
         boolean validity = name != null && name.matches("[A-Z][_A-Za-z]{1}[_A-Za-z]*([_A-Za-z]*\\s[_A-Za-z]*)*"); 
        
        if(validity){
            
            this.name = name; 
            
        } else { 
            
            this.name=null;
        
        } 
        
        return validity;
    }

    @XmlTransient
    public List<Liquoritem> getLiquoritemList() {
        return liquoritemList;
    }

    public void setLiquoritemList(List<Liquoritem> liquoritemList) {
        this.liquoritemList = liquoritemList;
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
        if (!(object instanceof Liquoritemcategory)) {
            return false;
        }
        Liquoritemcategory other = (Liquoritemcategory) object;
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
