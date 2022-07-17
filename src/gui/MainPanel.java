package gui;

import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.*;

/**
 * MainPanel class implements UI of main panel
 *
 * @author ZK
 */
public class MainPanel {
    private JFrame window;
    private JPanel mainPanel;
    private JPanel mainHeader;
    // button for sending URL
    private JButton sendButton;
    // URL address text field
    private JTextField urlAddress;

    // tabbed pane
    private JPanel tabbedPane;
    // combo box for request body tab and choosing http method type
    private JComboBox<String> bodyTab, MethodChooser;
    // button for Bearer and Query and Header tabs
    private JButton headerTab;
    // panel that holds tabs and their related panels
    private JPanel tabPanel;
    // panels for body, form,  binary file, json, nobody (Body tab) and Bearer, Query and Header tabs
    private JPanel bodyPanel;
    private JPanel formPanel;
    private JPanel jsonPanel;
    private JPanel headerPanel;
    // property of Form panel
    private JPanel newFormCreator;
    // property of Header panel(main)
    private JPanel newHeaderCreator;
    // properties of JSON panel(main)
    private JTextArea jsonText;
    private JButton beautifyJSON;

    /**
     * create main panel
     *
     * @param window main window that panel is added to
     */
    public MainPanel(JFrame window) {
        this.window = window;
        setPanel();
    }


    /*
     ***These are Setting methods***
     */

