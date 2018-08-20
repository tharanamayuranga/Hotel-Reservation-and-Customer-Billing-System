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
@Table(name = "fooditemcategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fooditemcategory.findAll", query = "SELECT f FROM Fooditemcategory f")
    , @NamedQuery(name = "Fooditemcategory.findById", query = "SELECT f FROM Fooditemcategory f WHERE f.id = :id")
    , @NamedQuery(name = "Fooditemcategory.findByName", query = "SELECT f FROM Fooditemcategory f WHERE f.name = :name")})
public class Fooditemcategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fooditemcategoryId", fetch = FetchType.LAZY)
    private List<Fooditem> fooditemList;

    public Fooditemcategory() {
    }

    public Fooditemcategory(Integer id) {
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

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Fooditem> getFooditemList() {
        return fooditemList;
    }

    public void setFooditemList(List<Fooditem> fooditemList) {
        this.fooditemList = fooditemList;
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
        if (!(object instanceof Fooditemcategory)) {
            return false;
        }
        Fooditemcategory other = (Fooditemcategory) object;
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
