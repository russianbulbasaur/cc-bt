package bdecode;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BdecodedDictionary implements BdecodedObject {

    public LinkedHashMap<String,BdecodedObject> data;

     BdecodedDictionary(LinkedHashMap<String,BdecodedObject> data){
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

    @Override
    public Object data() {
        return data;
    }

    @Override
    public String bencode() {
         StringBuilder output = new StringBuilder();
         output.append('d');
         for(String key : data.keySet()) {
             output.append((new BdecodedString(key.getBytes(StandardCharsets.ISO_8859_1))).bencode());
             output.append(data.get(key).bencode());
         }
         output.append('e');
         return output.toString();
    }
}
