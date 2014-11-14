 package def;
 
 import java.util.ArrayList;
 
 public class Info
 {
	 /**
	  * Current type of information being read
	  */
   private infoTypes infoBeingRead;
   /**
    * Name of the element
    */
   private String Name;
   /**
    * Atomic mass of the element
    */
   private double Mass;
   /**
    * Proton number for the element
    */
   private int Proton;
   /**
    * 
    * @author Kyle
    * types of information that can be read into the class
    */
   public static enum infoTypes
   {
     NAME,  MASS,  PROTON,  DESC,  LINKS,  GROUP,  NONE;
   }
   
   /**
    * 
    * @return  current info being read
    */
   public infoTypes getInfoBeingRead()
   {
     return infoBeingRead;
   }
   
   /**
    * the description of the element
    */
   private ArrayList<String> descLines = new ArrayList<String>();
   /**
    * the links for the element
    */
   private ArrayList<String> links = new ArrayList<String>();
   /**
    * the group of the element
    */
   private int Group;
   
   /**
    * reads the info into a variable depenedent on <code>infoBeingRead</code>
    * @param info  a string conating the info being read
    */
   public void readInfo(String info)
   {
     switch (infoBeingRead)
     //Check which info is set to be read
     {
     case DESC: 
       getDescLines().add(info);
       break;
     case GROUP: 
       setGroup(Integer.parseInt(info));
       
       break;
     case LINKS: 
       getLinks().add(info);
       break;
     case MASS:
    	 setMass(Double.parseDouble(info));
       break;
     case NAME: 
       setName(info);
       break;
     case NONE: 
       
       break;
	case PROTON:
		setProton(Integer.parseInt(info));
		break;
	default:
		break;
     }
   }
   /**
    * 
    * @return  Name of info class parent element
    */
   public String getName()
   {
     return Name;
   }
   /**
    * sets name of element to <code>name</code>
    * @param name 
    */
   public void setName(String name)
   {
     this.Name = name;
   }
   /**
    * 
    * @return  Mass of info class parent element
    */
   public double getMass()
   {
     return Mass;
   }
   /**
    * sets mass of element to <code>mass</code>
    * @param mass
    */
   public void setMass(double mass)
   {
     this.Mass = mass;
   }
   /**
    * 
    * @return  Proton number of info class parent element
    */
   public int getProton()
   {
     return Proton;
   }
   /**
    * sets Proton number of element to <code>proton</code>
    * @param mass
    */
   public void setProton(int proton)
   {
     this.Proton = proton;
   }
   /**
    * 
    * @return  Description of info class parent element
    */
   public ArrayList<String> getDescLines()
   {
     return descLines;
   }
   /**
    * sets description of element to <code>descLines</code>
    * @param mass
    */
   public void setDescLines(ArrayList<String> descLines)
   {
     this.descLines = descLines;
   }
   /**
    * 
    * @return  Links of info class parent element
    */
   public ArrayList<String> getLinks()
   {
     return links;
   }
   /**
    * sets links for th  element to <code>links</code>
    * @param mass
    */
   public void setLinks(ArrayList<String> links)
   {
     this.links = links;
   }
   /**
    * 
    * @return  Group of info class parent element
    */
   public int getGroup()
   {
     return Group;
   }
   /**
    * sets group of element to <code>group</code>
    * @param mass
    */
   public void setGroup(int group)
   {
     this.Group = group;
   }
   /**
    * sets the type of info to be read to <code>infoBeingRead</code>
    * @param mass
    */
   public void setInfoBeingRead(infoTypes infoBeingRead)
   {
     this.infoBeingRead = infoBeingRead;
   }
 }


