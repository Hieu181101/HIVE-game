package meerkat;

import java.util.ArrayList;

public class PieceFlyweightFactory {
    ArrayList<PieceFlyweight> flyweights;

    static PieceFlyweightFactory instance;

    private PieceFlyweightFactory() {
        flyweights = new ArrayList<>();
    }

    public static PieceFlyweightFactory getInstance() {
        if (PieceFlyweightFactory.instance == null) {
            PieceFlyweightFactory.instance = new PieceFlyweightFactory();
        }
        return PieceFlyweightFactory.instance;
    }

    public PieceFlyweight get(PieceType type) {
        PieceFlyweight flyweight = null;
        for (PieceFlyweight item : flyweights) {
            if (item.type == type) {
                flyweight = item;
                break;
            }
        }

        if (flyweight == null) {
            flyweight = new PieceFlyweight(type);
            flyweights.add(flyweight);

        }

        return flyweight;
    }
}
