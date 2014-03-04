package org.jnetpcap.examples;
  

import java.util.Date;  
  
import org.coders.Commons;
import org.jnetpcap.Pcap;  
import org.jnetpcap.packet.PcapPacket;  
import org.jnetpcap.packet.PcapPacketHandler;  
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.shared.encryption.JackSumEngine;

  


public class ClassicPcapExampleOfflineCapture {  
 
	private JackSumEngine JackSumInOut= new JackSumEngine();
	static int i=0;

    public static void main(String[] args) {  
    	ClassicPcapExampleOfflineCapture MainOC = new ClassicPcapExampleOfflineCapture();
    	System.out.println(MainOC.main1());
    } 
    
    
    public String main1(){
    	final StringBuilder Output= new StringBuilder();
    	
    	  final StringBuilder errbuf = new StringBuilder(); // For any error msgs  
          final String file = "files/ms08_067.pcap";  
    
          System.out.printf("Opening file for reading: %s%n", file);  
   
          Pcap pcap = Pcap.openOffline(file, errbuf);  
    
          if (pcap == null) {  
              System.err.printf("Error while opening device for capture: "  
                  + errbuf.toString());  
              return "Error";  
          }  
          
          Output.append("PID\tDate\tSrc\tSrcPort\tDest\tDestPort\tcaplen\tlen\thex\n");
        		  
          PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {  
    
          	Tcp tcp = new Tcp(); // Preallocate a Tcp header
          	  byte[] sIP = new byte[4]; // Should be outside the callback method for efficiency  
          	  byte[] dIP = new byte[4];  
          	  
          	Ip4 ip = new Ip4(); // Should be outside the callback method for efficiency
          	  
          	
              public void nextPacket(PcapPacket packet, String user) {  
              	   i++;

              	   Date Date= new Date(packet.getCaptureHeader().timestampInMillis());
              	   String StrDate=Date+"";
              	   int intCaplen=packet.getCaptureHeader().caplen(); // Length actually captured
              	   int intWirelen=packet.getCaptureHeader().wirelen(); // Original length 
              	   String strhexdump = packet.toHexdump(packet.size(), false, false, true);  
              	   
              	   String strTcpDest="";
              	   String strTcpSrc="";
              	   if (packet.hasHeader(tcp)) {  
              		    strTcpDest=tcp.destination()+"";
              		    strTcpSrc=tcp.source()+"";     
              	   }  
              	   
              	   String strIp4Dest="";
              	   String strIp4Src="";
              	   if (packet.hasHeader(ip)) {  
              		    strIp4Dest=org.jnetpcap.packet.format.FormatUtils.ip(ip.destination())+"";
              		    strIp4Src=org.jnetpcap.packet.format.FormatUtils.ip(ip.source())+"";     
              	   }  
              	   
              	   
              	  if(strIp4Src.trim().length()>=1){//no ARP packets. 
              		  
              	     Output.append(""+i+
                       		"\t"+StrDate+
                       		"\t"+strIp4Src+"\t"+strTcpSrc+
                       		"\t"+strIp4Dest+"\t"+strTcpDest+
                       		"\t"+intCaplen+
                       		"\t"+intWirelen+
                       		"\t"+(strhexdump.trim().replace("\t", "").replace("\n","").replace(" ",""))+
                       		//"\nMD5 of Connect HexDump\t"+JackSumInOut.md5_HEX(strhexdump.trim())+
                       		"\n");  
              	  }
             
                 
              }

          };  
    
          try {  
              pcap.loop(pcap.LOOP_INFINATE, jpacketHandler,"");  
          } finally {   
             pcap.close();  
          }
          
		return Output.toString();  
    }
}  