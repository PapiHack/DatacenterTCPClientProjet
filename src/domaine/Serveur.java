package domaine;

import java.io.Serializable;

/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 *  
 */
@SuppressWarnings("serial")
public class Serveur implements Serializable
{
	private String numServ;
	private String nomServ;
	private Admin admin;
	private Salle salle;
	
	public Serveur(String num, String nom, Admin admin, Salle salle) 
	{
		this.setNumServ(num);
		this.setNomServ(nom);
		this.setAdmin(admin);
		this.setSalle(salle);
	}
	
	public String getNumServ() 
	{
		return this.numServ;
	}
	
	public void setNumServ(String numServ) 
	{
		this.numServ = numServ;
	}
	
	public String getNomServ() 
	{
		return this.nomServ;
	}
	
	public void setNomServ(String nomServ) 
	{
		this.nomServ = nomServ;
	}
	
	public Admin getAdmin() 
	{
		return this.admin;
	}
	
	public void setAdmin(Admin admin) 
	{
		this.admin = admin;
	}
	
	public Salle getSalle() 
	{
		return this.salle;
	}
	
	public void setSalle(Salle salle) 
	{
		this.salle = salle;
	}
}
