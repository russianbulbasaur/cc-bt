package peer.messages;

public class MessageFactory {
    public static Message parseMessage(byte[] data) throws Exception {
        switch(data[4]){
            case 1:
                return new UnchokeMessage(data);
            case 2:
                return new InterestedMessage(data);
            case 5:
                return new BitfieldMessage(data);
            case 6:
                return new RequestMessage(data);
            default:
                throw new Exception(String.format("unknown message type: %d",data[0]));
        }
    }
}
