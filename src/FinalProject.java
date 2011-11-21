import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class FinalProject extends DefaultHandler  {

	/**
	 * @param args
	 */
	public static void main(String[] args) {  // edgeconfig_1.xml
		
		String input = "";
		String fileToParse = "";
		ArrayList<String> tokens = new ArrayList<String>();
		Scanner scan = new Scanner(System.in);
		
		while ( true ) {
			input = scan.nextLine();
			StringTokenizer tokenInput = new StringTokenizer(input);
			assignTokens(tokenInput, tokens);
			System.out.println(tokens);
			if ( tokens.get(0).equalsIgnoreCase("CONFIGURATION") )  {
				fileToParse = tokens.get(1);
			}
			else if ( tokens.get(0).equalsIgnoreCase("DPDevice") )  {
				if ( tokens.size() == 1 ) {
					System.out.println("Should print the number of DPDevices");
					count( tokens.get(0), fileToParse );
				}
				else if ( tokens.size() == 3 )  {
					System.out.println("Should print number of domains for DPDevices");
					countDomains( tokens.get(1), fileToParse );
				}
				else if ( tokens.size() == 5 )  {
					System.out.println("Should print number of deployment policies in" +
							"DPDevice id# and DPDomain id#");
					countPoliciesInDeviceAndDomain(tokens.get(1), tokens.get(3), fileToParse);
				}
				else  {
					System.out.println("Wrong number of args");
				}
			}
			else if ( tokens.get(0).equalsIgnoreCase("DeploymentPolicy") )  {
				if ( tokens.size() == 3 )  {
					System.out.println("Should list number of serivce end points in policy passed in");
				}
				else if ( tokens.size() == 5 )  {
					System.out.println("Should list all attributes of serviceendpoint #id of " +
							"DeploymentPolicy #id");
				}
				else  {
					System.out.println("Wrong number of args");
				}
			}
		}
	}
	
	public static void assignTokens(StringTokenizer tokenInput, ArrayList<String> tokens)  {
		
		tokens.clear();
		while ( tokenInput.hasMoreTokens() )  {
			tokens.add( tokenInput.nextToken() );
		}
	}
	
	public static void count(String target, String fileToParse)  {
		
		DefaultHandler handler = new DeviceCountHandler();
		SAXParserFactory factory = SAXParserFactory.newInstance();
	    try {
	      SAXParser parser = factory.newSAXParser();
	      parser.parse(fileToParse, handler);
	    } catch(Exception e) {
	      String errorMessage =
	        "Error parsing " + fileToParse + ": " + e;
	      System.err.println(errorMessage);
	      e.printStackTrace();
	    }	
	}
	
	public static void countDomains( String deviceName, String fileToParse )  {

		DefaultHandler handler = new DomainsInDeviceHandler(deviceName);
		SAXParserFactory factory = SAXParserFactory.newInstance();
	    try {
	      SAXParser parser = factory.newSAXParser();
	      parser.parse(fileToParse, handler);
	    } catch(Exception e) {
	      String errorMessage =
	        "Error parsing " + fileToParse + ": " + e;
	      System.err.println(errorMessage);
	      e.printStackTrace();
	    }

	}

	public static void countPoliciesInDeviceAndDomain( String deviceName, 
			String domainName, String fileToParse )  {

		DefaultHandler handler = new PoliciesInDeviceAndDomainHandler(deviceName, domainName);
		SAXParserFactory factory = SAXParserFactory.newInstance();
	    try {
	      SAXParser parser = factory.newSAXParser();
	      parser.parse(fileToParse, handler);
	    } catch(Exception e) {
	      String errorMessage =
	        "Error parsing " + fileToParse + ": " + e;
	      System.err.println(errorMessage);
	      e.printStackTrace();
	    }	

	}

	
	// PoliciesInDeviceAndDomainHandler
	
    /*
        if (args.length == 1)  {
        	try  {
        		// Open the file that is the first command line parameter
        		FileInputStream fstream = new FileInputStream(args[0]);

        		// Convert our input stream to a DataInputStream
        		DataInputStream in = new DataInputStream(fstream);

                // Continue to read lines while there are still none left to read
        		while (in.available() !=0)  {
                // Print file line to screen
        			System.out.println (in.readLine());
        		}

        		in.close();
        	} 
        		catch (Exception e)  {
        			System.err.println("File input error");
        		}
        }
        else
        	System.out.println("Invalid parameters");
	}
	*/
}
