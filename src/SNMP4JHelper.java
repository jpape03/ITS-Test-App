import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JLabel;







import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.*;
import org.*;

public class SNMP4JHelper{
	
	
	public SNMP4JHelper() {
	}
	
	 public static final String READ_COMMUNITY = "public";

     public static final String WRITE_COMMUNITY= "private";

     public static final int mSNMPVersion =0; // 0 represents SNMP version=1

     public static final String OID_UPS_OUTLET_GROUP1 =

     "1.3.6.1.4.1.318.1.1.1.12.3.2.1.3.1";

     public static final String OID_SYS_DESCR="1.3.6.1.2.1.1.1.0";


     public String snmpGet(String strAddress, String community, String strOID)

     {

     String str="";

     try

     {

     OctetString community1 = new OctetString(community);

     strAddress= strAddress;

     Address targetaddress = new UdpAddress(strAddress);

     TransportMapping transport = new DefaultUdpTransportMapping();

     transport.listen();

     CommunityTarget comtarget = new CommunityTarget();

     comtarget.setCommunity(community1);

     comtarget.setVersion(SnmpConstants.version1);

     comtarget.setAddress(targetaddress);

     comtarget.setRetries(2);

     comtarget.setTimeout(5000);

     PDU pdu = new PDU();

     ResponseEvent response;

     Snmp snmp;

     pdu.add(new VariableBinding(new OID(strOID)));

     pdu.setType(PDU.GET);

     snmp = new Snmp(transport);

     response = snmp.get(pdu,comtarget);

     if(response != null)

     {

     if(response.getResponse().getErrorStatusText().equalsIgnoreCase("Success"))

     	{

     PDU pduresponse=response.getResponse();

     str=pduresponse.getVariableBindings().firstElement().toString();

     if(str.contains("="))

     		{

     int len = str.indexOf("=");

     str=str.substring(len+2, str.length());

     		}

     	}

     }

     else

     {

     System.out.println("Feeling like a TimeOut occured ");

     }

     snmp.close();

     } catch(Exception e) 
     { e.printStackTrace();
       return " Get Failed";
     }
     System.out.println("Response="+str);

     return str;

     }
     
     

     public String snmpSet(String strAddress, String community, String strOID, int Value)

     {
     	 
     strAddress= strAddress;

     Address targetAddress = GenericAddress.parse(strAddress);

     Snmp snmp;

     try

     {

     TransportMapping transport = new DefaultUdpTransportMapping();

     snmp = new Snmp(transport);

     transport.listen();

     CommunityTarget target = new CommunityTarget();

     target.setCommunity(new OctetString(community));

     target.setAddress(targetAddress);

     target.setRetries(3);

     target.setTimeout(5000);

     target.setVersion(SnmpConstants.version1);

     PDU pdu = new PDU();
     
     pdu.add(new VariableBinding(new OID(strOID), new Integer32(Value)));
    
     pdu.setType(PDU.SET);
     
      
     ResponseListener listener1 = new ResponseListener() {
     	
         public void onResponse(ResponseEvent event1) {

               ((Snmp)event1.getSource()).cancel(event1.getRequest(), this);  
               
               System.out.println("Set Status is:"+event1.getResponse().getErrorStatusText());
                
         }
         
               };
               Thread.sleep(1000);
               String s="";//snmp.set(pdu, target).getResponse().getErrorStatusText();           
               snmp.send(pdu, target, null, listener1);
               Thread.sleep(1000);
               snmp.close();
               return s;
               }

               catch (Exception e)

               {
               e.printStackTrace();
              return "Communication Failed";
				

               }
               }


     

     public String snmpOctetSet(String strAddress, String community, String strOID, String Value)

     {
     	 
     strAddress= strAddress;

     Address targetAddress = GenericAddress.parse(strAddress);

     Snmp snmp;

     try

     {

     TransportMapping transport = new DefaultUdpTransportMapping();

     snmp = new Snmp(transport);

     transport.listen();

     CommunityTarget target = new CommunityTarget();

     target.setCommunity(new OctetString(community));

     target.setAddress(targetAddress);

     target.setRetries(3);

     target.setTimeout(5000);

     target.setVersion(SnmpConstants.version1);

     PDU pdu = new PDU();
     
     if(Value.matches("-?\\d+(.\\d+)?"))
     pdu.add(new VariableBinding(new OID(strOID), new Integer32(Integer.parseInt(Value))));
     else
     pdu.add(new VariableBinding(new OID(strOID), new OctetString(Value)));

     pdu.setType(PDU.SET);
     
      
     
     
     ResponseListener listener1 = new ResponseListener() {
    	
           public void onResponse(ResponseEvent event1) {

                 ((Snmp)event1.getSource()).cancel(event1.getRequest(), this);  
                 
                 //System.out.println("Set Status is:"+event1.getResponse().getErrorStatusText());
                  
           }
           
                 };
                 Thread.sleep(1000);
                 String s=snmp.set(pdu, target).getResponse().getErrorStatusText();           
                 snmp.send(pdu, target, null, listener1);
                 Thread.sleep(1000);
                 snmp.close();
                 return s;
                 }

                 catch (Exception e)

                 {
                 e.printStackTrace();
                return "Communication Failed";
				

                 }
                 }

     
     
     public  String snmpByteSet(String strAddress, String community, String strOID, OctetString activationCode)

     {
     	 
     strAddress= strAddress;

     Address targetAddress = GenericAddress.parse(strAddress);

     Snmp snmp;

     try

     {

     TransportMapping transport = new DefaultUdpTransportMapping();

     snmp = new Snmp(transport);

     transport.listen();

     CommunityTarget target = new CommunityTarget();

     target.setCommunity(new OctetString(community));

     target.setAddress(targetAddress);

     target.setRetries(3);

     target.setTimeout(5000);

     target.setVersion(SnmpConstants.version1);

     PDU pdu = new PDU();
     
     pdu.add(new VariableBinding(new OID(strOID), new OctetString(activationCode)));

     pdu.setType(PDU.SET);
     
      
     
     
     ResponseListener listener1 = new ResponseListener() {
    	
           public void onResponse(ResponseEvent event1) {

                 ((Snmp)event1.getSource()).cancel(event1.getRequest(), this);  
                 
                 System.out.println("Set Status is:"+event1.getResponse().getErrorStatusText());
                  
           }
           
                 };
                 Thread.sleep(1000);
                 String s="";//snmp.set(pdu, target).getResponse().getErrorStatusText();           
                 snmp.send(pdu, target, null, listener1);
                 Thread.sleep(1000);
                 snmp.close();
                 return s;
                 }

                 catch (Exception e)

                 {
                 e.printStackTrace();
                return "Communication Failed";
				

                 }
                 }


}

