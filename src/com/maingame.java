package com;

import java.sql.*;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.text.DecimalFormat;
import javax.swing.border.Border;

public class maingame extends Thread {
    private static String username;
    private static int diffLevel = 1, cordx, cordy;
    private static float cash = 1000f, bitAmount = 0f;
    private static Boolean played = false, cashoutPLAYBUTTON = false;

    private static int[] cordinate = new int[30];
    private static JButton[][] bt = new JButton[5][5];

    private static ArrayList<Integer> list = new ArrayList<>();

    private static Border defaultborder;
    private static JLabel cashText, addCash, bit, diff, profit;
    private static JTextField addCashTF, addBitTF, addDiff;
    private static JButton addCashBT, startBT, cashOut, restart;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    // for store connection
    private static Connection con;

    public static JPanel mainGAme(float getCash, Connection getCon, String getUserName) {
        con = getCon;
        cash = getCash;
        username = getUserName;
        runThread();
        JPanel gameHome = new JPanel();
        gameHome.setPreferredSize(new Dimension(1100, 700));
        gameHome.setBackground(Color.decode("#122132"));
        gameHome.setLayout(new BorderLayout(20, 20));

        gameHome.add(left(), BorderLayout.WEST);
        gameHome.add(right(), BorderLayout.EAST);

        return gameHome;
    }

