package meerkat;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;

public class PieceFlyweight {
    PieceType type;
    Image icon;

    PieceFlyweight(PieceType type) {
        this.type = type;
    }

    public void draw(Graphics g, Point p, int size) {
        if (this.icon == null) {
            String url;
            switch (this.type) {
                case Ant:
                    url = "/Ant.png";
                    break;
                case Beetle:
                    url = "/Beetle.png";
                    break;
                case Grasshopper:
                    url = "/Grasshopper.png";
                    break;
                case Queen:
                    url = "/Queen.png";
                    break;
                case Spider:
                    url = "/Spider.png";
                    break;
                default:
                    throw new IllegalStateException();

            }
            try {
                this.icon = ImageIO.read(getClass().getResource(url));
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        g.drawImage(this.icon, p.x - size, p.y - size, null);

    }
}
