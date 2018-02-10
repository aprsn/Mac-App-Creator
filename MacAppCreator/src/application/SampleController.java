package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class SampleController implements Initializable {
 
	//Define -->
	FileChooser jarFileChooser = new FileChooser();
	FileChooser iconFileChooser = new FileChooser();
	//------------------------------------------
	private String sep = File.separator;
	@FXML
	private Label directoryLabel;
	@FXML
	private Label jarDirectoryLabel;
	@FXML
	private Label iconDirectoryLabel;
	@FXML
	private JFXTextField applicationNameField;
	@FXML
	private JFXTextField appVersion;
	@FXML
	private JFXTextField appCopyright;
	//------------------------------------------
	private String appName;
	private String JARDirectory;
	private String Directory;
	private String IconDirectory;
	//<-- Define
	public void alert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		 alert.setTitle("Error");
		 alert.setHeaderText("Mac App Creator");
		 alert.setContentText(message);
		 alert.show();
	}
	@FXML
	public void exitSystem(MouseEvent event) {
		System.exit(0);
	}
	 @FXML	
	private void infoSystem(MouseEvent event) {
		 
		 Alert alert = new Alert(AlertType.INFORMATION);
		 
		 alert.setTitle("Info");
		 
		 alert.setHeaderText("Mac App Creator");
		 
		 String s ="Convert your runnable jar file to Mac app.  \n\n\n Powered by Alper San";
		 
		 alert.setContentText(s);
		 
		 alert.show();

	}
	
	 @FXML
	 private void chooseDirectory(MouseEvent event) {
		 Stage primaryStage = Main.getPrimaryStage();
		
		 DirectoryChooser chooser = new DirectoryChooser();
		 chooser.setTitle("Choose a Location");
		 String userDir = System.getProperty("user.home");
		 File defaultDirectory = new File(userDir+sep+"Desktop");
		 chooser.setInitialDirectory(defaultDirectory);
		 File selectedDirectory = chooser.showDialog(primaryStage); 
		
		 if(selectedDirectory !=null) {
				directoryLabel.setText("Location: "+selectedDirectory.getAbsolutePath());
				Directory = selectedDirectory.getAbsolutePath();
		 }else {}
		 
	 }
	 @FXML
	 private void chooseJar(MouseEvent event) throws Exception {
		 Stage primaryStage = Main.getPrimaryStage();
		 File jarFile = jarFileChooser.showOpenDialog(primaryStage);
		 
			 if (jarFile != null) {
				 jarDirectoryLabel.setText("JAR File Location: "+jarFile.getAbsolutePath());
				 JARDirectory = jarFile.getAbsolutePath();
			 }else{}
	 }
	 @FXML
	 private void chooseIcon(MouseEvent event) {
		 Stage primaryStage = Main.getPrimaryStage();
		 
		 File iconFile = iconFileChooser.showOpenDialog(primaryStage);
		 if (iconFile != null) {
			 iconDirectoryLabel.setText("Icon Location: "+iconFile.getAbsolutePath());
			  IconDirectory= iconFile.getAbsolutePath();
		 }else{}
	 }
	 @FXML
	 private void createApp(MouseEvent event) {
		 
		 	String getAppName = applicationNameField.getText();
		 	appName = getAppName.replaceAll("\\s+","");
		 	String appVers = appVersion.getText();
		 	String appCopy = appCopyright.getText();
		 	
		 	if(appVers.isEmpty()) {appVers = "1.0";}
		 	if(appCopy.isEmpty()) {appCopy = "Copyright Alper San";}
		 
		 	if(getAppName.isEmpty()) {
		 		alert("Please enter an app name and try again!");
		 	}else {
			File dosya = new File(Directory+sep+ appName + ".app");
			dosya.mkdir();
			File contents = new File(Directory+sep+ appName+".app"+sep+"Contents");
			contents.mkdir();
			File MacOS = new File(Directory+sep+ appName+".app"+sep+"Contents"+sep+"MacOS");
			MacOS.mkdir();
			File resources = new File(Directory+sep+ appName+".app"+sep+"Contents"+sep+"Resources");
			resources.mkdir();
			
		
			//copy Jar File
			File jarFile = new File(JARDirectory);
			String createJar = Directory+sep+ appName+".app"+sep+"Contents"+sep+"MacOS"+sep+jarFile.getName();
			File newJarFile = new File(createJar);
			Path newJarFilePath = newJarFile.toPath();
			Path srcJarFilePath = jarFile.toPath();
			try {
				Files.copy(srcJarFilePath, newJarFilePath);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			//Copy icns File
			File iconFile = new File(IconDirectory);
			String createIcon = Directory+sep+ appName+".app"+sep+"Contents"+sep+"Resources"+sep+"application.icns";
			File newIconFile = new File(createIcon);
			Path newIconFilePath = newIconFile.toPath();
			Path srcIconFilePath = iconFile.toPath();
			try {
				Files.copy(srcIconFilePath, newIconFilePath);
			} catch (Exception e1) {
				alert(e1.getMessage());
			}
			
			
			
			//Create info.plist
			File infoPlist = new File(Directory + sep +appName + ".app"+sep+"Contents"+sep+"info.plist");
			try {
				infoPlist.createNewFile();
			} catch (IOException e) {
				alert(e.getMessage());
			}
			//Create launcher
			File launcher = new File(Directory + sep + appName + ".app"+sep+"Contents"+sep+"MacOS"+sep+"launcher");
			try {
				launcher.createNewFile();
			} catch (IOException e) {
				alert(e.getMessage());
			}
			
			
			//Fill the files
			createFile fill = new createFile();
			fill.launcher(appName, Directory , JARDirectory , getAppName);
			fill.infoPlist(appName, Directory, appVers , appCopy);
			
			try {
				Runtime.getRuntime().exec("chmod +x "+Directory + sep + appName + ".app"+sep+"Contents"+sep+"MacOS"+sep+"launcher");
			} catch (Exception e) {
				alert(e.getMessage());
			}
			
		 	}
	 }
	 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Only Jar Files
		 jarFileChooser.getExtensionFilters().addAll(
			        new ExtensionFilter("JAR Files", "*.jar"));
		 //Only ICNS Files
		 iconFileChooser.getExtensionFilters().addAll(
			        new ExtensionFilter("ICNS Files", "*.icns"));
	}
	//Init -End-

}



