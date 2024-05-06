import com.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class App {
    private int x, y;

    public App() {
        JFrame mainFrame = new JFrame("Mines");
        // default setting for main frame
        mainFrame.setUndecorated(true);
        mainFrame.setSize(1100, 700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLayout(null);

        mainFrame.add(titleBar(mainFrame));

        // getting current directory
        // String currentDirectory = System.getProperty("user.dir");

        // setting frame icon
        try {
            // get image from image folder which exist in project Folder
            Image imgicon = ImageIO.read(getClass().getClassLoader().getResource("logo.png"));
            mainFrame.setIconImage(imgicon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // adding panel into main panel
        JPanel home = new JPanel();
        // home.setPreferredSize(new Dimension(1100, 700));
        home.setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
        home.setLayout(null);

        // creating start button
        JButton letStart = new JButton("Let's Play");
        letStart.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        letStart.setFocusable(false);
        letStart.setBounds(450, 500, 200, 40);
        letStart.setBorder(BorderFactory.createLineBorder(Color.orange));
        letStart.setForeground(Color.YELLOW);
        letStart.setBackground(Color.decode("#0E1C33"));

        // adding button functionalities
        letStart.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                // create temp jFrame for store the reference of login JFrame
                JFrame temp = loginPage.loginPageFrame();
                temp.setVisible(true);
                mainFrame.dispose();

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                letStart.setBounds(430, 495, 240, 50);
                letStart.setForeground(Color.yellow);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                letStart.setBounds(450, 500, 200, 40);
                letStart.setForeground(Color.yellow);
            }

        });

        // adding Button into Home Panel
        home.add(letStart);

        // bg image
        try {
            // get image from image folder which exist in project Folder
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResource("home.jpg"));

            Image imgBg = img.getScaledInstance(1100, 700, Image.SCALE_FAST);
            JLabel bgImg = new JLabel();
            bgImg.setBounds(0, 0, 1100, 700);
            bgImg.setIcon(new ImageIcon(imgBg));

            home.add(bgImg);

        } catch (IOException e) {
            e.printStackTrace();
        }

        mainFrame.add(home);
        mainFrame.setVisible(true);
    }

    private JPanel titleBar(JFrame mainFrame) {
        // create custom titlebar
        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, -3));
        titleBar.setBounds(0, 0, mainFrame.getWidth(), 30);
        titleBar.setBackground(Color.decode("#0E1C33"));

        titleBar.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });

        titleBar.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                int xx = e.getXOnScreen();
                int yy = e.getYOnScreen();

                mainFrame.setLocation(xx - x, yy - y);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }

        });

        // adding minimize button
        JButton minButton = new JButton("-");
        minButton.setBackground(null);
        minButton.setForeground(Color.WHITE);
        minButton.setFocusable(false);
        minButton.setBorder(null);
        minButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 24));

        // action
        minButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setState(JFrame.ICONIFIED);
            }

        });

        titleBar.add(minButton);

        // adding close button
        JButton closeButton = new JButton("X");
        closeButton.setBackground(null);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusable(false);
        closeButton.setBorder(null);
        closeButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));

        // action
        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }

        });

        titleBar.add(closeButton);
        return titleBar;
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        App obj = new App();
    }
}