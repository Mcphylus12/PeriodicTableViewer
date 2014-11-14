 package def;
 
 import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
 
 @SuppressWarnings("serial")
public class InfoPane
   extends JPanel
   implements MouseListener, ActionListener, ClipboardOwner
 {
	 /**
	  * Name of chosen Element
	  */
   private String Name;
   /**
    * Mass of chosen element
    */
   private double Mass;
   /**
    * Proton number of chosen element
    */
   private int Proton;
   /**
    * Description of the chosen element
    */
   private ArrayList<String> descLines = new ArrayList<String>();
   /**
    * Area to display elements description
    */
   private JTextArea descdisp;
   /**
    * Links for the cosen element
    */
   private ArrayList<String> links = new ArrayList<String>();
   /**
    * Area to display links
    */
   private JTextArea linkDisp;
   /**
    * Button to copy highlighted Text
    */
   private JButton Copy;
   /**
    * Group of selected Element
    */
   private int Group;
   /**
    * Create the infopane initialising variables 
    */
   public InfoPane()
   {
	   //Setup layout of infoPane
     setLayout(null);
     setBackground(Color.WHITE);
     setBorder(BorderFactory.createLineBorder(Color.BLACK));
     this.descdisp = new JTextArea();
     this.descdisp.setLineWrap(true);
     this.descdisp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
     this.descdisp.setEditable(false);
     this.descdisp.setWrapStyleWord(true);
     add(this.descdisp);
     this.linkDisp = new JTextArea();
     
     this.linkDisp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
     this.linkDisp.setLineWrap(true);
     this.linkDisp.setEditable(false);
     this.linkDisp.setWrapStyleWord(true);
     add(this.linkDisp);
     
     this.Copy = new JButton("Copy Highlighted Text");
     this.Copy.addActionListener(this);
     add(this.Copy);
   }
   /**
    * load an element into the variables preparing to display and display the new window
    * @param e
    */
   public void displayElement(Element e)
   {
	   //Set display variable to that of the loaded element
     this.Name = e.getInfo().getName();
     this.Mass = e.getInfo().getMass();
     this.Proton = e.getInfo().getProton();
     this.descLines = e.getInfo().getDescLines();
     this.links = e.getInfo().getLinks();
     this.Group = e.getInfo().getGroup();
     
     this.descdisp.setText("");
     for (int i = 0; i < this.descLines.size(); i++) {
       this.descdisp.append((String)this.descLines.get(i));
     }
     this.linkDisp.setText("");
     for (int i = 0; i < this.links.size(); i++) {
       this.linkDisp.append((String)this.links.get(i) + "\n");
     }
   }
   /**
    * update class 
    */
   public void Update()
   {
	   //Update the Graphics Canvas 
     repaint();
     
     this.descdisp.setBounds(10, 95, getWidth() - 20, 200);
     this.linkDisp.setBounds(10, 300, getWidth() - 20, 150);
     this.Copy.setBounds(10, 455, getWidth() - 20, 15);
   }
   /**
    * paint class
    * @param g  graphics context for the pane
    */
   public void paint(Graphics g)
   {
     super.paint(g);
     
 //Draw Labels
     g.drawString("Name:" + this.Name, 10, 20);
     g.drawString("Mass:" + this.Mass, 10, 35);
     g.drawString("Proton number:" + this.Proton, 10, 50);
     g.drawString("Group:" + (this.Group >= 0 ? Integer.valueOf(this.Group) : "N/A"), 10, 65);
     g.drawString("Desciption:", 10, 80);
     
 
     g.setColor(Color.BLACK);
     g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
   }
   
   public void mousePressed(MouseEvent e) {}
   
   public void mouseClicked(MouseEvent e) {}
   
   public void mouseEntered(MouseEvent e) {}
   
   public void mouseExited(MouseEvent e) {}
   
   public void mouseReleased(MouseEvent e) {}
   /**
    * called when an action is performed on the pane
    * @param a  class containg information on the event
    */
   public void actionPerformed(ActionEvent a)
   {
     if (a.getSource() == this.Copy)
     {
    	 //copy highlighted text to current clipboard
       String selText = this.linkDisp.getSelectedText() == null ? this.descdisp.getSelectedText() : this.linkDisp.getSelectedText();
       StringSelection stringSelection = new StringSelection(selText);
       
       Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, this);
     }
   }
   
   public void lostOwnership(Clipboard arg0, Transferable arg1) {}
 }


