package presentation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

import domaine.*;

/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 *  
 */
@SuppressWarnings("serial")
public class GestionServeur extends JFrame implements ActionListener
{
	private JLabel lnum, lnom, ladmin, lsalle;
	private JTextField chnum, chnom;
	@SuppressWarnings("rawtypes")
	private JComboBox chadmin, chsalle;
	private JButton aj, qt, aff, rec, sup, mod;
	private JPanel pan1, pan2, pan3;
	@SuppressWarnings("unused")
	private Socket socket;
	DataOutputStream out;
	DataInputStream in;
    ObjectOutputStream oos;
    ObjectInputStream ois;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GestionServeur(Socket socket,  DataOutputStream out, DataInputStream in, ObjectOutputStream oos, ObjectInputStream ois) 
	{
		try 
		{
			this.socket = socket;
			this.out = out;
			this.in = in;
			this.oos = oos;
			this.ois = ois;
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
		
		 this.lnum = new JLabel("N° serveur : ");
		 this.lnom = new JLabel("Nom serveur : ");
		 this.ladmin = new JLabel("Admin : ");
		 this.lsalle = new JLabel("Salle : ");
		 this.chnom = new JTextField();
		 this.chnum = new JTextField();
		 this.chadmin = new JComboBox();
		 this.chsalle = new JComboBox();
		 
		 try 
		 {
			 this.oos.writeObject("Listing d'admin");
	         this.oos.flush();
			 ArrayList<Admin> listeAdmin = (ArrayList<Admin>)ois.readObject();
			 this.oos.writeObject("Listing de salle");
	         this.oos.flush();
			 ArrayList<Salle> listeSalle = (ArrayList<Salle>)ois.readObject();
			 
			 for (Admin admin : listeAdmin) 
			 {
				 this.chadmin.addItem(admin);
			 }
			 
			 for (Salle salle : listeSalle) 
			 {
				 this.chsalle.addItem(salle);
			 }
		 }
		 catch(Exception re) 
		 {
			 System.out.println(re.getMessage());
		 }
		 
		 this.aj = new JButton("Enregistrer");
		 this.qt = new JButton("Quitter");
		 this.rec = new JButton("Rechercher");
		 this.mod = new JButton("Modifier");
		 this.sup = new JButton("Supprimer");
		 this.aff= new JButton("Afficher");
		 this.aj.addActionListener(this);
		 this.aff.addActionListener(this);
		 this.qt.addActionListener(this);
		 this.mod.addActionListener(this);
		 this.rec.addActionListener(this);
		 this.sup.addActionListener(this);
		 pan1=new JPanel();
		 pan2=new JPanel();
		 pan3=new JPanel();
		 pan1.setLayout(new GridLayout(1,2));
		 pan1.add(lnum);
		 pan1.add(chnum);
		 pan1.add(rec);
		 pan2.setLayout(new GridLayout(3,2));
		 pan2.add(lnom);
		 pan2.add(chnom);
		 pan2.add(ladmin);
		 pan2.add(chadmin);
		 pan2.add(lsalle);
		 pan2.add(chsalle);
		 pan3.add(aj);
		 pan3.add(aff);
		 pan3.add(qt);
		 pan3.add(sup);
		 pan3.add(mod);
			
		 add(pan1,BorderLayout.NORTH);
		 add(pan2,BorderLayout.CENTER);
		 add(pan3,BorderLayout.SOUTH);
		 setTitle("Client RMI Swing");
		 setSize(600,200);
	   	 setLocationRelativeTo(null);
	   	 sup.setEnabled(false);
	   	 mod.setEnabled(false);
	   	 setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == aj) 
		{
			try 
			{
				this.oos.writeObject("Ajout de serveur");
	        	this.oos.flush();
				Admin admin = (((Admin) (this.chadmin.getSelectedItem())));
				Salle salle = (((Salle) (this.chsalle.getSelectedItem())));
				Serveur serveur = new Serveur(chnum.getText(), chnom.getText(), admin, salle);
				this.oos.writeObject(serveur);
	        	this.oos.flush();
				this.chnum.setText("");
				this.chnom.setText("");
				this.chadmin.setSelectedIndex(0);
				this.chsalle.setSelectedIndex(0);
			}
			catch(Exception re) 
			{
				System.out.println(re.getMessage());
			}
		}
		else if(event.getSource() == aff) 
		{
			try 
			{
				this.oos.writeObject("Listing de serveur");
	        	this.oos.flush();
				@SuppressWarnings("unchecked")
				ArrayList<Serveur> listeServeur = (ArrayList<Serveur>)ois.readObject();
				dispose();
				new ListeServeur(listeServeur, this.socket, this.out, this.in, this.oos, this.ois);
			}
			catch(Exception e) 
			{
				System.out.println(e.getMessage());
			}
		}
		else if(event.getSource() == mod) 
		{
			this.chnum.setEnabled(false);
			try 
			{
				this.oos.writeObject("Modification de serveur");
	        	this.oos.flush();
				Admin admin = ((Admin) (this.chadmin.getSelectedItem()));
				Salle salle = ((Salle) (this.chsalle.getSelectedItem()));
				Serveur serveur = new Serveur(this.chnum.getText(), this.chnom.getText(), admin, salle);
				this.oos.writeObject(serveur);
				this.oos.flush();
				this.chnum.setText("");
				this.chnom.setText("");
				this.chadmin.setSelectedIndex(0);
				this.chsalle.setSelectedIndex(0);
				
			}
			catch(Exception re) 
			{
				System.out.println(re.getMessage());
			}
		}
		else if(event.getSource() == rec) 
		{
			String num = this.chnum.getText();
			Serveur serveur;
			try 
			{
	    		this.oos.writeObject("Recherche de serveur");  
	    		this.oos.flush();
	    		this.oos.writeObject(num);
	    		this.oos.flush();
				serveur = (Serveur)this.ois.readObject();
				if(serveur == null)
					JOptionPane.showMessageDialog(null,"Serveur inexistant !!!");
				else 
				{
					this.chnum.setText(serveur.getNumServ());
					this.chnum.setEnabled(false);
					this.chnom.setText(serveur.getNomServ());
					this.chadmin.setSelectedItem(serveur.getAdmin());
					this.chsalle.setSelectedItem(serveur.getSalle());
   					this.mod.setEnabled(true);
   					this.sup.setEnabled(true);
				}
			}
			catch(Exception re) 
			{
				System.out.println(re.getMessage());
			}
		}
		else if(event.getSource() == sup) 
		{
			String num = this.chnum.getText();
			try 
			{
	    		this.oos.writeObject("Suppression de serveur");  
	    		this.oos.flush();
	    		this.out.writeChars(num);
	    		this.oos.flush();
				this.chnum.setText("");
				this.chnom.setText("");
				this.chadmin.setSelectedIndex(0);;
				this.chsalle.setSelectedIndex(0);
				this.sup.setEnabled(false);
			}
			catch(Exception re) 
			{
				System.out.println(re.getMessage());
			}
		}
		else if(event.getSource() == qt) 
		{
			this.dispose();
		}
		
	}

}
