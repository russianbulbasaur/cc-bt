package bdecode;
import java.math.*;

public class BdecodedInteger implements BdecodedObject{
    BigInteger data;

    BdecodedInteger(BigInteger integer) {
        this.data = integer;
    }

    @Override
    public int stringLength() {
        return data.toString().length() + 2;
    }

    @Override
    public BdecodedObjectType type() {
        return BdecodedObjectType.integer;
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
        return 'i' +
                data.toString() +
                'e';
    }
}
