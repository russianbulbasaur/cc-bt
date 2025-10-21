package bdecode;
import java.math.*;
import java.nio.charset.StandardCharsets;

public class BdecodedString implements BdecodedObject{
    byte[] data;

    BdecodedString(byte[] data) {
        this.data = data;
    }

    @Override
    public int stringLength() {
        return data.length + String.valueOf(data.length).length() + 1;
    }


    @Override
    public BdecodedObjectType type() {
        return BdecodedObjectType.string;
    }

    @Override
    public Object toJavaObject() {
        return new String(data,StandardCharsets.ISO_8859_1);
    }

    @Override
    public Object data() {
        return data;
    }

    @Override
    public String bencode() {
        return String.valueOf(data.length) +
                ':' +
                new String(data,StandardCharsets.ISO_8859_1);
    }
}
