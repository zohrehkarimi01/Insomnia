package controller;

import gui.*;
import gui.Window;
import jurl.FileUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

/**
 * ControllerWindow class implements logic for main window
 *
 * @author ZK
 */
public class ControllerWindow extends Controller {

    /**
     * create controller
     *
     * @param window           main frame
     * @param newRequest       new request frame
     * @param optionsWindow    option window
     * @param data             data of program
     * @param requestListPanel request Panel
     * @param mainPanel        the middle pael
     * @param responsePanel    response panel
     */
    public ControllerWindow(Window window, NewRequestWindow newRequest, OptionsWindow optionsWindow, AppData data,
                            RequestListPanel requestListPanel, MainPanel mainPanel, ResponsePanel responsePanel) {
        super(window, newRequest, optionsWindow, data, requestListPanel, mainPanel, responsePanel);
        setController();
        setWindow();
    }

    /**
     * set listeners
     */
    public void setController() {
        window.getOptions().addActionListener(e -> optionsMenu());
        window.getExit().addActionListener(e -> exit());
        window.getToggleSidebar().addActionListener(e -> toggleSideBar());
        window.getToggleFullScreen().addActionListener(e -> toggleFullScreen());
        window.getAbout().addActionListener(e -> aboutController());
        window.getHelp().addActionListener(e -> helpController());
        window.getWindow().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                FileUtils.saveSettings(data.getSettings());
            }
        });
    }

    /**
     * set window properties according to settings
     */
    private void setWindow(){
        data.getSettings().setToggleFullScreenMode(false);
        data.getSettings().setToggleFullScreenMode(false);
    }
    /**
     * option menu
     */
    private void optionsMenu() {
        optionsWindow.getOptionsWindow().setLocationRelativeTo(window.getWindow());
        optionsWindow.getOptionsWindow().setLocation(((window.getWindow().getWidth() - optionsWindow.getOptionsWindow().getWidth()) / 2) + window.getWindow().getX(),
                ((window.getWindow().getHeight() - optionsWindow.getOptionsWindow().getHeight()) / 2) + window.getWindow().getY());
        optionsWindow.getOptionsWindow().setVisible(true);
    }

    /**
     * exit the program
     */
    private void exit() {
        if (data.getSettings().isSystemTrayMode()) {
            window.getWindow().setExtendedState(JFrame.ICONIFIED);
        } else {
            FileUtils.saveSettings(data.getSettings());
            System.exit(0);
        }
    }

    /**
     * method to show or hide sidebar
     */
    private void toggleSideBar() {
        if (data.getSettings().isToggleSideBarMode()) {
            requestListPanel.getRequestListPanel().setVisible(true);
            data.getSettings().setToggleSideBarMode(false);
        } else {
            requestListPanel.getRequestListPanel().setVisible(false);
            data.getSettings().setToggleSideBarMode(true);
        }
    }

    /**
     * toggle full screen method
     */
    private void toggleFullScreen() {
        if (data.getSettings().isToggleFullScreenMode()) {
            data.getSettings().setToggleFullScreenMode(false);
            window.getWindow().setLocation(data.getSettings().getLocation());
            window.getWindow().setSize(data.getSettings().getSize());
        } else {
            try {
                data.getSettings().setToggleFullScreenMode(true);
                data.getSettings().setSize(window.getWindow().getSize());
                data.getSettings().setLocation(window.getWindow().getLocation());
                window.getWindow().setExtendedState(Frame.MAXIMIZED_BOTH);
                window.getWindow().setUndecorated(true);
            } catch (Exception e) {
                //Print Sth
            }
        }
    }

    /**
     * set controller for about item
     */
    public void aboutController() {
        // create options window
        JFrame frame = new JFrame("About Us");
        try {
            frame.setIconImage(ImageIO.read(new File("res/Icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.getContentPane();
        frame.setSize(350, 200);
        frame.setLocation((window.getWindow().getWidth() - frame.getWidth()) / 2, (window.getWindow().getHeight() - frame.getHeight()) / 2);
        frame.setLocationRelativeTo(window.getWindow());
        frame.setResizable(false);
        // create and add panel to window
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colors.requestPanelColor);
        panel.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        frame.add(panel);
        // add properties
        JTextArea textArea = new JTextArea("Hi!\nI'm Zohreh. My student number is 9831052.\nThis app is inspired by insomnia app.\nHope you enjoy it :)");
        textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        textArea.setFont(new Font("Times New Roman", Font.BOLD, 16));
        textArea.setBackground(Colors.requestPanelColor);
        textArea.setForeground(Colors.sendPanelColor);
        panel.add(textArea, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    /**
     * set controller for help item
     */
    public void helpController() {
        // create options window
        JFrame frame = new JFrame("Help");
        try {
            frame.setIconImage(ImageIO.read(new File("res/Icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.getContentPane();
        frame.setSize(650, 300);
        frame.setLocation((window.getWindow().getWidth() - frame.getWidth()) / 2, (window.getWindow().getHeight() - frame.getHeight()) / 2);
        frame.setLocationRelativeTo(window.getWindow());
        frame.setResizable(false);
        // create and add panel to window
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colors.requestPanelColor);
        panel.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        frame.add(panel);
        // add properties
        JTextArea textArea = new JTextArea("Usage: Jurl <url> [options...]\n" +
                " -d, --data <data>  HTTP POST data\n" +
                " -f, --follow       Follow redirect from server\n" +
                " -j, --json <jsonBody>     Set json message body\n" +
                " -H, --header <header>  Pass custom header(s) to server\n" +
                " -h, --help         This help text\n" +
                " -i, --include      Include protocol response headers in the output\n" +
                " -M, --method       Set the http request method(GET is default)\n" +
                " -O, --output [filename]   Save the response body in a file\n" +
                " -S, --save       Save the request and all it's settings\n" +
                "     --upload <@file>   Upload a file on sever");
        textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        textArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        textArea.setBackground(Colors.requestPanelColor);
        textArea.setForeground(Colors.sendPanelColor);
        panel.add(textArea, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
