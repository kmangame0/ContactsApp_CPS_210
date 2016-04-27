
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UI extends Application {

	public static final Pattern VALID_NAME_REGEX = 
		    Pattern.compile("^[\\p{L}.'-]+$", Pattern.CASE_INSENSITIVE);
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern VALID_PHONE_REGEX = 
			Pattern.compile("^[1-9][0-9]{10}");
	ListView<Contact> LeftlistView = new ListView<Contact>();
	ListView<String> RightlistView = new ListView<String>();
	private Stage primaryStage;
	private static ContactBook cb;
	private Contact blank;
	private Contact selected;
	private StackPane MainPane;
	private GridPane CenterGridPane;
	private VBox LeftVBox;
	private StackPane RightPane;
	
	private ListCell<Contact> cell;
	ListCell <String> rightcell;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage=primaryStage;
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
		

		
		BackgroundFill bkfil = new BackgroundFill(Color.LIGHTSTEELBLUE, null, new Insets(0));
		Background Background = new Background(bkfil);
		
		CenterGridPane.setBackground(Background);
		MainPane.setBackground(Background);
		LeftVBox.setBackground(Background);
		LeftVBox.setBackground(Background);
		RightPane.setBackground(Background);

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
				String[] tokens = s.split(", ");
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
		popup.initModality(Modality.WINDOW_MODAL);
		popup.initOwner(primaryStage);
		GridPane MainPane = new GridPane();
		MainPane.setPadding(new Insets(10));
		MainPane.setVgap(5);
		
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
		
		BooleanBinding bb = new BooleanBinding() {
		    {
		        super.bind(TXFNAME.textProperty(),
		        		   TXLNAME.textProperty(),
		        		   TXEMAIL.textProperty(),
		        		   TXPHONENUMBER.textProperty());
		    }
		    @Override
		    protected boolean computeValue() {
		        return (TXFNAME.getText().isEmpty())
		                || (TXLNAME.getText().isEmpty())
		                || (TXEMAIL.getText().isEmpty())
//		                || (TXPHONENUMBER.getText().isEmpty())
		        		||	(!(TXPHONENUMBER.getText().length() == 10));
		    }
		};
		
		Button FinalizeAdd = new Button("Done");
		FinalizeAdd.disableProperty().bind(bb);
		
		if (c.FirstName != null) {
			TXFNAME.setText(c.FirstName.trim());
			TXLNAME.setText(c.LastName.trim());
			TXEMAIL.setText(c.Email.trim());
			TXPHONENUMBER.setText(c.PhoneNumber.trim());
		}
		
		FinalizeAdd.setOnAction((event) -> {
			if(!selected.equals(blank)){
				
				try {
					cb.removefromBookandFile(c);
				} catch (Exception e1) {
					e1.printStackTrace();
			}
			}
			String txfFirstName = TXFNAME.getText();
			String txfLastName = TXLNAME.getText();
			String txfEmail = TXEMAIL.getText();
			String txfPhoneNumber = TXPHONENUMBER.getText();

			try {
				Contact person = new Contact(txfFirstName, txfLastName, txfEmail, txfPhoneNumber);
				cb.addContactToBook(person);
				cb.WriteCBToFile();
				setLeftListView();
				UserMessage("Confirmation", person.FirstName + " " + person.LastName + " has been Added");

			} catch (Exception e) {
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

		Scene scene = new Scene(MainPane, 260, 170);
		popup.setTitle("Add Contact");
		popup.setScene(scene);
		popup.show();
	}
	
	public void UserMessage(String Title, String message) {
		Stage s = new Stage();
		s.getIcons().add(new Image("file:cbicon.png"));
		s.initModality(Modality.WINDOW_MODAL);
		s.initOwner(primaryStage);
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
//					System.out.println(selected.PhoneNumber+"\n" +selected.getFormattedPhoneNumber());
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
					rightcell.setFont(Font.font("Cambria", FontWeight.SEMI_BOLD, 20));
					rightcell.setTextAlignment(TextAlignment.CENTER);
					rightcell.setPadding(new Insets(15));
					return rightcell;
					
				}
			});
		}
		}
}