    /**
     * set the main panel
     */
    private void setPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(487, 640));
        mainPanel.setOpaque(true);
        mainPanel.setBackground(Colors.mainColor);
        window.add(mainPanel);
        setProperties();
    }

    /**
     * set properties of main panel
     */
    private void setProperties() {
        setURLHeadLine();
        setTabPanel();
    }

    /**
     * set URL bar of main panel
     */
    private void setURLHeadLine() {
        //  create and set URL headline panel
        mainHeader = new JPanel(new BorderLayout());
        mainHeader.setPreferredSize(new Dimension(478, 47));
        mainHeader.setBackground(Colors.sendPanelColor);
        // set properties
        setMethodChooser();
        setURLAddressBar();
        setSendButton();
        // add URL headline
        mainPanel.add(mainHeader, BorderLayout.NORTH);
    }

    /**
     * create method chooser combo box
     */
    private void setMethodChooser() {
        String[] methods = {"GET", "POST", "PUT", "DELETE"};
        MethodChooser = new JComboBox<>(methods);
        MethodChooser.setPreferredSize(new Dimension(74, 47));
        MethodChooser.setFont(new Font("Segoe", Font.PLAIN, 12));
        MethodChooser.setOpaque(true);
        MethodChooser.setBackground(Colors.sendPanelColor);
        MethodChooser.setAlignmentY(Component.TOP_ALIGNMENT);
        MethodChooser.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        MethodChooser.setFocusable(false);
    }

    /**
     * create URL text field
     */
    private void setURLAddressBar() {
        urlAddress = new JTextField();
        urlAddress.setFont(new Font("Segoe", Font.PLAIN, 14));
        urlAddress.setPreferredSize(new Dimension(320, 47));
        urlAddress.setOpaque(true);
        urlAddress.setBackground(Colors.sendPanelColor);
        PromptSupport.setPrompt("Enter URL...", urlAddress);
        urlAddress.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
    }

    /**
     * create send button field
     */
    private void setSendButton() {
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe", Font.PLAIN, 16));
        sendButton.setPreferredSize(new Dimension(70, 47));
        sendButton.setOpaque(false);
        sendButton.setBackground(Colors.sendPanelColor);
        sendButton.setForeground(Colors.responseLabelsFore);
        sendButton.setBorder(BorderFactory.createEmptyBorder());
        sendButton.setContentAreaFilled(false);
    }

    /**
     * set tab panel of main panel
     */
    private void setTabPanel() {
        // create and set tab panel
        tabPanel = new JPanel(new BorderLayout());
        tabPanel.setBackground(Colors.mainColor);
        setTabbedPane();
        // set each panel in the tabbed panel: Body(nobody), Form, JSON, Binary, Bearer, Query, Header,
        setBodyPanel();
        setFormPanel();
        setJsonPanel();
        setHeaderPanel();
        // add tab panel
        mainPanel.add(tabPanel, BorderLayout.CENTER);
    }

    /**
     * set tabbed pane pf tab panel
     *
     * @return tabbed pane
     */
    private JPanel setTabbedPane() {
        // create and set tabbed pane
        tabbedPane = new JPanel();
        tabbedPane.setLayout(new BoxLayout(tabbedPane, BoxLayout.LINE_AXIS));
        tabbedPane.setPreferredSize(new Dimension(478, 40));
        tabbedPane.setBackground(Colors.mainColor);
        tabbedPane.setBorder(BorderFactory.createLineBorder(Colors.lightHideColor, 1));
        // set properties
        setBodyTab();
        setHeaderTab();
        // add properties
        tabbedPane.add(bodyTab);
        tabbedPane.add(headerTab);

        return tabbedPane;
    }

    /**
     * create body tab (which is a combo box)
     */
    private void setBodyTab() {
        // Body Tab
        String[] bodyTypes = {"Body", "Form", "JSON"};
        bodyTab = new JComboBox<>(bodyTypes);
        bodyTab.setForeground(Colors.sendPanelColor);
        bodyTab.setBackground(Colors.mainColor);
        bodyTab.setMaximumSize(new Dimension(95, 40));
        bodyTab.setFont(new Font("Segoe", Font.PLAIN, 14));
        bodyTab.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        bodyTab.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /**
     * create Header tab (which is a button)
     */
    private void setHeaderTab() {
        headerTab = new JButton("Header");
        headerTab.setMaximumSize(new Dimension(95, 40));
        headerTab.setFont(new Font("Segoe", Font.PLAIN, 14));
        headerTab.setForeground(Colors.sendPanelColor);
        headerTab.setBackground(Colors.mainColor);
        headerTab.setContentAreaFilled(false);
        headerTab.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerTab.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    /**
     * set no body panel
     */
    private void setBodyPanel() {
        bodyPanel = new JPanel(new GridBagLayout());
        bodyPanel.setBackground(Colors.mainColor);
        JLabel handLabel = new JLabel();
        ImageIcon handImage = new ImageIcon("res/hand.png");
        handLabel.setIcon(handImage);
        bodyPanel.add(handLabel);
    }

    /**
     * set form data panel
     */
    private void setFormPanel() {
        // create form panel
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Colors.mainColor);
        // create a panel for new form creation
        newFormCreator = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        newFormCreator.setMaximumSize(new Dimension(478, 40));
        newFormCreator.setBackground(Colors.mainColor);
        newFormCreator.setAlignmentX(Component.LEFT_ALIGNMENT);
        newFormCreator.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        // text field New name
        JTextField name = new JTextField("New name");
        name.setBackground(Colors.mainColor);
        name.setForeground(Colors.lightHideColor);
        name.setPreferredSize(new Dimension(180, 30));
        name.setEditable(false);
        name.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.lightHideColor));
        // text field New value
        JTextField value = new JTextField("New value");
        value.setBackground(Colors.mainColor);
        value.setForeground(Colors.lightHideColor);
        value.setPreferredSize(new Dimension(180, 30));
        value.setEditable(false);
        value.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.lightHideColor));
        // add text fields to create panel
        newFormCreator.add(name);
        newFormCreator.add(value);
        // add create panel to form panel
        formPanel.add(newFormCreator);
    }

    /**
     * set JSON panel
     */
    private void setJsonPanel() {
        // create JSON panel
        jsonPanel = new JPanel(new BorderLayout());
        jsonPanel.setBackground(Colors.mainColor);
        // create text field
        jsonText = new JTextArea();
        PromptSupport.setPrompt("...", jsonText);
        jsonText.setBackground(Colors.mainColor);
        jsonText.setForeground(new Color(241, 147, 31));
        jsonText.setFont(new Font("Monospaced", Font.BOLD, 13));
        jsonText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        // create JSON number of row keeper
        JTextArea jsonRowNumKeeper = new JTextArea();
        for (int i = 1; i < 37; i++)
            jsonRowNumKeeper.append(" " + (i > 9 ? i : " " + i) + " \n");
        jsonRowNumKeeper.setBackground(Colors.mainColor);
        jsonRowNumKeeper.setForeground(Colors.lightHideColor);
        jsonRowNumKeeper.setPreferredSize(new Dimension(30, 466));
        jsonRowNumKeeper.setFont(new Font("Monospaced", Font.BOLD, 13));
        jsonRowNumKeeper.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        // create beautify button
        beautifyJSON = new JButton("Beautify JSON");
        beautifyJSON.setBackground(Colors.mainColor);
        beautifyJSON.setForeground(Colors.sendPanelColor);
        beautifyJSON.setPreferredSize(new Dimension(400, 40));
        JScrollPane scrollPane = new JScrollPane(jsonText);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        // add components to JSON panel
        jsonPanel.add(scrollPane, BorderLayout.CENTER);
        jsonPanel.add(jsonRowNumKeeper, BorderLayout.WEST);
        jsonPanel.add(beautifyJSON, BorderLayout.SOUTH);
    }

    /**
     * set header panel
     */
    private void setHeaderPanel() {
        // create header panel
        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Colors.mainColor);
        // create a panel for new header creation
        newHeaderCreator = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        newHeaderCreator.setMaximumSize(new Dimension(478, 40));
        newHeaderCreator.setBackground(Colors.mainColor);
        newHeaderCreator.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        newHeaderCreator.setAlignmentX(Component.LEFT_ALIGNMENT);
        // text field header
        JTextField header = new JTextField("New header");
        header.setBackground(Colors.mainColor);
        header.setForeground(Colors.lightHideColor);
        header.setPreferredSize(new Dimension(180, 30));
        header.setEditable(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.lightHideColor));
        // text field value
        JTextField value = new JTextField("New value");
        value.setBackground(Colors.mainColor);
        value.setForeground(Colors.lightHideColor);
        value.setPreferredSize(new Dimension(180, 30));
        value.setEditable(false);
        value.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.lightHideColor));
        // add text fields to create panel
        newHeaderCreator.add(header);
        newHeaderCreator.add(value);
        // add create panel to header panel
        headerPanel.add(newHeaderCreator);
    }

    /**
     * add a new form to form panel
     *
     * @param newForm form to be added
     */
    public void toCreateNewForm(JPanel newForm) {
        formPanel.add(newForm, formPanel.getComponents().length - 1);
    }

    /**
     * add a new header to header panel
     *
     * @param newHeader header to be added
     */
    public void toCreateNewHeader(JPanel newHeader) {
        headerPanel.add(newHeader, headerPanel.getComponents().length - 1);
    }

    /*
     ***These are Getter methods***
     */

    /**
     * get main panel
     *
     * @return mainPanel field
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * get send button
     *
     * @return sendButton field
     */
    public JButton getSendButton() {
        return sendButton;
    }

    /**
     * get URL text field
     *
     * @return urlAddress field
     */
    public JTextField getUrlAddress() {
        return urlAddress;
    }

    /**
     * get body tab combo box
     *
     * @return bodyTab field
     */
    public JComboBox<String> getBodyTab() {
        return bodyTab;
    }

    /**
     * get method chooser combo box
     *
     * @return MethodChooser field
     */
    public JComboBox<String> getMethodChooser() {
        return MethodChooser;
    }

    /**
     * get header tab field
     *
     * @return headerTab field
     */
    public JButton getHeaderTab() {
        return headerTab;
    }

    /**
     * get body panel
     *
     * @return bodyPanel field
     */
    public JPanel getBodyPanel() {
        return bodyPanel;
    }

    /**
     * get form panel
     *
     * @return formPanel field
     */
    public JPanel getFormPanel() {
        return formPanel;
    }

    /**
     * get JSON panel
     *
     * @return jsonPanel field
     */
    public JPanel getJsonPanel() {
        return jsonPanel;
    }

    /**
     * get header panel
     *
     * @return headerPanel field
     */
    public JPanel getHeaderPanel() {
        return headerPanel;
    }

    /**
     * get new form creator panel
     *
     * @return newFormCreator field
     */
    public JPanel getNewFormCreator() {
        return newFormCreator;
    }

    /**
     * get new header creator panel
     *
     * @return newHeaderCreator field
     */
    public JPanel getNewHeaderCreator() {
        return newHeaderCreator;
    }

    /**
     * get tab panel
     *
     * @return tabPanel field
     */
    public JPanel getTabPanel() {
        return tabPanel;
    }

    /**
     * get JSON text field
     *
     * @return jsonText field
     */
    public JTextArea getJsonText() {
        return jsonText;
    }

    /**
     * get beautifyJSON button
     *
     * @return beautifyJSON field
     */
    public JButton getBeautifyJSON() {
        return beautifyJSON;
    }

    /**
     * get the main header which has URL address bar and send button
     *
     * @return mainHeader
     */
    public JPanel getMainHeader() {
        return mainHeader;
    }

    /**
     * get tabbed pane
     *
     * @return tabbedPane
     */
    public JPanel getTabbedPane() {
        return tabbedPane;
    }

}
