package main;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by William on 4/20/2017.
 */
public class ContactHelper {
    private static ContactHelper ourInstance = new ContactHelper();

    public static ContactHelper getInstance() {
        return ourInstance;
    }

    private ContactHelper() {
    }

    public ArrayList<Contact> getContacts() throws SQLException {

        ArrayList<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM PUBLIC.CONTACT ORDER BY EMAIL";
        Connection connection = DbHelper.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        Contact contact;
        while(rs.next()) {
            contact = new Contact(rs.getString(  "FIRSTNAME"),
                                    rs.getString("LASTNAME"),
                                    rs.getString("EMAIL"),
                                    rs.getString("PERSONALPHONE"),
                                    rs.getString("ADDRESS"),
                                    rs.getString("COMPANYPHONE"),
                                    rs.getString("COMPANYNAME"),
                                    rs.getString("NOTES"));
            contacts.add(contact);
        }

        return contacts;
    }
}
