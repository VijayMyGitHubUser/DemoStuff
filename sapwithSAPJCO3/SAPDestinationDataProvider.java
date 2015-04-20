
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Properties;



import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

/**
 * This class implements the SAP JCO 3.x DestinationDataProvider interface which is then registered with the
 * SAP JCO. At runtime, JCO will use the provided implementation to get the destination configuration. SAP 
 * JCO only allows you to register one implementation in the whole JVM, which means for the whole JCS we could
 * only have one implementation registered. Since we are registering our implementation in SAP R/3 connector, this
 * will prevent other connectors who may want to use SAP JCO 3.x to register their own implementation of it.
 * 
 * When we need to add another connector who may want to use SAP JCO 3.x, this implementation will need to be moved
 * to the JCS level so that other connectors are able to use it.
 * 
 * @author li$ji02
 *
 */
public class SAPDestinationDataProvider implements DestinationDataProvider
{
    private DestinationDataEventListener destinationDataEventListener;
    private Map<String, Properties> destinationMap = Collections.synchronizedMap(new HashMap<String, Properties>());
    
    

    public SAPDestinationDataProvider()
    {
   
    }

    /* (non-Javadoc)
     * @see com.sap.conn.jco.ext.DestinationDataProvider#getDestinationProperties(java.lang.String)
     */
    @Override
    public Properties getDestinationProperties(String name)
    {
        return this.destinationMap.get(name);
    }

    /* (non-Javadoc)
     * @see com.sap.conn.jco.ext.DestinationDataProvider#setDestinationDataEventListener(com.sap.conn.jco.ext.DestinationDataEventListener)
     */
    @Override
    public void setDestinationDataEventListener(DestinationDataEventListener listener)
    {
        this.destinationDataEventListener = listener;        
    }

    /* (non-Javadoc)
     * @see com.sap.conn.jco.ext.DestinationDataProvider#supportsEvents()
     */
    @Override
    public boolean supportsEvents()
    {
        return true;
    }

    /* Add / remove destination properties 
     * 
     * @param destName
     * @param props - null indicates delete
     *
     */
    public void changeDestinationData(final String destName, final Properties props)
    {
        assert destName != null && destName.length() > 0;
       // System.out.println(" debug -1");
       // System.out.println(" debug -1"+ destinationMap);
        System.out.println(" debug -1"+  destinationDataEventListener);

        if (props != null)
        {
            this.destinationMap.put(destName, props);
            
            this.destinationDataEventListener.updated(destName);
            
            // debug log the passed in properties
           
        }
        else
        {
            if (this.destinationMap.remove(destName) != null)
            {
               this.destinationDataEventListener.deleted(destName);
            }
        }
    }
    
    private void debugLogDestinationProperties(final String destName, final Properties props)
    {
        Properties newProps = (Properties)props.clone();

        // check to see if it has a password property
        if (newProps.getProperty(DestinationDataProvider.JCO_PASSWD) != null)
        {
            newProps.setProperty(DestinationDataProvider.JCO_PASSWD, "********");
        }
        
       
    }
}
