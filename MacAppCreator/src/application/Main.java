package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	double xx;
	double xy;
	
	 //declare primary stage -->
	private static Stage primaryStage; 
    private static void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }
    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }
    //<-- declare primary stage
	
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
			  root.setOnMousePressed(new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent event) {
		                xx = event.getSceneX();
		                xy = event.getSceneY();
		            }
		        });

		             root.setOnMouseDragged(new EventHandler<MouseEvent>() {
		                 @Override
		                 public void handle(MouseEvent event) {
		                     primaryStage.setX(event.getScreenX() - xx);
		                     primaryStage.setY(event.getScreenY() - xy);
		                 }
		             });
			
		          
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
