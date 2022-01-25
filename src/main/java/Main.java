import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("/onboarding.fxml"));
        Parent root= loader.load();//onboarding as initial scene
        Scene scene=new Scene(root);
        stage.setTitle("Sales Taxes Calculator");
        stage.setScene(scene);
        stage.show();

    }

}
