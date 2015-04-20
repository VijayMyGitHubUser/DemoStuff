import lotus.domino.*;
import java.util.*;
import java.io.*;

public class TestLND {

	private String organization = "";
	private String host = "";
	private String username = "";
	private String password = "";
	private String idFile = "";
	private boolean ssl = false;
	private String formula = "";
	private Session session = null;
	private String certID = "";
	private String certPwd = "";
	private String template = "";
	private String account = "";
	private String mailServer ="";

	private final String OULocation = "e:\\ous\\";
	private final String ACCLocation = "e:\\user\\";
	private final String TOPOrg = "caorg";

	static final int SUMMARY   = 1;
	static final int NAMES     = 2;
    	static final int AUTHORS   = 4;
    	static final int PROTECTED = 8;
    	static final int SIGNED    = 16;

	public static void main(String[] args) {

		String organization = "";
		String host = "";
		String username = "";
		String password = "";
		String idFile = "";
		String formula = "";
		String certID = "";
		String certPwd = "";
		String template = "";
		String account = "";
		String mailServer = "";
		boolean ssl = false;
		for(int i = 0; i < args.length; i++) {
			//System.out.println(args[i] + " " + args[i+1]);
			if("-h".equals(args[i])) {
				if((i + 1) < args.length) {
					host = args[i + 1];
					i++;
					continue;
				}
			} else if("-u".equals(args[i])) {
				if((i + 1) < args.length) {
					username = args[i + 1];
					i++;
					continue;
				}
			} else if("-p".equals(args[i])) {
				if((i + 1) < args.length) {
					password = args[i + 1];
					i++;
					continue;
				}
			} else if("-s".equals(args[i])) {
				if((i + 1) < args.length) {
					String tmp = args[i + 1];
					if("yes".equalsIgnoreCase(tmp) || "y".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp)) {
						ssl = true;
					} else {
						ssl = false;
					}
				}
			} else if("-i".equals(args[i])) {
				if((i + 1) < args.length) {
					idFile = args[i + 1];
					i++;
					continue;
				}
			} else if("-o".equals(args[i])) {
				if((i + 1) < args.length) {
					organization = args[i + 1];
					i++;
					continue;
				}
			} else if("-c".equals(args[i])) {
				if((i + 1) < args.length) {
					certID = args[i + 1];
					i++;
					continue;
				}
			} else if("-cp".equals(args[i])) {
				if((i + 1) < args.length) {
					certPwd = args[i + 1];
					i++;
					continue;
				}
			} else if("-t".equals(args[i])) {
				if((i + 1) < args.length) {
					template = args[i + 1];
					i++;
					continue;
				}
			} else if("-a".equals(args[i])) {
				if((i + 1) < args.length) {
					account = args[i + 1];
					i++;
					continue;
				}
			} else if("-m".equals(args[i])) {
				if((i + 1) < args.length) {
					mailServer = args[i + 1];
					i++;
					continue;
				}
			}
		}
		
		if("".equals(organization) || "".equals(host) || "".equals(username) || "".equals(password) || "".equals(certID) || "".equals(certPwd) || "".equals(template) || "".equals(account) || "".equals(mailServer)) {
			System.out.println("Usage: java TestLND -o <organization> -h <hostname:port> -u <username> -p <password> -c <cert.id location> -cp <cert.id password> -t <mail template> -a <account to be created>");
			return;
		}

