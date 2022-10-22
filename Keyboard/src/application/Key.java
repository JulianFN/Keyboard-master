package application;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.LinkedList;

/**
 * @author Julian Nieto
 * Description: Actually keys for {@link KeyBoard}
 */
public class Key {
    private final Rectangle2D SCREEN_BOUNDS = new Rectangle2D(0, 0, 1920, 1080);
    private final boolean black;
    private final int key;
    private int note;
    private AudioClip audio;
    private Rectangle keyRect;
    private Rectangle whiteRect;
    private Text text;
    private final LinkedList<Color> color;
    private boolean triggered = false;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private static final int START_NOTE = 9; // A note
    public static final int NUM_WHITE_KEYS = 52;

    private double keyWidth;

    /**
     * Constructs all of the keys
     *
     * @param i
     */
    public Key(int i) {
        color = new LinkedList<Color>();
        key = i;
        note = (i + START_NOTE) % 12;
        if (note < 5) // Before E
            black = note % 2 == 1;
        else
            black = note % 2 == 0;
        this.draw();

        //KeyEvents
        keyRect.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                char key = event.getText().charAt(0);
                if (event.getCode() != KeyCode.ESCAPE && key == (char) (i + 38)) {
                    if (!triggered) {
                        //audio.play(.2)
                        triggered = true;
                        if (black)
                            changeColor(Color.DARKGRAY);
                        else
                            changeColor(Color.LIGHTGRAY);
                    }
                }
            }
        });
        keyRect.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                char key = event.getText().charAt(0);
                if (key == (char) (i + 38)) {
                    if (black) {
                        color.remove(Color.DARKGRAY);
                        keyRect.setFill(color.peek());
                    } else {
                        color.remove(Color.LIGHTGRAY);
                        keyRect.setFill(color.peek());
                        whiteRect.setFill(color.peek());
                    }
                    triggered = false;
                }
            }
        });
    }

    /**
     * Draws All of the keys
     *
     * @return
     */
    public void draw() {
        double x = (SCREEN_BOUNDS.getWidth() / NUM_WHITE_KEYS) * calculateWhite(); // Calculates amount of whites determine spot
        double y = SCREEN_BOUNDS.getHeight() - (SCREEN_BOUNDS.getHeight() / 4);
        double height = (SCREEN_BOUNDS.getHeight() / 8);
        double whiteWidth = SCREEN_BOUNDS.getWidth() / NUM_WHITE_KEYS;
        double offset = (SCREEN_BOUNDS.getWidth() / NUM_WHITE_KEYS) - SCREEN_BOUNDS.getWidth() / 208;
        //if Black
        if (black) {
            x = x + offset;
            keyRect = new Rectangle(x, y, whiteWidth / 2, height);
            color.push(Color.BLACK);
            keyWidth = whiteWidth / 2;
            keyRect.setFill(Color.BLACK);
        } else {
            /**
             * Drawing white keys since some keys are cut off because of the black keys overlapping we draw 2 rectangles
             * One represents the bottom half of the white key and the other the top half
             */
            color.push(Color.WHITE);
            keyRect = new Rectangle(x, y + (SCREEN_BOUNDS.getHeight() / 8), whiteWidth, height);
            keyRect.setFill(Color.WHITE);
            if (key == 0 || key == 87) {
                whiteRect = new Rectangle(x, y, whiteWidth, height);
                whiteRect.setFill(Color.WHITE);
                keyWidth = whiteWidth;
            } else if (note == 4 || note == 11) {
                whiteRect = new Rectangle(x + SCREEN_BOUNDS.getWidth() / 208, y, offset, height);
                whiteRect.setFill(Color.WHITE);
                keyWidth = offset;
            } else if (note == 2 || note == 7 || note == 9) {
                whiteRect = new Rectangle(x + SCREEN_BOUNDS.getWidth() / 208, y, SCREEN_BOUNDS.getWidth() / 104, height);
                whiteRect.setFill(Color.WHITE);
                keyWidth = SCREEN_BOUNDS.getWidth() / 104;
            } else {
                whiteRect = new Rectangle(x, y, offset, height);
                whiteRect.setFill(Color.WHITE);
                keyWidth = offset;
            }
        }
        text = new Text(x, y + (black ? 50 : 150), this.getNoteName() + " " + key);
        text.setFill(Color.RED);
        text.setFont(new Font(10));
    }

    /**
     * gets the Key
     *
     * @return
     */
    public int getKey() {
        return key;
    }

    /**
     * Changes Color to c
     *
     * @param c Color
     */
    public void changeColor(Color c) {
        color.addFirst(c);
        if (black) {
            keyRect.setFill(color.peek());
        } else {
            keyRect.setFill(color.peek());
            whiteRect.setFill(color.peek());
        }
    }

    /**
     * Calculates amount of Whites before it
     *
     * @return number of whites
     */
    private int calculateWhite() {
        boolean blackKey;
        int x = 0;
        for (int k = 1; k <= key; k++) {
            note = (k + 9) % 12;
            if (note < 5)
                blackKey = note % 2 == 1;
            else
                blackKey = note % 2 == 0;
            if (!blackKey)
                x++;
        }
        return x;
    }

    public void removeColor(Color c) {
        color.remove(c);
        if (black) {
            keyRect.setFill(color.peek());
        } else {
            keyRect.setFill(color.peek());
            whiteRect.setFill(color.peek());
        }
    }

    /**
     * gets the Rectangle
     *
     * @return
     */
    public Rectangle getRect() {
        return keyRect;
    }

    public boolean isPlaying() {
        return triggered;
    }

    /**
     * Returns extra Rectangles for Whites
     *
     * @return
     */
    public Rectangle getWRect() {
        return whiteRect;
    }

    public Text getNoteText() {
        return text;
    }


    public LinkedList<Color> getColor() {
        return color;
    }

    public boolean getBlack() {
        return black;
    }

    public void setPlaying(boolean mid) {
        triggered = mid;
    }

    public String getNoteName() {
        return NOTE_NAMES[note];
    }

    public double getKeyWidth() {
        return keyWidth;
    }
}
