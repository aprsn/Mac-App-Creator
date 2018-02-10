package application;

import java.io.File;
import java.io.PrintWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class createFile {
	public void alert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		 alert.setTitle("Error");
		 alert.setHeaderText("Mac App Creator");
		 alert.setContentText(message);
		 alert.show();
	}
	private String sep = File.separator;
	public void infoPlist(String appName, String Directory , String version , String copy) {
		
		
		try {
			PrintWriter plistWriter = new PrintWriter(Directory+sep+ appName+".app"+sep+"Contents"+sep+"info.plist", "UTF-8");	
			plistWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
					"<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" + 
					"<plist version=\"1.0\">\n" + 
					"<dict>\n" + 
					"	<key>CFBundleDevelopmentRegion</key>\n" + 
					"	<string>English</string>\n" + 
					"	<key>CFBundleExecutable</key>\n" + 
					"	<string>launcher</string>\n" + 
					"	<key>CFBundleIconFile</key>\n" + 
					"	<string>application.icns</string>\n" + 
					"	<key>CFBundleInfoDictionaryVersion</key>\n" + 
					"	<string>6.0</string>\n" + 
					"	<key>CFBundlePackageType</key>\n" + 
					"	<string>APPL</string>\n" + 
					
					"	<key>NSHumanReadableCopyright</key>\n"+
					"	<string>\""+copy+"\"</string>\n" +
					"	<key>CFBundleShortVersionString</key>\n" + 
					"	<string>\""+version+"\"</string>\n" + 
					"	<key>CFBundleName</key>\n" + 
					"	<string>\""+appName+"</string>"+
				
					"	<key>CFBundleSignature</key>\n" + 
					"	<string>xmmd</string>\n" + 
					"	<key>CFBundleVersion</key>\n" + 
					"	<string>2.2</string>\n" + 
					"	<key>NSAppleScriptEnabled</key>\n" + 
					"	<string>NO</string>\n" + 
					"</dict>\n" + 
					"</plist>");
			plistWriter.close();
		}catch(Exception e) {
			 alert(e.getMessage());
		}
	}
	
	public void launcher(String location, String Directory , String jarLocation, String appName ) {
		
		File jarFile = new File(jarLocation);
		try {
			PrintWriter launcherWriter = new PrintWriter(Directory+sep+location+".app"+sep+"Contents"+sep+"MacOS"+sep+"launcher", "UTF-8");	
			launcherWriter.write("#!/bin/sh\n" + 
					"#--Alper San--\n" + 
					"\n" + 
					"# Constants\n" + 
					"JAVA_MAJOR=1\n" + 
					"JAVA_MINOR=7\n" + 
					"APP_JAR=\""+jarFile.getName()+"\"\n" + 
					"APP_NAME=\""+appName+"\"\n" + 
					"VM_ARGS=\"\"\n" + 
					"\n" + 
					"DIR=$(cd \"$(dirname \"$0\")\"; pwd)\n" + 
					"\n" + 
					"# Error message for NO JAVA dialog\n" + 
					"ERROR_TITLE=\"Cannot launch $APP_NAME\"\n" + 
					"ERROR_MSG=\"$APP_NAME requires Java version $JAVA_MAJOR.$JAVA_MINOR or later to run.\"\n" + 
					"DOWNLOAD_URL=\"http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1880261.html\"\n" + 
					"\n" + 
					"# Is Java installed?\n" + 
					"if type -p java; then\n" + 
					"    _java=\"java\"\n" + 
					"elif [[ -n \"$JAVA_HOME\" ]] && [[ -x \"$JAVA_HOME/bin/java\" ]]; then\n" + 
					"    _java=\"$JAVA_HOME/bin/java\"\n" + 
					"else\n" + 
					"    osascript \\\n" + 
					"	-e \"set question to display dialog \\\"$ERROR_MSG\\\" with title \\\"$ERROR_TITLE\\\" buttons {\\\"Cancel\\\", \\\"Download\\\"} default button 2\" \\\n" + 
					"	-e \"if button returned of question is equal to \\\"Download\\\" then open location \\\"$DOWNLOAD_URL\\\"\"\n" + 
					"	echo \"$ERROR_TITLE\"\n" + 
					"	echo \"$ERROR_MSG\"\n" + 
					"	exit 1\n" + 
					"fi\n" + 
					"\n" + 
					"# Java version check\n" + 
					"if [[ \"$_java\" ]]; then\n" + 
					"    version=$(\"$_java\" -version 2>&1 | awk -F '\"' '/version/ {print $2}')\n" + 
					"    if [[ \"$version\" < \"$JAVA_MAJOR.$JAVA_MINOR\" ]]; then\n" + 
					"        osascript \\\n" + 
					"    	-e \"set question to display dialog \\\"$ERROR_MSG\\\" with title \\\"$ERROR_TITLE\\\" buttons {\\\"Cancel\\\", \\\"Download\\\"} default button 2\" \\\n" + 
					"    	-e \"if button returned of question is equal to \\\"Download\\\" then open location \\\"$DOWNLOAD_URL\\\"\"\n" + 
					"    	echo \"$ERROR_TITLE\"\n" + 
					"    	echo \"$ERROR_MSG\"\n" + 
					"    	exit 1\n" + 
					"    fi\n" + 
					"fi\n" + 
					"\n" + 
					"# Run the application\n" + 
					"exec $_java $VM_ARGS -Dapple.laf.useScreenMenuBar=true -Dcom.apple.macos.use-file-dialog-packages=true -Xdock:name=\"$APP_NAME\" -Xdock:icon=\"$DIR/../Resources/application.icns\" -cp \".;$DIR;\" -jar \"$DIR/$APP_JAR\"");
			launcherWriter.close();
		}catch(Exception e) {
			 alert(e.getMessage());
		}
		
	}

	
	
}
