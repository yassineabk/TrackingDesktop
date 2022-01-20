package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity

public class User implements Serializable {

	private static final long serialVersionUID = 1L;  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUser;
	private String nom;
	private String prenom;
	private String email;
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;
	private String telephone;
	@OneToMany( targetEntity=Smartphone.class, mappedBy="user" )
    private List<Smartphone> phones = new ArrayList<>();

	public User() {
		super();
	}   
	
	public User(int idUser, String nom, String prenom, String email, Date dateNaissance, String telephone) {
		super();
		this.idUser = idUser;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.dateNaissance = dateNaissance;
		this.telephone = telephone;
	}

	public User(String nom, String prenom, String email, Date dateNaissance, String telephone) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.dateNaissance = dateNaissance;
		this.telephone = telephone;
	}

	public int getId() {
		return this.idUser;
	}

	public void setId(int id) {
		this.idUser = id;
	}   
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}   
	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}   
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public Date getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}   
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	
	public List<Smartphone> getPhones() {
		return phones;
	}

	@Override
	public String toString() {
		return "User [id=" + idUser + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", dateNaissance="
				+ dateNaissance + ", telephone=" + telephone + "]";
	}
	
   
}
