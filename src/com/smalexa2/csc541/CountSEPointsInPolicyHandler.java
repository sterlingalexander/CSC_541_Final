package com.smalexa2.csc541;
import java.util.HashSet;
import java.util.Set;
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
	Set<String> endPoints = new HashSet<String>();

  /** 
   *  
   *  
   */
  
	public void startElement(String namespaceUri, String localName, String qualifiedName,
                           Attributes attributes)  throws SAXException {
		if ( !this.deployPolicyFlag )  {
			if (qualifiedName.equalsIgnoreCase("deploymentPolicy") )  {
				this.currDeployPolicy = attributes.getValue("xmi:id");
//				System.out.println("What is the current device name...  I know, it's ==> " + attributes.getValue("xmi:id") );
				if ( attributes.getValue("xmi:id").equalsIgnoreCase(this.deploymentPolicy) )  {
					this.deployPolicyFlag = true;
				}
			}
		}
		else  {
			if ( qualifiedName.equalsIgnoreCase("serviceend-point")  )  {
				if (this.currDeployPolicy.equalsIgnoreCase(this.deploymentPolicy))  {
					endPoints.add( attributes.getValue("xmi:id") );					
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

  public void endDocument() throws SAXException {
    String message = "Result: " + endPoints.size();
    System.out.println(message);
  }
}