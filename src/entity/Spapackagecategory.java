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
@Table(name = "spapackagecategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spapackagecategory.findAll", query = "SELECT s FROM Spapackagecategory s")
    , @NamedQuery(name = "Spapackagecategory.findById", query = "SELECT s FROM Spapackagecategory s WHERE s.id = :id")
    , @NamedQuery(name = "Spapackagecategory.findByName", query = "SELECT s FROM Spapackagecategory s WHERE s.name = :name")})
public class Spapackagecategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spapackagecategoryId", fetch = FetchType.EAGER)
    private List<Spapackage> spapackageList;

    public Spapackagecategory() {
    }

    public Spapackagecategory(Integer id) {
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
    public List<Spapackage> getSpapackageList() {
        return spapackageList;
    }

    public void setSpapackageList(List<Spapackage> spapackageList) {
        this.spapackageList = spapackageList;
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
        if (!(object instanceof Spapackagecategory)) {
            return false;
        }
        Spapackagecategory other = (Spapackagecategory) object;
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
