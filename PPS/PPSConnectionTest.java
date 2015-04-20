/**
* The PPSConnectionTest program tries to establish connection to PeopleSoft server
* and mainly provides details about the connection ,
* is the connection successful or not .True - Successful , false - not successful
*  Attributes required for connection are : USER ID , PASSWORD, SERVER NAME and the PORT NUMBER
* (PPS SERVER's default PORT - JOLT Listener PORT - file- psaappsrv.cfg)
* 
* @author  vodpr01
* @version 1.0
* @since   2014-06-27 
*/
import psft.pt8.joa.API;
import psft.pt8.joa.ISession;

public class PPSConnectionTest {

	public static void main(String[] args) {
		ISession session;
		String userID = args[0];
		String password = args[1];
		String server = args[2];
		String port = args[3];
		boolean connected = false;
		try {
			//user input
			System.out.println("Attributes required to test connection are : USER ID , PASSWORD, SERVER NAME and the PORT NUMBER ," +
					" please provide these details while you run the PPSConnection as arguments :" +
					"example : java PPSConnectionTest USer PSWD PPSSERVER1 9010");
			System.out.println("user ID provided is :" + userID);
			System.out.println("password provided is :" + password);
			System.out.println("PPS server details provided is :" + server);
			System.out.println("port number provided is  :" + port);
			// Create PeopleSoft Session Object
			session = API.createSession();

			String appServerPath = server + ":" + port;
			System.out
					.println("********BEFORE CONNECT CALL----- USER ID is "
							+ userID +", Server name and port is :" + appServerPath);			
			connected = session.connect(1, appServerPath, userID, password, null);
			
			System.out
					.println("************** AFTER CONNECT CALL --- The connection to PPS is succesful: "
							+ connected);
			
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

}
