package tracker;

import bdecode.BdecodedDictionary;
import bdecode.BdecodedObject;
import bdecode.BdecodedObjectType;
import bdecode.Bdecoder;
import peer.Peer;
import peer.PeerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrackerRequest {
    private final String url;
    private final String infoHash;
    private final String peerId;
    private final int port;
    private final int downloaded;
    private final int uploaded;
    private final int left;
    private final int compact;

    protected TrackerRequest(String url,
                             String infoHash,
                             String peerId,
                          int port,
                          int uploaded,
                          int downloaded,
                          int left,
                          int compact){
        this.url = url;
        this.infoHash = infoHash;
        this.peerId = peerId;
        this.port = port;
        this.uploaded = uploaded;
        this.downloaded = downloaded;
        this.left = left;
        this.compact = compact;
    }

    public TrackerResponse doRequest() throws IOException,Exception {
        HttpClient client = HttpClient.newHttpClient();
        String u = String.format("%s?info_hash=%s&peer_id=%s&port=%d&uploaded=%d&downloaded=%d&left=%d&compact=%d",
                url,infoHash,peerId,
                port,uploaded,
                downloaded, left,
                compact);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(u))
                .build();
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        client.close();
        Bdecoder decoder = new Bdecoder();
        BdecodedObject bdecodedObject = (decoder.decode(response.body()));
        if(bdecodedObject.type() != BdecodedObjectType.dictionary) throw new Exception("response is not a valid dictionary");
        BdecodedDictionary responseDictionary = (BdecodedDictionary) bdecodedObject;
        if(responseDictionary.data.get("interval").type() != BdecodedObjectType.integer) throw new Exception("interval is not an integer");
        if(responseDictionary.data.get("peers").type() != BdecodedObjectType.string) throw new Exception("peers is not a string");
        Map<String,Object> data = (Map<String,Object>) bdecodedObject.toJavaObject();
        byte[] peersBytes = (byte[]) responseDictionary.data.get("peers").data();
        List<Peer> peers = new ArrayList<>();
        PeerFactory peerFactory = new PeerFactory();
        int index = 0;
        while(index<peersBytes.length) {
            byte[] peerBytes = new byte[6];
            for(int i=0;i<6;i++){
                peerBytes[i] = peersBytes[index];
                index++;
            }
            peers.add(peerFactory.buildPeer(peerBytes));
        }
        return new TrackerResponse((BigInteger)data.get("interval"),peers);
    }


    public static class Builder {
        private String infoHash;
        private String peerId = "12345678901234567890";
        private int port = 6881;
        private int uploaded = 0;
        private int downloaded = 0;
        private int left;
        private int compact = 1;
        private String url;


        public void setURL(String url) {
            this.url = url;
        }

        public void setInfoHash(String infoHash) {
            this.infoHash = infoHash;
        }

        public void setPeerId(String peerId) {
            this.peerId = peerId;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public void setUploaded(int uploaded){
            this.uploaded = uploaded;
        }

        public void setDownloaded(int downloaded) {
            this.downloaded = downloaded;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public void setCompact(int compact) {
            this.compact = compact;
        }

        public TrackerRequest build() {
            return new TrackerRequest(url,infoHash,peerId,port,uploaded,downloaded,left,compact);
        }
    }
}
