package tracker;
import peer.Peer;

import java.math.BigInteger;
import java.util.List;

public class TrackerResponse {
    private final BigInteger interval;
    private final List<Peer> peers;

    TrackerResponse(BigInteger interval, List<Peer> peers){
        this.interval = interval;
        this.peers = peers;
    }

    public List<Peer> getPeerList() {
        return peers;
    }

    public BigInteger getInterval() {
        return interval;
    }
}
