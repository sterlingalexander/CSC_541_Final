package com.smalexa2.csc541;
import java.io.FileNotFoundException;
import java.util.*;

import javax.xml.parsers.*;
import org.xml.sax.helpers.DefaultHandler;

public class FinalProject extends DefaultHandler  {

	/** 
	 *  CSC 541 Final Project --> Project 2
	 *  	Sterling Alexander
	 *  	Stephen Key
	 */
	
	public static void main(String[] args) {  // edgeconfig_1.xml
		
		String input = "";
		String fileToParse = "";
		ArrayList<String> tokens = new ArrayList<String>();
		Scanner scan = new Scanner(System.in);
		System.out.println("\nReady to accept input...\n");
		while ( true ) {
			input = scan.nextLine();
			StringTokenizer tokenInput = new StringTokenizer(input);
			assignTokens(tokenInput, tokens);
			try {
				if ( tokens.get(0).equalsIgnoreCase("CONFIGURATION") )  {
					fileToParse = tokens.get(1);
					System.out.println( fileToParse + " will now be parsed." );
				}
				else if ( tokens.get(0).equalsIgnoreCase("DPDevice") )  {
					if ( tokens.size() == 1 ) {
						count( tokens.get(0), fileToParse );
					}
					else if ( tokens.size() == 3 )  {
						countDomains( tokens.get(1), fileToParse );
					}
					else if ( tokens.size() == 5 )  {
						countPoliciesInDeviceAndDomain(tokens.get(1), tokens.get(3), fileToParse);
					}
					else  {
						usage();
					}
				}
				else if ( tokens.get(0).equalsIgnoreCase("DeploymentPolicy") )  {
					if ( tokens.size() == 3 )  {
						countSEPointsInDomain(tokens.get(1), fileToParse);
					}
					else if ( tokens.size() == 4 )  {
						listSEPointAttributesInPolicy(tokens.get(1), tokens.get(3), fileToParse);
					}
					else  {
						usage();
					}
				}
				else if ( tokens.get(0).equalsIgnoreCase("end") || 
							tokens.get(0).equalsIgnoreCase("exit") )  {
					System.exit(0);
				}
			}
			catch (IndexOutOfBoundsException e)  {
				usage();
			}
		}
	}
	
	/** 
	 *  Parses input into tokens.
	 */
	
	public static void assignTokens(StringTokenizer tokenInput, ArrayList<String> tokens)  {
		
		tokens.clear();
		while ( tokenInput.hasMoreTokens() )  {
			tokens.add( tokenInput.nextToken() );
		}
	}
	
	/** 
	 *  Instantiates handler to count number of DPDevices in 
	 *  	the supplied XML file and then passes handler
	 *  	to SAX factory parser.
	 */
	
	public static void count(String target, String fileToParse)  {
		
		DefaultHandler handler = new DeviceCountHandler();
		parseWithHandler(handler, fileToParse);
	}
	
	/** 
	 *  Instantiates handler to count number of DPDomains in a DPDevice from 
	 *  	the supplied XML file and then passes handler
	 *  	to SAX factory parser.
	 */
	
	public static void countDomains( String deviceName, String fileToParse )  {

		DefaultHandler handler = new DomainsInDeviceHandler(deviceName);
		parseWithHandler(handler, fileToParse);
	}

	/** 
	 *  Instantiates handler to count number of DeploymentPolicies in
	 *  	a particular DPDevice and DPDomain from  
	 *  	the supplied XML file and then passes handler
	 *  	to SAX factory parser.
	 */

	
	public static void countPoliciesInDeviceAndDomain( String deviceName, 
			String domainName, String fileToParse )  {

		DefaultHandler handler = new PoliciesInDeviceAndDomainHandler(deviceName, domainName);
		parseWithHandler(handler, fileToParse);
	}
	
	/** 
	 *  Instantiates handler to count number of ServiceEndPoints in a particular 
	 *  	DPDomain from the supplied XML file and then passes handler
	 *  	to SAX factory parser.
	 */

	
	public static void countSEPointsInDomain( String domainName, String fileToParse )  {

		DefaultHandler handler = new CountSEPointsInPolicyHandler(domainName);
		parseWithHandler(handler, fileToParse);
	}

	/** 
	 *  Instantiates handler to list all attributes of a particular ServiceEndPoint
	 *  	that is associated with a DeploymentPolicy from the supplied XML file 
	 *  	and then passes handler to SAX factory parser.
	 */
	
	public static void listSEPointAttributesInPolicy(String policyID, String endPointID, 
			String fileToParse)  {
		
		DefaultHandler handler = new PolicyEndPointAttributesHandler(policyID, endPointID);
		parseWithHandler(handler, fileToParse);
	}
	
	/** 
	 *  Gets parser from factory and supplies it with appropriate handler to  
	 *  	parse the supplied XML file.
	 */
	
	public static void parseWithHandler(DefaultHandler handler, String fileToParse)  {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
	    try {
	      SAXParser parser = factory.newSAXParser();
	      parser.parse(fileToParse, handler);
	    }
	    catch (FileNotFoundException e)  {
	    	System.out.println("\nThe file specified using the \"Configuration\" command is " +
	    			"invalid.\n");
	    }
	    catch(Exception e) {
	      String errorMessage = "Error parsing " + fileToParse + ": " + e;
	      System.err.println(errorMessage);
	      e.printStackTrace();
	    }
	}
	
	public static void usage()  {
		
		System.out.println("============================================================");
		System.out.println("Usage:");
		System.out.println("============================================================");		
		System.out.println("Configuration <xml_file_to_parse>");
		System.out.println("\tLoads XML file to parse.\n");
		System.out.println("DPDevice");
		System.out.println("\tReturns a count of all DPDevices in the configuration.\n");
		System.out.println("DPDevice <device_name> DPDomain");
		System.out.println("\tReturns a count of all DPDomains associated with the \n" +
				"\trequested DPDevice.\n");
		System.out.println("DPDevice <device_name> DPDomain <domain_id> DeploymentPolicy");
		System.out.println("\tReturns a count of all Deployment Policies associated with the \n" +
				"\trequested DPDevice and DPDomain.\n");
		System.out.println("DeploymentPolicy <policy_id> Serviceendpoint");
		System.out.println("\tReturns a count of all service end points in the requested \n" +
				"\tdeployment policy.\n");
		System.out.println("DeploymentPolicy <policy_id> Serviceendpoint <serviceendpoint_id>");
		System.out.println("\tLists all the attributes of the requested service end point that \n" +
				"\tis associated with the requested deployment policy.");
		System.out.println("End");
		System.out.println("\tExits the program.");
		System.out.println("============================================================");
	}
}
