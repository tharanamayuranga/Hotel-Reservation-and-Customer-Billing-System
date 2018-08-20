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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import util.Security;

/**
 *
 * @author Tharana
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u WHERE u.disable = 0")
    , @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id")
    , @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByHint", query = "SELECT u FROM User u WHERE u.hint = :hint")
    , @NamedQuery(name = "User.findByDisable", query = "SELECT u FROM User u WHERE u.disable = :disable")
     

     //get unassigned usernames
    , @NamedQuery(name = "User.findUnassignedUsername", query = "SELECT u FROM User u WHERE u.username = :username AND u.disable = 0")
    
      

})
    
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "username")
    private String username;
    @Size(max = 45)
    @Column(name = "password")
    private String password;
    @Size(max = 45)
    @Column(name = "hint")
    private String hint;
    @Column(name = "disable")
    private Integer disable;

//    @ManyToMany(mappedBy = "userList", fetch = FetchType.LAZY)
//    private List<Role> roleList;
    
    
    
    
    @JoinTable(name = "userrole", joinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "role_id", referencedColumnName = "id")})
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//newely added (cascade = CascadeType.ALL) and(@Fetch(FetchMode.SELECT)) for fill form  problem
    @Fetch(FetchMode.SELECT)

    private List<Role> roleList;
    
    
    


    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public boolean  setPassword(String password) {
        boolean validity = password != null && password.matches("[A-Za-z0-9]{4}[A-Za-z0-9]+"); 
        
        if(validity){
            
            this.password = Security.encrypt(password);  // to de encrypted...
        
        } else {
            
            this.password = null; 
        
        } 
        
        return validity; 
    }

    public String getHint() {
        return hint;
    }

    public boolean  setHint(String hint) {
        
        boolean validity;

        if (hint == null || hint.isEmpty()) {

            validity = true;
            this.hint = null;

        } else {

            hint = hint.trim();

            if (hint.matches("[A-Za-z0-9]{1}[A-Za-z0-9]+")) {

                this.hint = hint;
                validity = true;

            } else {

                this.hint = null;
                validity = false;

            }

        }

        return validity;
    }

    public Integer getDisable() {
        return disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    @XmlTransient
    public List<Role> getRoleList() {
        return roleList;
    }

    public boolean  setRoleList(List<Role> roleList) {// can't understand
        boolean validity = roleList != null && !roleList.isEmpty();
        
        if (validity) {
            
            this.roleList = roleList;
            
        } else {
            
            this.roleList = null;
            
        }
        
        return validity;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public boolean  setEmployeeId(Employee employeeId) {
        boolean validity = employeeId != null;
        
        if (validity) {
            
            this.employeeId = employeeId;
            
        } else {
            
            this.employeeId = null;
            
        }
        
        return validity;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.User[ id=" + id + " ]";
    }
    
}
