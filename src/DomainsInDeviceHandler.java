import org.xml.sax.*;
import org.xml.sax.helpers.*;


/** A SAX parser handler 
 *  
 *  
 */

public class DomainsInDeviceHandler extends DefaultHandler {
	
	DomainsInDeviceHandler()  {}
	
	DomainsInDeviceHandler(String targetDeviceID)  {
		deviceID = targetDeviceID;
	}
	
	private String deviceID = "";
	private boolean deviceFlag = false;
	private int count = 0;
	private String currDevice = "";

  /** 
   *  
   *  
   */
  
	public void startElement(String namespaceUri, String localName, String qualifiedName,
                           Attributes attributes)  throws SAXException {
		if ( !this.deviceFlag )  {
			if (qualifiedName.equalsIgnoreCase("devices") )  {
//				System.out.println("What is the current device name...  I know, it's ==> " + attributes.getValue("xmi:id") );
				if ( attributes.getValue("xmi:id").equalsIgnoreCase(this.deviceID) )  {
					this.deviceFlag = true;
					this.currDevice = attributes.getValue("xmi:id");
				}
			}
		}
		else  {
			if ( qualifiedName.equalsIgnoreCase("domains")  )  {
				if (this.currDevice.equalsIgnoreCase(this.deviceID))  {
				count++;
				}
			}
		}
	}
	/** 
	*  
	*  
	*/

  public void endElement(String namespaceUri, String localName, String qualifiedName)
      		throws SAXException  {
	  if ( this.deviceFlag )  {
			if (qualifiedName.equalsIgnoreCase("devices") )  {
				this.deviceFlag = false;
			}
	  }
  }

  /** 
   *  
   *  
   */

  public void endDocument() throws SAXException {
    String message = "Result: " + count; 
    System.out.println(message);
  }
}