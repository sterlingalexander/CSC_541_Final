import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.StringTokenizer;
import javax.swing.*;

/** A SAX parser handler that keeps track of the number
 *  of copies of Core Web Programming ordered. Entries
 *  that look like this will be recorded:
 *  <XMP>
 *    ...
 *    <count>23</count>
 *    <book>
 *      <isbn>0130897930</isbn>
 *      ...
 *    </book>
 *  </XMP>
 *  All other entries will be ignored -- different books,
 *  orders for yachts, things that are not even orders, etc.
 */

public class DeviceCountHandler extends DefaultHandler {
	
	DeviceCountHandler()  {}
	
	DeviceCountHandler(String target)  {
		findTarget = target;
	}
	
	private String findTarget = "";
	private boolean collectCount = false;
	private int currentCount = 0;
	private int totalCount = 0;

  /** If you start the "count" or "isbn" elements,
   *  set a flag so that the characters method can check
   *  the value of the tag body.
   */
  
	public void startElement(String namespaceUri, String localName, String qualifiedName,
                           Attributes attributes)  throws SAXException {
		if (qualifiedName.equals("devices"))  {
			currentCount++;
		} 
	}
	/** If you end the "count" or "isbn" elements,
	*  set a flag so that the characters method will no
	*  longer check the value of the tag body.
	*/

  public void endElement(String namespaceUri, String localName, String qualifiedName)
      		throws SAXException  {
	  if (qualifiedName.equals("devices")) {
		  ;
	  } 
  }

  /** Since the "count" entry comes before the "book"
   *  entry (which contains "isbn"), we have to temporarily
   *  record all counts we see. Later, if we find a
   *  matching "isbn" entry, we will record that temporary
   *  count.
   */

  /*
  public void characters(char[] chars, int startIndex, int endIndex)  {
	  
	  if ( collectCount )  {
		  currentCount++;
	  }
  }
*/  
  /** Report the total number of copies ordered.
   *  Gently chide underachievers.
   */

  public void endDocument() throws SAXException {
    String message = "Result:  " + currentCount; 
    System.out.println(message);
  }
}