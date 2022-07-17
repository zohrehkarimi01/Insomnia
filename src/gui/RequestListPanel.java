package gui;

import jurl.FileUtils;
import jurl.Request;

import javax.swing.*;
import java.awt.*;

/**
 * RequestListPanel implements UI of request panel
 *
 * @author ZK
 */
public class RequestListPanel {

    private JFrame window;
    // request list panel
    private JPanel requestListPanel;
    // list of requests
    private JList<String> requestList;
    // model of request list
    private DefaultListModel<String> model;
    // button to save or delete a request
    private JButton saveRequestButton, deleteRequestButton;
    // button for creating new requests and sending URL
    private JButton newRequestButton;

    /**
     * create a request panel
     *
     * @param window main window that panel is added to
     */
    public RequestListPanel(JFrame window) {
        this.window = window;
        setPanel();
    }

    /*
     ***These are Setting methods***
     */

    /**
     * set request panel
     */
    private void setPanel() {
        requestListPanel = new JPanel();
        requestListPanel.setLayout(new BoxLayout(requestListPanel, BoxLayout.Y_AXIS));
        requestListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        requestListPanel.setPreferredSize(new Dimension(290, 640));
        requestListPanel.setOpaque(true);
        requestListPanel.setBackground(Colors.requestPanelColor);
        requestListPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Colors.lightHideColor));
        setProperties();
        window.add(requestListPanel, BorderLayout.WEST);
    }

    /**
     * set properties of request panel
     */
    private void setProperties() {
        setLabel();
        setRequestButtons();
        setList();
    }

    /**
     * create Insomnia label
     */
    private void setLabel() {
        JLabel insomniaLabel = new JLabel("   Insomnia                                         ");
        insomniaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        insomniaLabel.setMaximumSize(new Dimension(290, 47));
        insomniaLabel.setFont(new Font("Segoe", Font.PLAIN, 18));
        insomniaLabel.setOpaque(true);
        insomniaLabel.setBackground(Colors.insomniaColor);
        insomniaLabel.setForeground(Colors.sendPanelColor);

        requestListPanel.add(insomniaLabel);
    }

    /**
     * set filter and new button
     */
    private void setRequestButtons() {
        JPanel buttonKeeper = new JPanel(new FlowLayout());
        buttonKeeper.setMaximumSize(new Dimension(290, 50));
        buttonKeeper.setBackground(Colors.requestPanelColor);
        buttonKeeper.setAlignmentX(Component.LEFT_ALIGNMENT);

        setSaveButton();
        setDeleteButton();
        setNewButton();

        buttonKeeper.add(saveRequestButton);
        buttonKeeper.add(deleteRequestButton);
        buttonKeeper.add(newRequestButton);

        requestListPanel.add(buttonKeeper);
    }

    /**
     * create save button to save a request
     */
    private void setSaveButton() {
        saveRequestButton = new JButton("Save");
        saveRequestButton.setFont(new Font("Segoe", Font.PLAIN, 15));
        saveRequestButton.setForeground(Colors.sendPanelColor);
        saveRequestButton.setBackground(Colors.insomniaColor);
        saveRequestButton.setOpaque(true);
        saveRequestButton.setContentAreaFilled(true);
        saveRequestButton.setBorder(BorderFactory.createLineBorder(Colors.requestListFore, 2, true));
        saveRequestButton.setFocusable(true);
        saveRequestButton.setPreferredSize(new Dimension(80, 30));
    }

    /**
     * create delete button to delete a request
     */
    private void setDeleteButton() {
        deleteRequestButton = new JButton("Delete");
        deleteRequestButton.setFont(new Font("Segoe", Font.PLAIN, 15));
        deleteRequestButton.setForeground(Colors.sendPanelColor);
        deleteRequestButton.setBackground(Colors.insomniaColor);
        deleteRequestButton.setOpaque(true);
        deleteRequestButton.setContentAreaFilled(true);
        deleteRequestButton.setBorder(BorderFactory.createLineBorder(Colors.requestListFore, 2, true));
        deleteRequestButton.setFocusable(true);
        deleteRequestButton.setPreferredSize(new Dimension(80, 30));
    }

    /**
     * create new button
     */
    private void setNewButton() {
        newRequestButton = new JButton("\u2795");
        newRequestButton.setFont(new Font("Segoe", Font.PLAIN, 16));
        newRequestButton.setForeground(Colors.sendPanelColor);
        //newRequestButton.setBorder(BorderFactory.createLineBorder(new Color(237, 237, 237), 3, true));
        newRequestButton.setOpaque(true);
        newRequestButton.setFocusable(false);
        newRequestButton.setContentAreaFilled(false);
        newRequestButton.setPreferredSize(new Dimension(50, 30));
    }

    /**
     * create list of requests
     */
    private void setList() {
        model = new DefaultListModel<>();
        requestList = new JList<>(model);
        updateListGUI();
        requestList.setFont(new Font("Segoe", Font.PLAIN, 14));
        requestList.setFocusable(false);
        requestList.setAlignmentX(Component.LEFT_ALIGNMENT);
        requestList.setFixedCellHeight(40);
        requestList.setMaximumSize(new Dimension(290, 500));
        requestList.setBackground(Colors.requestPanelColor);
        requestList.setSelectionBackground(Colors.requestListSelected);
        requestList.setForeground(Colors.requestListFore);
        requestList.setSelectionForeground(Colors.sendPanelColor);

        requestListPanel.add(requestList);
    }

    /**
     * update requests list
     */
    public void updateListGUI() {
        Request[] requests = FileUtils.readAllRequests();
        String name;
        for (Request request : requests) {
            name = "     " + request.getRequestName();
            model.addElement(name);
        }
    }

    /*
     ***These are Getter methods***
     */

    /**
     * get request panel
     *
     * @return requestListPanel field
     */
    public JPanel getRequestListPanel() {
        return requestListPanel;
    }

    /**
     * get request list
     *
     * @return requestList field
     */
    public JList<String> getRequestList() {
        return requestList;
    }

    /**
     * get new button
     *
     * @return newRequestButton field
     */
    public JButton getNewRequestButton() {
        return newRequestButton;
    }

    /**
     * get save button
     *
     * @return saveRequestButton field
     */
    public JButton getSaveRequestButton() {
        return saveRequestButton;
    }

    /**
     * get delete button
     *
     * @return deleteRequestButton field
     */
    public JButton getDeleteRequestButton() {
        return deleteRequestButton;
    }

    /**
     * get model of request list
     *
     * @return model filed
     */
    public DefaultListModel<String> getModel() {
        return model;
    }
}