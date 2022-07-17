package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Window class implements UI of main frame of Insomnia app
 *
 * @author ZK
 */
public class Window {
    // main frame
    private static JFrame window;
    // menu items
    private static JMenuItem options, exit, toggleSidebar, toggleFullScreen,
            help, about;

    /**
     * create window
     */
    public Window() {
        setLookAndFeel();
        setWindow();
        setMenuBar();
    }

    /*
     ***These are Setting methods***
     */

    /**
     * create and set main window
     */
    private void setWindow() {
        window = new JFrame("Insomnia");
        window.setLocation(194, 55);
        window.setSize(new Dimension(1096, 647));
        window.setMinimumSize(new Dimension(625, 500));
        try {
            window.setIconImage(ImageIO.read(new File("res/Icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * set look and feel of GUI
     */
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            //     UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //     UIManager.setLookAndFeel ("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            //     UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * set menuBar of window
     */
    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        //menuBar.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(230, 230, 230));

        JMenu applicationMenu = new JMenu("Application");
        JMenu viewMenu = new JMenu("View");
        JMenu helpMenu = new JMenu("Help");

        options = new JMenuItem("Options", 'p');
        options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        exit = new JMenuItem("Exit", 'x');
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        toggleFullScreen = new JMenuItem("Toggle Full Screen", 'f');
        toggleFullScreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        toggleSidebar = new JMenuItem("Toggle Sidebar", 'b');
        toggleSidebar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        help = new JMenuItem("Help", 'h');
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        about = new JMenuItem("About", 'a');
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        Font font = new Font("Arial", Font.PLAIN, 12);
        applicationMenu.setFont(font);
        viewMenu.setFont(font);
        helpMenu.setFont(font);

        options.setFont(font);
        exit.setFont(font);
        toggleFullScreen.setFont(font);
        toggleSidebar.setFont(font);
        help.setFont(font);
        about.setFont(font);

        applicationMenu.add(options);
        applicationMenu.add(exit);
        window.setBackground(Color.BLACK);
        viewMenu.add(toggleFullScreen);
        viewMenu.add(toggleSidebar);
        helpMenu.add(help);
        helpMenu.add(about);

        menuBar.add(applicationMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        window.setJMenuBar(menuBar);
    }

    /*
     ***These are Getter methods***
     */

    /**
     * get options menu item
     *
     * @return options field
     */
    public JMenuItem getOptions() {
        return options;
    }

    /**
     * get exit menu item
     *
     * @return exit field
     */
    public JMenuItem getExit() {
        return exit;
    }

    /**
     * get toggleSidebar menu item
     *
     * @return toggleSidebar field
     */
    public JMenuItem getToggleSidebar() {
        return toggleSidebar;
    }

    /**
     * get toggleFullScreen menu item
     *
     * @return toggleFullScreen field
     */
    public JMenuItem getToggleFullScreen() {
        return toggleFullScreen;
    }

    /**
     * get help menu item
     *
     * @return help field
     */
    public JMenuItem getHelp() {
        return help;
    }

    /**
     * get about menu item
     *
     * @return about field
     */
    public JMenuItem getAbout() {
        return about;
    }

    /**
     * get main window
     *
     * @return window field
     */
    public JFrame getWindow() {
        return window;
    }
}