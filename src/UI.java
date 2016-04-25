
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.regex.Pattern;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.BuilderFactory;
public class UI extends Application{
	private static ContactBook cb;
	Contact selected;
	Contact person;
	StackPane MainPane;
	GridPane CenterGridPane;
	VBox LeftVBox;
	HBox LeftPane;
	StackPane RightPane;
	HBox internalRightVBoxMenu;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		selected = new Contact(null,null,null,null);
		
		MainPane = new StackPane();
		CenterGridPane = new GridPane();
		LeftVBox = new VBox();
		LeftPane = new HBox();
		RightPane = new StackPane();
		internalRightVBoxMenu = new HBox();
		
		CenterGridPane.setAlignment(Pos.CENTER);
		LeftVBox.setAlignment(Pos.CENTER);
		RightPane.setAlignment(Pos.CENTER);
		CenterGridPane.setHgap(25);
		CenterGridPane.setVgap(10);
		//CenterGridPane.scaleXProperty().bind(MainPane.layoutXProperty());
		//CenterGridPane.scaleYProperty().bind(MainPane.heightProperty());
		
	
		
		BackgroundFill bckfill = new BackgroundFill(Color.ANTIQUEWHITE, null, new Insets(5));
		BackgroundFill AntiqueBackFill = new BackgroundFill(Color.ANTIQUEWHITE, null, new Insets(0));
		Background AntiqueBackground = new Background(AntiqueBackFill);
		Background Gridbackground = new Background(bckfill);
		CenterGridPane.setBackground(Gridbackground);
		MainPane.setBackground(AntiqueBackground);
		LeftPane.setBackground(AntiqueBackground);
		LeftVBox.setBackground(AntiqueBackground);
		RightPane.setBackground(AntiqueBackground);
		
		
		BorderStroke bdstk = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2));
		Border bd = new Border(bdstk);
		LeftPane.setBorder(bd);
		LeftPane.setPadding(new Insets(15));
		LeftVBox.setPadding(new Insets(15));
		RightPane.setBorder(bd);
		 
		//Labels for the HBox and VBox
		
		Font titleFont = Font.font("Lato Semibold", FontWeight.EXTRA_BOLD, 25);
		Label CurrentContacts = new Label("Contacts");
		CurrentContacts.setTextAlignment(TextAlignment.CENTER);
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
		DeleteContact.setOnAction((event)->{
			try {
				if(selected.FirstName!=null && selected.LastName!=null){
					cb.removefromBookandFile(selected);
			}
			else{
				UserMessage("No Contact Selected", "To Delete a Contact, You Must First Select What Contact To Delete");
			}
				cb.removefromBookandFile(selected);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		Button EditContact = new Button("Edit Contact");
		
		EditContact.setOnAction(new EditHandler());
		
		buttonHBox.getChildren().addAll(AddContact,EditContact,  DeleteContact);
		buttonHBox.setSpacing(10);
		buttonHBox.setAlignment(Pos.BASELINE_CENTER);
		
		//end add delete buttons
		MainPane.getChildren().add(CenterGridPane);
		
		//CenterGridPane.setGridLinesVisible(true);
		CenterGridPane.add(CurrentContacts, 0, 0);
		CenterGridPane.add(ContactInfo, 1, 0);
		CenterGridPane.add(LeftPane, 0, 1);
		CenterGridPane.add(RightPane, 1, 1);
		CenterGridPane.add(buttonHBox, 1, 2);
		
		LeftPane.getChildren().add(LeftVBox);
	
		//Label for contact information
		Label txf = new Label();
		Font displayContactFont = new Font(20);
		txf.setFont(displayContactFont);
		txf.setWrapText(true);
		txf.setTextAlignment(TextAlignment.CENTER);
		
		txf.textProperty();
//		txf.setEditable(false);
		txf.setText("Contact Information Displayed here");
		txf.setMinWidth(RightPane.getWidth());
		txf.setMinHeight(RightPane.getHeight());
	
		//scroll bar for contact buttons
		ScrollBar scrl = new ScrollBar();
		scrl.setOrientation(Orientation.VERTICAL);
		scrl.setMin(0);
		scrl.setMax(200);
		scrl.setValue(0);
//		scrl.setPrefHeight(LeftVBox.getHeight());
		scrl.setLayoutX(LeftPane.getWidth() -10);
		LeftPane.getChildren().add(scrl);
	
		scrl.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    LeftVBox.setLayoutY(-new_val.doubleValue());
            }
        });
		
		
		for(Contact c : cb.getIndex()){
			Button ContactButton = new Button(c.FirstName +" "+c.LastName);
			LeftVBox.getChildren().add(ContactButton);
			ContactButton.setOnAction((event)->{
				txf.setText(c.toString());
				selected = c;
			});
		}
		LeftVBox.setSpacing(5);
		
		MainPane.setPadding(new Insets(10,10,10,10));
		
		
		
		//internalRightVBoxMenu.getChildren().addAll(AddContact, DeleteContact);
		internalRightVBoxMenu.setAlignment(Pos.BOTTOM_CENTER);
		RightPane.getChildren().add(txf);
		RightPane.getChildren().add(internalRightVBoxMenu);
		
		
		Scene scene = new Scene(MainPane, 600, 400);	
		primaryStage.setTitle("Contact Book");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	
	public class AddHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			try {
				Contact blank = new Contact(null,null,null,null);
				alterPrompt(blank);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class EditHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			try{
				if(selected.FirstName!=null){
					alterPrompt(selected);
				}
				else{
					UserMessage("No Contact Selected", "To edit a Contact, You Must First Select What Contact To Edit");
				}
				
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		cb = new ContactBook();
		File fi = new File("Contacts.txt");
		
		if(fi.exists()){
			Scanner sc = new Scanner(fi);
			sc.useDelimiter(Pattern.compile(",|(\r\n)"));
			while(sc.hasNext()){
				String f; String l; String e; String p;
				f = sc.next();
				l = sc.next();
				e = sc.next();
				p = sc.next();
				Contact c = new Contact(f,l,e,p);
				cb.addContactToBook(c);
				System.out.println(c.toString());
			}
		}
		else{
			System.out.println("Creating new Contacts File");
		}
		
		launch(args);
	}

	
	public void alterPrompt(Contact c) throws IOException{
		
		Stage popup = new Stage();
		GridPane MainPane = new GridPane();
		MainPane.setPadding(new Insets(10));
		
		Label FName = new Label("First Name"); 	TextField TXFNAME = new TextField();
		Label LName = new Label("Last Name");		TextField TXLNAME = new TextField();
		Label Email = new Label("Email Address");		TextField TXEMAIL = new TextField();
		Label PhoneNumber = new Label("Phone Number");		TextField TXPHONENUMBER = new TextField();
		
		Button FinalizeAdd = new Button("Done");
			if(c.FirstName!=null){
			
			TXFNAME.setText(c.FirstName);
			TXLNAME.setText(c.LastName);
			TXEMAIL.setText(c.Email);
			TXPHONENUMBER.setText(c.PhoneNumber);
			}
		
		
		FinalizeAdd.setOnAction((event) -> {
			String txfFirstName = TXFNAME.getText();
			String txfLastName = TXLNAME.getText();
			String txfEmail = TXEMAIL.getText();
			String txfPhoneNumber = TXPHONENUMBER.getText();
			
			try {
				Contact person = new Contact(txfFirstName, txfLastName, txfEmail, txfPhoneNumber);
				cb.addContactToBook(person);
				cb.WriteCBToFile();
				System.out.println(cb.toString());
				
				
				
				UserMessage("Confirmation", person.FirstName +" "+person.LastName +" has been Added");
				
				
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
	public void UserMessage(String Title, String message){
		Stage s = new Stage();
		StackPane p = new StackPane();
		p.setAlignment(Pos.CENTER);
		Label label = new Label(message);
		label.setAlignment(Pos.CENTER);
		p.getChildren().add(label);
		Scene cscene = new Scene(p, 400,20);
		s.setTitle(Title);
		s.setScene(cscene);
		s.show();
	}
}
