package application;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Julian Nieto Description: This class is one that deals with the stage
 * and adding and of the panes.
 */
public class KeyBoard {
    public static final Rectangle2D SCREEN_BOUNDS = new Rectangle2D(0, 0, 1920, 1080);
    // Canvas for Background
    private final Canvas c = new Canvas(SCREEN_BOUNDS.getWidth(), SCREEN_BOUNDS.getHeight());
    public static final Key[] keys = new Key[88]; // Keys

    /**
     * @param primaryStage
     * @param str          song name
     * @param menu
     */
    public KeyBoard(Stage primaryStage, String str, Pane menu) {
        try {
            MidiHandle device = new MidiHandle();
            Pane notes = new Pane();
            Pane pane = new Pane();

            if (device.getKeyboard() == null) {
                System.out.println("good");
            }
            // adding Keys
            for (int x = 0; x < keys.length; x++) {
                keys[x] = new Key(x);
                pane.getChildren().add(keys[x].getRect());
                if (!keys[x].getBlack()) {
                    // adding whites
                    pane.getChildren().add(keys[x].getWRect());
                }
                pane.getChildren().add(keys[x].getNoteText());
            }

            primaryStage.setX(SCREEN_BOUNDS.getMinX());
            primaryStage.setY(SCREEN_BOUNDS.getMinY());
            primaryStage.setWidth(SCREEN_BOUNDS.getWidth());
            primaryStage.setHeight(SCREEN_BOUNDS.getHeight());

            // Calls SongReader
            SongReader s = new SongReader(str + ".mid", notes, keys, primaryStage, menu);

            // draws background
            c.getGraphicsContext2D().drawImage(new Image("/application/BackOfGround.jpg", SCREEN_BOUNDS.getWidth(), SCREEN_BOUNDS.getHeight(), false, false), 0, 0);

            // add the panes
            Group root = new Group();
            root.getChildren().add(c);
            root.getChildren().add(notes);
            root.getChildren().add(pane);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            // primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,);

            //adding keyEvents
            primaryStage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        s.getSequencer().stop();
                        Group temp = new Group();
                        temp.getChildren().add(menu);
                        primaryStage.setScene(new Scene(temp));
                    }
                }

            });


            for (int z = 0; z < keys.length; z++) {
                primaryStage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, keys[z].getRect().getOnKeyPressed());
                primaryStage.getScene().addEventHandler(KeyEvent.KEY_RELEASED, keys[z].getRect().getOnKeyReleased());
            }

            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.show();

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.exit(0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
