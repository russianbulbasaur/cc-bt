package bdecode;
import java.math.*;
import java.nio.charset.StandardCharsets;

public class BdecodedString implements BdecodedObject{
    String data;

    BdecodedString(String s) {
        this.data = s;
    }

    @Override
    public int stringLength() {
        return data.length() + String.valueOf(data.length()).length() + 1;
    }


    @Override
    public BdecodedObjectType type() {
        return BdecodedObjectType.string;
    }

    @Override
    public Object toJavaObject() {
        return data;
    }

    @Override
    public Object data() {
        return data;
    }

    @Override
    public String bencode() {
        byte[] bytes = data.getBytes(StandardCharsets.ISO_8859_1);
        return String.valueOf(bytes.length) +
                ':' +
                new String(bytes,StandardCharsets.ISO_8859_1);
    }
}
