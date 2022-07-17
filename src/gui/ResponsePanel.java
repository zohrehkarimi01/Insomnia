package gui;

import jurl.CommandListener;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * ResponsePanel class implements UI of response panel
 *
 * @author ZK
 */
public class ResponsePanel {

    private JFrame window;
    private JPanel responsePanel;
    private JPanel statusPanel;
    // properties of response head line
    private JLabel statusMessage, dataSize, timePassed;
    // combo box for message body
    private JComboBox<String> bodyTab;
    // button for header
    private JButton headerTab;
    // panel that holds response tabs and their related panels
    private JPanel responseTabPanel;
    private JPanel tabbedPane;
    // panels for raw , preview and header tabs
    private JPanel rawPanel, previewPanel, responseHeaderPanel;
    // property of raw panel
    private JTextArea rawText;
    // property of header panel
    private JTable responseHeadersTable;
    private JButton copyToClipboardButton;

    /**
     * create a response panel
     *
     * @param window main window that panel is added to
     */
    public ResponsePanel(JFrame window) {
        this.window = window;
        setPanel();
    }

    /*
     ***These are Setting methods***
     */

    /**
     * set response panel
     */
    public void setPanel() {
        responsePanel = new JPanel(new BorderLayout());
        responsePanel.setPreferredSize(new Dimension(330, 640));
        responsePanel.setOpaque(true);
        responsePanel.setBackground(Colors.mainColor);
        responsePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Colors.lightHideColor));
        setProperties();
        window.add(responsePanel, BorderLayout.EAST);
    }

    /**
     * set properties of response panel
     */
    private void setProperties() {
        setStatusPanel();
        setTabPanel();
    }

    /**
     * set tab panel
     */
    private void setTabPanel() {
        // set tabs and panels
        responseTabPanel = new JPanel(new BorderLayout());
        responseTabPanel.setBackground(Colors.mainColor);
        setTabbedPane();
        // set each panel in the tabbed panel nobody, form, JSON, header,
        setRawPanel("");
        setPreviewPanel();
        setHeaderPanel();
        responsePanel.add(responseTabPanel, BorderLayout.CENTER);
    }

    /**
     * set status panel
     */
    private void setStatusPanel() {
        // create status panel
        statusPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        flowLayout.setHgap(7);
        flowLayout.setVgap(10);
        statusPanel.setLayout(flowLayout);
        statusPanel.setPreferredSize(new Dimension(330, 46));
        statusPanel.setBackground(Colors.sendPanelColor);
        statusPanel.setBorder(BorderFactory.createEmptyBorder());
        // add properties
        if (statusMessage != null)
            statusPanel.add(statusMessage);
        if (timePassed != null)
            statusPanel.add(timePassed);
        if (dataSize != null)
            statusPanel.add(dataSize);
        // add status panel to response panel
        responsePanel.add(statusPanel, BorderLayout.NORTH);
    }

    /**
     * set status properties
     *
     * @param message   status message
     * @param time      time passed
     * @param dataValue size of data
     */
    public void addStatusProperties(String message, String time, String dataValue) {
        setMessageStatus(message);
        setTimePassed(time);
        setDataSize(dataValue);
    }

    /**
     * set status message and code label
     *
     * @param message status message
     */
    public void setMessageStatus(String message) {
        statusMessage = new JLabel(message);
        if (message.startsWith("2"))
            statusMessage.setBackground(Colors.statusCodeOK);
        else if (message.startsWith("4"))
            statusMessage.setBackground(Colors.statusCodeBad);
        else if (message.startsWith("3"))
            statusMessage.setBackground(Colors.insomniaColor);
        else {
            statusMessage.setBackground(Colors.statusCodeError);
        }
        statusMessage.setForeground(Color.white);
        statusMessage.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        //statusMessage.
        statusMessage.setFont(new Font("Arial", Font.BOLD, 13));
        statusMessage.setOpaque(true);
    }

    /**
     * set time passed label
     *
     * @param time time passed
     */
    private void setTimePassed(String time) {
        timePassed = new JLabel(time);
        timePassed.setBackground(Colors.responseLabelsBack);
        timePassed.setForeground(Colors.responseLabelsFore);
        timePassed.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        timePassed.setFont(new Font("Arial", Font.BOLD, 13));
        timePassed.setOpaque(true);
    }

    /**
     * set data size label
     *
     * @param dataValue data size
     */
    private void setDataSize(String dataValue) {
        dataSize = new JLabel(dataValue);
        dataSize.setBackground(Colors.responseLabelsBack);
        dataSize.setForeground(Colors.responseLabelsFore);
        dataSize.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        dataSize.setFont(new Font("Arial", Font.BOLD, 13));
        dataSize.setOpaque(true);
    }

    /**
     * set tabbed pane of tab panel
     *
     * @return tabbed pane
     */
    private JPanel setTabbedPane() {
        // create tabbed pane
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
     * create body tab
     */
    private void setBodyTab() {
        String[] messageBody = {"Raw", "Preview"};
        bodyTab = new JComboBox<>(messageBody);
        bodyTab.setForeground(Colors.sendPanelColor);
        bodyTab.setBackground(Colors.mainColor);
        bodyTab.setMaximumSize(new Dimension(95, 40));
        bodyTab.setFont(new Font("Segoe", Font.PLAIN, 14));
        bodyTab.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        bodyTab.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /**
     * create header tab
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
     * create Raw panel
     *
     * @param text response body to show
     */
    public void setRawPanel(String text) {
        // create raw panel
        rawPanel = new JPanel(new BorderLayout());
        rawPanel.setBackground(Colors.mainColor);
        // create raw text area
        rawText = new JTextArea(text);
        rawText.setBackground(Colors.mainColor);
        rawText.setForeground(Colors.sendPanelColor);
        rawText.setEditable(false);
        rawText.setFont(new Font("Monospaced", Font.PLAIN, 13));
        // create scroll bar
        JScrollPane scrollPane = new JScrollPane(rawText);
        scrollPane.setBackground(Colors.mainColor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rawText.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        // add scroll bar
        rawPanel.add(scrollPane);
    }

    /**
     * add a text to raw panel
     *
     * @param text text to be added
     */
    public void addRawText(String text) {
        rawText.setText(text);
    }

    /**
     * create preview panel
     */
    private void setPreviewPanel() {
        previewPanel = new JPanel(new GridBagLayout());
        previewPanel.setBackground(Colors.mainColor);
    }

    /**
     * show the pic response
     *
     * @param url pic address
     */
    public void addImagePreview(String url) {
        JLabel label = new JLabel();
        ImageIcon image = new ImageIcon(url);
        label.setIcon(image);
        previewPanel.add(label);
    }

    /**
     * show json response
     *
     * @param string json text
     */
    public void addJsonPreview(String string) {
        JTextArea textArea = new JTextArea(CommandListener.toPrettyFormat(string));
        previewPanel.add(textArea);
    }

    /**
     * create header panel
     */
    private void setHeaderPanel() {
        responseHeaderPanel = new JPanel(new BorderLayout());
        responseHeaderPanel.setBackground(Colors.mainColor);
        createCopyButton();
    }

    /**
     * set response headers table
     *
     * @param responseHeaders map of response headers
     */
    public void addResponseHeaders(Map<String, List<String>> responseHeaders) {
        String[][] data = new String[responseHeaders.keySet().size()][2];
        int index = 0;
        for (String key : responseHeaders.keySet()) {
            data[index][0] = key;
            List<String> values = responseHeaders.get(key);
            String str = "";
            for (int i = 0; i < values.size(); i++) {
                str += values.get(i);
                if (i != values.size() - 1)
                    str += (" | ");
            }
            data[index][1] = str;
            index++;
        }
        String[] columns = {"NAME", "VALUE"};
        responseHeadersTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(responseHeadersTable);
        scrollPane.setLayout(new ScrollPaneLayout());
        scrollPane.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        scrollPane.setBackground(Colors.mainColor);

        scrollPane.setForeground(Colors.sendPanelColor);
        responseHeadersTable.setBackground(Colors.mainColor);
        responseHeadersTable.setForeground(Colors.sendPanelColor);
        scrollPane.setPreferredSize(new Dimension(responseHeadersTable.getWidth(), responseHeadersTable.getRowCount() * 23));
        responseHeadersTable.setPreferredSize(new Dimension(responseHeadersTable.getWidth(), window.getHeight() - 19));
        responseHeadersTable.setRowHeight(25);
        responseHeadersTable.setEnabled(false);
        // adding
        responseHeaderPanel.add(scrollPane, BorderLayout.CENTER);
        responseHeaderPanel.add(copyToClipboardButton, BorderLayout.SOUTH);
    }

    /**
     * create copy button
     */
    public void createCopyButton() {
        copyToClipboardButton = new JButton("Copy to Clipboard");
        copyToClipboardButton.setOpaque(true);
        copyToClipboardButton.setPreferredSize(new Dimension(300, 30));
        copyToClipboardButton.setBackground(Colors.mainColor);
        copyToClipboardButton.setForeground(Colors.sendPanelColor);
        copyToClipboardButton.setBorder(BorderFactory.createLineBorder(Colors.sendPanelColor, 1, true));
    }

    /*
     ***These are Getter methods***
     */

    /**
     * get response panel
     *
     * @return responsePanel field
     */
    public JPanel getResponsePanel() {
        return responsePanel;
    }

    /**
     * get status message label
     *
     * @return statusMessage field
     */
    public JLabel getStatusMessage() {
        return statusMessage;
    }

    /**
     * get data size label
     *
     * @return dataSize field
     */
    public JLabel getDataSize() {
        return dataSize;
    }

    /**
     * get time passed label
     *
     * @return timePassed field
     */
    public JLabel getTimePassed() {
        return timePassed;
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
     * get header tab button
     *
     * @return headerTab field
     */
    public JButton getHeaderTab() {
        return headerTab;
    }

    /**
     * get response tab panel
     *
     * @return responseTabPanel field
     */
    public JPanel getResponseTabPanel() {
        return responseTabPanel;
    }

    /**
     * get raw panel
     *
     * @return rawPanel field
     */
    public JPanel getRawPanel() {
        return rawPanel;
    }

    /**
     * get preview panel
     *
     * @return previewPanel field
     */
    public JPanel getPreviewPanel() {
        return previewPanel;
    }

    /**
     * get header panel of response panel
     *
     * @return responseHeaderPanel field
     */
    public JPanel getResponseHeaderPanel() {
        return responseHeaderPanel;
    }

    /**
     * get textArea of raw panel
     *
     * @return rawText field
     */
    public JTextArea getRawText() {
        return rawText;
    }

    /**
     * get response header table
     *
     * @return responseHeadersTable field
     */
    public JTable getResponseHeadersTable() {
        return responseHeadersTable;
    }

    /**
     * get copy to clipboard button
     *
     * @return copyToClipboardButton field
     */
    public JButton getCopyToClipboardButton() {
        return copyToClipboardButton;
    }

    /**
     * get tabbed pane of response panel
     *
     * @return tabbedPane
     */
    public JPanel getTabbedPane() {
        return tabbedPane;
    }

    /**
     * get status Panel
     *
     * @return statusPanel
     */
    public JPanel getStatusPanel() {
        return statusPanel;
    }
}