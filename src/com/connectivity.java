package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class connectivity {
    public static Connection connect() {
        String url = "jdbc:mysql://database-1.czky0gi04ylb.eu-north-1.rds.amazonaws.com:3306/minegamedb";
        String user = "admin";
        String password = "database1pass";
        Connection con = null;

        try {
            // load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // create connection
            con = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException cnfe) {
            // cnfe.printStackTrace();

            JOptionPane optionPane = new JOptionPane("No Internet !!", JOptionPane.WARNING_MESSAGE);
            JDialog dialog = optionPane.createDialog("Warning!");
            dialog.setAlwaysOnTop(true); // to show top of all other application
            dialog.setVisible(true); // to visible the dialog
            System.exit(0);

        } catch (SQLException sqle) {
            // sqle.printStackTrace();

            JOptionPane optionPane = new JOptionPane("No Internet !!", JOptionPane.WARNING_MESSAGE);
            JDialog dialog = optionPane.createDialog("Warning!");
            dialog.setAlwaysOnTop(true); // to show top of all other application
            dialog.setVisible(true); // to visible the dialog
            System.exit(0);

        }

        return con;
    }
}
