
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Handler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
public class UI extends Application{
	private static ContactBook cb;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		StackPane MainPane = new StackPane();
		GridPane CenterGridPane = new GridPane();
		VBox LeftVBox = new VBox();
		Pane RightPane = new Pane();
		HBox internalRightVBoxMenu = new HBox();
		
		
		CenterGridPane.setAlignment(Pos.CENTER);
		LeftVBox.setAlignment(Pos.CENTER);
		//RightVBox.setAlignment(Pos.CENTER);
		CenterGridPane.setHgap(25);
		CenterGridPane.setVgap(10);
		//CenterGridPane.scaleXProperty().bind(MainPane.layoutXProperty());
		//CenterGridPane.scaleYProperty().bind(MainPane.heightProperty());
		
	
		
		BackgroundFill bckfill = new BackgroundFill(Color.ANTIQUEWHITE, null, new Insets(5));
		BackgroundFill AntiqueBackFill = new BackgroundFill(Color.LINEN, null, new Insets(0));
		Background AntiqueBackground = new Background(AntiqueBackFill);
		Background Gridbackground = new Background(bckfill);
		CenterGridPane.setBackground(Gridbackground);
		MainPane.setBackground(AntiqueBackground);
		LeftVBox.setBackground(AntiqueBackground);
		RightPane.setBackground(AntiqueBackground);
		
		
		
		
		BorderStroke bdstk = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2));
		Border bd = new Border(bdstk);
		LeftVBox.setBorder(bd);
		RightPane.setBorder(bd);
		 
		//Labels for the HBox and VBox
		
		Font titleFont = Font.font("Lato Semibold", FontWeight.EXTRA_BOLD, 30);
		Label CurrentContacts = new Label("Current Contacts");
		Label ContactInfo = new Label("Contact Information");
		CurrentContacts.setUnderline(true);
		ContactInfo.setUnderline(true);
		CurrentContacts.setFont(titleFont);
		ContactInfo.setFont(titleFont);
		
		//end label
		
		LeftVBox.setPrefHeight(300);
		LeftVBox.setPrefWidth(CurrentContacts.getWidth());
		
		//Add Delete Buttons
		HBox buttonHBox = new HBox();
		Button AddContact = new Button("Add Contact");
		AddContact.setOnAction(new AddHandler());
		
		Button DeleteContact = new Button("Delete Contact");
		DeleteContact.setOnAction(new DeleteHandler());
		
		Button EditContact = new Button("Edit Contact");
		
		EditContact.setOnAction(new EditHandler());
		
		buttonHBox.getChildren().addAll(AddContact,EditContact,  DeleteContact);
		buttonHBox.setSpacing(10);
		buttonHBox.setAlignment(Pos.BASELINE_CENTER);
		
		//end add delete buttons
		MainPane.getChildren().add(CenterGridPane);
		
		CenterGridPane.setGridLinesVisible(true);
		
		
		CenterGridPane.add(CurrentContacts, 0, 0);
		CenterGridPane.add(ContactInfo, 1, 0);
		CenterGridPane.add(LeftVBox, 0, 1);
		CenterGridPane.add(RightPane, 1, 1);
		CenterGridPane.add(buttonHBox, 1, 2);
		
	
	
		//Textfeild for contact information
		TextArea txf = new TextArea();
		
		txf.textProperty();
		txf.setEditable(false);
		txf.setText("Contact information is here");
		txf.setMinWidth(RightPane.getWidth());
		txf.setMinHeight(RightPane.getHeight());
	
		
		
	
		LeftVBox.getChildren().add(txf);
	
		
		
		MainPane.setPadding(new Insets(10,10,10,10));
		
		
		
		//internalRightVBoxMenu.getChildren().addAll(AddContact, DeleteContact);
		internalRightVBoxMenu.setAlignment(Pos.BOTTOM_CENTER);
		RightPane.getChildren().add(txf);
		RightPane.getChildren().add(internalRightVBoxMenu);
		
		
		
		
		Scene scene = new Scene(MainPane, 700, 500);	
		primaryStage.setTitle("Contact Book");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public class AddHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			try {
				addPrompt();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class EditHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			editContact();
		}
	}
	
	public class DeleteHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e){
			deleteContact();
		}
	}
	
	public static void main(String[] args) {
		cb = new ContactBook();
		//File fi = new File("Contacts.txt");
		
		try {
			FileInputStream fstream = new FileInputStream("Contacts.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String s;
			while((s = br.readLine()) != null) {
				String[] tokens = s.split(",");
				Contact c = new Contact(tokens[0], tokens[1], tokens[2], tokens[3]);
				cb.addContactToBook(c);
			}
			in.close();
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		
		
//		if(fi.exists()){
//			Scanner sc = new Scanner(fi);
//			sc.useDelimiter("[,]");
//			while(sc.hasNextLine()){
//				String f; String l; String e; String p;
//				f = sc.next();
//				l = sc.next();
//				e = sc.next();
//				p = sc.next();
//				Contact c = new Contact(f,l,e,p);
//				cb.addContactToBook(c);
//				System.out.println(c.toString());
//			}
//		}
		launch(args);
	}
	
	public void createContact(){
		//Flesh out
		
		System.out.println("Adding new contact");
	}
	public void deleteContact(){
		//flesh out
		System.out.println("Deleting a contact");
	}
	public void editContact(){
		System.out.println("Editing a contact");
	}
	public void addPrompt() throws IOException{
		Stage popup = new Stage();
		GridPane MainPane = new GridPane();
		MainPane.setPadding(new Insets(10));
		
		Label FName = new Label("First Name"); 	TextField TXFNAME = new TextField();
		Label LName = new Label("Last Name");		TextField TXLNAME = new TextField();
		Label Email = new Label("Email Address");		TextField TXEMAIL = new TextField();
		Label PhoneNumber = new Label("Phone Number");		TextField TXPHONENUMBER = new TextField();
		
		Button FinalizeAdd = new Button("Done");
		
		
		FinalizeAdd.setOnAction((event) -> {
			String txfFirstName = TXFNAME.getText();
			String txfLastName = TXLNAME.getText();
			String txfEmail = TXEMAIL.getText();
			String txfPhoneNumber = TXPHONENUMBER.getText();
			
			try {
				Contact c = new Contact(txfFirstName, txfLastName, txfEmail, txfPhoneNumber);
				cb.addContactToBook(c);
				cb.WriteCBToFile();
				System.out.println(cb.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			popup.close();
		});
		
		MainPane.add(FName, 0, 0);	MainPane.add(TXFNAME, 1, 0);
		MainPane.add(LName, 0, 1);	MainPane.add(TXLNAME, 1, 1);
		MainPane.add(Email, 0, 2);	MainPane.add(TXEMAIL, 1, 2);
		MainPane.add(PhoneNumber, 0, 3);	MainPane.add(TXPHONENUMBER, 1, 3);
		MainPane.add(FinalizeAdd, 1, 4);
		
		

		Scene scene = new Scene(MainPane, 280, 180);	
		popup.setTitle("Add Contact");
		popup.setScene(scene);
		popup.show();
	}
}
