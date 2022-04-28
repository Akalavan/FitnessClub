package sample.email;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import sample.interfaces.impls.CollectionControlStock;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class EmailSession {

    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String[] args) throws MessagingException {
        final Properties properties = new Properties();
        try {
            properties.load(EmailSession.class.getClassLoader().getResourceAsStream("sample/email/mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Address[] addresses = new Address[CollectionControlStock.getEmailAddresses().size()];
        String[] send = new String[CollectionControlStock.getEmailAddresses().size()];
        int i = 0;
        for (Map.Entry entry : CollectionControlStock.getEmailAddresses().entrySet()) {
            String id = ((InternetAddress) entry.getKey()).getAddress();
            send[i] = (String) entry.getValue();
            addresses[i] = new InternetAddress(id);
            i++;
        }

        Session mailSession = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress("Mail@mail.ru"));
        Transport tr = null;
        tr = mailSession.getTransport();
        tr.connect(null, "password");
        for (int j = 0; j < addresses.length; j++) {
            message.addRecipient(Message.RecipientType.TO, addresses[j]);
            message.setSubject("Fitness центер");
            message.setText(send[j]);
            try {
                tr.sendMessage(message, message.getAllRecipients());
                conSql();
                int id = 0;
                try {
                    String query = "select tik_id from sport where mail = '" + addresses[j] + "';";
                    System.out.println(query);
                    rs = stmt.executeQuery(query);
                    while (rs.next()) id = rs.getInt(1);
                    query = "UPDATE `active_stock` SET `statusEmail` = '1' WHERE `active_stock`.`id_sport` = " + id;
                    stmt.executeUpdate(query);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
        }
        tr.close();


    }

    private static void conSql() {
        try {
            con = (Connection) DriverManager.getConnection(
                    URL + "?user=" + USER + "&password=" + PASSWORD +
                            "&useUnicode=true&characterEncoding=utf8");
            stmt = (Statement) con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
