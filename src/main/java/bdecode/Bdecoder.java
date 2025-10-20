package bdecode;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Bdecoder {
    private static final Gson gson = new Gson();

    public BdecodedObject decode(String encoded) {
        BdecodedObject result = null;
        try {
            char c = encoded.charAt(0);
            if (Character.isDigit(c)) {
                result = decodeString(encoded);
            }
            switch (c){
                case 'i':
                    result = decodeInteger(encoded);
                case 'l':
                    result = decodeList(encoded);
                    break;
                case 'd':
                    break;
            }
            if(result == null) {
                throw new BdecoderException(String.format("invalid format: %s",encoded));
            }
            printJson(result);
        }catch(BdecoderException e) {
            System.out.println(e.getMessage());
        }
        return result;
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
        return new BdecodedInteger(new BigInteger(encoded.substring(1,encoded.length()-1)));
    }

    private BdecodedList decodeList(String encoded) {
        List<BdecodedObject> list = new ArrayList<>();
        int i = 1;
        while(i<encoded.length()) {
            BdecodedObject object = decode(encoded.substring(i));
            i += object.stringLength();
            list.add(object);
        }
        return new BdecodedList(list);
    }

    private void printJson(Object output) {
        System.out.println(gson.toJson(output));
    }


}
