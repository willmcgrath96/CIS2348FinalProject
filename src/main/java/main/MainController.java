package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.activation.DataSource;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MainController{

    public ContactController contactController;
    private ObservableList<Contact> contacts;

    @FXML
    public TableView tblContacts;
    @FXML
    public TableColumn colName;
    @FXML
    public TableColumn colEmail;
    @FXML
    public Button addContact;
    @FXML
    public Button editContact;
    @FXML
    public Button delContact;
    @FXML
    public Button importContact;
    @FXML
    public Label conLabel;

    @FXML public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        contacts = FXCollections.observableArrayList();
        setTable();
    }

    public void importFile(ActionEvent event) throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose CSV File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            Scanner scn = new Scanner(selectedFile);
            while(scn.hasNextLine()){
                String[] data = scn.nextLine().trim().split(",");
                String firstName = data[0];
                String lastName = data[1];
                String email = data[2];
                String persPhone = data[3];
                String address = data[4];
                String compPhone = data[5];
                String compName = data[6];
                String notes = data[7];
                Contact contact = new Contact(firstName,lastName,email,persPhone,address,compPhone,compName,notes);
                newContact(new Contact(firstName,lastName,email,persPhone,address,compPhone,compName,notes));
                contact.save();
            }
        }
    }

    public void addContact() throws Exception{
        //FXMLLoader fxmlLoader = new FXMLLoader();
        //Parent root = fxmlLoader.load(getClass().getResource("contact.fxml").openStream());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("contact.fxml"));
        Parent root = loader.load();
        contactController = loader.getController();
        contactController.init(this);
        Stage stage = new Stage();
        stage.setTitle("Contact");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void newContact(Contact contact) {
        contacts.add(contact);
        if (!contacts.isEmpty()) {
            tblContacts.setItems(contacts);
        }
    }

    public void setLabel() throws Exception{
        int selectedIndex = tblContacts.getSelectionModel().getSelectedIndex();
        Contact c = (Contact)tblContacts.getItems().get(selectedIndex);
        conLabel.setText(c.getFirstName() + c.getLastName());
        conLabel.setText(c.getEmailAddress());
        conLabel.setText(c.getPersPhone());
        conLabel.setText(c.getAddress());
        conLabel.setText(c.getCompPhone());
        conLabel.setText(c.getCompName());
        conLabel.setText(c.getNotes());


    }

    public void changeContact(ActionEvent event) throws Exception{
        Contact c = removeContact();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("contact.fxml"));
        Parent root = loader.load();
        contactController = loader.getController();
        contactController.init(this);
        Stage stage = new Stage();
        stage.setTitle("Contact");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        contactController.textFirst.setText(c.getFirstName());
        contactController.textLast.setText(c.getLastName());
        contactController.textEmail.setText(c.getEmailAddress());
        contactController.textPhone.setText(c.getPersPhone());
        contactController.textAddress.setText(c.getAddress());
        contactController.compName.setText(c.getCompPhone());
        contactController.compPhone.setText(c.getCompName());
        contactController.textNotes.setText(c.getNotes());
        stage.show();
        /*
        Connection connection = DbHelper.getInstance().getConnection();
        String insertSQL = "UPDATE contact SET (FIRSTNAME, "+
               "LASTNAME, " +
               "EMAIL, " +
               "PERSONALPHONE, " +
               "ADDRESS, " +
               "COMPANYPHONE, " +
               "COMPANYNAME, " +
               "NOTES) " +
               "= (?,?,?,?,?,?,?,?) where (email) = ?";
        PreparedStatement statement = connection.prepareStatement(insertSQL);
        statement.setString(1,c.getFirstName());
        statement.setString(2,c.getLastName());
        statement.setString(3,c.getEmailAddress());
        statement.setString(4,c.getPersPhone());
        statement.setString(5,c.getAddress());
        statement.setString(6,c.getCompPhone());
        statement.setString(7,c.getCompName());
        statement.setString(8,c.getNotes());
        statement.setString(9,c.getEmailAddress());
        statement.execute();
        */

    }

    public void setTable(){
        ContactHelper contactHelper = ContactHelper.getInstance();
        try {
            ArrayList<Contact> contactArray = contactHelper.getContacts();
            for (Contact c : contactArray) {
                contacts.add(c);
            }
            if (!contacts.isEmpty()) {
                tblContacts.setItems(contacts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delContact(ActionEvent event) throws Exception {
        removeContact();
    }

    public Contact removeContact() throws Exception{
        int selectedIndex = tblContacts.getSelectionModel().getSelectedIndex();
        Contact c = (Contact)tblContacts.getItems().get(selectedIndex);
        tblContacts.getItems().remove(selectedIndex);
        Connection connection = DbHelper.getInstance().getConnection();
        String insertSQL = "delete from contact where (email) = ?";
        PreparedStatement statement = connection.prepareStatement(insertSQL);
        statement.setString(1,c.getEmailAddress());
        statement.execute();
        return c;
    }





}
