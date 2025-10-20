import bencode.Bdecoder;
import com.google.gson.Gson;
// import com.dampcake.bencode.Bencode; - available if you need it!

public class Main {
  public static void main(String[] args) throws Exception {
    String command = args[0];
    if("decode".equals(command)) {
        Bdecoder decoder = new Bdecoder();
        decoder.decode(args[1]);
    } else {
      System.out.println("Unknown command: " + command);
    }

  }
}
