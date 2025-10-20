package bencode;

public class Bdecoder {

    public String decode(String encoded) {
        try {
            char c = encoded.charAt(0);
            if (Character.isDigit(c)) {
                return decodeString(encoded);
            }
            switch (c){
                case 'i':
                    return String.valueOf(decodeInteger(encoded));
                case 'l':
                    break;
                case 'd':
                    break;
            }
            throw new BdecoderException(String.format("invalid format: %s",encoded));
        }catch(BdecoderException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public String decodeString(String encoded) throws BdecoderException {
        StringBuilder buffer = new StringBuilder();
        int i = 0;
        for(;i<encoded.length();i++) {
            char c = encoded.charAt(i);
            if(Character.isDigit(c)){
                buffer.append(c);
            }else if (c == ':'){
                break;
            }else{
                throw new BdecoderException(String.format("invalid encoded bstring: %s",encoded));
            }
        }
        int length = Integer.parseInt(buffer.toString());
        return encoded.substring(i,i+length);
    }


    private int decodeInteger(String encoded) throws BdecoderException {
        return Integer.parseInt(encoded.substring(1,encoded.length()-1));
    }
}
