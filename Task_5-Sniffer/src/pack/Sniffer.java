package pack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;

public class Sniffer {
    private List<PcapIf> alldevs;
    private final StringBuilder errbuf;
    private final int snaplen = Pcap.DEFAULT_SNAPLEN;
    private final int flags = Pcap.MODE_PROMISCUOUS;
    private final int timeout = 15 * 1000;
    private final int captureNum = Pcap.LOOP_INFINITE;
    private final PacketHandler handler;

    public Sniffer(){
        alldevs = new ArrayList<>();
        errbuf = new StringBuilder();
        handler = new PacketHandler();
    }

    public void setDevices(List<PcapIf> list){ alldevs = list; }
    public PacketHandler getHandler(){return handler;}
    public List<PcapIf> getList(){
        return alldevs;
    }
    public StringBuilder getErrorBuf(){
        return errbuf;
    }
    public int getCaptureNum(){return captureNum;}

    public static void main(String[] args) {
        Sniffer session = new Sniffer();
        session.setDevices(session.findDevices(session.getList(), session.getErrorBuf()));
        int result = session.chooseDevice(session.getList());
        if(result == -1) {
            System.out.println("Device doesn't exist");
            return;
        }
        while(true) {
            Pcap pcap = session.openSnifferSession(session.getList().get(result));
            if(pcap == null) break;
            pcap.loop(session.getCaptureNum(), session.getHandler().getPacket(), session.getList().get(result).getName());
        }
    }

    public int chooseDevice(List<PcapIf> devices){
        for(PcapIf device : devices){
            System.out.println(device.getName() + " " + device.getAddresses());
        }
        System.out.print("Choose device:");
        int choice = new Scanner(System.in).nextInt();
        if(choice < 0 || choice >= devices.size()){
            return -1;
        }
        return choice;
    }

    public List<PcapIf> findDevices(List<PcapIf> alldevs, StringBuilder errbuf){
        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r != Pcap.OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s", errbuf
                    .toString());
            return null;
        }

        return alldevs;
    }

    public Pcap openSnifferSession(PcapIf device){
        Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
        if (pcap == null) {
            System.err.printf("Error while opening device for capture: "
                    + errbuf.toString());
            return null;
        }
        return pcap;
    }
}

class PacketHandler {
    private final PcapPacketHandler<String> packet;

    public PacketHandler() {
        packet = new PcapPacketHandler<String>() {
            @Override
            public void nextPacket(PcapPacket packet, String user) {
                Ip4 IP = new Ip4();
                if (!packet.hasHeader(IP)) {
                    return;
                }
                byte[] source = IP.source();
                byte[] destination = IP.destination();
                String IPSource = FormatUtils.ip(source);
                String IPDestination = FormatUtils.ip(destination);
                System.out.printf("Received packet at %s packet length=%d " + " %s ----> %s\n",
                        new Date(packet.getCaptureHeader().timestampInMillis()),
                        packet.getCaptureHeader().wirelen(),
                        IPSource,
                        IPDestination
                );
            }
        };

    }

    public PcapPacketHandler<String> getPacket() {
        return packet;
    }
}