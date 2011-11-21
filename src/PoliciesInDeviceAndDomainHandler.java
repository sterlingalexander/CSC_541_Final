import org.xml.sax.*;
import org.xml.sax.helpers.*;


/** A SAX parser handler 
 *  
 *  
 */

public class PoliciesInDeviceAndDomainHandler extends DefaultHandler {
	
	PoliciesInDeviceAndDomainHandler()  {}
	
	PoliciesInDeviceAndDomainHandler(String targetDeviceID, String targetDomainID)  {
		deviceID = targetDeviceID;
		domainID = targetDomainID;
	}
	
	private String deviceID = "";
	private String domainID = "";
	private boolean deviceFlag = false;
	private boolean domainFlag = false;
	private int count = 0;
	private String currDevice = "";
	private String currDomain = "";

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
		if ( !this.domainFlag )  {
			if ( attributes.getValue("xmi:id").equalsIgnoreCase(this.domainID) )  {
				this.domainFlag = true;
				this.currDomain = attributes.getValue("xmi:id");
			}
		}
		
		if ( this.domainFlag && this.deviceFlag ) {
			if ( qualifiedName.equalsIgnoreCase("deploymentPolicy")  )  {
				if (this.currDevice.equalsIgnoreCase(this.deviceID) && 
						this.currDomain.equalsIgnoreCase(this.domainID) )  {
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
	  if ( this.domainFlag )  {
		  if ( qualifiedName.equalsIgnoreCase("domains") )  {
			  this.domainFlag = false;
		  }
	  }
  }

  /** 
   *  
   *  
   */


  public void characters(char[] chars, int startIndex, int length)  {

  }
  
  /** 
   *  
   */

  public void endDocument() throws SAXException {
    String message = "Result: " + count; 
    System.out.println(message);
  }
}