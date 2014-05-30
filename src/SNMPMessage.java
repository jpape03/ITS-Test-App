import java.nio.ByteBuffer;

import org.snmp4j.smi.OctetString;


public class SNMPMessage {
	
	
	public void defineMessage(String IPaddress, String messageNo, String message, int runtimePriority) throws InterruptedException{
		SNMP4JHelper SNMPRequest=new SNMP4JHelper();
				
		SNMPRequest.snmpSet(IPaddress, "public", "1.3.6.1.4.1.1206.4.2.3.5.8.1.9.3."+messageNo, 6);  //Set Message Status to modify request
		Thread.sleep(500);
		SNMPRequest.snmpOctetSet(IPaddress, "public", "1.3.6.1.4.1.1206.4.2.3.5.8.1.3.3."+messageNo , message); //Set message string
		Thread.sleep(500);
		SNMPRequest.snmpSet(IPaddress, "public", "1.3.6.1.4.1.1206.4.2.3.5.8.1.8.3."+messageNo, 100);  //Set runtime priority
		Thread.sleep(500);
		SNMPRequest.snmpSet(IPaddress, "public", "1.3.6.1.4.1.1206.4.2.3.5.8.1.9.3."+ messageNo, 7);  // Set message status back to valid state
	}
	
	public void activateMessage(String IPaddress, OctetString activationCode){
		SNMP4JHelper SNMPRequest=new SNMP4JHelper();
				
	    SNMPRequest.snmpByteSet(IPaddress, "public", "1.3.6.1.4.1.1206.4.2.3.6.3.0", activationCode);
		
	}
	
	public OctetString getMessageActivation(String IPaddress, String messageNo, int duration){
		byte [] CRC = new byte [2];
		byte [] durationByte = new byte [2];
		SNMP4JHelper SNMPRequest=new SNMP4JHelper();
		int durationCopy=duration;
		
		durationByte[1]=(byte) duration;
		durationByte[0] = (byte) (duration >>> 8);
		String priority= SNMPRequest.snmpGet(IPaddress, "public", "1.3.6.1.4.1.1206.4.2.3.5.8.1.8.3."+messageNo);  //get runtime priority
		byte priorityByte=(byte)Integer.parseInt(priority);
		String memoryType=SNMPRequest.snmpGet(IPaddress, "public", "1.3.6.1.4.1.1206.4.2.3.5.8.1.1.3."+messageNo);  //get message memory type
		byte memoryTypeByte=(byte)Integer.parseInt(memoryType);
		String messageNum=SNMPRequest.snmpGet(IPaddress, "public", "1.3.6.1.4.1.1206.4.2.3.5.8.1.2.3."+messageNo); //get Message Number
		byte messageNumByte=(byte)Integer.parseInt(messageNum);
		String messageCRC=SNMPRequest.snmpGet(IPaddress, "public", "1.3.6.1.4.1.1206.4.2.3.5.8.1.5.3."+messageNo); // get message crc
		
		CRC[0]=(byte) (Integer.parseInt(messageCRC) & 0xFF);
		CRC[1]=	(byte) ((Integer.parseInt(messageCRC) >> 8) & 0xFF);	
		byte[] ip={00,00,00,00};
		
		byte[] activationCode={durationByte[1],durationByte[0],priorityByte,memoryTypeByte,0, messageNumByte, CRC[1], CRC[0], 0, 0, 0, 0};

		OctetString o=new OctetString(activationCode);
		return o;
}

}
