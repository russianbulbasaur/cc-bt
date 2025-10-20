package bdecode;

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
        return length+1;
    }

    @Override
    public Object data() {
        return data;
    }

    @Override
    public BdecodedObjectType type() {
        return BdecodedObjectType.list;
    }
}
