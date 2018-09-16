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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tharana
 */
@Entity
@Table(name = "privilege")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Privilege.findAll", query = "SELECT p FROM Privilege p")
    , @NamedQuery(name = "Privilege.findById", query = "SELECT p FROM Privilege p WHERE p.id = :id")
    , @NamedQuery(name = "Privilege.findBySel", query = "SELECT p FROM Privilege p WHERE p.sel = :sel")
    , @NamedQuery(name = "Privilege.findByIns", query = "SELECT p FROM Privilege p WHERE p.ins = :ins")
    , @NamedQuery(name = "Privilege.findByUpd", query = "SELECT p FROM Privilege p WHERE p.upd = :upd")
    , @NamedQuery(name = "Privilege.findByDel", query = "SELECT p FROM Privilege p WHERE p.del = :del")


    //For Search methods..
    , @NamedQuery(name = "Privilege.findAllByRole", query = "SELECT p FROM Privilege p WHERE p.roleId = :role")
    , @NamedQuery(name = "Privilege.findAllByModule", query = "SELECT p FROM Privilege p WHERE p.moduleId = :module")
    , @NamedQuery(name = "Privilege.findAllByRoleModule", query = "SELECT p FROM Privilege p WHERE p.roleId = :role AND p.moduleId = :module")
        
})
public class Privilege implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "sel")
    private Integer sel;
    @Column(name = "ins")
    private Integer ins;
    @Column(name = "upd")
    private Integer upd;
    @Column(name = "del")
    private Integer del;
    @JoinColumn(name = "module_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Module moduleId;
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Role roleId;

    public Privilege() {
    }

    public Privilege(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSel() {
        return sel;
    }

    public void setSel(Integer sel) {
        this.sel = sel;
    }

    public Integer getIns() {
        return ins;
    }

    public void setIns(Integer ins) {
        this.ins = ins;
    }

    public Integer getUpd() {
        return upd;
    }

    public void setUpd(Integer upd) {
        this.upd = upd;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public Module getModuleId() {
        return moduleId;
    }

    public boolean  setModuleId(Module moduleId) {
        this.moduleId= moduleId;
       
        return moduleId!= null; 
    }

    public Role getRoleId() {
        return roleId;
    }

    public boolean  setRoleId(Role roleId) {
        this.roleId= roleId;
       
       return roleId!= null; 
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
        if (!(object instanceof Privilege)) {
            return false;
        }
        Privilege other = (Privilege) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Privilege[ id=" + id + " ]";
    }
    
}
