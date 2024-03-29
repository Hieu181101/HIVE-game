package meerkat;

public class BoardPosition {
    public static final BoardPosition ORIGIN = new BoardPosition(0, 0, 0);

    public static final BoardPosition NORTH = new BoardPosition(0, 1, -1);
    public static final BoardPosition NORTHEAST = new BoardPosition(1, 0, -1);
    public static final BoardPosition SOUTHEAST = new BoardPosition(1, -1, 0);
    public static final BoardPosition SOUTH = new BoardPosition(0, -1, 1);
    public static final BoardPosition SOUTHWEST = new BoardPosition(-1, 0, 1);
    public static final BoardPosition NORTHWEST = new BoardPosition(-1, 1, 0);

    public static final BoardPosition[] DIRECTIONS = {
        NORTH,
        NORTHEAST,
        SOUTHEAST,
        SOUTH,
        SOUTHWEST,
        NORTHWEST
    };

    private int r;
    private int s;
    private int t;

    public int getR() {
        return r;
    }

    public int getS() {
        return s;
    }

    public int getT() {
        return t;
    }

    public BoardPosition(int r, int s, int t) {
        if (r + s + t != 0) {
            throw new IllegalArgumentException("Invalid coordinate");
        }

        this.r = r;
        this.s = s;
        this.t = t;
    }

    public BoardPosition(BoardPosition other) {
        this.r = other.r;
        this.s = other.s;
        this.t = other.t;
    }

    public BoardPosition add(BoardPosition other) {
        BoardPosition result = new BoardPosition(this);
        result.r += other.r;
        result.s += other.s;
        result.t += other.t;

        return result;
    }

    public BoardPosition sub(BoardPosition other) {
        BoardPosition result = new BoardPosition(this);
        result.r -= other.r;
        result.s -= other.s;
        result.t -= other.t;

        return result;
    }

    public BoardPosition scale(int factor) {
        BoardPosition result = new BoardPosition(this);
        result.r *= factor;
        result.s *= factor;
        result.t *= factor;

        return result;
    }

    public BoardPosition[] getNeighbours() {
        BoardPosition[] neighbours = {
                this.add(NORTH),
                this.add(NORTHEAST),
                this.add(SOUTHEAST),
                this.add(SOUTH),
                this.add(SOUTHWEST),
                this.add(NORTHWEST),
        };

        return neighbours;
    }

    public BoardPosition rotateLeft() {
        return new BoardPosition(-t, -r, -s);

    }

    public BoardPosition rotateRight() {
        return new BoardPosition(-s, -t, -r);

    }

    public int hashCode() {
        return r * 577 + s * 317 + t * 97;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BoardPosition other = (BoardPosition) o;
        return other.r == this.r && other.s == this.s && other.t == this.t;
    }
}
