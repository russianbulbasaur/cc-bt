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
}
