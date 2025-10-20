package bdecode;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bdecoder {


    public BdecodedObject decode(String encoded) {
        try {
            char c = encoded.charAt(0);
            if (Character.isDigit(c)) {
                return decodeString(encoded);
            }
            switch (c){
                case 'i':
                    return decodeInteger(encoded);
                case 'l':
                    return decodeList(encoded);
                case 'd':
                    return decodeDictionary(encoded);
            }
        }catch(BdecoderException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public BdecodedString decodeString(String encoded) throws BdecoderException {
        StringBuilder buffer = new StringBuilder();
        int i = 0;
        for(;i<encoded.length();i++) {
            char c = encoded.charAt(i);
            if(Character.isDigit(c)){
                buffer.append(c);
            }else if (c == ':'){
                i++;
                break;
            }else{
                throw new BdecoderException(String.format("invalid encoded bstring: %s",encoded));
            }
        }
        int length = Integer.parseInt(buffer.toString());
        return new BdecodedString(encoded.substring(i,i+length));
    }


    private BdecodedInteger decodeInteger(String encoded) {
        StringBuilder b = new StringBuilder();
        for(int i=1;i<encoded.length();i++) {
            char c = encoded.charAt(i);
            if(Character.isDigit(c)){
                b.append(c);
            }else if (c == 'e') break;
        }
        return new BdecodedInteger(new BigInteger(b.toString()));
    }

    private BdecodedList decodeList(String encoded) {
        List<BdecodedObject> list = new ArrayList<>();
        int i = 1;
        while(i<encoded.length()) {
            BdecodedObject object = decode(encoded.substring(i));
            i += object.stringLength();
            list.add(object);
            if(encoded.charAt(i) == 'e') break;
        }
        return new BdecodedList(list);
    }


    private BdecodedDictionary decodeDictionary(String encoded) throws BdecoderException{
        Map<String,BdecodedObject> map = new HashMap<>();
        int i = 1;
        while(i<encoded.length()) {
            BdecodedObject keyObject = decode(encoded.substring(i));
            if(keyObject.type()!=BdecodedObjectType.string) {
                throw new BdecoderException("expected a string key");
            }
            String key = (String)keyObject.toJavaObject();
            i += keyObject.stringLength();
            BdecodedObject value = decode(encoded.substring(i));
            i += value.stringLength();
            map.put(key,value);
            if(encoded.charAt(i) == 'e') break;
        }
        return new BdecodedDictionary(map);
    }
}
