package controller;

import jurl.Request;
import gui.*;

import java.awt.event.WindowEvent;

/**
 * ControllerRequestWindow class implements logic for new request window
 *
 * @author ZK
 */
public class ControllerRequestWindow extends Controller {

    /**
     * create controller for request window
     *
     * @param window           main frame
     * @param newRequest       new request frame
     * @param optionsWindow    option window
     * @param data             data of program
     * @param requestListPanel request Panel
     * @param mainPanel        the middle panel
     * @param responsePanel    response panel
     */
    public ControllerRequestWindow(Window window, NewRequestWindow newRequest, OptionsWindow optionsWindow, AppData data,
                                   RequestListPanel requestListPanel, MainPanel mainPanel, ResponsePanel responsePanel) {
        super(window, newRequest, optionsWindow, data, requestListPanel, mainPanel, responsePanel);
        setController();
    }

    /**
     * set listeners of new request window
     */
    private void setController() {
        newRequest.getCreateButton().addActionListener(e -> createBtActionListener());
    }

    /**
     * create listener for create button
     */
    private void createBtActionListener() {
        String method = (String) (newRequest.getChooseMethod().getSelectedItem());
        requestListPanel.getModel().addElement("     " + newRequest.getRequestName().getText());
        Request request = new Request("");
        request.setMethod(method);
        request.setFollowRedirect(data.getSettings().isFollowRedirect());
        request.setRequestName(newRequest.getRequestName().getText());
        data.getRequestList().put("     " + newRequest.getRequestName().getText(), request);
        newRequest.getRequestName().setText("My Request");
        newRequest.getChooseMethod().setSelectedItem("GET");
        newRequest.getNewRequestWindow().dispatchEvent(new WindowEvent(newRequest.getNewRequestWindow(), WindowEvent.WINDOW_CLOSING));
    }

}
