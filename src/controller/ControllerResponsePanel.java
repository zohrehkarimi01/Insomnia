package controller;

import gui.Window;
import jurl.Printer;
import gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ControllerResponsePanel class implements logic for response panel
 *
 * @author ZK
 */
public class ControllerResponsePanel extends Controller {

    /**
     * create controller for response panel
     *
     * @param window           main frame
     * @param newRequest       new request frame
     * @param optionsWindow    option window
     * @param data             data of program
     * @param requestListPanel request Panel
     * @param mainPanel        the middle panel
     * @param responsePanel    response panel
     */
    public ControllerResponsePanel(Window window, NewRequestWindow newRequest, OptionsWindow optionsWindow, AppData data,
                                   RequestListPanel requestListPanel, MainPanel mainPanel, ResponsePanel responsePanel) {
        super(window, newRequest, optionsWindow, data, requestListPanel, mainPanel, responsePanel);
        setController();
    }

    /**
     * set listeners of response panel
     */
    private void setController() {
        connectTabToPanel(responsePanel.getHeaderTab(), responsePanel.getResponseHeaderPanel());
        connectComboBot();
        responsePanel.getCopyToClipboardButton().addActionListener(e -> copy());
    }

    /**
     * connect tab to panel
     *
     * @param button tab
     * @param panel  tab panel
     */
    private void connectTabToPanel(JButton button, JPanel panel) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (responsePanel.getResponseTabPanel().getComponents().length > 1)
                    responsePanel.getResponseTabPanel().remove(1);
                responsePanel.getResponseTabPanel().add(panel);
                window.getWindow().setSize(window.getWindow().getWidth() + 1, window.getWindow().getHeight() + 1);
                window.getWindow().setSize(window.getWindow().getWidth() - 1, window.getWindow().getHeight() - 1);
            }
        });
    }

    /**
     * connect combo box to panels
     */
    private void connectComboBot() {
        responsePanel.getBodyTab().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (responsePanel.getResponseTabPanel().getComponents().length > 1)
                    responsePanel.getResponseTabPanel().remove(1);
                String tab = (String) responsePanel.getBodyTab().getSelectedItem();
                if (tab.equals("Raw"))
                    responsePanel.getResponseTabPanel().add(responsePanel.getRawPanel(), BorderLayout.CENTER);
                else if (tab.equals("Preview"))
                    responsePanel.getResponseTabPanel().add(responsePanel.getPreviewPanel(), BorderLayout.CENTER);

                window.getWindow().setSize(window.getWindow().getWidth() + 1, window.getWindow().getHeight() + 1);
                window.getWindow().setSize(window.getWindow().getWidth() - 1, window.getWindow().getHeight() - 1);
            }
        });
    }

    /**
     * copy text of header panel
     */
    private void copy() {
        if (data.getRequestInCharge().getResponseHeaders() == null)
            return;
        StringSelection stringSelection = new StringSelection(Printer.getResponseHeadersStr(data.getRequestInCharge().getResponseHeaders()));
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
