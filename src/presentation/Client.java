package presentation;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.net.*;
import java.io.*;


/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 *  
 */
@SuppressWarnings("serial")
public class Client extends JFrame implements ActionListener
{
	private JButton gAdmin, gSalle, gServeur, gServeurBySalle, gServeurByAdmin;
	private JLabel label;
	private Socket socket;
	DataOutputStream out;
	DataInputStream in;
    ObjectOutputStream oos;
    ObjectInputStream ois;
	
	public Client() 
	{
		this.label = new JLabel("==> Gestion du Datacenter :");
		label.setForeground(Color.white);
		Panel pan = new Panel("images/2.jpg");
		
		Font police = new Font("Tahoma", Font.BOLD, 30);
		Font po = new Font("Arial", Font.ITALIC, 20);
		
		this.gAdmin = new JButton("Gestion des admins");
		this.gSalle = new JButton("Gestion des salles");
		this.gServeur = new JButton("Gestion des serveurs");
		this.gServeurBySalle = new JButton("Liste des serveurs par salle");
		this.gServeurByAdmin = new JButton("Liste des serveurs par admin");
		
		label.setFont(police); 
		label.setLocation(30, 20);
		label.setSize(500, 40);
		gAdmin.setBounds(20, 80, 350, 50);
		gAdmin.setFont(po);
		gSalle.setFont(po); 
		gSalle.setBounds(60, 200, 350, 50);
		gServeur.setFont(po); 
		gServeur.setBounds(100, 320, 350, 50);
		gServeurByAdmin.setFont(po); 
		gServeurByAdmin.setBounds(140, 440, 350, 50);
		gServeurBySalle.setFont(po); 
		gServeurBySalle.setBounds(180, 560, 350, 50);
		
		gAdmin.addActionListener(this);
		gSalle.addActionListener(this);
		gServeur.addActionListener(this);
		gServeurByAdmin.addActionListener(this);
		gServeurBySalle.addActionListener(this);
		
		pan.add(label); pan.add(gAdmin); pan.add(gSalle); pan.add(gServeur);
		pan.add(gServeurBySalle); pan.add(gServeurByAdmin);
		
		this.setTitle("Gestion Datacenter");
		pan.setLayout(null);
		this.setContentPane(pan);
		this.setLocationRelativeTo(this);
		this.setSize(900, 700);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		try 
		{
			this.socket = new Socket("localhost", 8000);
			this.out = new DataOutputStream(this.socket.getOutputStream());
			this.in = new DataInputStream(this.socket.getInputStream());
			this.oos = new ObjectOutputStream(this.socket.getOutputStream());
			this.ois = new ObjectInputStream(this.socket.getInputStream());
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == this.gAdmin) 
		{
			new GestionAdmin(this.socket, this.out, this.in, this.oos, this.ois);
		}
		
		if(event.getSource() == this.gSalle) 
		{
			new GestionSalle(this.socket, this.out, this.in, this.oos, this.ois);
		}
		
		if(event.getSource() == this.gServeur) 
		{
			new GestionServeur(this.socket, this.out, this.in, this.oos, this.ois);
		}
		
		if(event.getSource() == this.gServeurByAdmin) 
		{
			new ListeServeurParAdmin(this.socket, this.out, this.in, this.oos, this.ois);
		}
		
		if(event.getSource() == this.gServeurBySalle) 
		{
			new ListeServeurParSalle(this.socket, this.out, this.in, this.oos, this.ois);
		}
	}
	
	public static void main(String args[]) throws Exception
	{
		SplashScreen.getInstance("images/ibm-serveur-bis.png");
		
		try 
		{
			Thread.sleep(4000);
			SplashScreen.getInstance().end();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		new Client();
	}

}
