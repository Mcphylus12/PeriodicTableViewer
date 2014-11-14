 package def;
 
 import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
/**
 * 
 * @author Kyle
 *
 */
@SuppressWarnings("serial")
class EditWindow
   extends JFrame
   implements ActionListener
 {

	/**
	 * Height of Window
	 */
   private static final int HEIGHT = 400;
   /**
    * Width of window
    */
private static final int WIDTH = 600;
/**
 * Label for name field
 */
private JLabel LName;
/**
 * Field for element name
 */
   private JTextField nameField;
   /**
    * Label for proton field
    */
   private JLabel LProton;
   /**
    * Field for proton number of element
    */
   private JTextField Protonnumber;
   /**
    * Label for mass field
    */
   private JLabel LMass;
   /**
    * Field for atomic mass of element
    */
   private JTextField Atomnumber;
   /**
    * Label for group field
    */
   private JLabel LGroup;
   /**
    * Field for group of element
    */
   private JTextField Group;
   /**
    * Lable for description field
    */
   private JLabel Ldesc;
   /**
    * Field for description of element
    */
   private JTextArea descriptArea;
   /**
    * Label for links
    */
   private JLabel LLinks;
   /**
    * Field for relevant links for an element
    */
   private ArrayList<JTextField> Links;
   /**
    * Info of the loaded element
    */
   public Info LoadedInfo;
   /**
    * Element loaded into the edit window
    */
   public Element LoadedElement;
   /**
    * Confirm Button for confirming changes
    */
   private JButton Confirm;
   /**
    * Cancel Button
    */
   private JButton Cancel;
   /**
    * Button for chaging password
    */
   private JButton passChange;
   
   /**
    * Creates edit window
    * @param f  Prent window used for positioning
    */
   public EditWindow(JFrame f)
   {
     super("Editing Window");
     
     //setup window positions and icons
     setBounds(f.getX() + f.getWidth() / 2 - 300, f.getY() + f.getHeight() / 2 - 200, WIDTH, HEIGHT);
     setIconImage(MainWin.getImage("AtomIcon"));
     setDefaultCloseOperation(0);
     setLayout(null);
     setResizable(false);
     
 
 
     //add Cancel, Confirm and Change Password buttons 
     Cancel = new JButton("Cancel");
     Cancel.setBounds(getWidth() / 2, getHeight() - 60, getWidth() / 2 - 10, 30);
     Cancel.addActionListener(this);
     add(Cancel);
     
     Confirm = new JButton("Confirm Changes");
     Confirm.setBounds(5, getHeight() - 60, getWidth() / 2 - 10, 30);
     Confirm.addActionListener(this);
     add(Confirm);
     
     passChange = new JButton("Change Password");
     passChange.setBounds(getWidth() - 200, 0, 190, 20);
     passChange.addActionListener(this);
     add(passChange);
     
     //setup labels and field
     LName = new JLabel("Name : ");
     LName.setBounds(getposfromGrid(0, 0, 1, 1));
     add(LName);
     
     nameField = new JTextField();
     nameField.setBounds(getposfromGrid(1, 0, 1, 1));
     nameField.setEditable(true);
     add(nameField);
     
     LProton = new JLabel("Proton Number : ");
     LProton.setBounds(getposfromGrid(0, 1, 1, 1));
     add(LProton);
     
     Protonnumber = new JTextField();
     Protonnumber.setBounds(getposfromGrid(1, 1, 1, 1));
     Protonnumber.setEditable(true);
     add(Protonnumber);
     
     LMass = new JLabel("Atomic Mass : ");
     LMass.setBounds(getposfromGrid(0, 2, 1, 1));
     add(LMass);
     
     Atomnumber = new JTextField();
     Atomnumber.setBounds(getposfromGrid(1, 2, 1, 1));
     Atomnumber.setEditable(true);
     add(Atomnumber);
     
     LGroup = new JLabel("Group : ");
     LGroup.setBounds(getposfromGrid(0, 3, 1, 1));
     add(LGroup);
     
     Group = new JTextField();
     Group.setBounds(getposfromGrid(1, 3, 1, 1));
     Group.setEditable(true);
     add(Group);
     
     Ldesc = new JLabel("Description : ");
     Ldesc.setBounds(getposfromGrid(0, 4, 1, 1));
     add(Ldesc);
     
     descriptArea = new JTextArea();
     descriptArea.setBounds(getposfromGrid(1, 4, 2, 8));
     descriptArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
     descriptArea.setLineWrap(true);
     descriptArea.setWrapStyleWord(true);
     descriptArea.setEditable(true);
     add(descriptArea);
     
     LLinks = new JLabel("Links");
     LLinks.setBounds(getposfromGrid(3, 0, 1, 1));
     add(LLinks);
     
     Links = new ArrayList<JTextField>();
     for (int i = 1; i <= 10; i++)
     {
       Links.add(new JTextField());
       ((JTextField)Links.get(i - 1)).setBounds(getposfromGrid(3, i, 1, 1));
       add((Component)Links.get(i - 1));
     }
     setVisible(true);
   }
   /**
    * Get pixel poistion from table grid position
    * @param x  grid x position
    * @param y  grid y position
    * @param Width  cell width
    * @param Height  cell height
    * @return  pixel x, y, width and height
    */
   public Rectangle getposfromGrid(int x, int y, int Width, int Height)
   {
     return new Rectangle(125 * x + 20, 20 * y + 20, 120 * Width, 20 * Height);
   }
   /**
    * create edit window with selected element
    * @param se  element loaded into to window
    * @param f  parent window used for positioning
    */
   public EditWindow(Element se, JFrame f)
   {
     this(f);
     LoadedElement = se;
     LoadedInfo = LoadedElement.getInfo();
     
     //set information in window to that of loaded element
     Protonnumber.setEditable(false);
     Group.setEditable(false);
     
     nameField.setText(LoadedInfo.getName());
     Protonnumber.setText(String.valueOf(LoadedInfo.getProton()));
     Atomnumber.setText(String.valueOf(LoadedInfo.getMass()));
     Group.setText(LoadedInfo.getGroup() >= 0 ? String.valueOf(LoadedInfo.getGroup()) : "N/A");
     
     descriptArea.setText("");
     for (int i = 0; i < LoadedInfo.getDescLines().size(); i++) {
       descriptArea.append((String)LoadedInfo.getDescLines().get(i));
     }
     for (int i = 0; i < LoadedInfo.getLinks().size(); i++) {
       ((JTextField)Links.get(i)).setText((String)LoadedInfo.getLinks().get(i));
     }
   }
  
   /**
    * Called when any action is performed in the pane
    * @param a  a class containing information on the event fired
    */
   public void actionPerformed(ActionEvent a)
   {
     if (a.getSource() == Cancel)
     {
    	 //close edit window
       dispose();
       MainWin.win.edtwin = null;
     }
     if (a.getSource() == Confirm)
     {
       boolean err = false;
       try
       {
    	 //write new information to file
         File f = new File("res/" + LoadedInfo.getName() + ".mol");
         if (!f.renameTo(new File("res/" + nameField.getText() + ".mol"))) {
           throw new IOException();
         }
         if ((nameField.getText().equals("")) || (Atomnumber.getText().equals(""))) {
           throw new IOException();
         }
         LoadedInfo.setName(nameField.getText());
         LoadedInfo.setProton(Integer.parseInt(Protonnumber.getText()));
         LoadedInfo.setMass(Double.parseDouble(Atomnumber.getText()));
         LoadedInfo.setGroup(LoadedInfo.getGroup() == -1 ? -1 : Integer.parseInt(Group.getText()));
         LoadedInfo.getDescLines().clear();
         for (String s : descriptArea.getText().split("\n")) {
           LoadedInfo.getDescLines().add(s);
         }
         LoadedInfo.getLinks().clear();
         for (JTextField j : Links) {
           if (j.getText() != "") {
             LoadedInfo.getLinks().add(j.getText());
           }
         }
         MainWin.writeInfo(LoadedElement);
       }
       catch (IOException e)
       {
    	   //catch any erroneous data
         JOptionPane.showMessageDialog(this, 
           "An element with this name already exists or a field is empty", 
           "Format Error", 
           0);
         err = true;
       }
       catch (NumberFormatException e)
       {
         JOptionPane.showMessageDialog(this, 
           "The mass or Proton number contains a letter and cannot.", 
           "Format Error", 
           0);
         err = true;
       }
       if (!err)
       {
         dispose();
         MainWin.win.edtwin = null;
       }
     }
     if (a.getSource() == passChange)
     {
       new PassWordBox(this, null, PassWordBox.opType.WRITE);
       System.out.println("hello");
     }
   }
 }

