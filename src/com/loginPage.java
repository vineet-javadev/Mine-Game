package com;

import java.sql.*;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.Border;

public class loginPage extends Thread {
    private static String username;
    private static float CASHfromDB;

    private static JFrame loginFrame;
    private static JButton newUser, forgotPassbt, loginUser;
    private static JLabel title, result;
    private static JPanel loginPanel, login, signUp, forgotPass;
    private static JTextField userNameInput, passWordInput, newuserNameInput, mailInput, newpassWordInput,
            cpassWordInput, forgotmailInput;
    private static Border defaultborder;

    private static Connection con = null;

    public static void gameframe() {
        JFrame gameFrame = new JFrame("Mines");

        // default setting for game frame like as main frame
        gameFrame.setSize(1100, 700);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("res/logo.png"));
        gameFrame.add(maingame.mainGAme(CASHfromDB, con, username));

        gameFrame.setVisible(true);
    }

    public static JFrame loginPageFrame() {
        runThread();
        loginFrame = new JFrame("Mines (LogIn Page)");
        loginFrame.setSize(1100, 700);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setLayout(null);

        // setting frame icon
        try {
            // get image from image folder which exist in project Folder
            File fileIcon = new File("res//logo.png");
            Image imgicon;
            imgicon = ImageIO.read(new File(fileIcon.toURI()));
            loginFrame.setIconImage(imgicon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // login panel
        loginPanel = new JPanel();
        loginPanel.setBackground(Color.decode("#122132"));
        loginPanel.setBounds(0, 0, 1100, 700);
        loginPanel.setLayout(null);

        // title text
        JLabel title0 = new JLabel("Mines Portal");
        title0.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        title0.setForeground(Color.WHITE);
        title0.setBounds(470, 50, 400, 60);
        loginPanel.add(title0);

        // title text
        title = new JLabel("Good to see you Again !");
        title.setFont(new Font(Font.DIALOG_INPUT, Font.ROMAN_BASELINE, 25));
        title.setForeground(Color.WHITE);
        title.setBounds(370, 120, 400, 60);
        loginPanel.add(title);

        // call function
        loginfunction();
        forgotfunction();
        signupfunction();
        // adding components into login base panel
        loginPanel.add(login);

        // adding base panel into base frame
        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);

        return loginFrame;
    }

    public static JPanel loginfunction() {
        // login panel
        login = new JPanel(new FlowLayout(FlowLayout.LEFT, 42, 10));
        login.setBounds(280, 200, 500, 370);
        login.setBackground(Color.WHITE);

        // ------------------------------------------------------------------------------//
        // adding component into login panel
        // gap
        JLabel gap = new JLabel("           ");
        gap.setPreferredSize(new Dimension(380, 15));
        login.add(gap);

        // username label
        JLabel userName = new JLabel("UserName :");
        userName.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        login.add(userName);

        // username input textfield
        userNameInput = new JTextField("abc@hewer", 41);
        userNameInput.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        defaultborder = userNameInput.getBorder();
        // mouse listener for username input field for giving hint to user
        userNameInput.addMouseListener(mouseListenerForTextField);
        login.add(userNameInput);

        // gap
        JLabel gap1 = new JLabel("           ");
        gap1.setPreferredSize(new Dimension(380, 7));
        login.add(gap1);

        // password label
        JLabel passWord = new JLabel("Password :");
        passWord.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        login.add(passWord);

        // password input field
        passWordInput = new JTextField("Password", 41);
        passWordInput.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        passWordInput.addMouseListener(mouseListenerForTextField);
        login.add(passWordInput);

        // gap
        JLabel gap2 = new JLabel("           ");
        gap2.setPreferredSize(new Dimension(400, 9));
        login.add(gap2);
        // gap
        JLabel gap2beforeBt = new JLabel(" ");
        gap2beforeBt.setPreferredSize(new Dimension(17, 9));
        login.add(gap2beforeBt);

        // submit button
        JButton submit = new JButton("Sign In");
        submit.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        submit.setPreferredSize(new Dimension(300, 40));
        submit.setBackground(Color.decode("#122132"));
        submit.setForeground(Color.WHITE);
        submit.setFocusable(false);
        submit.addActionListener(playGameAL);
        // submit.setHorizontalAlignment(FlowLayout.CENTER);
        login.add(submit);

        // -----------------------------------------------------------------------//
        // ----------------adding Sign Up or forgot password buttons--------------//
        // -----------------------------------------------------------------------//

        // gap
        JLabel gap3 = new JLabel("           ");
        gap3.setPreferredSize(new Dimension(400, 15));
        login.add(gap3);
        // need more gap
        JLabel gap4 = new JLabel("           ");
        gap4.setPreferredSize(new Dimension(400, 15));
        login.add(gap4);

        // new User button
        newUser = new JButton("Don't have an Account?");
        newUser.setPreferredSize(new Dimension(170, 30));
        newUser.setFocusable(false);
        newUser.addActionListener(newuser);
        login.add(newUser);

        // gap between
        JLabel gapbetween = new JLabel("");
        gapbetween.setPreferredSize(new Dimension(-10, 15));
        login.add(gapbetween);

        // forgot pass button
        forgotPassbt = new JButton("Forgot Password?");
        forgotPassbt.setPreferredSize(new Dimension(170, 30));
        forgotPassbt.setFocusable(false);
        forgotPassbt.addActionListener(forgot);
        login.add(forgotPassbt);

        return login;
    }

    public static JPanel signupfunction() {
        // sigIn panel
        signUp = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 10));
        signUp.setBounds(280, 200, 500, 370);
        signUp.setBackground(Color.WHITE);

        // ------------------------------------------------------------------------------//
        // adding component into signup panel
        JLabel gap = new JLabel("           ");
        gap.setPreferredSize(new Dimension(380, 15));
        signUp.add(gap);

        // username label
        JLabel userName = new JLabel(" UserName :  ");
        userName.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        signUp.add(userName);

        // username input textfield
        newuserNameInput = new JTextField("abc@hewer", 27);
        newuserNameInput.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        newuserNameInput.addMouseListener(mouseListenerForTextField);
        signUp.add(newuserNameInput);

        // gap
        JLabel gap2 = new JLabel(" ");
        gap2.setPreferredSize(new Dimension(395, 7));
        signUp.add(gap2);

        // Mail label
        JLabel mail = new JLabel(" Mail :      ");
        mail.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        signUp.add(mail);

        // username input textfield
        mailInput = new JTextField("abc@gmail.com", 27);
        mailInput.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        mailInput.addMouseListener(mouseListenerForTextField);
        signUp.add(mailInput);

        // // gap
        JLabel gap3 = new JLabel(" ");
        gap3.setPreferredSize(new Dimension(380, 7));
        signUp.add(gap3);

        // password label
        JLabel passWord = new JLabel(" Password :  ");
        passWord.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        signUp.add(passWord);

        // password input field
        newpassWordInput = new JTextField("Password", 27);
        newpassWordInput.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        newpassWordInput.addMouseListener(mouseListenerForTextField);
        signUp.add(newpassWordInput);

        // gap
        JLabel gap8 = new JLabel(" ");
        gap8.setPreferredSize(new Dimension(380, 7));
        signUp.add(gap8);

        // confirm password label
        JLabel cpassWord = new JLabel(" C Password :");
        cpassWord.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        signUp.add(cpassWord);

        // confirm password input field
        cpassWordInput = new JTextField("Confirm Password", 27);
        cpassWordInput.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        cpassWordInput.addMouseListener(mouseListenerForTextField);
        signUp.add(cpassWordInput);

        // gap
        JLabel gap7 = new JLabel("           ");
        gap7.setPreferredSize(new Dimension(450, 9));
        signUp.add(gap7);
        // gap
        JLabel gap2beforeBt = new JLabel("           ");
        gap2beforeBt.setPreferredSize(new Dimension(40, 9));
        signUp.add(gap2beforeBt);

        // submit button
        JButton submit = new JButton("Sign Up");
        submit.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        submit.setPreferredSize(new Dimension(300, 40));
        submit.setBackground(Color.decode("#122132"));
        submit.setForeground(Color.WHITE);
        submit.setFocusable(false);
        // add action listener for add functionalities
        submit.addActionListener(signUpAL);
        signUp.add(submit);

        // -----------------------------------------------------------------------//
        // ----------------adding Sign Up or forgot password buttons--------------//
        // -----------------------------------------------------------------------//

        // gap
        JLabel gap30 = new JLabel("           ");
        gap30.setPreferredSize(new Dimension(450, 5));
        signUp.add(gap30);
        // gapMore
        JLabel gap304 = new JLabel("");
        gap304.setPreferredSize(new Dimension(-5, 5));
        signUp.add(gap304);

        // new User button
        loginUser = new JButton("Account LogIn");
        loginUser.setPreferredSize(new Dimension(170, 30));
        loginUser.setFocusable(false);
        loginUser.addActionListener(lgin);
        signUp.add(loginUser);

        // gap between
        JLabel gapbetween = new JLabel("");
        gapbetween.setPreferredSize(new Dimension(-10, 15));
        signUp.add(gapbetween);

        // forgot pass button
        forgotPassbt = new JButton("Forgot Password?");
        forgotPassbt.setPreferredSize(new Dimension(170, 30));
        forgotPassbt.setFocusable(false);
        forgotPassbt.addActionListener(forgot);
        signUp.add(forgotPassbt);

        return signUp;
    }

    public static JPanel forgotfunction() {
        // forgotPass panel
        forgotPass = new JPanel(new FlowLayout(FlowLayout.LEFT, 42, 10));
        forgotPass.setBounds(280, 200, 500, 370);
        forgotPass.setBackground(Color.WHITE);

        // adding component into signup panel
        JLabel gap = new JLabel("           ");
        gap.setPreferredSize(new Dimension(380, 15));
        forgotPass.add(gap);

        // username label
        JLabel enterMail = new JLabel("Enter Mail : ");
        enterMail.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        forgotPass.add(enterMail);

        // username input textfield
        forgotmailInput = new JTextField("abc@gmail.com", 41);
        forgotmailInput.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        forgotmailInput.addMouseListener(mouseListenerForTextField);
        forgotPass.add(forgotmailInput);

        // gap
        JLabel gap3 = new JLabel(" ");
        gap3.setPreferredSize(new Dimension(400, 9));
        forgotPass.add(gap3);

        // gap
        JLabel gap2beforeBt = new JLabel(" ");
        gap2beforeBt.setPreferredSize(new Dimension(17, 9));
        forgotPass.add(gap2beforeBt);

        // submit button
        JButton submit = new JButton("Forgot Password");
        submit.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        submit.setPreferredSize(new Dimension(300, 40));
        submit.setBackground(Color.decode("#122132"));
        submit.setForeground(Color.WHITE);
        submit.setFocusable(false);

        // action listener for click here button ( forgot pass bt)
        submit.addActionListener(forgotAL);
        forgotPass.add(submit);

        // gap
        JLabel gap4 = new JLabel(" ");
        gap4.setPreferredSize(new Dimension(400, 9));
        forgotPass.add(gap4);
        // gap
        JLabel gap5 = new JLabel(" ");
        gap5.setPreferredSize(new Dimension(400, 9));
        forgotPass.add(gap5);

        // result
        result = new JLabel(" Your Password : ");
        result.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 17));
        forgotPass.add(result);

        // -----------------------------------------------------------------------//
        // ----------------adding Sign Up or forgot password buttons--------------//
        // -----------------------------------------------------------------------//

        // gap
        JLabel gap30 = new JLabel("           ");
        gap30.setPreferredSize(new Dimension(400, 10));
        forgotPass.add(gap30);
        // gap
        JLabel gap40 = new JLabel("           ");
        gap40.setPreferredSize(new Dimension(400, 15));
        forgotPass.add(gap40);
        // gap
        JLabel gap50 = new JLabel("           ");
        gap50.setPreferredSize(new Dimension(400, 10));
        forgotPass.add(gap50);

        // login button
        loginUser = new JButton("Account LogIn");
        loginUser.setPreferredSize(new Dimension(170, 30));
        loginUser.setFocusable(false);
        loginUser.addActionListener(lgin);
        forgotPass.add(loginUser);

        // gap between
        JLabel gapbetween = new JLabel("");
        gapbetween.setPreferredSize(new Dimension(-10, 15));
        forgotPass.add(gapbetween);

        // new User button
        newUser = new JButton("Don't have an Account?");
        newUser.setPreferredSize(new Dimension(170, 30));
        newUser.setFocusable(false);
        newUser.addActionListener(newuser);
        forgotPass.add(newUser);

        return forgotPass;
    }

    // action Listener for Forgot Button
    static ActionListener forgotAL = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // exception handling islye kyuki mail 10 se kam char ka hua to
            try {

                // finding the position of @ sign (@gmail.com)
                int findBegningposMail = forgotmailInput.getText().length() - 10;
                // creating a sub string form mail input for compare that tha data entered by
                // user is correct or not
                String tempSubSrtringMail = forgotmailInput.getText().substring(findBegningposMail,
                        forgotmailInput.getText().length());

                // checking condition
                if (forgotmailInput.getText().equals("abc@gmail.com") || forgotmailInput.getText().equals("")) {
                    // user must input correct main ( not same or not null)
                    // pop up Msg
                    JOptionPane.showMessageDialog(loginPanel,
                            "Default Mail or Blank field are not Allow", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (forgotmailInput.getText().length() <= 10) {
                    // @gmail.com ke sath kuch extra character be hone chiye tbhi valid case hoga
                    // pop up Msg
                    JOptionPane.showMessageDialog(loginPanel,
                            "Wrong Mail !", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (!tempSubSrtringMail.equals("@gmail.com")) {
                    // checking input mail is correct order or not
                    // pop up Msg
                    JOptionPane.showMessageDialog(loginPanel,
                            "Wrong Mail !", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // let's create connection
                    // Connection con = connectivity.connect();
                    if (con != null) {

                        // creating query
                        String q = "SELECT Password from dataset WHERE Mail = '" + forgotmailInput.getText() + "'";
                        try {
                            Statement sc = con.createStatement();
                            // execute query
                            ResultSet Rs = sc.executeQuery(q);
                            String getPass = "";
                            while (Rs.next()) {
                                getPass = Rs.getString("Password");
                            }
                            result.setText(" Your Password : " + getPass);
                            // pop up Msg
                            JOptionPane.showMessageDialog(loginPanel,
                                    result.getText(), "INFORMATION",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException edf) {
                            edf.printStackTrace();
                        }

                    }
                }
            } catch (Exception check) {

            }
        }

    };
    // action listener for Sign Up button
    static ActionListener signUpAL = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // exception handling islye kyuki username 6 se kam char ka hua to or mail 10
            // char se
            try {

                // finding the position of @ sign ( @hewer)
                int findBegningpos = newuserNameInput.getText().length() - 6;
                // creating a sub string form user input for compare that tha data entered by
                // user is correct or not
                String tempSubSrtring = newuserNameInput.getText().substring(findBegningpos,
                        newuserNameInput.getText().length());

                // finding the position of @ sign (@gmail.com)
                int findBegningposMail = mailInput.getText().length() - 10;
                // creating a sub string form mail input for compare that tha data entered by
                // user is correct or not
                String tempSubSrtringMail = mailInput.getText().substring(findBegningposMail,
                        mailInput.getText().length());

                // checking condition
                if (newuserNameInput.getText().equals("abc@hewer") || mailInput.getText().equals("abc@gmail.com")
                        || newpassWordInput.getText().equals("Password")
                        || cpassWordInput.getText().equals("Confirm Password")) {
                    // default case not allow ( CHECKING )
                    // pop up Msg
                    JOptionPane.showMessageDialog(loginPanel,
                            "Default Values are not Allow", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (newuserNameInput.getText().equals("") || mailInput.getText().equals("")
                        || newpassWordInput.getText().equals("") || cpassWordInput.getText().equals("")) {
                    // blank are also not Allow ( CHECKING )
                    // pop up Msg
                    JOptionPane.showMessageDialog(loginPanel,
                            "Blank Fields are not Allow", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (newuserNameInput.getText().length() <= 6 || mailInput.getText().length() <= 10) {
                    // @hewer ke sath kuch extra character be hone chiye tbhi valid case hoga
                    // pop up Msg
                    JOptionPane.showMessageDialog(loginPanel,
                            "Wrong Format ( UserName or Mail )", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (!tempSubSrtring.equals("@hewer") || !tempSubSrtringMail.equals("@gmail.com")) {
                    // checking user name & mail is correct order or not
                    // pop up Msg
                    JOptionPane.showMessageDialog(loginPanel,
                            "Wrong Input !", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (!newpassWordInput.getText().equals(cpassWordInput.getText())) {
                    // password should match with Confirm Password
                    // pop up Msg
                    JOptionPane.showMessageDialog(loginPanel,
                            "Password doesn't Match", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // let's create connection
                    // Connection con = connectivity.connect();
                    if (con != null) {
                        try {
                            // create query
                            String q = "insert into dataset(UserName , Mail , Password , cash) value(?,?,?,?)";

                            // prepared Statement
                            PreparedStatement Pst = con.prepareStatement(q);
                            Pst.setString(1, newuserNameInput.getText());
                            Pst.setString(2, mailInput.getText());
                            Pst.setString(3, newpassWordInput.getText());
                            Pst.setInt(4, 00);

                            // execute
                            Pst.executeUpdate();
                            // pop up Msg
                            JOptionPane.showMessageDialog(loginPanel,
                                    "Data add Successfully. Now You can Login & Enjoy...", "INFORMATION",
                                    JOptionPane.INFORMATION_MESSAGE);

                        } catch (SQLException edf) {
                            edf.printStackTrace();
                        }
                    }
                }
            } catch (Exception check) {

            }
        }

    };

    // action listener for Sign in button
    static ActionListener playGameAL = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            // exception handling islye kyuki username 6 se kam char ka hua to
            try {

                // finding the position og @ sing ( @hewer)
                int findBegningpos = userNameInput.getText().length() - 6;
                // creating a sub string form user input for compare that tha data entered by
                // user is correct or not
                String tempSubSrtring = userNameInput.getText().substring(findBegningpos,
                        userNameInput.getText().length());

                // checking condition
                if (userNameInput.getText().equals("abc@hewer") || passWordInput.getText().equals("Password")) {
                    // default case not allow ( CHECKING )
                    // pop up Msg
                    JOptionPane.showMessageDialog(loginPanel,
                            "Default Values are not Allow", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (userNameInput.getText().equals("") || passWordInput.getText().equals("")) {
                    // blank are also not Allow ( CHECKING )
                    // pop up Msg
                    JOptionPane.showMessageDialog(loginPanel,
                            "Blank Fields are not Allow", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (userNameInput.getText().length() <= 6) {
                    // @hewer ke sath kuch extra character be hone chiye tbhi valid case hoga
                    // pop up Msg
                    JOptionPane.showMessageDialog(loginPanel,
                            "Wrong UserName !", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (!tempSubSrtring.equals("@hewer")) {
                    // checking user name is correct order or not
                    // pop up Msg
                    JOptionPane.showMessageDialog(loginPanel,
                            "Wrong Username !", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // collect username
                    username = userNameInput.getText();
                    // let's create connection
                    // Connection con = connectivity.connect();
                    if (con != null) {

                        // creating query
                        String q = "SELECT Password , cash from dataset WHERE UserName = '" + userNameInput.getText()
                                + "'";
                        try {
                            Statement sc = con.createStatement();
                            // execute query
                            ResultSet Rs = sc.executeQuery(q);
                            String getPass = "";
                            while (Rs.next()) {
                                getPass = Rs.getString("Password");
                                CASHfromDB = Rs.getInt("cash");

                            }
                            if (getPass.equals(passWordInput.getText())) {
                                gameframe();
                                loginFrame.dispose();
                            }

                        } catch (SQLException edf) {
                            edf.printStackTrace();
                        }
                    }

                }
            } catch (Exception check) {

            }

        }

    };
    // action listener for new user button
    static ActionListener newuser = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            title.setText("   Yours Welcome");
            title.setBounds(400, 120, 400, 60);
            login.setVisible(false);
            forgotPass.setVisible(false);
            signUp.setVisible(true);
            loginPanel.add(signUp);
        }

    };

    // actionListener for login button
    static ActionListener lgin = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            title.setText("Good to see you Again !");
            title.setBounds(370, 120, 400, 60);
            forgotPass.setVisible(false);
            signUp.setVisible(false);
            login.setVisible(true);
            loginPanel.add(login);
        }

    };

    // actionListener for forgot button
    static ActionListener forgot = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            title.setText("Hope you will get Your Password.");
            title.setBounds(340, 120, 400, 60);
            login.setVisible(false);
            signUp.setVisible(false);
            forgotPass.setVisible(true);
            loginPanel.add(forgotPass);
        }

    };

    static MouseListener mouseListenerForTextField = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            JTextField temp = (JTextField) e.getSource();
            if (temp == userNameInput) {
                userNameInput.setText("");
                userNameInput.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == passWordInput) {
                passWordInput.setText("");
                passWordInput.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == newuserNameInput) {
                newuserNameInput.setText("");
                newuserNameInput.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == mailInput) {
                mailInput.setText("");
                mailInput.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == newpassWordInput) {
                newpassWordInput.setText("");
                newpassWordInput.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == cpassWordInput) {
                cpassWordInput.setText("");
                cpassWordInput.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == forgotmailInput) {
                forgotmailInput.setText("");
                forgotmailInput.setBorder(BorderFactory.createLineBorder(Color.black));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JTextField temp = (JTextField) e.getSource();
            if (temp == userNameInput) {
                userNameInput.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == passWordInput) {
                passWordInput.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == newuserNameInput) {
                newuserNameInput.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == mailInput) {
                mailInput.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == newpassWordInput) {
                newpassWordInput.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == cpassWordInput) {
                cpassWordInput.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == forgotmailInput) {
                forgotmailInput.setBorder(BorderFactory.createLineBorder(Color.black));
            }

        }

        @Override
        public void mouseExited(MouseEvent e) {
            JTextField temp = (JTextField) e.getSource();
            if (temp == userNameInput) {
                userNameInput.setBorder(defaultborder);
            } else if (temp == passWordInput) {
                passWordInput.setBorder(defaultborder);
            } else if (temp == newuserNameInput) {
                newuserNameInput.setBorder(defaultborder);
            } else if (temp == mailInput) {
                mailInput.setBorder(defaultborder);
            } else if (temp == newpassWordInput) {
                newpassWordInput.setBorder(defaultborder);
            } else if (temp == cpassWordInput) {
                cpassWordInput.setBorder(defaultborder);
            } else if (temp == forgotmailInput) {
                forgotmailInput.setBorder(defaultborder);
            }

        }

    };

    // creating a thread for create connection
    public static void runThread() {
        loginPage t1 = new loginPage();
        t1.start();
    }

    public void run() {
        con = connectivity.connect();
    }

}