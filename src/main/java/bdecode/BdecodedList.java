package bdecode;

import java.util.ArrayList;
import java.util.List;

public class BdecodedList implements BdecodedObject{
    List<BdecodedObject> data;

    BdecodedList(List<BdecodedObject> data){
        this.data = data;
    }

    @Override
    public int stringLength() {
        int length = 0;
        for(BdecodedObject object : data) {
            length += object.stringLength();
        }
        return length+2;
    }


    @Override
    public BdecodedObjectType type() {
        return BdecodedObjectType.list;
    }

    @Override
    public Object toJavaObject() {
        List<Object> data = new ArrayList<>();
        for(BdecodedObject object : this.data) {
            data.add(object.toJavaObject());
        }
        return data;
    }

    @Override
    public Object data() {
        return data;
    }

    @Override
    public String bencode() {
        StringBuilder output = new StringBuilder();
        output.append('l');
        for(BdecodedObject object : data) {
            output.append(object.bencode());
        }
        output.append('e');
        return output.toString();
    }
}
