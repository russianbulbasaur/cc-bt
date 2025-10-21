import bdecode.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TorrentFile {
    private Bdecoder decoder = new Bdecoder();
    public Map<String,Object> data;
    public Map<String,Object> info;
    public String trackerURL;
    public BigInteger length;
    public BigInteger pieceLength;
    public String infoHash;
    public byte[] infoHashBytes;
    public List<String> hashes;
    public TorrentFile(String filePath) throws IOException,FileNotFoundException {
        byte[] data = Files.readAllBytes(Path.of(filePath));
        BdecodedObject object = decoder.decode(data);
        BdecodedDictionary infoBdecoded = (BdecodedDictionary) (((Map<String,BdecodedObject>)object.data()).get("info"));
        this.data = (Map<String,Object>)object.toJavaObject();
        trackerURL = (String) this.data.get("announce");
        info = ((Map<String,Object>)this.data.get("info"));
        length = (BigInteger) info.get("length");
        pieceLength = (BigInteger) info.get("piece length");
        byte[] hashes = ((String) info.get("pieces")).getBytes(StandardCharsets.ISO_8859_1);
        int size = hashes.length / 20;
        this.hashes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            StringBuilder b = new StringBuilder();
            for (int s = i * 20; s < (i + 1) * 20; s++) {
                b.append(String.format("%02x", hashes[s] & 0xff));
            }
            this.hashes.add(b.toString());
        }
        infoHash = calculateInfoHash(infoBdecoded.bencode());
        System.out.printf("Tracker URL: %s\n",trackerURL);
        System.out.printf("Length: %d\n",length);
        System.out.printf("Info Hash: %s\n",infoHash);
        System.out.printf("Piece Length: %d\n",pieceLength);
    }


    private String calculateInfoHash(String input) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            infoHashBytes = md.digest(input.getBytes(StandardCharsets.ISO_8859_1));
            // Java 17+: HexFormat
            return java.util.HexFormat.of().formatHex(infoHashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 not available", e);
        }
    }
}

