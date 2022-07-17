package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * optionsWindow class implements UI of options window
 *
 * @author ZK
 */
public class OptionsWindow {
    private JFrame optionsWindow;
    private JCheckBox followRedirect, systemTray;
    private JComboBox<String> themes;
    private JFrame window;

    /**
     * create an options window
     *
     * @param window main window
     */
    public OptionsWindow(JFrame window) {
        this.window = window;
        setWindow();
    }

    /*
     ***These are Setting methods***
     */

    /**
     * set options window
     */
    private void setWindow() {
        // create options window
        optionsWindow = new JFrame("Options");
        try {
            optionsWindow.setIconImage(ImageIO.read(new File("res/Icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        optionsWindow.getContentPane();
        optionsWindow.setSize(250, 200);
        optionsWindow.setLocation((window.getWidth() - optionsWindow.getWidth()) / 2, (window.getHeight() - optionsWindow.getHeight()) / 2);
        optionsWindow.setLocationRelativeTo(window);
        optionsWindow.setResizable(false);
        // create and add panel to window
        JPanel panel = new JPanel(null);
        panel.setBackground(Colors.requestPanelColor);
        panel.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        optionsWindow.add(panel);
        // add properties
        panel.add(followRedirectTask());
        panel.add(followRedirect);
        panel.add(systemTrayTask());
        panel.add(systemTray);
        panel.add(themesTask());
        panel.add(themes);
    }

    /**
     * create follow redirect check bow and label
     *
     * @return follow Redirect label
     */
    private JLabel followRedirectTask() {
        JLabel name = new JLabel("Follow Redirect");
        name.setForeground(Colors.sendPanelColor);
        name.setLocation(20, 20);
        name.setSize(150, 30);
        name.setFont(new Font("Segoe", Font.PLAIN, 14));
        followRedirect = new JCheckBox();
        followRedirect.setBackground(Colors.requestPanelColor);
        followRedirect.setLocation(170, 20);
        followRedirect.setSize(30, 30);
        return name;
    }

    /**
     * create hide on system tray check bow and label
     *
     * @return system tray label
     */
    private JLabel systemTrayTask() {
        JLabel name = new JLabel("Hide In System Tray");
        name.setForeground(Colors.sendPanelColor);
        name.setLocation(20, 60);
        name.setSize(150, 30);
        name.setFont(new Font("Segoe", Font.PLAIN, 14));
        systemTray = new JCheckBox();
        systemTray.setBackground(Colors.requestPanelColor);
        systemTray.setLocation(170, 60);
        systemTray.setSize(30, 30);
        return name;
    }

    /**
     * create theme chooser combo box and label
     *
     * @return theme label
     */
    private JLabel themesTask() {
        JLabel name = new JLabel("Choose Theme");
        name.setForeground(Colors.sendPanelColor);
        name.setLocation(20, 100);
        name.setSize(130, 30);
        name.setFont(new Font("Segoe", Font.PLAIN, 14));
        String[] themeNames = {"Dark", "Light"};
        themes = new JComboBox<String>(themeNames);
        themes.setBackground(Colors.requestPanelColor);
        themes.setForeground(Colors.sendPanelColor);
        themes.setLocation(150, 100);
        themes.setSize(60, 30);
        return name;
    }

    /*
     ***These are Getter methods***
     */

    /**
     * get options window
     *
     * @return OptionsWindow field
     */
    public JFrame getOptionsWindow() {
        return optionsWindow;
    }

    /**
     * get follow redirect check box
     *
     * @return followRedirect field
     */
    public JCheckBox getFollowRedirect() {
        return followRedirect;
    }

    /**
     * get hide on system tray check box
     *
     * @return systemTray field
     */
    public JCheckBox getSystemTray() {
        return systemTray;
    }

    /**
     * get theme chooser combo box
     *
     * @return themes field
     */
    public JComboBox<String> getThemes() {
        return themes;
    }
}
