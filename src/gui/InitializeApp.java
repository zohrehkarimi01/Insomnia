package gui;

import controller.*;

/**
 * initialize insomnia app
 *
 * @author ZK
 */
public class InitializeApp {

    /**
     * create all app view and controllers
     */
    public InitializeApp() {
        Colors colors = new Colors();
        // model class
        AppData data = new AppData();
        // user interface creator classes
        Window window = new Window();
        RequestListPanel requestListPanel = new RequestListPanel(window.getWindow());
        MainPanel mainPanel = new MainPanel(window.getWindow());
        ResponsePanel responsePanel = new ResponsePanel(window.getWindow());
        OptionsWindow options = new OptionsWindow(window.getWindow());
        NewRequestWindow newRequest = new NewRequestWindow(window.getWindow());
        // logic classes
        ControllerWindow controllerWindow = new ControllerWindow(window, newRequest, options, data, requestListPanel, mainPanel, responsePanel);
        ControllerRequestPanel controllerRequestPanel = new ControllerRequestPanel(window, newRequest, options, data, requestListPanel, mainPanel, responsePanel);
        ControllerRequestWindow controllerRequestWindow = new ControllerRequestWindow(window, newRequest, options, data, requestListPanel, mainPanel, responsePanel);
        ControllerMainPanel controllerMainPanel = new ControllerMainPanel(window, newRequest, options, data, requestListPanel, mainPanel, responsePanel);
        ControllerResponsePanel controllerResponsePanel = new ControllerResponsePanel(window, newRequest, options, data, requestListPanel, mainPanel, responsePanel);
        ControllerOptions controllerOptions = new ControllerOptions(window, newRequest, options, data, requestListPanel, mainPanel, responsePanel);
        // display
        window.getWindow().setVisible(true);
    }

}
