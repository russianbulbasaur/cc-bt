package bdecode;
import java.math.*;

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
    public Object data() {
        return data;
    }

    @Override
    public BdecodedObjectType type() {
        return BdecodedObjectType.string;
    }
}
