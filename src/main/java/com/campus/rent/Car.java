package com.campus.rent;

//la classe des voitures
public class Car {
	private int id;
	private String marque;
	private String modele;
	private String couleur;
	
	/**
	 * @param id
	 * @param marque
	 * @param modele
	 * @param couleur
	 */
	public Car(int id, String marque, String modele, String couleur) {
		this.id = id;
		this.marque = marque;
		this.modele = modele;
		this.couleur = couleur;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the marque
	 */
	public String getMarque() {
		return marque;
	}

	/**
	 * @param marque the marque to set
	 */
	public void setMarque(String marque) {
		this.marque = marque;
	}

	/**
	 * @return the modele
	 */
	public String getModele() {
		return modele;
	}
	
	/**
	 * @param modele the modele to set
	 */
	public void setModele(String modele) {
		this.modele = modele;
	}

	/**
	 * @return the couleur
	 */
	public String getCouleur() {
		return couleur;
	}

	/**
	 * @param couleur the couleur to set
	 */
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", marque=" + marque + ", modele=" + modele + ", couleur=" + couleur + "]";
	}
	
	

}
