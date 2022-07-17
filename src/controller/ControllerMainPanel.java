package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gui.Window;
import jurl.HTTPClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import gui.*;

/**
 * ControllerMainPanel class implements logic of main panel
 *
 * @author ZK
 */
public class ControllerMainPanel extends Controller {

    /**
     * create controllerMainPanel
     *
     * @param window           main frame
     * @param newRequest       new request frame
     * @param optionsWindow    option window
     * @param data             data of program
     * @param requestListPanel request Panel
     * @param mainPanel        the middle panel
     * @param responsePanel    response panel
     */
    public ControllerMainPanel(Window window, NewRequestWindow newRequest, OptionsWindow optionsWindow, AppData data,
                               RequestListPanel requestListPanel, MainPanel mainPanel, ResponsePanel responsePanel) {
        super(window, newRequest, optionsWindow, data, requestListPanel, mainPanel, responsePanel);
        setController();
    }

    /**
     * set all Listeners in main panel
     */
    private void setController() {
        connectTabToPanel(mainPanel.getHeaderTab(), mainPanel.getHeaderPanel());
        connectComboBot();
        mainPanel.getBeautifyJSON().addActionListener(e -> beautify());
        mainPanel.getSendButton().addActionListener(e -> setSendBt());
        mainPanel.getMethodChooser().addActionListener(e -> methodChooserController());

        // form panel
        for (Component component : mainPanel.getNewFormCreator().getComponents()) {
            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    Form form = new Form();
                    setFormPanelController(form);
                }
            });
        }
        // headers panel
        for (Component component : mainPanel.getNewHeaderCreator().getComponents()) {
            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    Header header = new Header();
                    setHeaderPanelController(header);
                }
            });
        }
    }

    /**
     * connect button to panel
     *
     * @param button tab
     * @param panel  panel tab
     */
    private void connectTabToPanel(JButton button, JPanel panel) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPanel.getTabPanel().getComponents().length > 1)
                    mainPanel.getTabPanel().remove(1);
                mainPanel.getTabPanel().add(panel);
                window.getWindow().setSize(window.getWindow().getWidth() + 1, window.getWindow().getHeight() + 1);
                window.getWindow().setSize(window.getWindow().getWidth() - 1, window.getWindow().getHeight() - 1);
            }
        });
    }

    /**
     * connecting combo box to panels
     */
    private void connectComboBot() {
        mainPanel.getBodyTab().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPanel.getTabPanel().getComponents().length > 1)
                    mainPanel.getTabPanel().remove(1);
                String tab = (String) mainPanel.getBodyTab().getSelectedItem();
                if (tab.equals("Body"))
                    mainPanel.getTabPanel().add(mainPanel.getBodyPanel(), BorderLayout.CENTER);
                else if (tab.equals("Form"))
                    mainPanel.getTabPanel().add(mainPanel.getFormPanel(), BorderLayout.CENTER);
                else if (tab.equals("JSON"))
                    mainPanel.getTabPanel().add(mainPanel.getJsonPanel(), BorderLayout.CENTER);

                window.getWindow().setSize(window.getWindow().getWidth() + 1, window.getWindow().getHeight() + 1);
                window.getWindow().setSize(window.getWindow().getWidth() - 1, window.getWindow().getHeight() - 1);
            }
        });
    }

    /**
     * set method chooser controller
     */
    public void methodChooserController() {
        String method = (String) mainPanel.getMethodChooser().getSelectedItem();
        data.getRequestInCharge().setMethod(method);
    }

    /**
     * Connection class sets and sends request and update user interface
     */
    private class Connection implements Runnable {

        /**
         * send thread
         */
        @Override
        public void run() {
            String body = (String) mainPanel.getBodyTab().getSelectedItem();
            if (body.equals("Body")) {
                // form data
                while (mainPanel.getFormPanel().getComponents().length > 1) {
                    mainPanel.getFormPanel().remove(mainPanel.getFormPanel().getComponents().length - 1);
                }
                data.getRequestInCharge().setFormBody(new HashMap<>());
                // json
                mainPanel.getJsonText().setText("");
                data.getRequestInCharge().setJsonBody("");
            } else if (body.equals("Form")) {
                mainPanel.getJsonText().setText("");
                data.getRequestInCharge().setJsonBody("");
            } else if (body.equals("JSON")) {
                while (mainPanel.getFormPanel().getComponents().length > 1) {
                    mainPanel.getFormPanel().remove(mainPanel.getFormPanel().getComponents().length - 1);
                }
                data.getRequestInCharge().setFormBody(new HashMap<>());
                data.getRequestInCharge().setJsonBody(mainPanel.getJsonText().getText());
            }
            String url = mainPanel.getUrlAddress().getText();
            if (!(url.startsWith("http://")))
                url = "http://" + url;
            data.getRequestInCharge().setUrl(url);
            mainPanel.getUrlAddress().setText(url);
            HTTPClient client = new HTTPClient(data.getRequestInCharge(), false);
            Handler handler = new Handler();
            handler.run();
        }
    }

    /**
     * connecting send button
     */
    private void setSendBt() {
        Thread thread = new Thread(new Connection());
        thread.start();
    }

    /**
     * beautify JSON button
     */
    private void beautify() {
        String text = mainPanel.getJsonText().getText();
        if (text == null || text.trim().length() == 0)
            return;
        try {
            // here toPrettyFormat method is used
            String pretty = toPrettyFormat(text);
            mainPanel.getJsonText().setText(pretty);
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    /**
     * Convert a JSON string to pretty print version
     *
     * @param jsonString the json string that we want to convert
     * @return prettyJson
     */
    private String toPrettyFormat(String jsonString) {
        JsonParser parser = new JsonParser();
        // this is where I run into exception
        JsonObject json = parser.parse(jsonString).getAsJsonObject(); // getAsJsonObject() returns JsonObject

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}