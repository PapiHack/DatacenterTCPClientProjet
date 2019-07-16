package presentation;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * 
 * @author PapiH4ck3R
 * @since 11/07/19
 * @version 0.0.1
 *  
 */
@SuppressWarnings("serial")
public class Panel extends JPanel 
{
	private String imgPath;
	
	public Panel(String image) 
	{
		this.setImgPath(image);
	}
	
	public void paintComponent(Graphics g) 
	{
		try 
		{
			Image img = ImageIO.read(new File(this.imgPath));
			g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(), this);
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getImgPath() 
	{
		return this.imgPath;
	}
	
	public void setImgPath(String imgPath) 
	{
		this.imgPath = imgPath;
	}
}
