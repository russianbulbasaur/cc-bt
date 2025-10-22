package peer;

import java.net.Inet4Address;

public class Peer {
    private final Inet4Address ip;
    private final int port;
    protected Peer(Inet4Address ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void display() {
        System.out.printf("%s:%d\n",ip.getHostAddress(),port);
    }

    public Inet4Address getIPAddress (){
        return ip;
    }

    public int getPort() {
        return port;
    }
}
