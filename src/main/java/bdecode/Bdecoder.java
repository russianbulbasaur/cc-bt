package bdecode;

import com.google.gson.Gson;
import utils.ByteSlice;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Bdecoder {


    public BdecodedObject decode(byte[] encoded) {
        try {
            char c = (char) encoded[0];
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

    public BdecodedString decodeString(byte[] encoded) throws BdecoderException {
        StringBuilder buffer = new StringBuilder();
        int i = 0;
        for(;i<encoded.length;i++) {
            char c = (char)encoded[i];
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
        return new BdecodedString(new String(ByteSlice.slice(encoded, i, i + length), StandardCharsets.ISO_8859_1));
    }


    private BdecodedInteger decodeInteger(byte[] encoded) {
        int end = -1;
        for(int i=1;i<encoded.length;i++) {
            byte c = encoded[i];
            if (c == 'e'){
                end = i;
                break;
            }
        }
        return new BdecodedInteger(new BigInteger(new String(ByteSlice.slice(encoded,1,end))));
    }

    private BdecodedList decodeList(byte[] encoded) {
        List<BdecodedObject> list = new ArrayList<>();
        int i = 1;
        while(i<encoded.length) {
            BdecodedObject object = decode(ByteSlice.slice(encoded,i));
            i += object.stringLength();
            list.add(object);
            if(encoded[i] == 'e') break;
        }
        return new BdecodedList(list);
    }


    private BdecodedDictionary decodeDictionary(byte[] encoded) throws BdecoderException{
        LinkedHashMap<String,BdecodedObject> map = new LinkedHashMap<>();
        int i = 1;
        while(i<encoded.length) {
            BdecodedObject keyObject = decode(ByteSlice.slice(encoded,i));
            if(keyObject==null) break;
            if(keyObject.type()!=BdecodedObjectType.string) {
                throw new BdecoderException("expected a string key");
            }
            String key = (String)keyObject.toJavaObject();
            i += keyObject.stringLength();
            BdecodedObject value = decode(ByteSlice.slice(encoded,i));
            i += value.stringLength();
            map.put(key,value);
            if(encoded[i] == 'e') break;
        }
        return new BdecodedDictionary(map);
    }
}
