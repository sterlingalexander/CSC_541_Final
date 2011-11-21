import org.xml.sax.*;
import org.xml.sax.helpers.*;


/** A SAX parser handler 
 *  
 *  
 */

public class CountSEPointsInPolicyHandler extends DefaultHandler {
	
	CountSEPointsInPolicyHandler()  {}
	
	CountSEPointsInPolicyHandler(String targetDeploymentPolicy)  {
		deploymentPolicy = targetDeploymentPolicy;
	}
	
	private String deploymentPolicy = "";
	private boolean deployPolicyFlag = false;
	private int count = 0;
	private String currDeployPolicy = "";

  /** 
   *  
   *  
   */
  
	public void startElement(String namespaceUri, String localName, String qualifiedName,
                           Attributes attributes)  throws SAXException {
		if ( !this.deployPolicyFlag )  {
			if (qualifiedName.equalsIgnoreCase("deploymentPolicy") )  {
//				System.out.println("What is the current device name...  I know, it's ==> " + attributes.getValue("xmi:id") );
				if ( attributes.getValue("xmi:id").equalsIgnoreCase(this.deploymentPolicy) )  {
					this.deployPolicyFlag = true;
					this.currDeployPolicy = attributes.getValue("xmi:id");
				}
			}
		}
		else  {
			if ( qualifiedName.equalsIgnoreCase("serviceend-point")  )  {
				if (this.currDeployPolicy.equalsIgnoreCase(this.deploymentPolicy))  {
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
	  if ( this.deployPolicyFlag )  {
			if (qualifiedName.equalsIgnoreCase("deploymentPolicy") )  {
				this.deployPolicyFlag = false;
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
    String message = "Result:  " + count; 
    System.out.println(message);
  }
}