import org.xml.sax.*;
import org.xml.sax.helpers.*;


/** 
 *  
 *  
 *  
 */

public class DeviceCountHandler extends DefaultHandler {
	
	DeviceCountHandler()  {}
	
	private int currentCount = 0;

  /** 
   *  
   *  
   */
  
	public void startElement(String namespaceUri, String localName, String qualifiedName,
                           Attributes attributes)  throws SAXException {
		if (qualifiedName.equals("devices"))  {
			currentCount++;
		} 
	}
	/** 
	*  
	*  
	*/

  public void endElement(String namespaceUri, String localName, String qualifiedName)
      		throws SAXException  {
	  if (qualifiedName.equals("devices")) {
		  ;
	  } 
  }

  /** 
   *  
   */

  /*
  public void characters(char[] chars, int startIndex, int endIndex)  {
	  
	  if ( collectCount )  {
		  currentCount++;
	  }
  }
*/  
  /** 
   *  
   */

  public void endDocument() throws SAXException {
    String message = "Result:  " + currentCount; 
    System.out.println(message);
  }
}