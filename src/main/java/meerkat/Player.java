package meerkat;

public enum Player {
    White,
    Black;

    @Override
    public String toString() {
        switch (this) {
            case Black:
                return "Black";
            case White:
                return "White";
            default:
                throw new IllegalStateException();
        }
    }
}
