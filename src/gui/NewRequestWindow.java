package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * NewRequestWindow class implements UI of new request window
 *
 * @author ZK
 */
public class NewRequestWindow {

    private JFrame window;
    private JFrame newRequestWindow;
    private JTextField requestName;
    private JComboBox<String> chooseMethod;
    private JButton createButton;

    /**
     * create a request window
     *
     * @param window the main window
     */
    public NewRequestWindow(JFrame window) {
        this.window = window;
        setWindow();
    }

    /*
     ***These are Setting methods***
     */

    /**
     * set request window
     */
    private void setWindow() {
        newRequestWindow = new JFrame("New Request");
        try {
            newRequestWindow.setIconImage(ImageIO.read(new File("res/Icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        newRequestWindow.getContentPane();
        newRequestWindow.setSize(700, 200);
        newRequestWindow.setLocation((window.getWidth() - newRequestWindow.getWidth()) / 2, (window.getHeight() - newRequestWindow.getHeight()) / 2);
        newRequestWindow.setLocationRelativeTo(window);
        newRequestWindow.setResizable(false);
        JPanel panel = new JPanel(null);
        panel.setBackground(Colors.requestPanelColor);
        panel.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        newRequestWindow.add(panel);
        setChooseMethod();
        setCreateButton();
        panel.add(setRequestName());
        panel.add(requestName);
        panel.add(chooseMethod);
        panel.add(createButton);
    }

    /**
     * set label of name text field
     *
     * @return name label
     */
    private JLabel setRequestName() {
        JLabel name = new JLabel("Name");
        name.setForeground(Colors.sendPanelColor);
        name.setLocation(10, 20);
        name.setSize(50, 30);
        name.setFont(new Font("Segoe", Font.PLAIN, 16));
        requestName = new JTextField("My Request");
        requestName.setLocation(10, 50);
        requestName.setSize(570, 40);
        requestName.setBorder(BorderFactory.createLineBorder(Colors.lightHideColor, 1, true));
        requestName.setBackground(new Color(220, 220, 220));
        requestName.setFont(new Font("Segoe", Font.PLAIN, 16));

        return name;
    }

    /**
     * set method chooser combo box
     */
    private void setChooseMethod() {
        String[] methods = {"GET", "POST", "PUT", "DELETE"};
        chooseMethod = new JComboBox<>(methods);
        chooseMethod.setLocation(585, 50);
        chooseMethod.setSize(90, 40);
        chooseMethod.setFont(new Font("Segoe", Font.PLAIN, 14));
        chooseMethod.setOpaque(true);
    }

    /**
     * set create button
     */
    private void setCreateButton() {
        createButton = new JButton("Create");
        createButton.setLocation(590, 110);
        createButton.setSize(80, 40);
        createButton.setBackground(Colors.insomniaColor);
        createButton.setForeground(Colors.sendPanelColor);
        createButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Colors.heavyInsomniaColor));
    }

    /*
     ***These are Getter methods***
     */

    /**
     * get new request window
     *
     * @return newRequestWindow filed
     */
    public JFrame getNewRequestWindow() {
        return newRequestWindow;
    }

    /**
     * get new request name text field
     *
     * @return requestName field
     */
    public JTextField getRequestName() {
        return requestName;
    }

    /**
     * get method chooser combo box
     *
     * @return chooseMethod field
     */
    public JComboBox<String> getChooseMethod() {
        return chooseMethod;
    }

    /**
     * get create button
     *
     * @return createButton field
     */
    public JButton getCreateButton() {
        return createButton;
    }
}
