package logic;

public class TradePort {
    private boolean[] tradePorts = new boolean[6];
    //represent the existence of each type of port

    public TradePort() {
        for (int i = 0; i < tradePorts.length; i++) {
            tradePorts[i] = false;
        }
    }

    public boolean[] getTradePorts() {
        return tradePorts;
    }

    public boolean hasPort(Card c) {
        if(c == null) {
            return tradePorts[5];
        }
        return tradePorts[c.ordinal()];
    }

    public void addPort(Card c) {
        if(c == null) {
            tradePorts[5] = true;
        }
        tradePorts[c.ordinal()] = true;
    }
}

