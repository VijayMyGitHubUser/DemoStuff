

import org.apache.axis2.client.ServiceClient;

import com.ca.jcs.sfdc.partner.SforceServiceStub;


import com.ca.jcs.sfdc.partner.SforceServiceStub;
import com.ca.jcs.sfdc.partner.UnexpectedErrorFault;
import com.ca.jcs.sfdc.partner.SforceServiceStub.InvalidateSessions;



public class SFDCConnection {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
        //System.out.println("testss");
	    
	    String user  = "sfdctest@cam.com";
	    String password = "NotAllowed@1";
	    String token = "gSsjO8HVRIjkpscyq3FsiAAh";
	    String url   = "" ;

	    boolean doNotUseProxy;
	    String proxyServer;
	    int    proxyServerPort;
	    String proxyUserDomain;
	    String proxyUserName;
	    String proxyUserPassword;
	    
	    SforceServiceStub.LoginResult loginResult = null;
	    String patternString = "https://[^/?]+\\.(sales|visual\\.)force\\.com/services/(S|s)(O|o)(A|a)(P|p)/(u|c)/.*";

	    try{
	    	SforceServiceStub session = null;
	    	//System.out.println("testinngg ");
	    	
	    	if(url != null && url.length() > 0)
	        {
	            if(url.matches(patternString))
	            {
	                session = new SforceServiceStub(url);
	            }
	            else
	            {
	                System.out.println("Cannot login to Salesforce.com, invalid URL.");
	                return;
	            }
	        }
	    	else{
	        	session = new SforceServiceStub();
	        }

        //System.out.println("testing -1");
        String pwd = password;
        if(token != null && token.length() > 0)
        {
            pwd += token;
        }

       // System.out.println("testing -2");
        ServiceClient client = session._getServiceClient();
        
        //System.out.println("testing -3");
        
        
        SforceServiceStub.Login login = new SforceServiceStub.Login();
        login.setUsername(user);
        login.setPassword(pwd);
	    
        try
        {
            
        	SforceServiceStub.LoginResponse response = session.login(login, null, null);
            loginResult = response.getResult();
            System.out.println("****************************************************************************");
            System.out.println("User Name ="+ user);
            System.out.println("Token is ="+ token);
            System.out.println();
            System.out.println("CONNECTIN HAS ESTABLISHED. ");
            
            System.out.println("****************************************************************************");
        }
        catch (Exception ex)
        {
        	System.out.println("connection Failed, " + ex.getMessage()+"User name or Password is incorrect");
        	//ex.printStackTrace();
        }
  	   }catch(Exception e){
  		System.out.println(e.getMessage());   
  	   }
  	   }
	    
}
