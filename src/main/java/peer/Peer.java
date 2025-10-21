package peer;

import java.net.Inet4Address;

public class Peer {
    private final Inet4Address ip;
    private final int port;
    protected Peer(Inet4Address ip, int port){
        this.ip = ip;
        this.port = port;
    }

    Inet4Address getIP() {
        return ip;
    }

    int getPort() {
        return port;
    }


    public void display() {
        System.out.printf("%s:%d\n",ip.getHostAddress(),port);
    }
}
