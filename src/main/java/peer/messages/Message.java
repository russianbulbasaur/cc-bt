package peer.messages;

public interface Message {
    byte[] getBytes();
    int getMessageType();
    byte[] getPayload();
}
