package application;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static application.MIDIConstants.MICROSECONDS_PER_SECOND;

/**
 * @author Julian Nieto
 * Description: Note class draws and animates the Notes
 */
public class Note {
    public static final Rectangle2D SCREEN_BOUNDS = KeyBoard.SCREEN_BOUNDS;
    private final long time;
    private final int durationToReachEnd = 5;
    private final long length; //tick length
    private Rectangle note;
    private Timeline timeline;
    private final double ticksToMic;
    private final Color paint;
    private final Key key;
    private boolean in = false;
    private DoubleProperty y;

    // public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E",
    // "F", "F#", "G", "G#", "A", "A#", "B"};

    public Note(float time, long length, double ticksToMic, Color color, Key key) {
        paint = color;
        this.time = (long) time;
        this.length = length;
        this.ticksToMic = ticksToMic;
        this.key = key;
        // System.out.println("Sup "+length+ " " +ticks);
    }

    public void play(Pane x, TextField tex) {
        this.draw();
        DoubleProperty xdouble = new SimpleDoubleProperty();
        y = new SimpleDoubleProperty();
        note.setFill(paint);


        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = System.nanoTime();

            @Override
            public void handle(long now) {

                double visibleTime = getVisibleTime();

                double targetPixelsPerSecond = getPixelsFromTopToKeyboard() / visibleTime;

                note.setFill(paint);
                double y = note.getY();
                if (!in && (y + note.getHeight()) > getPixelsFromTopToKeyboard()) {
                    in = true;
                    if (!key.isPlaying()) {
                        key.changeColor(paint);
                    }
                }
                double elapsedSeconds = (now - lastUpdate) / 1_000_000_000.0;

                Point2D translationVector = new Point2D(0, 1)
                        .normalize()
                        .multiply(targetPixelsPerSecond * elapsedSeconds);

                note.setY(translationVector.getY() + y);

                if (y > getPixelsFromTopToKeyboard()) {
                    x.getChildren().remove(note);
                    key.removeColor(paint);
                    this.stop();
                }
                lastUpdate = now;


            }
        };
        x.getChildren().add(note);
//		note.setOnKeyPressed(new EventHandler<KeyEvent>() {
//			public void handle(KeyEvent event) {
//				// //System.out.println(timeline.getCurrentTime().toSeconds()+ "
//				// " +timeline.getCurrentRate());
//				// if(isPlayed())
//				// {
//				// System.out.println("Points");
//				// }
//			}
//		});
        timer.start();
    }

    private double getPixelsFromTopToKeyboard() {
        double keyboardHeightProportion = .25; // keyboard takes 1/4 of height
        return (SCREEN_BOUNDS.getHeight() - (SCREEN_BOUNDS.getHeight() * keyboardHeightProportion));
    }

    public void draw() {
        Rectangle rect = key.getBlack() ? key.getRect() : key.getWRect();

        double noteLengthInSeconds = (length * ticksToMic) / MICROSECONDS_PER_SECOND;
        double visibleTime = getVisibleTime();
        double amountOfVisibleTimes = noteLengthInSeconds / visibleTime;
        double lengthInPixels = amountOfVisibleTimes * getPixelsFromTopToKeyboard();
//        System.out.println("Length In Pixels: " + lengthInPixels + " visibleTime: " + visibleTime + " noteLengthInSeconds " + noteLengthInSeconds + " ticksToMic: " + ticksToMic);
//        System.out.println(" NOTE: " + key.getNoteName() + " X: " + rect.getX() + " Y: " + -lengthInPixels + " Width: " + key.getKeyWidth() + " Height: " + lengthInPixels);
        note = new Rectangle(rect.getX(), -lengthInPixels, key.getKeyWidth(), lengthInPixels);
        note.setArcHeight(10);
        note.setArcWidth(10);
        note.setFill(paint);
    }


    public boolean getIn() {
        return in;
    }

    public Rectangle getNote() {
        return note;
    }

    public double getTime() {
        return time * ticksToMic;
    }

    public DoubleProperty getY() {
        return y;
    }

    public void setIn(boolean f) {
        in = f;
    }

    public Color getPaint() {
        return paint;
    }

    public double getVisibleTime() {
        double keyboardHeightProportion = .25; // keyboard takes 1/4 of height
        return durationToReachEnd - durationToReachEnd * keyboardHeightProportion;

    }

    // public double getDuration()
    // {
    // return (note.getHeight()+550)/180;
    // }
    public int compareTo(Note s) {
        if (this.getTime() > s.getTime())
            return 1;
        else if (this.getTime() < s.getTime())
            return -1;
        else
            return 0;
    }
}
