/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tharana
 */
@Entity
@Table(name = "customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
    , @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id")
    , @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name")
    , @NamedQuery(name = "Customer.findByDobirth", query = "SELECT c FROM Customer c WHERE c.dobirth = :dobirth")
    , @NamedQuery(name = "Customer.findByIdno", query = "SELECT c FROM Customer c WHERE c.idno = :idno")
    , @NamedQuery(name = "Customer.findByMobile", query = "SELECT c FROM Customer c WHERE c.mobile = :mobile")
    , @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email")
    , @NamedQuery(name = "Customer.findByAssigned", query = "SELECT c FROM Customer c WHERE c.assigned = :assigned")


    //user define

    // get last id
    , @NamedQuery(name = "Customer.getCustomerId", query = "SELECT c FROM Customer c WHERE c.id = ( SELECT MAX(c.id) FROM Customer c )")
    
    //Serach Querys
    
    , @NamedQuery(name = "Customer.findAllByName", query = "SELECT c FROM Customer c WHERE c.name LIKE :name  AND c.disable = 0")
    , @NamedQuery(name = "Customer.findAllById", query = "SELECT c FROM Customer c WHERE c.idno LIKE :idno AND c.disable = 0")
    , @NamedQuery(name = "Customer.findAllByCustomerType", query = "SELECT c FROM Customer c WHERE c.customertypeId = :customertype AND c.disable = 0 ")
    , @NamedQuery(name = "Customer.findAllByNameId", query = "SELECT c FROM Customer c WHERE c.idno LIKE :idno AND c.name LIKE :name AND c.disable = 0")
    , @NamedQuery(name = "Customer.findAllByNameCustomerType", query = "SELECT c FROM Customer c WHERE c.customertypeId = :customertype AND c.name LIKE :name AND c.disable = 0 ")
    , @NamedQuery(name = "Customer.findAllByIdCustomerType", query = "SELECT c FROM Customer c WHERE c.idno LIKE :idno AND c.customertypeId = :customertype AND c.disable = 0 ")
    , @NamedQuery(name = "Customer.findAllByNameIdCustomerType", query = "SELECT c FROM Customer c WHERE c.idno LIKE :idno AND c.customertypeId = :customertype AND c.name LIKE :name AND c.disable = 0 ")
        
        
})
public class Customer implements Serializable {

    @Column(name = "disable")
    private Integer disable;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "address")
    private String address;
    @Column(name = "dobirth")
    @Temporal(TemporalType.DATE)
    private Date dobirth;
    @Size(max = 45)
    @Column(name = "idno")
    private String idno;
    @Size(max = 12)
    @Column(name = "mobile")
    private String mobile;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @Column(name = "assigned")
    @Temporal(TemporalType.DATE)
    private Date assigned;
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Country countryId;
    @JoinColumn(name = "customertype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Customertype customertypeId;
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Gender genderId;
    @JoinColumn(name = "idtype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Idtype idtypeId;

    public Customer() {
    }

    public Customer(Integer id) {
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
        boolean validity = name != null && name.matches("[A-Z][_A-Za-z. ]{3}[_A-Za-z]*([_A-Za-z]*\\s[_A-Za-z]*)*"); 
        
        if(validity){
            
            this.name = name; 
            
        } else { 
            
            this.name=null;
        
        } 
        
        return validity; 
    }

    public String getAddress() {
        return address;
    }

    public boolean setAddress(String address) {
         boolean validity = address != null && address.matches("^[_A-Za-z0-9-/.:,\\s]{5}[_A-Za-z0-9-/.:,\\s]*"); 
        
        if(validity){
            
            this.address = address; 
        
        } else { 
            
            this.address=null;
        
        } 
        
        return validity;
    }

    public Date getDobirth() {
        return dobirth;
    }

    public boolean  setDobirth(Date dobirth) {
        this.dobirth=dobirth; 
        return dobirth != null;
    }

    public String getIdno() {
        return idno;
    }

    public boolean  setIdno(String idno) {
          boolean validity = idno != null && idno.matches("\\d{9}[V|v|x|X]");
        if (validity) {
            this.idno = idno;
        } else {
            this.idno = null;
        }
        return validity;
    }
    public String getMobile() {
        return mobile;
    }

    public boolean  setMobile(String mobile) {
         boolean validity = mobile != null && ( mobile.matches("07[0|1|2|5|6|7|8]\\d{7}") || mobile.matches("\\+947[0|1|2|5|6|7|8]\\d{7}") ); 
        
        if(validity){
            
            this.mobile = mobile; 
            
        } else { 
            
            this.mobile=null;
        
        } 
        
        return validity; 
    }

    public String getEmail() {
        return email;
    }

    public boolean  setEmail(String email) {
         boolean validity;

        if (email == null || email.isEmpty()) {

            validity = true;
            this.email = null;

        } else {

            email = email.trim();

            if (email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {

                this.email = email;
                validity = true;

            } else {

                this.email = null;
                validity = false;

            }

        }

        return validity;
    }

    public Date getAssigned() {
        return assigned;
    }

    public void setAssigned(Date assigned) {
        this.assigned = assigned;
    }

    public Country getCountryId() {
        return countryId;
    }

    public void setCountryId(Country countryId) {
        this.countryId = countryId;
    }

    public Customertype getCustomertypeId() {
        return customertypeId;
    }

    public void setCustomertypeId(Customertype customertypeId) {
        this.customertypeId = customertypeId;
    }

    public Gender getGenderId() {
        return genderId;
    }

    public void setGenderId(Gender genderId) {
        this.genderId = genderId;
    }

    public Idtype getIdtypeId() {
        return idtypeId;
    }

    public void setIdtypeId(Idtype idtypeId) {
        this.idtypeId = idtypeId;
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
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + id + " ]";
    }

    public Integer getDisable() {
        return disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

   
    
}
