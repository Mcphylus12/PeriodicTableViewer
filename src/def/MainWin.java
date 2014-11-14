 package def;

 import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import def.Info.infoTypes;
 
 @SuppressWarnings("serial")
public class MainWin
   extends JFrame
   implements Runnable, ComponentListener, ActionListener
 {
	 /**
	  * Map containg images and their references
	  */
   public static HashMap<String, Image> images = new HashMap<String, Image>();
   /**
    * object of this class to which works as an entry point to the program
    */
   public static MainWin win;
   /**
    * Periodic table pane for displaying th eperiodic table
    */
   public TablePane perioTable;
   /**
    * infopane class for displaying information
    */
   public InfoPane infoPane;
   /**
    * handle for the edit window
    */
   public EditWindow edtwin;
   /**
    * Handle for the edit window
    */
   public JButton EditElement;
   /**
    * handle for the password window
    */
   public PassWordBox passBox;
   /**
    * Size of the screen
    */
   final Dimension winSize = Toolkit.getDefaultToolkit().getScreenSize();
   /**
    * desired size of the program
    */
   Dimension appSize = new Dimension(800, 600);
   /**
    * Thread for custom updates
    */
   Thread upDateThread;
   
   /**
    * Create the main window
    * @param Title  The title for the window
    */
   public MainWin(String Title)
   {
     super(Title);
     addImage("res/atom_icon.jpg", "AtomIcon");
     
     //setup window
     setDefaultCloseOperation(3);
     setBounds(this.winSize.width / 2 - this.appSize.width / 2, this.winSize.height / 2 - this.appSize.height / 2, this.appSize.width, this.appSize.height);
     setLayout(null);
     setIconImage(getImage("AtomIcon"));
     addComponentListener(this);
     
 //set up child panes
     this.perioTable = new TablePane();
     this.infoPane = new InfoPane();
     this.EditElement = new JButton("Edit Element");
     this.perioTable.setBounds(5, 5, (int)((getWidth() - 20) * 0.75D), getHeight() - 40);
     this.infoPane.setBounds((int)((getWidth() - 20) * 0.75D) + 10, 5, (int)((getWidth() - 20) * 0.25D), (int)(getHeight() * 0.8D));
     this.EditElement.setBounds((int)((getWidth() - 20) * 0.75D + 10.0D), (int)(getHeight() * 0.8D + 10.0D), (int)((getWidth() - 20) * 0.25D), (int)(getHeight() * 0.05D));
     
     this.EditElement.addActionListener(this);
     
     add(this.perioTable);
     add(this.infoPane);
     add(this.EditElement);
     
     this.upDateThread = new Thread(this);
     this.upDateThread.start();
     
     setVisible(true);
   }
   /**
    * Create an edit window
    * @param e  element to be loaded into the window
    */
   public void createEditWindow(Element e)
   {
     if (this.edtwin == null) {
       this.edtwin = new EditWindow(e, this);
     }
   }
   /**
    * create a window to read or write password
    * @param e  an element to pass through to the edit window
    */
   public void createPassBox(Element e)
   {
     this.passBox = new PassWordBox(this, e, PassWordBox.opType.READ);
   }
   /**
    * entry point to the program
    * @param args  arguments from the command line
    */
   public static void main(String[] args)
   {
     win = new MainWin("Chemical information");
   }
   /**
    * Start the manual updates
    */
   public void StartUpdateThread()
   {
     while(true)
     {
       try
       {
         Thread.sleep(33L);
       }
       catch (InterruptedException e)
       {
         e.printStackTrace();
       }
       update();
     }
   }
   /**
    * updates all pnes contained by the window
    */
   public void update()
   {
     this.perioTable.Update();
     this.infoPane.Update();
   }
   /**
    * adds an image to image map
    * @param filepath  the file path to the image
    * @param Key  the string key to get the image
    */
   public static void addImage(String filepath, String Key)
   {
     BufferedImage tmp = null;
     try
     {
       tmp = ImageIO.read(new File(filepath));
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     images.put(Key, tmp);
   }
   /**
    * get an image from the image map
    * @param Key  key to use as reference
    * @return  an image
    */
   public static Image getImage(String Key)
   {
     return (Image)images.get(Key);
   }
   /**
    * chack the password against the file
    * @param s  the entered password
    * @return  whether the password was the same
    */
   public static boolean CheckPass(String s)
   {
     BufferedReader passReader = null;
     try
     {
    	 //read correct pass in from file
       passReader = new BufferedReader(new FileReader(new File("res/pass.pass")));
       String passfromFile = passReader.readLine();
       passReader.close();
       return s.equalsIgnoreCase(passfromFile);
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     return false;
   }
   /**
    * write a new password
    * @param s  new password
    */
   public static void WriteNewPass(String s)
   {
     BufferedWriter writer = null;
     try
     {
       writer = new BufferedWriter(new FileWriter(new File("res/pass.pass"), false));
       writer.write(s.toLowerCase());
       writer.close();
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   /**
    * get info from a .mol file
    * @param element  name of .mol file
    * @return  an info class with the information from file
    */
   public static Info getInfofromFile(String element)
   {
     BufferedReader inFile = null;
     
     String Line = "";
     
     Info tmp = new Info();
     
     tmp.setInfoBeingRead(Info.infoTypes.NONE);
     try
     {
       inFile = new BufferedReader(new FileReader("res/" + element));
     }
     catch (FileNotFoundException e)
     {
       e.printStackTrace();
       
       System.exit(0);
     }
     try
     {
       while ((Line = inFile.readLine()) != null) {
         if (Line.startsWith("#")) {
           switch (Line)
           {
           //change the info to be read variable whenever a line in a file starts with a hash
           case "#Proton": 
             tmp.setInfoBeingRead(infoTypes.PROTON);
             break;
           case "#Mass": 
        	   tmp.setInfoBeingRead(infoTypes.MASS);
             break;
           case "#Name": 
        	   tmp.setInfoBeingRead(infoTypes.NAME);
             break;
           case "#Description": 
        	   tmp.setInfoBeingRead(infoTypes.DESC);
             break;
           case "#Group": 
        	   tmp.setInfoBeingRead(infoTypes.GROUP);
             break;
           case "#Links": 
        	   tmp.setInfoBeingRead(infoTypes.LINKS);
        	  break;
       }
         }
         else{
        	 tmp.readInfo(Line);
         }
      
     }
       inFile.close();	
				}
						
     catch (IOException e)
     {
       e.printStackTrace();
     }
     return tmp;
   }
   /**
    * write edited info to file
    * @param e  the element containg info to write
    */
   public static void writeInfo(Element e)
   {
     BufferedWriter outFile = null;
     try
     {
       outFile = new BufferedWriter(new FileWriter("res/" + e.getInfo().getName() + ".mol"));
     }
     catch (IOException er)
     {
       er.printStackTrace();
       
       System.exit(0);
     }
     try
     {
       outFile.write("#Name");
       outFile.newLine();
       outFile.write(e.getInfo().getName());
       outFile.newLine();
       
       outFile.write("#Mass");
       outFile.newLine();
       outFile.write(String.valueOf(e.getInfo().getMass()));
       outFile.newLine();
       
       outFile.write("#Proton");
       outFile.newLine();
       outFile.write(String.valueOf(e.getInfo().getProton()));
       outFile.newLine();
       
       outFile.write("#Description");
       outFile.newLine();
       for (String s : e.getInfo().getDescLines())
       {
         outFile.write(s);
         outFile.newLine();
       }
       outFile.write("#Links");
       outFile.newLine();
       for (String s : e.getInfo().getLinks())
       {
         outFile.write(s);
         outFile.newLine();
       }
       outFile.write("#Group");
       outFile.newLine();
       outFile.write(String.valueOf(e.getInfo().getGroup()));
       outFile.newLine();
       
       outFile.close();
     }
     catch (IOException e1)
     {
       e1.printStackTrace();
     }
   }
   /**
    * start the update thread
    */
   public void run()
   {
     StartUpdateThread();
   }
   /**
    * resize component
    * @param c
    */
   public void componentResized(ComponentEvent c)
   {
	   //reset size of child components
     this.perioTable.setBounds(5, 5, (int)((getWidth() - 20) * 0.75D), getHeight() - 40);
     this.infoPane.setBounds((int)((getWidth() - 20) * 0.75D) + 10, 5, (int)((getWidth() - 20) * 0.25D), (int)(getHeight() * 0.8D));
     this.EditElement.setBounds((int)((getWidth() - 20) * 0.75D + 10.0D), (int)(getHeight() * 0.8D + 10.0D), (int)((getWidth() - 20) * 0.25D), (int)(getHeight() * 0.05D));
   }
   
   public void componentHidden(ComponentEvent arg0) {}
   
   public void componentMoved(ComponentEvent arg0) {}
   
   public void componentShown(ComponentEvent arg0) {}
   /**
    * handles events inside the window
    * @param e  class containing info on the event
    */
   public void actionPerformed(ActionEvent e)
   {
     if (win.perioTable.clickedElement != null) {
       createPassBox(win.perioTable.clickedElement);
     }
   }
 }


