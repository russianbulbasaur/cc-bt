package peer.messages;

import utils.ByteSlice;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UnchokeMessage implements Message{
    private final ByteBuffer payload;
    private final byte type;
    private final ByteBuffer bytes;
    private final int messageLength;
    protected UnchokeMessage() {
        payload = ByteBuffer.allocate(0);
        type = 1;
        bytes = ByteBuffer.allocate(5);
        messageLength = 5;
        bytes.order(ByteOrder.BIG_ENDIAN).putInt(messageLength);
        bytes.put(type);
    }

    protected UnchokeMessage(byte[] data) {
        this.messageLength = ByteBuffer.wrap(ByteSlice.slice(data,0,4)).order(ByteOrder.BIG_ENDIAN).getInt();
        this.type = data[4];
        this.bytes = ByteBuffer.wrap(data);
        this.payload = ByteBuffer.wrap(ByteSlice.slice(data,5));
    }

    @Override
    public byte[] getBytes() {
        return bytes.array();
    }

    @Override
    public int getMessageType() {
        return type;
    }

    @Override
    public byte[] getPayload() {
        return payload.array();
    }
}
