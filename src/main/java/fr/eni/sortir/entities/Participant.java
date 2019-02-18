package fr.eni.sortir.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PARTICIPANTS")
public class Participant implements Serializable {
	@Id
	@GeneratedValue
	@Column(name="no_participant")
	private Integer noParticipant;
	private String pseudo;
	private String nom;
	private String prenom;
	private String telephone;
	private String mail;
	@Column(name="mot_de_passe")
	private String motDePasse;
	private Boolean administrateur;
	private Boolean actif;
	@OneToMany(mappedBy = "primaryKey.participant")
	private Collection<Inscription> inscriptions = new ArrayList<>();
	
	public Participant() {
		super();
	}

	public Participant(String pseudo, String nom, String prenom, String telephone, String mail, String motDePasse,
			Boolean administrateur, Boolean actif, Collection<Inscription> inscriptions) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.mail = mail;
		this.motDePasse = motDePasse;
		this.administrateur = administrateur;
		this.actif = actif;
		this.inscriptions = inscriptions;
	}

	public Integer getNoParticipant() {
		return noParticipant;
	}

	public void setNoParticipant(Integer noParticipant) {
		this.noParticipant = noParticipant;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public Boolean getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Boolean administrateur) {
		this.administrateur = administrateur;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public Collection<Inscription> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(Collection<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}
	
	public void addInscription(Inscription inscription) {
		this.inscriptions.add(inscription);
	}
	
	public void removeInscription(Inscription inscription) {
		this.inscriptions.remove(inscription);
	}
}
