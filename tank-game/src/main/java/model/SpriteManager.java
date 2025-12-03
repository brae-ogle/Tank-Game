package model;
import javafx.scene.image.Image;
//Handler for loading and providing game sprites
public class SpriteManager {
    public Image tankUp = new Image(getClass().getResource("/tankU.gif").toExternalForm());
    public Image tankDown = new Image(getClass().getResource("/tankD.gif").toExternalForm());
    public Image tankLeft = new Image(getClass().getResource("/tankL.gif").toExternalForm());
    public Image tankRight = new Image(getClass().getResource("/tankR.gif").toExternalForm());

    public Image missileUp = new Image(getClass().getResource("/missileU.gif").toExternalForm());
    public Image missileDown = new Image(getClass().getResource("/missileD.gif").toExternalForm());
    public Image missileLeft = new Image(getClass().getResource("/missileL.gif").toExternalForm());
    public Image missileRight = new Image(getClass().getResource("/missileR.gif").toExternalForm());

    public Image medpack = new Image(getClass().getResource("/medpack.png").toExternalForm());

    public Image wall = new Image(getClass().getResource("/wall.png").toExternalForm());
    public Image introScreen = new Image(getClass().getResource("/introscreen.png").toExternalForm());
}
