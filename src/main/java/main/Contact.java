package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by keithlancaster on 3/16/17.
 */
public class Contact{
        public String firstName;
        public String lastName;
        public String emailAddress;
        public String persPhone;
        public String address;
        public String compPhone;
        public String compName;
        public String notes;
        public String fullName;



        public Contact(String firstName, String lastName, String emailAddress, String persPhone, String address, String compPhone, String compName, String notes){
            this.firstName = firstName;
            this.lastName = lastName;
            this.fullName = firstName + lastName;
            this.emailAddress = emailAddress;
            this.persPhone = persPhone;
            this.address = address;
            this.compPhone = compPhone;
            this.compName = compName;
            this.notes = notes;
        }

    /*Getters and setters*/
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPersPhone() {
        return persPhone;
    }

    public void setPersPhone(String persPhone) {
        this.persPhone = persPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCompPhone() {
        return compPhone;
    }

    public void setCompPhone(String compPhone) {
        this.compPhone = compPhone;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public void save() throws SQLException {
        Connection connection = DbHelper.getInstance().getConnection();

        String insertSQL = "insert into contact(firstName,lastName,email,personalPhone,address,companyPhone,companyName,notes) values" +
                "(?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(insertSQL);
        statement.setString(1, this.firstName);
        statement.setString(2, this.lastName);
        statement.setString(3, this.emailAddress);
        statement.setString(4, this.persPhone);
        statement.setString(5, this.address);
        statement.setString(6, this.compPhone);
        statement.setString(7, this.compName);
        statement.setString(8, this.notes);
        statement.execute();
        statement.close();
        connection.close();

    }


}
