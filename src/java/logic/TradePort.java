package logic;

public class TradePort {
    private boolean[] tradePorts = new boolean[6];

    public boolean[] getTradePorts() {
        return tradePorts;
    }

    public boolean hasPort(Card c) {
        return tradePorts[c.ordinal()];
    }

    public void addPort(Card c) {
        if(c == null) {
            tradePorts[5] = true;
        }
        tradePorts[c.ordinal()] = true;
    }
}

