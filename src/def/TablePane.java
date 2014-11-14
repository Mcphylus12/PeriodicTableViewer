 package def;
 
 import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;
 
 @SuppressWarnings("serial")
public class TablePane
   extends JPanel
   implements MouseListener, MouseMotionListener
 {
	 /**
	  * x poition to draw table from
	  */
   private int AnchorX;
   /**
    * y position to draw table from
    */
   private int AnchorY;
   /**
    * mouse x position form previous frame
    */
   private int oldmouseX;
   /**
    * mouse y position from previous frame
    */
   private int oldmouseY;
   /**
    * element being moused over
    */
   public Element selectedElement = null;
   /**
    * list of all elements in the table
    */
   public ArrayList<Element> elements = new ArrayList<Element>();
   /**
    * image to hold the table
    */
   BufferedImage Periotable = new BufferedImage(18 * Element.getWidthHeight() + 1, 10 * Element.getWidthHeight() + 1, 6);
   /**
    * graphics context for the image
    */
   Graphics PerioGraphics = Periotable.getGraphics();
   /**
    * element that has been clicked
    */
   public Element clickedElement = null;
   /**
    * initialise the table and load in elements
    */
   public TablePane()
   {
     Info iterationinfo = null;
     for (File file : new File("res").listFiles()) {
       if (file.getName().endsWith(".mol"))
       {
         iterationinfo = MainWin.getInfofromFile(file.getName());
         elements.add(new Element(getTableCoordsFromProtonNumber(iterationinfo.getProton(), iterationinfo.getGroup()), iterationinfo));
         System.out.println(file.getName());
       }
     }
     setBackground(Color.LIGHT_GRAY);
     MainWin.addImage("res/Select.png", "SelectSquare");
     MainWin.addImage("res/Chosen.png", "clickedSquare");
     addMouseListener(this);
     addMouseMotionListener(this);
     
     AnchorX = 0;
     AnchorY = 0;
   }
   /**
    * paint the table
    * @param g  graphics context for the pane
    */
   public void paint(Graphics g)
   {
     super.paint(g);
     PerioGraphics.setColor(Color.WHITE);
     PerioGraphics.fillRect(0, 0, 18 * Element.getWidthHeight(), 10 * Element.getWidthHeight());
     for (Element e : elements)
     {
       PerioGraphics.setColor(getColourFromProtonNumber(e.getInfo().getProton()));
       PerioGraphics.fillRect((int)e.getBoundingBox().getX(), (int)e.getBoundingBox().getY(), (int)e.getBoundingBox().getWidth(), (int)e.getBoundingBox().getHeight());
       PerioGraphics.setColor(Color.BLACK);
       PerioGraphics.drawRect((int)e.getBoundingBox().getX(), (int)e.getBoundingBox().getY(), (int)e.getBoundingBox().getWidth(), (int)e.getBoundingBox().getHeight());
       PerioGraphics.drawString(String.valueOf(e.getInfo().getProton()), (int)e.getBoundingBox().getX() + Element.getWidthHeight() / 2 - PerioGraphics.getFontMetrics().stringWidth(String.valueOf(e.getInfo().getProton())) / 2, (int)(e.getBoundingBox().getY() + Element.getWidthHeight() * 0.3D));
       PerioGraphics.drawString(e.getInfo().getName(), (int)e.getBoundingBox().getX() + Element.getWidthHeight() / 2 - PerioGraphics.getFontMetrics().stringWidth(e.getInfo().getName()) / 2, (int)(e.getBoundingBox().getY() + Element.getWidthHeight() * 0.5D));
       PerioGraphics.drawString(e.getInfo().getName(), (int)e.getBoundingBox().getX() + Element.getWidthHeight() / 2 - PerioGraphics.getFontMetrics().stringWidth(e.getInfo().getName()) / 2, (int)(e.getBoundingBox().getY() + Element.getWidthHeight() * 0.5D));
       PerioGraphics.drawString(String.valueOf(e.getInfo().getMass()), (int)e.getBoundingBox().getX() + Element.getWidthHeight() / 2 - PerioGraphics.getFontMetrics().stringWidth(String.valueOf(e.getInfo().getMass())) / 2, (int)(e.getBoundingBox().getY() + Element.getWidthHeight() * 0.8D));
     }
     g.drawImage(Periotable, AnchorX, AnchorY, 18 * Element.getWidthHeight() + 1, 10 * Element.getWidthHeight() + 1, this);
     if (clickedElement != null) {
       g.drawImage(MainWin.getImage("clickedSquare"), 
         (int)clickedElement.getBoundingBox().getX() + AnchorX, 
         (int)clickedElement.getBoundingBox().getY() + AnchorY, 
         (int)clickedElement.getBoundingBox().getWidth() + 1, 
         (int)clickedElement.getBoundingBox().getHeight() + 1, this);
     }
     if (selectedElement != null) {
       g.drawImage(MainWin.getImage("SelectSquare"), 
         (int)selectedElement.getBoundingBox().getX() + AnchorX, 
         (int)selectedElement.getBoundingBox().getY() + AnchorY, 
         (int)selectedElement.getBoundingBox().getWidth() + 1, 
         (int)selectedElement.getBoundingBox().getHeight() + 1, this);
     }
     g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
   }
   /**
    * update the pane
    */
   public void Update()
   {
     repaint();
   }
   /**
    * get color for an element in the table
    * @param PN  proton number of the element
    * @return  the colour the element should be painted
    */
   public static Color getColourFromProtonNumber(int PN)
   {
     int[] Blues = { 3, 11, 19, 37, 55, 87 };
     int[] Cyans = { 4, 12, 20, 38, 56, 88 };
     int[] Grays = { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 72, 73, 74, 75, 76, 77, 78, 79, 80, 104, 105, 106, 107, 108, 109, 110, 111, 112 };
     int[] LGrays = { 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103 };
     int[] Magentas = { 2, 10, 18, 36, 54, 86, 118 };
     int[] Reds = { 9, 17, 35, 53, 85, 117 };
     int[] Whites = { 1, 6, 7, 8, 15, 16, 34 };
     int[] Yellows = { 5, 14, 32, 33, 51, 52, 84 };
     int[] Greens = { 13, 31, 49, 50, 81, 82, 83, 113, 114, 115, 116 };
     for (int i : Blues) {
       if (PN == i) {
         return Color.BLUE;
       }
     }
     for (int i : Cyans) {
       if (PN == i) {
         return Color.CYAN;
       }
     }
     for (int i : Grays) {
       if (PN == i) {
         return Color.GRAY;
       }
     }
     for (int i : LGrays) {
       if (PN == i) {
         return Color.LIGHT_GRAY;
       }
     }
     for (int i : Magentas) {
       if (PN == i) {
         return Color.MAGENTA;
       }
     }
     for (int i : Reds) {
       if (PN == i) {
         return Color.RED;
       }
     }
     for (int i : Whites) {
       if (PN == i) {
         return Color.WHITE;
       }
     }
     for (int i : Yellows) {
       if (PN == i) {
         return Color.YELLOW;
       }
     }
     for (int i : Greens) {
       if (PN == i) {
         return Color.GREEN;
       }
     }
     return Color.white;
   }
   /**
    * get grid co ordinates from the proton number and group
    * @param  PN  proton number of the element
    * @param  Group  group of the element
    * @return  Point in the table 
    */
   public static Point getTableCoordsFromProtonNumber(int PN, int Group)
   {
     int X = 0;
     int Y = 0;
     if ((Group >= 1) && (Group <= 2)) {
       X = (Group - 1) * Element.getWidthHeight();
     } else if ((Group >= 3) && (Group <= 8)) {
       X = (Group + 9) * Element.getWidthHeight();
     } else if (PN <= 54) {
       X = (PN % 18 - 1) * Element.getWidthHeight();
     } else if ((PN >= 72) && (PN <= 86)) {
       X = (PN - 72 + 3) * Element.getWidthHeight();
     } else if ((PN >= 104) && (PN <= 118)) {
       X = (PN - 104 + 3) * Element.getWidthHeight();
     } else if ((PN >= 57) && (PN <= 71)) {
       X = (PN - 57 + 3) * Element.getWidthHeight();
     } else if ((PN >= 89) && (PN <= 103)) {
       X = (PN - 89 + 3) * Element.getWidthHeight();
     }
     if (PN <= 2) {
       Y = 0;
     } else if (PN <= 18) {
       Y = (int)Math.floor((PN - 2) / 8.5D + 1.0D) * Element.getWidthHeight();
     } else if ((PN <= 56) || (PN == 88) || (PN == 87)) {
       Y = ((int)(Math.floor(PN - 18) / 18.5D) + 3) * Element.getWidthHeight();
     } else if ((PN >= 72) && (PN <= 86)) {
       Y = 5 * Element.getWidthHeight();
     } else if ((PN >= 104) && (PN <= 118)) {
       Y = 6 * Element.getWidthHeight();
     } else if ((PN >= 57) && (PN <= 71)) {
       Y = 8 * Element.getWidthHeight();
     } else if ((PN >= 89) && (PN <= 103)) {
       Y = 9 * Element.getWidthHeight();
     }
     return new Point(X, Y);
   }
   /**
    * track mouse movement
    * @param e  class that contains informtion on the mouse
    */
   public void mouseMoved(MouseEvent e)
   {
     oldmouseX = e.getX();
     oldmouseY = e.getY();
     
     boolean onElement = false;
     for (Element el : getElements()) {
       if (el.getBoundingBox().contains(e.getX() - AnchorX, e.getY() - AnchorY))
       {
         selectedElement = el;
         
         onElement = true;
         
         break;
       }
     }
     if (!onElement) {
       selectedElement = null;
     }
   }
   /**
    * 
    * @return  the elements of the table
    */
   private ArrayList<Element> getElements()
   {
     return elements;
   }
   
   public void mouseReleased(MouseEvent e) {}
   
   public void mouseClicked(MouseEvent e) {}
   
   /**
    * handles the mouse click
    * @param e  see mousemoved
    */
   public void mousePressed(MouseEvent e)
   {
     if (selectedElement != null)
     {
       clickedElement = selectedElement;
       MainWin.win.infoPane.displayElement(clickedElement);
     }
   }
   
   public void mouseEntered(MouseEvent e) {}
   
   public void mouseExited(MouseEvent e) {}
   
   /**
    * handles the mouse moving with the button held down
    * @param e  see mousemoved
    */
   public void mouseDragged(MouseEvent e)
   {
     if ((e.getX() >= AnchorX) && 
       (e.getX() <= AnchorX + Periotable.getWidth(this)) && 
       (e.getY() >= AnchorY) && 
       (e.getY() <= AnchorY + Periotable.getHeight(this)))
     {
       AnchorX += e.getX() - oldmouseX;
       AnchorY += e.getY() - oldmouseY;
     }
     if (AnchorX < -Periotable.getWidth(this) + 20) {
       AnchorX = (-Periotable.getWidth(this) + 20);
     }
     if (AnchorY < -Periotable.getHeight(this) + 20) {
       AnchorY = (-Periotable.getHeight(this) + 20);
     }
     if (AnchorX > getWidth() - 20) {
       AnchorX = (getWidth() - 20);
     }
     if (AnchorY > getHeight() - 20) {
       AnchorY = (getHeight() - 20);
     }
     oldmouseX = e.getX();
     oldmouseY = e.getY();
   }
 }


