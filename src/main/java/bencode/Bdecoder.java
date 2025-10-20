package bencode;

import com.google.gson.Gson;

import java.math.BigInteger;

public class Bdecoder {
    private static final Gson gson = new Gson();

    public void decode(String encoded) {
        Object result = null;
        try {
            char c = encoded.charAt(0);
            if (Character.isDigit(c)) {
                result = decodeString(encoded);
            }
            switch (c){
                case 'i':
                    result = decodeInteger(encoded);
                case 'l':
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
    }

    public String decodeString(String encoded) throws BdecoderException {
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
        return encoded.substring(i,i+length);
    }


    private BigInteger decodeInteger(String encoded) {
        return new BigInteger(encoded.substring(1,encoded.length()-1));
    }


    private void printJson(Object output) {
        System.out.println(gson.toJson(output));
    }
}
