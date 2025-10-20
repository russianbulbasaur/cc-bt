import bdecode.BdecodedObject;
import bdecode.BdecodedObjectType;
import bdecode.Bdecoder;
import com.google.gson.Gson;
import torrent_parser.TorrentFile;

import java.util.List;
// import com.dampcake.bencode.Bencode; - available if you need it!

public class Main {
    private static final Gson gson = new Gson();
  public static void main(String[] args) throws Exception {
    String command = args[0];
    if("decode".equals(command)) {
        Bdecoder decoder = new Bdecoder();
        printJson(decoder.decode(args[1].getBytes()));
    } else if(command.equals("info")) {
        try {
            TorrentFile file = new TorrentFile(args[1]);
            System.out.printf("Tracker URL: %s\n",file.trackerURL);
            System.out.printf("Length: %d\n",file.length);
            System.out.println(file.infoBdecoded.bencode());
            System.out.printf("Info Hash: %s\n",file.infoHash(file.infoBdecoded.bencode()));
            System.out.printf("Piece Length: %d\n",file.pieceLength);
        }catch(Exception e){
            System.out.println("caught: "+e.getMessage());
        }
    }else{
      System.out.println("Unknown command: " + command);
    }

  }


    static private void printJson(BdecodedObject output) {
        System.out.println(gson.toJson(output.toJavaObject()));
    }
}
