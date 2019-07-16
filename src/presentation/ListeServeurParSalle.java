package presentation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import domaine.Salle;
import domaine.Serveur;

/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 *  
 */
@SuppressWarnings("serial")
public class ListeServeurParSalle extends JFrame implements ActionListener, ItemListener
{
	@SuppressWarnings("unused")
	private JTable table;
	private JLabel lsalle;
	@SuppressWarnings("rawtypes")
	private JComboBox chsalle;
	private ArrayList<Salle> salles;
	private ArrayList<Serveur> serveurs;
	private JScrollPane sc;
	private JPanel pan, pan1, pan2;
	private JButton qt;
	@SuppressWarnings("unused")
	private Salle salle;
	@SuppressWarnings("unused")
	private Socket socket;
	DataOutputStream out;
	DataInputStream in;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ListeServeurParSalle(Socket socket, DataOutputStream out, DataInputStream in, ObjectOutputStream oos, ObjectInputStream ois) 
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
    	this.socket = socket;
    	this.chsalle = new JComboBox();
    	this.lsalle = new JLabel("Selectionnez une salle : ");
    	this.pan = new JPanel();
    	this.pan1 = new JPanel();
    	this.pan2 = new JPanel();
		this.qt = new JButton("Quitter");
		this.sc  = new JScrollPane();
    	
    	try 
    	{
			this.oos.writeObject("Listing de salle");
        	this.oos.flush();
    		this.salles = (ArrayList<Salle>)ois.readObject();
    		this.salle = this.salles.get(0);
			this.oos.writeObject("Listing de serveur");
        	this.oos.flush();
        	this.serveurs = (ArrayList<Serveur>)ois.readObject();
    	}
    	catch(Exception re) 
    	{
    		System.out.println(re.getMessage());
    	}
    	
    	for(Salle salle : salles) 
    	{
    		this.chsalle.addItem(salle);
    	}

			this.table = this.initTable(serveurs);
		
			this.pan.setLayout(new GridLayout(1, 1));
			this.pan.add(this.lsalle); this.pan.add(this.chsalle);
			setTitle("Client RMI : liste des serveurs par salle");
			this.chsalle.addItemListener(this);
			setSize(550, 600);
			qt.addActionListener(this); 
			pan1.add(sc);
			pan2.add(qt);
			add(pan,BorderLayout.NORTH);
			add(pan1,BorderLayout.CENTER);
			add(pan2,BorderLayout.SOUTH);
			setLocationRelativeTo(null);
			setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent event) 
	{
		this.dispose();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
    	try 
    	{
			this.oos.writeObject("Listing de serveur par salle");
        	this.oos.flush();
    		this.salle = (Salle) this.chsalle.getSelectedItem();
			this.oos.writeObject(this.salle);
        	this.oos.flush();
    		this.serveurs = (ArrayList<Serveur>)ois.readObject();
    	}
    	catch(Exception re) 
    	{
    		System.out.println(re.getMessage());
    	}
    	this.initTable(this.serveurs);
	}
	
	public JTable initTable(ArrayList<Serveur> serveurs) 
	{
		JTable table = new JTable();
		DefaultTableModel modele = (DefaultTableModel)table.getModel();
		modele.addColumn("N° serveur");
		modele.addColumn("Nom serveur");
		modele.addColumn("Admin");
		modele.addColumn("Salle");
    	
    	int ligne = 0;
		
		  for (Serveur serveur : this.serveurs)
		  {
			  modele.addRow( new Object[0]);
			  modele.setValueAt(String.valueOf(serveur.getNumServ()), ligne, 0);
			  modele.setValueAt(serveur.getNomServ(), ligne, 1);
			  modele.setValueAt(serveur.getAdmin(), ligne, 2);
			  modele.setValueAt(serveur.getSalle(), ligne, 3);
			  ligne++;
		  }
		  

		this.sc.setViewportView(table);
		
		return table;
	}

}
