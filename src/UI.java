
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.regex.Pattern;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
import javafx.util.Callback;

public class UI extends Application {
	ListView<Contact> LeftlistView = new ListView<Contact>();
	ListView<String> RightlistView = new ListView<String>();
	private static ContactBook cb;
	Contact blank;
	Contact selected;
	Contact person;
	StackPane MainPane;
	GridPane CenterGridPane;
	VBox LeftVBox;
	StackPane RightPane;
	HBox internalRightVBoxMenu;
	
	private ListCell<Contact> cell;
	ListCell <String> rightcell;

	@Override
	public void start(Stage primaryStage) throws Exception {

		blank = new Contact(null, null, null, null);
		selected = blank;

		primaryStage.getIcons().add(new Image("file:cbicon.png"));
		
		MainPane = new StackPane();
		CenterGridPane = new GridPane();
		LeftVBox = new VBox();
		RightPane = new StackPane();

		CenterGridPane.setAlignment(Pos.CENTER);
		LeftVBox.setAlignment(Pos.CENTER);
		RightPane.setAlignment(Pos.CENTER);
		CenterGridPane.setHgap(25);
		CenterGridPane.setVgap(10);
		

		
		BackgroundFill AntiqueBackFill = new BackgroundFill(Color.ANTIQUEWHITE, null, new Insets(0));
		Background AntiqueBackground = new Background(AntiqueBackFill);
		
		CenterGridPane.setBackground(AntiqueBackground);
		MainPane.setBackground(AntiqueBackground);
		LeftVBox.setBackground(AntiqueBackground);
		LeftVBox.setBackground(AntiqueBackground);
		RightPane.setBackground(AntiqueBackground);

		BorderStroke bdstk = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2));
		Border bd = new Border(bdstk);
		LeftVBox.setBorder(bd);
		
		RightPane.setBorder(bd);

		
		// Labels for the HBox and VBox

		Font titleFont = Font.font("Lato Semibold", FontWeight.EXTRA_BOLD, 25);
		Label CurrentContacts = new Label("Contacts");
		CurrentContacts.setTextAlignment(TextAlignment.CENTER);
		Label ContactInfo = new Label("Contact Information");
		CurrentContacts.setUnderline(true);
		ContactInfo.setUnderline(true);
		CurrentContacts.setFont(titleFont);
		ContactInfo.setFont(titleFont);

		// end label

		LeftVBox.setPrefHeight(300);
		

		setLeftListView();
		LeftVBox.getChildren().add(LeftlistView);

		// Add Delete Buttons
		HBox buttonHBox = new HBox();
		Button AddContact = new Button("Add Contact");
		AddContact.setOnAction(new AddHandler());

		Button DeleteContact = new Button("Delete Contact");
		DeleteContact.setOnAction((event) -> {
			try {
				if (selected.FirstName != null && selected.LastName != null) {
					UserMessage("Contact Removed",selected.FirstName +" " +selected.LastName +" has been removed");
					cb.removefromBookandFile(selected);
					selected = blank;
					setLeftListView();
					setRightListView();
				} else {
					UserMessage("No Contact Selected",
							"To Delete a Contact, You Must First Select What Contact To Delete");
				}
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		Button EditContact = new Button("Edit Contact");

		EditContact.setOnAction(new EditHandler());

		buttonHBox.getChildren().addAll(AddContact, EditContact, DeleteContact);
		buttonHBox.setSpacing(10);
		buttonHBox.setAlignment(Pos.BASELINE_CENTER);

		// end add delete buttons
		MainPane.getChildren().add(CenterGridPane);
		CenterGridPane.add(CurrentContacts, 0, 0);
		CenterGridPane.add(ContactInfo, 1, 0);
		CenterGridPane.add(LeftVBox, 0, 1);
		CenterGridPane.add(RightPane, 1, 1);
		CenterGridPane.add(buttonHBox, 1, 2);

		MainPane.setPadding(new Insets(10, 10, 10, 10));

		
		//setRightListView();
		RightPane.getChildren().add(RightlistView);
	
		Scene scene = new Scene(MainPane, 600, 400);
		primaryStage.setTitle("Contact Book");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public class AddHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			try {
				alterPrompt(blank);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class EditHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			try {
				if (selected.FirstName != null) {
					alterPrompt(selected);
				} else {
					UserMessage("No Contact Selected", "To edit a Contact, You Must First Select What Contact To Edit");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		cb = new ContactBook();
		try {
			FileInputStream fstream = new FileInputStream("Contacts.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String s;
			while ((s = br.readLine()) != null) {
				String[] tokens = s.split(",");
				Contact c = new Contact(tokens[0], tokens[1], tokens[2], tokens[3]);
				cb.addContactToBook(c);
			}
			in.close();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		launch(args);
	}

	public void alterPrompt(Contact c) throws IOException {

		Stage popup = new Stage();
		popup.getIcons().add(new Image("file:cbicon.png"));
		GridPane MainPane = new GridPane();
		MainPane.setPadding(new Insets(10));

		Label FName = new Label("First Name");
		TextField TXFNAME = new TextField();
		TXFNAME.setPromptText("Bruce");
		Label LName = new Label("Last Name");
		TextField TXLNAME = new TextField();
		TXLNAME.setPromptText("Wayne");
		Label Email = new Label("Email Address");
		TextField TXEMAIL = new TextField();
		TXEMAIL.setPromptText("Batman@gmail.com");
		Label PhoneNumber = new Label("Phone Number");
		TextField TXPHONENUMBER = new TextField();
		TXPHONENUMBER.setPromptText("9891234567");

		Button FinalizeAdd = new Button("Done");
		if (c.FirstName != null) {

			TXFNAME.setText(c.FirstName.trim());
			TXLNAME.setText(c.LastName.trim());
			TXEMAIL.setText(c.Email.trim());
			TXPHONENUMBER.setText(c.PhoneNumber.trim());
			cb.removefromBookandFile(c);
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
				setLeftListView();
				// System.out.println(cb.toString());

				UserMessage("Confirmation", person.FirstName + " " + person.LastName + " has been Added");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			popup.close();

		});

		MainPane.add(FName, 0, 0);
		MainPane.add(TXFNAME, 1, 0);
		MainPane.add(LName, 0, 1);
		MainPane.add(TXLNAME, 1, 1);
		MainPane.add(Email, 0, 2);
		MainPane.add(TXEMAIL, 1, 2);
		MainPane.add(PhoneNumber, 0, 3);
		MainPane.add(TXPHONENUMBER, 1, 3);
		MainPane.add(FinalizeAdd, 1, 4);

		Scene scene = new Scene(MainPane, 280, 180);
		popup.setTitle("Add Contact");
		popup.setScene(scene);
		popup.show();
	}

	public void UserMessage(String Title, String message) {
		Stage s = new Stage();
		s.getIcons().add(new Image("file:cbicon.png"));
		StackPane p = new StackPane();
		p.setAlignment(Pos.CENTER);
		Label label = new Label(message);
		label.setAlignment(Pos.CENTER);
		p.getChildren().add(label);
		Scene cscene = new Scene(p, 400, 20);
		s.setTitle(Title);
		s.setScene(cscene);
		s.show();
	}

	public void setLeftListView() {

		LeftlistView.setItems(FXCollections.observableArrayList(cb.getContactArray()));

		LeftlistView.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>() {

			@Override
			public ListCell<Contact> call(ListView<Contact> param) {

				cell = new ListCell<Contact>() {

					@Override
					protected void updateItem(Contact c, boolean bln) {
						super.updateItem(c, bln);
						if (c != null) {
							setText(c.getLastName() + ", " + c.getFirstName());
						}
					}
				};
				cell.setOnMouseClicked((event) -> {
					selected = LeftlistView.getSelectionModel().getSelectedItem();
					setRightListView();
					
				});
				return cell;
			}
		});

	}

	public void setRightListView() {
		if(selected.equals(blank)){
			RightlistView.setItems(null);
		}
		else{
			RightlistView.setItems(FXCollections.observableArrayList(
					new String[]
							{"First Name: " + selected.getFirstName().trim(),"Last Name: "+ selected.getLastName().trim(),
									"Email: " + selected.getEmail().trim(),"Telephone: " + selected.getFormattedPhoneNumber().trim()}));

			RightlistView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

				@Override
				public ListCell<String> call(ListView<String> param) {

					 rightcell = new ListCell<String>() {

						@Override
						protected void updateItem(String s, boolean bln) {
							super.updateItem(s, bln);
							if (s != null) {
								setText(s);
							}
						}
					};
					rightcell.setMouseTransparent(true);
					rightcell.setFocusTraversable(false);
					
					rightcell.setTextAlignment(TextAlignment.CENTER);
					
					
					
					return rightcell;
					
				}
			});
		}
		}
			
		
		

}



