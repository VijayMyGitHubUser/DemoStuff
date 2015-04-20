

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;




public class PreparedCodetoconnect {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		final String sapClient = "000";
		final String userId  = "SAP*";
		final String password  = "AdminQA1";
		final String systemNumber = "02";
		final String applicationServer = "/H/nwcluster";
		
		
		final String endPointName = "sap-EP5";
		String  language  = null;
		
		Properties connProperties = new Properties();
        

        connProperties.setProperty("jco.client.client", sapClient);
        //connProperties.setProperty("jco.client.type", "V");
        connProperties.setProperty("jco.client.user", userId);
        connProperties.setProperty("jco.client.passwd", password);
        
       // connProperties.setProperty("jco.client.lang", language != null ? language : "");

       
          // connProperties.setProperty("jco.client.saplogon_id", "nwcluster");
       
            connProperties.setProperty("jco.client.sysnr", systemNumber);
            connProperties.setProperty("jco.client.ashost", applicationServer);
            
            ///H/gate.acme.com/S/3299/P/secret/H/gate.sap.com/S/3298/H/iwdf8997.sap.com/S/3200
            
           connProperties.setProperty("jco.server.saprouter", "/H/nwcluster");
            
        //}
        
     /*   
	 // add the pool configuration settings 
        SAPMetaConnectorConfig connectorConfig = (SAPMetaConnectorConfig)this.getConnectorConfig();
        if (connectorConfig != null)
        {
            Properties poolSettings = connectorConfig.getSapConnectionPoolConfig().getSAPConnectionPoolConfig();
            if (poolSettings != null && poolSettings.size() > 0)
            {
                this.log.info("SAP Connection configuration " + poolSettings.toString());
                this.connProperties.putAll(poolSettings);
            }
        }
        
       */
            
       // (endPointName, connProperties);
           
         /*  
      
        */
		
	    String endPointName = "sap-EP5";
        File file;
        Properties connProperties2 = new Properties();
        try {
             file = new File("c:\\sapPropertiesFile.txt");
			 BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			 
			String line =  bufferedReader.readLine().trim();
			 while(line != null){
				 if(line.length() == 0){
					 line = bufferedReader.readLine();
					continue;
				 }
				 String subStrings[] = line.trim().split("=");				 
				 if(subStrings.length != 2){
					 System.out.println("You have given in wrong format. "+ subStrings.length );
					 return;
				 }
				 System.out.println(subStrings[0].trim()+"=" + subStrings[1].trim());
				 connProperties2.setProperty(subStrings[0].trim(), subStrings[1].trim());
				 line = bufferedReader.readLine();
				
			 }
		    System.out.println();
			System.out.println();
			SAPDestinationDataProvider sapDestDataProvider = new SAPDestinationDataProvider();
			 System.out.println("sapDestDataProvider object has created");	
			 
			com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(sapDestDataProvider);
			 System.out.println("registerred the DestinationDataProvider");
 
			
		     sapDestDataProvider.changeDestinationData(endPointName, connProperties2);
			 System.out.println("changeDestinationDate is done.");
		     JCoDestinationManager.getDestination(endPointName).getRepository();
				
                System.out.println();	 
				System.out.println("CONECTION is successfull");	
				System.out.println();				
			 
			 
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (JCoException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
		System.out.println(e2.getMessage());
	} 
      //destination.
	
	 
        
	}
	
}
