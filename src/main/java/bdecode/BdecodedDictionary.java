package bdecode;
import java.util.HashMap;
import java.util.Map;

public class BdecodedDictionary implements BdecodedObject {

    Map<String,BdecodedObject> data;

     BdecodedDictionary(Map<String,BdecodedObject> data){
         this.data = data;
     }

    @Override
    public int stringLength() {
        int length = 0;
        for(String key : data.keySet()) {
            length += data.get(key).stringLength();
        }
        return 2 + length;
    }

    @Override
    public BdecodedObjectType type() {
        return BdecodedObjectType.dictionary;
    }

    @Override
    public Object toJavaObject() {
        Map<String,Object> result = new HashMap<>();
        for(String key : data.keySet()) {
            result.put(key,data.get(key).toJavaObject());
        }
        return result;
    }
}
