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
public class Salle implements Serializable
{
	private String numSalle;
	private String nomSalle;
	
	public Salle(String num, String nom) 
	{
		this.setNumSalle(num);
		this.setNomSalle(nom);
	}
	
	public String getNumSalle() 
	{
		return this.numSalle;
	}
	
	public void setNumSalle(String numSalle) 
	{
		this.numSalle = numSalle;
	}
	
	public String getNomSalle() 
	{
		return this.nomSalle;
	}
	
	public void setNomSalle(String nomSalle) 
	{
		this.nomSalle = nomSalle;
	}
	
	public String toString() 
	{
		return this.nomSalle + " (N°" + this.numSalle + ")";
	}
}