		TestLND tlnd = new TestLND(organization, host, username, password, ssl, certID, certPwd, template, account, mailServer);
		tlnd.control();
		
	}

	public TestLND(String organization, String host, String username, String password, boolean ssl, String certID, String certPwd, String template, String account, String mailServer) {
		this.organization = organization;
		this.host = host;
		this.username = username;
		this.password = password;
		this.certID = certID;
		this.certPwd = certPwd;
		this.template = template;
		this.account = account;
		this.ssl = ssl;
		this.mailServer = mailServer;
	}

	public void control() {
		connect();
		System.out.println();
		//boolean result = createAccount(account);
		//if(result) {
			//System.out.println("result = "+ result);
			//createMailInBackground(account,"test");
			
			//System.out.println("With CN=AU-XUVZU01-LND2/O=caorg ");
			//createMailInBackground(account, "CN=AU-XUVZU01-LND2/O=caorg");
			//System.out.println("With CN=AU-XUVZU01-LND3/O=caorg ");
			//createMailInBackground(account, "CN=AU-XUVZU01-LND3/O=caorg");
		//}
		//disconnect();
	}

	public boolean connect() {
		String[] hostport = host.split(":");
		String IOR = "";
		try {
			if(hostport.length == 2) {
				System.out.println("Getting IOR from " + hostport[0]);
				IOR = NotesFactory.getIOR(hostport[0]);
				host = hostport[0];
			} else if(hostport.length == 1 ){
				System.out.println("Getting IOR from " + hostport[0]);
				IOR = NotesFactory.getIOR(hostport[0]);
			} else {
				System.out.println("Invalid hostname.");
				System.out.println("Usage: java TestLND -h <hostname:port> -u <username> -p <password> -s <ssl yes|no>");
				return false;
			}
			session = NotesFactory.createSessionWithIOR(IOR, username, password);
			System.out.println("********************************************************************************");
			System.out.println("Connected to the Domino server:" + host);
			System.out.println("*********************************************************************************");
		} catch (Exception e) {
			String msg = e.toString();
			if(msg.indexOf("Could not get IOR from Domino Server") > 0) {
				System.out.println(msg);
				try {
					if(hostport.length == 2) {
						System.out.println("Getting IOR from " + hostport[0] + ":" + hostport[1]);
						IOR = NotesFactory.getIOR(hostport[0] + ":" + hostport[1]);
						host = hostport[0];
						session = NotesFactory.createSessionWithIOR(IOR, username, password);
						System.out.println("Connected to the Domino server:" + host);
					} else {
						e.printStackTrace();
						return false;
					}
				} catch (Exception ee) {
					System.out.println(ee);
					e.printStackTrace();
					return false;
				}
			} else {
				System.out.println(e);
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public void disconnect() {
		try {
			if(session != null) {
				session.recycle();
				System.out.println("Disconnected.");
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public void createAccounts(int max, String suffix, String ou, String uou) {
		Registration reg = null;
		try {			
			reg = session.createRegistration();
			reg.setRegistrationServer(host);
			reg.setCreateMailDb(false);
			String pwd = "";
			reg.setCertifierIDFile(certID);
			DateTime dt = session.createDateTime("Today");
			dt.setNow();
			dt.adjustYear(100);
			reg.setExpiration(dt);
			reg.setIDType(Registration.ID_HIERARCHICAL);
			reg.setMinPasswordLength(5);
			reg.setNorthAmerican(true);
			reg.setRegistrationLog("log.nsf");
			reg.setUpdateAddressBook(true);
			reg.setStoreIDInAddressBook(true);
			if(!"".equals(uou)) {
				reg.setOrgUnit(uou);
			}

			for(int i = 0; i < max; i++) {
				String accName = suffix + i;
				String accId = ACCLocation + accName + ".id";
				/*
				 * Create Account
				 */
				boolean result = reg.registerNewUser(
						accName,
						accId,
						"CN=xuvzu01-lnd85-1/O=caorg",
						"",
						"",
						"certpassword",
						"",
						"",
						accName + ".nsf",
						"",
						"accountpassword",
						"",
						"");

				System.out.println("Creating account " + accName + ": " + result);

				/*
				 * Get Expiration
				 *
				DateTime dt2 = reg.getExpiration();
				System.out.println(dt2.toString());
				*/
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public boolean createAccount(String name) {
		Registration reg = null;
		try {
			
			
			
			reg = session.createRegistration();
			reg.setRegistrationServer(host);
			
			//reg.setMailInternetAddress("VijaySagar@ca.com");
			
			reg.setCreateMailDb(false);
			String pwd = "";
			reg.setCertifierIDFile(certID);
			DateTime dt = session.createDateTime("Today");
			dt.setNow();
			dt.adjustYear(100);
			reg.setExpiration(dt);
			reg.setIDType(Registration.ID_HIERARCHICAL);
			reg.setMinPasswordLength(5);
			reg.setNorthAmerican(true);
			reg.setRegistrationLog("log.nsf");
			reg.setUpdateAddressBook(true);
			reg.setStoreIDInAddressBook(true);

			String accName = name;
			String accId = name + ".id";
			/*
			 * Create Account
			 */
			Vector cluster = new Vector();
			//cluster.add("CN=AU-XUVZU01-LND1/O=caorg");
			//cluster.add("CN=AU-XUVZU01-LND2/o=caorg");
			//cluster.add("CN=AU-XUVZU01-LND3/o=caorg");
			
			System.out.println(name);
			System.out.println(accName);
			System.out.println(host);
			System.out.println(organization);
			
			//reg.setCreateMailDb(true);
			reg.setMailReplicaServers(cluster);
			boolean result = reg.registerNewUser(
					accName,
					accId,
					"CN=" + host + "/O=" + organization,
					name,
					"",
					certPwd,
					"",
					"",
					"",
					"",
					"P@$$w0rd",
					"",
					"");

			System.out.println("Creating account " + accName + ": " + result);
			return result;
			/*
			 * Get Expiration
			 *
			DateTime dt2 = reg.getExpiration();
			System.out.println(dt2.toString());
			*/
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return false;
	}

	public void createMailInBackground(String name, String otherServer) {
		try {
			String mailFile = "mail\\" + name + ".nsf";
			int acl = 1;
			int quota = 115;
			int threshold = 100;

			String homeServerCanonical = "CN=" + mailServer + "/O=" + organization;
			//String destinationServerCanonical = "CN=AU-XUVZU01-LND2/O=" + organization;
			Vector cluster = new Vector();
			//cluster.add("CN=AU-XUVZU01-LND1/O=caorg");
			//cluster.add("CN=AU-XUVZU01-LND2/o=caorg");
			//cluster.add("CN=AU-XUVZU01-LND3/o=caorg");
			
			
			String ouFullName = "O=" + organization;
			String userFullName = "CN=" + name + " " + name + "/O=" + organization;

			System.out.println("Opening AdminP.");
			final Database admindb = session.getDatabase(session.getServerName(), "admin4.nsf", false);
			Document createMail = admindb.createDocument();
			System.out.println("Setting attributes.");
			// Flag the document as the create mail proxy action using adminp
			relaceDocumentItem(createMail, "Form", "AdminRequest", SIGNED|SUMMARY);
			relaceDocumentItem(createMail, "ProxyAction", "24", SIGNED|SUMMARY);
			relaceDocumentItem(createMail, "ProxyProcess", "Adminp", SIGNED|SUMMARY);
			relaceDocumentItem(createMail, "Type", "AdminRequest", SIGNED|SUMMARY);

			// Set both the proxy server and proxy source server to the new user's mail server
			//relaceDocumentItem(createMail, "ProxyServer", homeServerCanonical, SIGNED|SUMMARY|NAMES);
			relaceDocumentItem(createMail, "ProxyServer", homeServerCanonical, SIGNED|SUMMARY|NAMES);
			relaceDocumentItem(createMail, "ProxySourceServer", homeServerCanonical, SIGNED|SUMMARY|NAMES);
			//relaceDocumentItem(createMail, "ProxyMailReplicaServers", destinationServerCanonical, SIGNED|SUMMARY|NAMES);
			relaceDocumentItem(createMail, "ProxyMailReplicaServers", cluster, SIGNED|SUMMARY|NAMES);
			//Enumeration venum = cluster.elements();
			//System.out.println("After Vector size is="+ cluster.size());
			//while(venum.hasMoreElements()){
				//System.out.println(" elements is "+venum.nextElement());
				//relaceDocumentItem(createMail, "ProxyMailReplicaServers", venum.nextElement(), SIGNED|SUMMARY|NAMES);
			//}
			/*
			System.out.println(" Before if 222 "+otherServer);
			if(otherServer.equals("CN=AU-XUVZU01-LND2/O=caorg")){
				System.out.println(" elements is 222 ");
				relaceDocumentItem(createMail, "ProxyMailReplicaServers", otherServer, SIGNED|SUMMARY|NAMES);
			}
			System.out.println(" Before if 333 "+otherServer);
			if(otherServer.equals("CN=AU-XUVZU01-LND3/O=caorg")){
				System.out.println(" elements is  333 ");
				relaceDocumentItem(createMail, "ProxyMailReplicaServers", otherServer, SIGNED|SUMMARY|NAMES);
			}
			
			*/
			// A number of items need to be set to the session's user
			String sessionUser = session.getUserName();
			relaceDocumentItem(createMail, "ProxyAuthor", session.createName(sessionUser).getCanonical(), SIGNED|SUMMARY|AUTHORS);
			relaceDocumentItem(createMail, "ProxyOriginatingAuthor", session.createName(sessionUser).getCanonical(), SIGNED|SUMMARY|AUTHORS);
			relaceDocumentItem(createMail, "$OnBehalfOf", session.createName(sessionUser).getCanonical(), SIGNED|SUMMARY|AUTHORS);
			relaceDocumentItem(createMail, "FullName", session.createName(sessionUser).getCanonical(), SIGNED|SUMMARY|AUTHORS);
			relaceDocumentItem(createMail, "ProxyOriginatingOrganization", ouFullName, SIGNED|SUMMARY|AUTHORS);

			// Set the mailfile related values ACL
			relaceDocumentItem(createMail, "ProxyDatabasePath", mailFile, SIGNED|SUMMARY);
			relaceDocumentItem(createMail, "ProxyMailfileAccesLevel", acl, SIGNED|SUMMARY);
			relaceDocumentItem(createMail, "ProxyTextItem1", template, SIGNED|SUMMARY);
			relaceDocumentItem(createMail, "ProxyNumItem1", quota, SIGNED|SUMMARY);
			relaceDocumentItem(createMail, "ProxyNumItem2", threshold, SIGNED|SUMMARY);

			relaceDocumentItem(createMail, "ProxyNameList", userFullName, SIGNED|SUMMARY|NAMES);
			
			// Sign and save the document
			createMail.sign();
			createMail.save(true, false);
			
		   
			
			
			
			System.out.println("Mail creation request sent. Please check the result in Domino Administrator");
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		
	}

	public void createOU(int max, String suffix, String topOU) {
		Registration reg = null;
		try {
			reg = session.createRegistration();
			reg.setRegistrationServer(host);
			reg.setCreateMailDb(false);
			if("caorg".equals(topOU)) {
				reg.setCertifierIDFile("C:\\Program Files\\IBM\\Lotus\\Domino\\data\\cert.id");
			} else {
				reg.setCertifierIDFile(OULocation + topOU + ".id");
			}
			DateTime dt = session.createDateTime("Today");
			dt.setNow();
			dt.adjustYear(100);
			reg.setExpiration(dt);
			reg.setIDType(Registration.ID_HIERARCHICAL);
			reg.setMinPasswordLength(5);
			reg.setNorthAmerican(true);
			reg.setRegistrationLog("log.nsf");
			reg.setUpdateAddressBook(true);
			reg.setStoreIDInAddressBook(true);

			for(int i = 100; i < 100 + max; i++) {
				String ouname = suffix + i;
				reg.setOrgUnit(ouname);
				boolean result = reg.registerNewCertifier(
						ouname,
						OULocation + ouname + ".id",
						"secret");
				System.out.println("OU creation: " + result);
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public void searchAccountsUnderOU(String ou) {
		try {
			
			
			ArrayList<String> results = searchOUList(ou, 2);
			String ous = "";
                        for(int i = 0; i < results.size(); i++) {
                            String name = results.get(i);
			    	//@matches(FullName;"CN=+{!/}/{!/}+{!/}/OU=OU1/O=caorg"))
				ous += " & !@Matches(FullName;\"CN=+{!/}/" + name + "\")";
				continue;
				/*
			    System.out.println(name);
                            String[] nodes = name.split("/");
                            if(nodes.length > 0) {
                                if(!"".equals(ous)) {
                                    ous += ":";
                                }
                                ous += "\"/" + nodes[0].toLowerCase() + "\"";
                            }
			    */
                        }

			Database db = session.getDatabase(host, "names.nsf");
			if(!db.isOpen()) db.open();

			//String searchFormula = "SELECT Type=\"Person\" & @Matches(FullName;\"cn=+{!/}+/+{!/}" + ou + "\") & !@Ends(@Left(@Lowercase(FullName);\"" + ou + "\");" + ous + ")";
			String searchFormula = "SELECT Type = \"Person\" & (@Matches(FullName; \"CN=+{!/}/O=caorg\") | @matches(FullName;\"CN=+{!/}/{!/}+{!/}/O=caorg\"))" + ous;

			
			/*
			String ous = "";
			for(int i = 3; i < 5; i++) {
				if(!"".equals(ous)) {
					ous += ":";
				}
				ous += "\"/ou=ou" + i + "\"";
			}
			String searchFormula = "SELECT Type=\"Person\" & @Matches(FullName;\"cn=+{!/}+/+{!/}/o=caorg\") & !@Ends(@Left(@Lowercase(FullName);\"/o=caorg\");" + ous + ")";
			*/
			
			System.out.println(searchFormula);
			System.out.println(searchFormula.length());
			DocumentCollection dc = db.search(searchFormula, null);
			System.out.println("Search account in names.nsf, and found " + dc.getCount() + " records");
			/*
			for (Document d = dc.getFirstDocument(); d != null; d = dc.getNextDocument()) {
				String fName = d.getFirstItem("FullName").getText();
				System.out.println(fName);
			}
			*/
			dc.recycle();
			db.recycle();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	/*
	 * upper: /ou=dev/o=caorg or /o=caorg
	 */
	private ArrayList<String> searchOUList(String upper, int max) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			Database db = session.getDatabase(host, "names.nsf");
			if(!db.isOpen()) db.open();
			String searchFormula = "SELECT Form = \"Certifier\" & CertificateType=\"0\" & @matches(FullName[1];\"OU={!/}+{!/}" + upper + "\")";
			System.out.println(searchFormula);
			DocumentCollection dc = db.search(searchFormula);
			System.out.println("Result Count: " + dc.getCount());
			int counter = 0;
			for (Document doc = dc.getFirstDocument(); doc != null; doc = dc.getNextDocument()) {
				if(counter >= max) break;
				String ouName = doc.getFirstItem("FullName").getText();
				System.out.println(ouName);
	                        result.add(ouName);
        	                doc.recycle();
				counter++;
			}
			db.recycle();
			dc.recycle();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return result;
	}

	public void readAccountProperty() {
		/*
		searchFormula = "SELECT Type = \"Person\" & (@Matches(FullName; \"CN=+{!/}/OU=ou1ou1/OU=OU1/O=caorg\"))";
					dc = db.search(searchFormula, null);
					System.out.println("Search account in names.nsf, and found " + dc.getCount() + " records");
					
					 * read document collections
					for (Document d = dc.getFirstDocument(); d != null; d = dc.getNextDocument()) {
						String fName = d.getFirstItem("FullName").getText();
						System.out.println(fName);
					}

					Document doc = dc.getFirstDocument();
					Vector items = doc.getItems();
					for(int j = 0; j < items.size(); j++) {
						Item item = (Item)(items.elementAt(j));
						System.out.println(item.getName() + ": ");
						System.out.println("===========================================");
						System.out.println(item.getValueString());
						System.out.println("===========================================");
						System.out.println("");
					}
					*/
	}


	//list all OUs
	public void searchAllOU() {
		try {
			Database db = session.getDatabase(host, "names.nsf");
			if(!db.isOpen()) db.open();
			String searchFormula = "SELECT Form = \"Certifier\" & CertificateType=\"0\" & @matches(FullName[1];\"OU={!/}+{!/}/O=caorg\")";
			System.out.println("Formula: \n" + searchFormula + "\n");
			DocumentCollection dc = db.search(searchFormula, null);
			if(dc.getCount() == 0) {
				System.out.println("No record is found.");
			}
			for (Document d = dc.getFirstDocument(); d != null; d = dc.getNextDocument()) {
				String fName = d.getFirstItem("FullName").getText();
				System.out.println(fName);
			}
			System.out.println("");
			db.recycle();
			dc.recycle();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public void checkFTIndex(String name) {
		try {
			Database db = session.getDatabase(host, name);
			if(db.isFTIndexed()) {
				System.out.println(db + " is Full Text Indexed.");
			} else {
				System.out.println(db + " is not Full Text Indexed.");
			}
			db.recycle();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public void searchInCertLog(String name, String OU) {
		try {
			String formula = "UserName=" + createDNForFormula(name, OU, false);
			System.out.println("Formula: " + formula);
			Database db = session.getDatabase(host, "certlog.nsf");
			printTime("Normal Search");
			DocumentCollection dc = db.search(formula);
			printTime("End");
			System.out.println("Matches: " + dc.getCount());
			for (Document d = dc.getFirstDocument(); d != null; d = dc.getNextDocument()) {
				String fn = d.getFirstItem("UserName").getText();
				String ed = d.getFirstItem("ExpirationDate").getText();
				System.out.println(fn + " Expiration Date: " + ed);
			}
			dc.recycle();
			db.recycle();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public void FTsearchInCertLog(String name, String OU) {
		try {
			String formula = createDNForFormula(name, OU, true);
			System.out.println("Formula: " + formula);
			Database db = session.getDatabase(host, "certlog.nsf");
			printTime("FTI Search");
			DocumentCollection dc = db.FTSearch(formula);
			printTime("End");
			System.out.println("Matches: " + dc.getCount());
			for (Document d = dc.getFirstDocument(); d != null; d = dc.getNextDocument()) {
				String fn = d.getFirstItem("UserName").getText();
				String ed = d.getFirstItem("ExpirationDate").getText();
				System.out.println(fn + " Expiration Date: " + ed);
			}
			dc.recycle();
			db.recycle();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public void deleteOU() {
		try {
			Database db = session.getDatabase(host, "names.nsf");
			if(!db.isOpen()) db.open();
				//SELECT Form = \"Certifier\" & CertificateType=\"0\" & @matches(FullName[1];\"OU=9+{!/}" + upper + "\")";
				//String searchFormula = "SELECT Form = \"Certifier\" & CertificateType=\"0\" & @matches(FullName[1];\"OU=ou" + i + "/O=caorg\")";
				//String searchFormula = "SELECT Form = \"Certifier\" & CertificateType=\"0\" & @matches(FullName[1];\"OU=ou2+{!/}/O=caorg\") & !@matches(FullName[1];\"OU=ou2/O=caorg\")";
				String searchFormula = "SELECT Form = \"Certifier\" & CertificateType=\"0\" & @matches(FullName[1];\"OU=ou9+{!/}/O=caorg\")";
				System.out.println("Formula: \n" + searchFormula + "\n");
				DocumentCollection dc = db.search(searchFormula, null);
				if(dc.getCount() == 0) {
					System.out.println("No record is found.");
				}
				for (Document d = dc.getFirstDocument(); d != null; d = dc.getNextDocument()) {
					String fName = d.getFirstItem("FullName").getText();
					System.out.println(fName);
					
					boolean remove = d.remove(true);
					if(remove) {
						System.out.println("Removed from domino directory.");
					} else {
						System.out.println("Cannot be removed from domino directory.");
					}
					d.recycle();
				}
				dc.recycle();
			db.recycle();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	private void relaceDocumentItem(Document doc, String key, Object value, int properties) throws NotesException {
		// Set the value
		Item docitem = doc.replaceItemValue(key, value);

		// Set the required item properties
		if ((SUMMARY & properties) > 0)
			docitem.setSummary(true);
		if ((NAMES & properties) > 0)
			docitem.setNames(true);
		if ((AUTHORS & properties) > 0)
			docitem.setAuthors(true);
		if ((PROTECTED & properties) > 0)
			docitem.setProtected(true);
		if ((SIGNED & properties) > 0)
			docitem.setSigned(true);
	}
	

	private String createDNForFormula(String name, String OU, boolean ft) {
		if(ft == true) {
			//full text index
			//Jerry W Walker/USA/CSC
			OU = OU.replaceAll("ou=|o=", "");
		}
		return name + OU;
	}

	public void printTime(String str) {
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR);
		int min = now.get(Calendar.MINUTE);
		int sec = now.get(Calendar.SECOND);
		int milli = now.get(Calendar.MILLISECOND);
		System.out.println(hour + ":" + min + ":" + sec + ":" + milli + "\t" + str);
	}
}
