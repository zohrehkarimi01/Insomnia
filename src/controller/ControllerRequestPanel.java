package controller;

import gui.*;
import gui.Window;
import jurl.FileUtils;

/**
 * ControllerRequestPanel class implements logic for request panel
 *
 * @author ZK
 */
public class ControllerRequestPanel extends Controller {

    /**
     * create controller for request panel
     *
     * @param window           main frame
     * @param newRequest       new request frame
     * @param optionsWindow    option window
     * @param data             data of program
     * @param requestListPanel request Panel
     * @param mainPanel        the middle panel
     * @param responsePanel    response panel
     */
    public ControllerRequestPanel(Window window, NewRequestWindow newRequest, OptionsWindow optionsWindow, AppData data,
                                  RequestListPanel requestListPanel, MainPanel mainPanel, ResponsePanel responsePanel) {
        super(window, newRequest, optionsWindow, data, requestListPanel, mainPanel, responsePanel);
        setController();
    }

    /**
     * set listeners for request list panel
     */
    private void setController() {
        requestListPanel.getNewRequestButton().addActionListener(e -> createNewRequest());
        requestListPanel.getRequestList().addListSelectionListener(e -> selectRequest());
        requestListPanel.getSaveRequestButton().addActionListener(e -> saveRequest());
        requestListPanel.getDeleteRequestButton().addActionListener(e -> deleteRequest());
    }

    /**
     * controller for selecting request from list
     */
    public void selectRequest() {
        Thread thread = new Thread(new Handler());
        thread.start();
    }

    /**
     * create a new request
     */
    private void createNewRequest() {
        newRequest.getNewRequestWindow().setLocationRelativeTo(window.getWindow());
        newRequest.getNewRequestWindow().setLocation(((window.getWindow().getWidth() - newRequest.getNewRequestWindow().getWidth()) / 2) + window.getWindow().getX(),
                ((window.getWindow().getHeight() - newRequest.getNewRequestWindow().getHeight()) / 2) + window.getWindow().getY());
        newRequest.getNewRequestWindow().setVisible(true);
    }

    /**
     * saving a request controller
     */
    private void saveRequest() {
        Thread save = new Thread(new SaveOrDeleteRequest(1));
        save.start();
    }

    /**
     * deleting a request controller
     */
    private void deleteRequest() {
        Thread delete = new Thread(new SaveOrDeleteRequest(2));
        delete.start();
    }

    /**
     * SaveOrDeleteRequest class handles save and delete action
     */
    private class SaveOrDeleteRequest implements Runnable {

        int action;

        /**
         * create object of SaveOrDeleteRequest
         *
         * @param action specifies action
         */
        private SaveOrDeleteRequest(int action) {
            this.action = action;
        }

        /**
         * start thread
         */
        @Override
        public void run() {
            if (action == 1) {
                if (data.getRequestInCharge() != null)
                    FileUtils.saveRequest(data.getRequestInCharge());
            } else {
                if (data.getRequestInCharge() != null) {
                    String name = "     " + data.getRequestInCharge().getRequestName();
                    FileUtils.deleteRequest(data.getRequestInCharge().getRequestName());
                    data.getRequestList().remove(name);
                    data.setRequestInCharge(null);
                    responsePanel.setRawPanel("");
                    requestListPanel.getModel().removeElement(name);
                }
            }
        }
    }
}
