package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Menu extends Application {

    private ArrayList<String> paths = new ArrayList<String>();
    private final ArrayList<Button> buttons = new ArrayList<Button>();
    private int x = 0;
    private int location = 0;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Group ro = new Group();
        Pane p = new Pane();
        File v = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "/music");
        // Scanner s = new Scanner(v);
        System.out.println(v.getAbsolutePath());
        // while(s.hasNext())
        // {
        // paths.add(s.nextLine());
        // }
        // s.close();
        System.out.println(v.exists());
        paths = new ArrayList<String>(Arrays.asList(v.list()));
        System.out.println(paths);
        for (int i = 0; i < paths.size(); i++) {
            buttons.add(new Button(paths.get(i).substring(0, paths.get(i).length() - 4)));
            buttons.get(i).setLayoutY(location);
            location += 25;
        }

        for (Button b : buttons) {
            b.setOnAction((event) -> {
                String song = ((Button) event.getSource()).getText();
                KeyBoard k = new KeyBoard(primaryStage, song, p);
            });
            x++;
        }

        // for(TextField we:s)
        // {
        // p.getChildren().add(we);
        // }
        p.getChildren().addAll(buttons);
        ro.getChildren().add(p);
        Scene t = new Scene(ro);
        primaryStage.setScene(t);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
