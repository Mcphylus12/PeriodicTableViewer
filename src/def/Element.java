 package def;
 
 import java.awt.Point;
 import java.awt.Rectangle;
 
 public class Element
 {
	 /**
	  * Side length of a cell in the table 
	  */
   private static final int WIDTHHEIGHT = 96;
   /**
    * Holds the positions and dimensions of the element in the table
    */
private Rectangle BoundingBox;
/**
 * Class containing info for the element
 */
   private Info info;
   /**
    * creates an element
    * @param P  position of the element on the table
    * @param i  info class to be used for the element
    */
   public Element(Point P, Info i)
   {
     this.BoundingBox = new Rectangle((int)P.getX(), (int)P.getY(), WIDTHHEIGHT, WIDTHHEIGHT);
     this.info = i;
   }
   
   /**
    * 
    * @return  Position of the element
    */
   public Rectangle getBoundingBox()
   {
     return this.BoundingBox;
   }
   
   public Info getInfo()
   {
     return this.info;
   }
   
   public static int getWidthHeight()
   {
     return WIDTHHEIGHT;
   }
 }


