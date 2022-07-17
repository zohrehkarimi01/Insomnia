package controller;

import gui.Window;
import jurl.FileUtils;
import jurl.Printer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import gui.*;

/**
 * Controller class is super class for all controllers
 *
 * @author ZK
 */
public abstract class Controller {
    protected Window window;
    protected NewRequestWindow newRequest;
    protected OptionsWindow optionsWindow;
    protected AppData data;
    protected RequestListPanel requestListPanel;
    protected MainPanel mainPanel;
    protected ResponsePanel responsePanel;

    /**
     * create controller
     *
     * @param window           main frame
     * @param newRequest       new request frame
     * @param optionsWindow    option window
     * @param data             data of program
     * @param requestListPanel request Panel
     * @param mainPanel        the middle panel
     * @param responsePanel    response panel
     */
    public Controller(Window window, NewRequestWindow newRequest, OptionsWindow optionsWindow, AppData data,
                      RequestListPanel requestListPanel, MainPanel mainPanel, ResponsePanel responsePanel) {
        this.window = window;
        this.newRequest = newRequest;
        this.optionsWindow = optionsWindow;
        this.data = data;
        this.requestListPanel = requestListPanel;
        this.mainPanel = mainPanel;
        this.responsePanel = responsePanel;
    }

    /**
     * set form controller
     *
     * @param form new form
     */
    public void setFormPanelController(Form form) {
        // delete button
        form.getDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // delete from panel
                mainPanel.getFormPanel().remove(form.getNewForm());
                // update request
                computeForms();
                window.getWindow().setSize(window.getWindow().getWidth() + 1, window.getWindow().getHeight() + 1);
                window.getWindow().setSize(window.getWindow().getWidth() - 1, window.getWindow().getHeight() - 1);
            }
        });
        // activation check box
        form.getActive().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computeForms();
            }
        });
        // text fields name and value
        JTextField[] formFields = {form.getName(), form.getValue()};
        for (JTextField formField : formFields) {
            formField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    computeForms();
                }
            });
        }
        mainPanel.toCreateNewForm(form.getNewForm());
        window.getWindow().setSize(window.getWindow().getWidth() + 1, window.getWindow().getHeight() + 1);
        window.getWindow().setSize(window.getWindow().getWidth() - 1, window.getWindow().getHeight() - 1);
    }

    /**
     * set header controller
     *
     * @param header new header
     */
    public void setHeaderPanelController(Header header) {
        // delete button
        header.getDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.getHeaderPanel().remove(header.getNewHeader());
                computeHeaders();
                window.getWindow().setSize(window.getWindow().getWidth() + 1, window.getWindow().getHeight() + 1);
                window.getWindow().setSize(window.getWindow().getWidth() - 1, window.getWindow().getHeight() - 1);
            }
        });
        // activation check box
        header.getActive().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computeHeaders();
            }
        });
        // text fields name and value
        JTextField[] headerFields = {header.getHeader(), header.getValue()};
        for (JTextField headerField : headerFields) {
            headerField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    computeHeaders();
                }
            });
        }
        mainPanel.toCreateNewHeader(header.getNewHeader());
        window.getWindow().setSize(window.getWindow().getWidth() + 1, window.getWindow().getHeight() + 1);
        window.getWindow().setSize(window.getWindow().getWidth() - 1, window.getWindow().getHeight() - 1);
    }

    /**
     * update headers of request
     */
    private void computeHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> noneActiveHeaders = new HashMap<>();
        for (Component component1 : mainPanel.getHeaderPanel().getComponents()) {
            JPanel headerPanel = (JPanel) component1;
            if (headerPanel == mainPanel.getNewHeaderCreator())
                continue;
            if (((JCheckBox) (headerPanel.getComponent(2))).isSelected()) {
                headers.put(((JTextField) headerPanel.getComponent(0)).getText(), ((JTextField) headerPanel.getComponent(1)).getText());
            } else {
                noneActiveHeaders.put(((JTextField) headerPanel.getComponent(0)).getText(), ((JTextField) headerPanel.getComponent(1)).getText());
            }
        }
        data.getRequestInCharge().setHeaders(headers);
        data.getRequestInCharge().setNoneActiveHeaders(noneActiveHeaders);
    }

    /**
     * update form body of request
     */
    private void computeForms() {
        HashMap<String, String> formData = new HashMap<>();
        HashMap<String, String> noneActiveFormData = new HashMap<>();
        for (Component component1 : mainPanel.getFormPanel().getComponents()) {
            JPanel formPanel = (JPanel) component1;
            if (formPanel == mainPanel.getNewFormCreator())
                continue;
            if (((JCheckBox) (formPanel.getComponent(2))).isSelected()) {
                formData.put(((JTextField) formPanel.getComponent(0)).getText(), ((JTextField) formPanel.getComponent(1)).getText());
            } else {
                noneActiveFormData.put(((JTextField) formPanel.getComponent(0)).getText(), ((JTextField) formPanel.getComponent(1)).getText());
            }
        }
        data.getRequestInCharge().setFormBody(formData);
        data.getRequestInCharge().setNoneActiveFormBody(noneActiveFormData);
    }

    /**
     * Handler class updates response panel
     */
    public class Handler implements Runnable {

        /**
         * run method is used in thread
         */
        @Override
        public void run() {
            // add main panel stuff
            if (mainPanel.getTabPanel().getComponents().length == 0)
                mainPanel.getTabPanel().add(mainPanel.getTabbedPane(), BorderLayout.NORTH);
            if (mainPanel.getMainHeader().getComponents().length != 3) {
                mainPanel.getMainHeader().add(mainPanel.getMethodChooser(), BorderLayout.WEST);
                mainPanel.getMainHeader().add(mainPanel.getUrlAddress(), BorderLayout.CENTER);
                mainPanel.getMainHeader().add(mainPanel.getSendButton(), BorderLayout.EAST);
            }
            // set request in charge
            data.setRequestInCharge(data.getRequestList().get(requestListPanel.getRequestList().getSelectedValue()));
            setMain();
            setResponse();
            window.getWindow().setSize(window.getWindow().getWidth() + 1, window.getWindow().getHeight() + 1);
            window.getWindow().setSize(window.getWindow().getWidth() - 1, window.getWindow().getHeight() - 1);
        }

        /**
         * update main panel
         */
        private void setMain() {
            if (data.getRequestInCharge() == null) {
                while (mainPanel.getTabPanel().getComponents().length != 0) {
                    mainPanel.getTabPanel().remove(0);
                }
                while (mainPanel.getMainHeader().getComponents().length != 0) {
                    mainPanel.getMainHeader().remove(0);
                }
                return;
            }
            // set url
            mainPanel.getUrlAddress().setText(data.getRequestInCharge().getUrl());
            // set method chooser
            mainPanel.getMethodChooser().setSelectedItem(data.getRequestInCharge().getMethod());
            // set form body
            while (mainPanel.getFormPanel().getComponents().length > 1) {
                mainPanel.getFormPanel().remove(0);
            }
            if (data.getRequestInCharge().getFormBody() != null && data.getRequestInCharge().getFormBody().size() != 0) {
                for (String key : data.getRequestInCharge().getFormBody().keySet()) {
                    Form form = new Form();
                    form.setForm(key, data.getRequestInCharge().getFormBody().get(key), true);
                    setFormPanelController(form);
                }
            }
            if (data.getRequestInCharge().getNoneActiveFormBody() != null && data.getRequestInCharge().getNoneActiveFormBody().size() != 0) {
                for (String key : data.getRequestInCharge().getNoneActiveFormBody().keySet()) {
                    Form form = new Form();
                    form.setForm(key, data.getRequestInCharge().getNoneActiveFormBody().get(key), false);
                    setFormPanelController(form);
                }
            }
            // set header panel
            while (mainPanel.getHeaderPanel().getComponents().length > 1) {
                mainPanel.getHeaderPanel().remove(0);
            }
            if (data.getRequestInCharge().getHeaders() != null && data.getRequestInCharge().getHeaders().size() != 0) {
                for (String key : data.getRequestInCharge().getHeaders().keySet()) {
                    Header header = new Header();
                    header.setHeader(key, data.getRequestInCharge().getHeaders().get(key), true);
                    setHeaderPanelController(header);
                }
            }
            if (data.getRequestInCharge().getNoneActiveHeaders() != null && data.getRequestInCharge().getNoneActiveHeaders().size() != 0) {
                for (String key : data.getRequestInCharge().getNoneActiveHeaders().keySet()) {
                    Header header = new Header();
                    header.setHeader(key, data.getRequestInCharge().getNoneActiveHeaders().get(key), false);
                    setHeaderPanelController(header);
                }
            }
            // set json body
            mainPanel.getJsonText().setText("");
            if (data.getRequestInCharge().getJsonBody() != null && data.getRequestInCharge().getJsonBody().length() != 0) {
                mainPanel.getJsonText().setText(data.getRequestInCharge().getJsonBody());
            }
        }

        /**
         * update response panel
         */
        private void setResponse() {
            while (responsePanel.getStatusPanel().getComponents().length != 0) {
                responsePanel.getStatusPanel().remove(0);
            }
            while (responsePanel.getResponseTabPanel().getComponents().length != 0) {
                responsePanel.getResponseTabPanel().remove(0);
            }
            while (responsePanel.getPreviewPanel().getComponents().length != 0) {
                responsePanel.getPreviewPanel().remove(0);
            }
            while (responsePanel.getResponseHeaderPanel().getComponents().length != 0) {
                responsePanel.getResponseHeaderPanel().remove(0);
            }
            if (data.getRequestInCharge() == null)
                return;
            if (data.getRequestInCharge().getResponseMessage().length() == 0)
                return;
            // set status part of response panel
            responsePanel.addStatusProperties(data.getRequestInCharge().getResponseMessage(),
                    data.getRequestInCharge().getTimePassed(), data.getRequestInCharge().getDataSize());
            responsePanel.getStatusPanel().add(responsePanel.getStatusMessage());
            responsePanel.getStatusPanel().add(responsePanel.getTimePassed());
            responsePanel.getStatusPanel().add(responsePanel.getDataSize());
            // add tabbed pane
            responsePanel.getResponseTabPanel().add(responsePanel.getTabbedPane(), BorderLayout.NORTH);
            // set response headers
            if (data.getRequestInCharge().getResponseBody() != null) {
                responsePanel.setRawPanel(Printer.setLines(new String(data.getRequestInCharge().getResponseBody()), 37));
                // Preview panel
                while (responsePanel.getPreviewPanel().getComponents().length != 0)
                    responsePanel.getPreviewPanel().remove(0);
                if (data.getRequestInCharge().getOutputName() == null || data.getRequestInCharge().getOutputName().length() == 0) {
                    data.getRequestInCharge().setOutputName("output_" + data.getRequestInCharge().getRequestName());
                }
                if (data.getRequestInCharge().getResponseHeaders() != null && data.getRequestInCharge().getResponseHeaders().get("Content-Type") != null) {
                    if (data.getRequestInCharge().getResponseHeaders().get("Content-Type").get(0).contains("image/png")) {
                        FileUtils.saveResponse(data.getRequestInCharge());
                        responsePanel.addImagePreview("responses/" + data.getRequestInCharge().getOutputName() + ".png");
                    } else if (data.getRequestInCharge().getResponseHeaders().get("Content-Type").get(0).contains("application/json")) {
                        responsePanel.addJsonPreview(new String(data.getRequestInCharge().getResponseBody()));
                    }
                }
            }
            if (data.getRequestInCharge().getResponseHeaders() != null)
                responsePanel.addResponseHeaders(data.getRequestInCharge().getResponseHeaders());
        }
    }
}
