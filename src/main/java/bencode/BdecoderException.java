package bencode;

public class BdecoderException extends Exception {
    private String message;
    BdecoderException(String message) {
        message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
