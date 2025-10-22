package peer.handshake;

import utils.ByteSlice;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Handshake {
    private ByteBuffer buffer;
    public String peerId;
    public Handshake(byte length, String protocol, long reserved, byte[] infoHashBytes){
        buffer = ByteBuffer.allocate(68);
        buffer.put(length);
        buffer.put(protocol.getBytes(StandardCharsets.ISO_8859_1));
        buffer.putLong(reserved);
        buffer.put(infoHashBytes);
        buffer.put(generateRandomPeerId());
    }


    Handshake(byte[] data) {
        String protocol = new String(ByteSlice.slice(data,1,20),StandardCharsets.ISO_8859_1);
        byte[] peerIdBytes = ByteSlice.slice(data,47);
        StringBuilder builder = new StringBuilder();
        for(byte b : peerIdBytes) {
            builder.append(String.format("%02x",b));
        }
        this.peerId = builder.toString();
    }

    public byte[] getBytes() {
        return buffer.array();
    }


    private byte[] generateRandomPeerId() {
        byte[] id = new byte[20];
        (new Random()).nextBytes(id);
        return id;
    }

}
