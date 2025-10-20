import bdecode.BdecodedObject;
import bdecode.BdecodedObjectType;
import bdecode.Bdecoder;
import com.google.gson.Gson;

import java.util.List;
// import com.dampcake.bencode.Bencode; - available if you need it!

public class Main {
    private static final Gson gson = new Gson();
  public static void main(String[] args) throws Exception {
    String command = args[0];
    if("decode".equals(command)) {
        Bdecoder decoder = new Bdecoder();
        printJson(decoder.decode(args[1]));
    } else {
      System.out.println("Unknown command: " + command);
    }

  }


    static private void printJson(BdecodedObject output) {
        System.out.println(gson.toJson(output.toJavaObject()));
    }
}
