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


  public void characters(char[] chars, int startIndex, int length)  {
/*	  
	  System.out.println("In Characters method");
	  System.out.println("Start index:  " + startIndex);
	  System.out.println("Length:  " + length);
	  System.out.println("Device flag ==> " + deviceFlag );
	  if ( deviceFlag )  {
		  int si = startIndex;
		  int ei = startIndex + length;
		  char[] c = Arrays.copyOfRange(chars, si, ei); 
		  System.out.println("Extracted String is:  " + new String(c) );
		  System.out.println("About to set deviceFlat to False...");
		  deviceFlag = false;
	  }  */
  }
  
  /** 
   *  
   */

  public void endDocument() throws SAXException {
    String message = "Result:  " + count; 
    System.out.println(message);
  }
}