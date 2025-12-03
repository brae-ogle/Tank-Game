package model;
import javafx.scene.image.Image;
import java.util.List;

public class ExplosionEvent {
    private int x, y;
    private int frameIndex = 0;
    private boolean finished = false;
    private List<Image> frames;

    public ExplosionEvent(int x, int y) {
        this.x = x; this.y = y;
        setFrames();
    }

    public void update() {
        frameIndex++;
        if (frameIndex >= frames.size()) {
            finished = true;
        }
    }

    public Image getCurrentFrame() {
        if (!finished) {
            return frames.get(frameIndex);
        } else {
            return null;
        }
    }

    public boolean isFinished() {
        return finished;
    }
    public int getX() { return x; }
    public int getY() { return y; }

    public List<Image> getFrames() { return frames; }

    private void setFrames() {
         Image explosionFrameZero = new Image(getClass().getResource("/0.gif").toExternalForm());
         Image explosionFrameOne = new Image(getClass().getResource("/1.gif").toExternalForm());
         Image explosionFrameTwo = new Image(getClass().getResource("/2.gif").toExternalForm());
         Image explosionFrameThree = new Image(getClass().getResource("/3.gif").toExternalForm());
         Image explosionFrameFour = new Image(getClass().getResource("/4.gif").toExternalForm());
         Image ExplosionFrameFive = new Image(getClass().getResource("/5.gif").toExternalForm());
         Image ExplosionFrameSix = new Image(getClass().getResource("/6.gif").toExternalForm());
         Image ExplosionFrameSeven = new Image(getClass().getResource("/7.gif").toExternalForm());
         Image ExplosionFrameEight= new Image(getClass().getResource("/8.gif").toExternalForm());
         Image ExplosionFrameNine= new Image(getClass().getResource("/9.gif").toExternalForm());
         Image ExplosionFrameTen= new Image(getClass().getResource("/10.gif").toExternalForm());
            frames = List.of(
                explosionFrameZero, explosionFrameOne, explosionFrameTwo,
                explosionFrameThree, explosionFrameFour, ExplosionFrameFive,
                ExplosionFrameSix, ExplosionFrameSeven, ExplosionFrameEight,
                ExplosionFrameNine, ExplosionFrameTen
            );
    }
}
