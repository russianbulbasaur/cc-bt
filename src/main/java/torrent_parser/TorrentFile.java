package torrent_parser;

import bdecode.*;
import utils.ByteSlice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
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
    public BdecodedObject infoBdecoded;
    public String trackerURL;
    public BigInteger length;
    public BigInteger pieceLength;
    public List<String> hashes;
    public TorrentFile(String filePath) throws IOException,FileNotFoundException {
        byte[] data = Files.readAllBytes(Path.of(filePath));
        BdecodedObject object = decoder.decode(data);
        infoBdecoded = (BdecodedObject) (((Map<String,BdecodedObject>)object.data()).get("info"));
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
    }


    public String infoHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            // Java 17+: HexFormat
            return java.util.HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 not available", e);
        }
    }
}

