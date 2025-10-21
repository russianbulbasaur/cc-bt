import bdecode.BdecodedObject;
import bdecode.Bdecoder;
import com.google.gson.Gson;
import peer.Peer;
import tracker.TrackerRequest;
import tracker.TrackerResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
// import com.dampcake.bencode.Bencode; - available if you need it!

public class Main {
    private static final Gson gson = new Gson();
  public static void main(String[] args) throws Exception {
    String command = args[0];
    if("decode".equals(command)) {
        Bdecoder decoder = new Bdecoder();
        printJson(decoder.decode(args[1].getBytes()));
    } else if(command.equals("info")) {
        TorrentFile file = new TorrentFile(args[1]);
    }else if(command.equals("peers")){
        TrackerRequest.Builder requestBuilder = getBuilder(args);
        TrackerResponse response = requestBuilder.build().doRequest();
        for(Peer peer : response.getPeerList()) {
            peer.display();
        }
    }else{
      System.out.println("Unknown command: " + command);
    }

  }

    private static TrackerRequest.Builder getBuilder(String[] args) throws IOException {
        TorrentFile file = new TorrentFile(args[1]);
        TrackerRequest.Builder requestBuilder = new TrackerRequest.Builder();
        requestBuilder.setURL(file.trackerURL);
        requestBuilder.setInfoHash(URLEncoder.encode(new String(file.infoHashBytes,
                StandardCharsets.ISO_8859_1),
                StandardCharsets.ISO_8859_1));
        requestBuilder.setPeerId("12345678901234567890");
        requestBuilder.setPort(6881);
        requestBuilder.setUploaded(0);
        requestBuilder.setDownloaded(0);
        requestBuilder.setLeft(Integer.parseInt(file.pieceLength.toString()));
        requestBuilder.setCompact(1);
        return requestBuilder;
    }


    static private void printJson(BdecodedObject output) {
        System.out.println(gson.toJson(output.toJavaObject()));
    }
}
