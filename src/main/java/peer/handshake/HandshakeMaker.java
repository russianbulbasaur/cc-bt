package peer.handshake;

import peer.Peer;
import peer.messages.InterestedMessage;
import peer.messages.Message;
import peer.messages.MessageFactory;
import utils.ByteSlice;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HandshakeMaker {
    public static Handshake connect(Peer peer, Handshake handshake) throws Exception {
        try(Socket s = new Socket()) {
            s.connect(new InetSocketAddress(peer.getIPAddress(),peer.getPort()));
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();
            os.write(handshake.getBytes());
            os.flush();
            byte[] response = is.readAllBytes();
            byte[] handshakeBytes = ByteSlice.slice(response,0,68);
            byte[] messageBytes = ByteSlice.slice(response,68);
            Message message = MessageFactory.parseMessage(messageBytes);
            if(message.getMessageType() != 5) throw new Exception(String.format("expected bitfield message,got %d",message.getMessageType()));
            Message interestedMessage = new InterestedMessage();
            os.write(interestedMessage.getBytes());
            os.flush();
            messageBytes = is.readAllBytes();
            messageBytes = is.readAllBytes();
            System.out.println(messageBytes.length);
            message = MessageFactory.parseMessage(messageBytes);
            if(message.getMessageType() != 1) throw new Exception(String.format("expected unchoke message,got %d",message.getMessageType()));
            is.close();
            os.close();
            s.close();
            return new Handshake(handshakeBytes);
        }
    }
}
