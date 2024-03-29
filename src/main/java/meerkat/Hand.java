package meerkat;

public class Hand {
    int ants;
    int beetles;
    int grasshoppers;
    int queens;
    int spiders;

    public Hand() {
        this.ants = 3;
        this.beetles = 2;
        this.grasshoppers = 3;
        this.queens = 1;
        this.spiders = 2;
    }

    public void decrementPiece(PieceType type) {
        switch (type) {
            case Ant:
                if (this.ants == 0) {
                    throw new IllegalStateException();
                }
                this.ants -= 1;
                break;
            case Beetle:
                if (this.beetles == 0) {
                    throw new IllegalStateException();
                }
                this.beetles -= 1;
                break;
            case Grasshopper:
                if (this.grasshoppers == 0) {
                    throw new IllegalStateException();
                }
                this.grasshoppers -= 1;
                break;
            case Queen:
                if (this.queens == 0) {
                    throw new IllegalStateException();
                }
                this.queens -= 1;
                break;
            case Spider:
                if (this.spiders == 0) {
                    throw new IllegalStateException();
                }
                this.spiders -= 1;
                break;
            default:
                throw new IllegalStateException();

        }
    }

    public int getCount(PieceType type) {
        switch (type) {
            case Ant:
                return this.ants;
            case Beetle:
                return this.beetles;
            case Grasshopper:
                return this.grasshoppers;
            case Queen:
                return this.queens;
            case Spider:
                return this.spiders;
            default:
                throw new IllegalStateException();

        }
    }

    public boolean isEmpty() {
        return this.ants == 0 && this.beetles == 0 && this.grasshoppers == 0 && this.queens == 0 && this.spiders == 0;
    }
}
