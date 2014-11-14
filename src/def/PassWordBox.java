 package def;
 
 import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
 
 @SuppressWarnings("serial")
public class PassWordBox
   extends JFrame
   implements ActionListener
 {
	 /**
	  * current operation type for the window
	  */
   public opType OpType;
   /**
    * label for the password
    */
   JLabel LPassword;
   /**
    * Field for entring password
    */
   JPasswordField PassField;
   /**
    * Confirm button for password
    */
   JButton Confirm;
   /**
    * element to pass to editwindow
    */
   Element passthroughelement;
   /**
    * 
    * @author Kyle
    * operation types
    */
   public static enum opType
   {
     READ,  WRITE;
   }
   /**
    * Create a password box
    * @param j  parent window for positioning 
    * @param e  element to pass through to edit window
    * @param o  operation type to open the window with
    */
   public PassWordBox(JFrame j, Element e, opType o)
   {
     super("Enter Password");
     setBounds(j.getX() + j.getWidth() / 2 - 175, j.getY() + j.getHeight() / 2 - 50, 350, 100);
     setResizable(false);
     setDefaultCloseOperation(2);
     setLayout(null);
     setIconImage(MainWin.getImage("AtomIcon"));
     this.passthroughelement = e;
     this.OpType = o;
     
     this.LPassword = new JLabel();
     if (this.OpType == opType.READ) {
       this.LPassword.setText("Enter Password:");
     } else if (this.OpType == opType.WRITE) {
       this.LPassword.setText("New Password:");
     }
     this.LPassword.setBounds(0, 10, 100, 50);
     add(this.LPassword);
     
     this.PassField = new JPasswordField();
     this.PassField.setBounds(100, 25, 150, 20);
     this.PassField.setEditable(true);
     add(this.PassField);
     
     this.Confirm = new JButton("Confirm");
     this.Confirm.setBounds(250, 40, 90, 25);
     this.Confirm.addActionListener(this);
     add(this.Confirm);
     setVisible(true);
   }
   /**
    * handles event performed within the window
    * @param a  class containing info on the event
    */
   public void actionPerformed(ActionEvent a)
   {
     if (this.OpType == opType.READ) {
       if (MainWin.CheckPass(String.valueOf(this.PassField.getPassword())))
       {
         MainWin.win.createEditWindow(this.passthroughelement);
         dispose();
         MainWin.win.passBox = null;
       }
       else
       {
         JOptionPane.showMessageDialog(this, 
           "Password is Incorrect", 
           "Password Error", 
           0);
       }
     }
     if (this.OpType == opType.WRITE) {
       if (!String.valueOf(this.PassField.getPassword()).equals(""))
       {
         MainWin.WriteNewPass(String.valueOf(this.PassField.getPassword()));
         dispose();
       }
       else
       {
         JOptionPane.showMessageDialog(this, 
           "Password cannot be empty", 
           "Format Error", 
           0);
       }
     }
   }
 }