    private static JPanel left() {
        JPanel leftMenu = new JPanel();
        leftMenu.setPreferredSize(new Dimension(360, 700));
        // leftMenu.setBorder(BorderFactory.createLineBorder(Color.black));
        leftMenu.setBackground(Color.decode("#122132"));

        leftMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        // Cash available JLabel
        cashText = new JLabel("CASH AVAILABLE : ₹" + df.format(cash));
        cashText.setForeground(Color.WHITE);
        cashText.setPreferredSize(new Dimension(360, 40));
        cashText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        cashText.setHorizontalAlignment(SwingConstants.CENTER);
        leftMenu.add(cashText);

        // gap
        JLabel gapLine = new JLabel("--------------------------------------------------------------------------");
        gapLine.setForeground(Color.black);
        gapLine.setPreferredSize(new Dimension(300, 40));
        leftMenu.add(gapLine);

        // add cash JLabel
        addCash = new JLabel("ADD AMOUNT :");
        addCash.setForeground(Color.WHITE);
        // cashText.setPreferredSize(new Dimension(300, 40));
        addCash.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        leftMenu.add(addCash);

        // add cash text field
        addCashTF = new JTextField("0", 16);
        addCashTF.setForeground(Color.WHITE);
        addCashTF.setBackground(Color.decode("#324452"));
        addCashTF.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        defaultborder = addCashTF.getBorder();
        addCashTF.addMouseListener(mouseListenerForTextField);

        leftMenu.add(addCashTF);

        // add cash button
        addCashBT = new JButton("ADD");
        addCashBT.setForeground(Color.WHITE);
        addCashBT.setBackground(Color.decode("#324452"));
        addCashBT.setFont(new Font(Font.MONOSPACED, Font.BOLD, 19));
        addCashBT.setPreferredSize(new Dimension(300, 30));
        addCashBT.setFocusable(false);

        // action listener for add cash button
        addCashBT.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!addCashTF.getText().equals("")) {
                    if (Float.parseFloat(addCashTF.getText()) > 0) {
                        cash = cash + Float.parseFloat(addCashTF.getText());
                        addCashTF.setText("0");
                        cashText.setText("CASH AVALIABLE : ₹" + df.format(cash));

                        runThread();
                    }
                }
            }

        });
        leftMenu.add(addCashBT);

        // gap
        JLabel gapLine1 = new JLabel("--------------------------------------------------------------------------");
        gapLine1.setForeground(Color.black);
        gapLine1.setPreferredSize(new Dimension(300, 40));
        leftMenu.add(gapLine1);

        // add bit JLabel
        bit = new JLabel("BIT AMOUNT:");
        bit.setForeground(Color.WHITE);
        // cashText.setPreferredSize(new Dimension(300, 40));
        bit.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        leftMenu.add(bit);

        // add cash text field
        addBitTF = new JTextField("0", 17);
        addBitTF.setForeground(Color.WHITE);
        addBitTF.setBackground(Color.decode("#324452"));
        addBitTF.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        addBitTF.addMouseListener(mouseListenerForTextField);
        leftMenu.add(addBitTF);

        // add difficulty level
        // add Jlabel
        diff = new JLabel("DIFFICULTY (1 - 15) :");
        diff.setForeground(Color.WHITE);
        // cashText.setPreferredSize(new Dimension(300, 40));
        diff.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        leftMenu.add(diff);

        // add diff text field
        addDiff = new JTextField("1", 7);
        addDiff.setForeground(Color.WHITE);
        addDiff.setBackground(Color.decode("#324452"));
        addDiff.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        leftMenu.add(addDiff);

        // add game start button
        startBT = new JButton("START GAME");
        startBT.setForeground(Color.WHITE);
        startBT.setBackground(Color.decode("#324452"));
        startBT.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        startBT.setPreferredSize(new Dimension(300, 33));
        startBT.setFocusable(false);

        // add action listener for start button
        startBT.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!addBitTF.getText().equals("") && !addDiff.getText().equals("")) {
                    diffLevel = Integer.parseInt(addDiff.getText());
                    bitAmount = Float.parseFloat(addBitTF.getText());

                    if (bitAmount > 0 && bitAmount <= cash && diffLevel > 0 && diffLevel <= 15) {
                        cash = cash - bitAmount;
                        cashText.setText("CASH AVALIABLE : ₹" + df.format(cash));
                        cashoutPLAYBUTTON = true;
                        buttonPlayable(cashoutPLAYBUTTON);
                        cashOutActivation(cashoutPLAYBUTTON, played);
                        addCashTF.setEditable(false);
                        addCashBT.setEnabled(false);
                        addBitTF.setEnabled(false);
                        addDiff.setEnabled(false);
                        startBT.setEnabled(false);

                        // finding cordinates
                        for (int i = 0; i < (diffLevel * 2) - 1; i = i + 2) {
                            cordinate[i] = (int) (Math.random() * 10) / 2;
                            cordinate[i + 1] = (int) (Math.random() * 10) / 2;
                        }
                    }
                }
            }

        });
        leftMenu.add(startBT);

        // gap
        JLabel gapLine2 = new JLabel("--------------------------------------------------------------------------");
        gapLine2.setForeground(Color.black);
        gapLine2.setPreferredSize(new Dimension(300, 40));
        leftMenu.add(gapLine2);

        // add profit JLabel
        profit = new JLabel("PROFIT : " + bitAmount);
        profit.setForeground(Color.WHITE);
        // cashText.setPreferredSize(new Dimension(300, 40));
        profit.setFont(new Font(Font.DIALOG, Font.PLAIN, 17));
        leftMenu.add(profit);

        // add cashout button
        cashOut = new JButton("CASHOUT");
        cashOut.setForeground(Color.black);
        // cashOut.setBackground(Color.decode("#324452"));
        cashOut.setBackground(Color.GREEN);
        cashOut.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        cashOut.setPreferredSize(new Dimension(300, 35));
        cashOut.setFocusable(false);
        cashOutActivation(cashoutPLAYBUTTON, played);

        // add action listener for cashout button
        cashOut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (played) {
                    cash += bitAmount;
                    cashText.setText("CASH AVALIABLE : ₹" + df.format(cash));
                    bitAmount = 0;
                    profit.setText("PROFIT : " + bitAmount);
                    addBitTF.setText("0");
                    cashoutPLAYBUTTON = false;
                    cashOutActivation(cashoutPLAYBUTTON, played);
                    restartBTActivation(cashoutPLAYBUTTON);
                    buttonPlayable(cashoutPLAYBUTTON);
                    addCashTF.setEditable(true);
                    addCashBT.setEnabled(true);
                    played = false;
                    runThread();
                }
            }
        });
        leftMenu.add(cashOut);

        // adding restart button
        restart = new JButton("RESTART");
        restart.setForeground(Color.WHITE);
        restart.setBackground(Color.decode("#324452"));
        restart.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        restart.setPreferredSize(new Dimension(300, 35));
        restart.setFocusable(false);
        restart.setEnabled(false);

        // funcnalities for restart button
        restart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                bitAmount = 0;
                cordx = cordy = 0;
                list.clear();
                // restarting display button
                for (int a = 0; a < 5; a++) {
                    for (int b = 0; b < 5; b++) {
                        bt[a][b].setEnabled(false);
                        bt[a][b].setBackground(Color.decode("#324452"));
                        bt[a][b].setIcon(null);
                    }
                }
                cashOut.setEnabled(false);
                restart.setEnabled(false);
                played = false;
                addBitTF.setEnabled(true);
                addDiff.setEnabled(true);
                startBT.setEnabled(true);
            }

        });

        leftMenu.add(restart);

        return leftMenu;

    }

    private static JPanel right() {
        JPanel rightGame = new JPanel();
        rightGame.setPreferredSize(new Dimension(700, 700));
        rightGame.setBorder(BorderFactory.createLineBorder(Color.black));
        rightGame.setBackground(Color.decode("#122132"));
        rightGame.setLayout(new BorderLayout());

        JPanel gapN = new JPanel();
        JPanel gapS = new JPanel();
        JPanel gapE = new JPanel();
        JPanel gapW = new JPanel();

        gapN.setPreferredSize(new Dimension(50, 50));
        gapS.setPreferredSize(new Dimension(50, 50));
        gapE.setPreferredSize(new Dimension(50, 50));
        gapW.setPreferredSize(new Dimension(50, 50));

        gapN.setBackground(Color.decode("#122132"));
        gapS.setBackground(Color.decode("#122132"));
        gapE.setBackground(Color.decode("#122132"));
        gapW.setBackground(Color.decode("#122132"));

        rightGame.add(gapN, BorderLayout.NORTH);
        rightGame.add(gapS, BorderLayout.SOUTH);
        rightGame.add(gapE, BorderLayout.EAST);
        rightGame.add(gapW, BorderLayout.WEST);

        // getting current directory
        // String currentDirectory = System.getProperty("user.dir");

        // image in game buttons
        // Icon iconGreen = new ImageIcon(
        // currentDirectory + "\\res\\greensmall.jpg");

        // Icon iconRed = new ImageIcon(
        // currentDirectory + "\\res\\redsmall.jpg");

        JPanel game = new JPanel(new GridLayout(5, 5, 10, 10));
        game.setBackground(Color.decode("#122132"));

        // for(int a= 0 ; a<30 ;a++)

        // functionality for game
        ActionListener onClick = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton temp = (JButton) e.getSource();
                played = true;
                cashOutActivation(cashoutPLAYBUTTON, played);

                Boolean makeChange = false;
                if (list.size() == 0) {
                    makeChange = true;
                } else {
                    makeChange = true;
                    for (int i = 0; i < list.size(); i = i + 2) {
                        cordx = list.get(i);
                        cordy = list.get(i + 1);
                        if (bt[cordx][cordy] == temp) {
                            makeChange = false;
                            break;
                        }
                    }
                }

                if (makeChange) {
                    // ye variables isle ke ek bar button press hone pr dobra press na ho
                    int trackCordinateForNotPlayAgainX = 0, trackCordinateForNotPlayAgainY = 0;
                    for (int a = 0; a < 5; a++) {
                        for (int b = 0; b < 5; b++) {
                            if (bt[a][b] == temp) {
                                trackCordinateForNotPlayAgainX = a;
                                trackCordinateForNotPlayAgainY = b;
                                break;
                            }
                        }
                    }

                    Boolean yesGreenFlag = true;
                    for (int i = 0; i < (diffLevel * 2) - 1; i = i + 2) {
                        cordx = cordinate[i];
                        cordy = cordinate[i + 1];
                        if (bt[cordx][cordy] == temp) {
                            yesGreenFlag = false;
                            temp.setBackground(Color.decode("#FF4001"));
                            temp.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("redsmall.jpg")));
                            cashoutPLAYBUTTON = false;
                            buttonPlayable(cashoutPLAYBUTTON);
                            cashOutActivation(cashoutPLAYBUTTON, played);
                            restartBTActivation(cashoutPLAYBUTTON);
                            // temp.setEnabled(false);
                            bitAmount = 0;
                            cash += bitAmount;
                            cashText.setText("CASH AVAILABLE : ₹" + df.format(cash));
                            profit.setText("PROFIT : " + bitAmount);
                            addBitTF.setText("0");
                            addCashTF.setEditable(true);
                            addCashBT.setEnabled(true);
                            list.clear();

                            runThread();
                            break;

                        }
                    }
                    if (yesGreenFlag) {
                        temp.setBackground(Color.decode("#84FF00"));
                        temp.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("greensmall.jpg")));
                        // temp.setEnabled(false);
                        bitAmount = bitAmount + (bitAmount / 100 * (1 + (diffLevel * 2)));
                        profit.setText("PROFIT : " + bitAmount);
                        // get cordinate for not re click button
                        list.add(trackCordinateForNotPlayAgainX);
                        list.add(trackCordinateForNotPlayAgainY);

                    }
                }
            }
        };

        // adding buttons
        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 5; b++) {
                bt[a][b] = new JButton();
                bt[a][b].setBackground(Color.decode("#324452"));
                bt[a][b].setEnabled(false);
                bt[a][b].addActionListener(onClick);

                game.add(bt[a][b]);
            }
        }

        // default activation for buttons
        buttonPlayable(cashoutPLAYBUTTON);

        rightGame.add(game, BorderLayout.CENTER);

        return rightGame;
    }

    private static void buttonPlayable(Boolean flag) {
        if (flag) {
            for (int a = 0; a < 5; a++) {
                for (int b = 0; b < 5; b++) {
                    bt[a][b].setEnabled(true);
                }
            }
        } else {
            for (int a = 0; a < 5; a++) {
                for (int b = 0; b < 5; b++) {
                    // bt[a][b].setEnabled(false);
                }
            }
        }
    }

    private static void cashOutActivation(Boolean flag, Boolean Flag2) {
        if (flag && Flag2) {
            cashOut.setEnabled(true);
        } else {
            cashOut.setEnabled(false);
        }
    }

    private static void restartBTActivation(Boolean flag) {
        if (flag) {
            restart.setEnabled(false);
        } else {
            restart.setEnabled(true);
        }
    }

    // mouse Listener for all text Fields
    static MouseListener mouseListenerForTextField = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            JTextField temp = (JTextField) e.getSource();
            if (temp == addCashTF) {
                addCashTF.setText("");
                addCashTF.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == addBitTF) {
                addBitTF.setText("");
                addBitTF.setBorder(BorderFactory.createLineBorder(Color.black));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JTextField temp = (JTextField) e.getSource();
            if (temp == addCashTF) {
                addCashTF.setBorder(BorderFactory.createLineBorder(Color.black));
            } else if (temp == addBitTF) {
                addBitTF.setBorder(BorderFactory.createLineBorder(Color.black));
            }

        }

        @Override
        public void mouseExited(MouseEvent e) {
            JTextField temp = (JTextField) e.getSource();
            if (temp == addCashTF) {
                addCashTF.setBorder(defaultborder);
            } else if (temp == addBitTF) {
                addBitTF.setBorder(defaultborder);
            }
        }

    };

    // creating a thread for update cash value in database during game in background
    public static void runThread() {
        maingame t1 = new maingame();
        t1.start();
    }

    public void run() {
        try {
            // create query
            String q = "UPDATE dataset SET cash = ? WHERE UserName = ?";

            // prepared Statement
            PreparedStatement Pst = con.prepareStatement(q);
            Pst.setFloat(1, cash);
            Pst.setString(2, username);

            // execute
            Pst.executeUpdate();
        } catch (Exception e) {
        }
    }
}
