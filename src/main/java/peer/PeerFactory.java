package peer;

import utils.ByteSlice;

import java.net.Inet4Address;
import java.nio.ByteBuffer;

public class PeerFactory {
    public Peer buildPeer(byte[] bytes) throws Exception{
        if(bytes.length!=6) throw new Exception("peer bytes are supposed to be 6 bytes");
        int port = ByteBuffer.wrap(bytes,4,2).getShort() & 0xFFFF;
        Inet4Address ip = (Inet4Address) Inet4Address.getByAddress(ByteSlice.slice(bytes,0,4));
        return new Peer(ip,port);
    }
}
