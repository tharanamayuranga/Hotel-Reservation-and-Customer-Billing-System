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
@Table(name = "idtype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Idtype.findAll", query = "SELECT i FROM Idtype i")
    , @NamedQuery(name = "Idtype.findById", query = "SELECT i FROM Idtype i WHERE i.id = :id")
    , @NamedQuery(name = "Idtype.findByName", query = "SELECT i FROM Idtype i WHERE i.name = :name")
        
    //Binding 
    , @NamedQuery(name = "Idtype.findAllByName", query = "SELECT i FROM Idtype i WHERE i.name = :name"),
    
})
public class Idtype implements Serializable {

    @Size(max = 45)
    @Column(name = "name")
    private String name;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtypeId", fetch = FetchType.EAGER)
    private List<Customer> customerList;

    public Idtype() {
    }

    public Idtype(Integer id) {
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
    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
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
        if (!(object instanceof Idtype)) {
            return false;
        }
        Idtype other = (Idtype) object;
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
