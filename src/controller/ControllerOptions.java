package controller;

import jurl.Request;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.*;

/**
 * ControllerOptions class implements logic for options window
 *
 * @author ZK
 */
public class ControllerOptions extends Controller {

    /**
     * create controller for options window
     * @param window main frame
     * @param newRequest new request frame
     * @param optionsWindow option window
     * @param data data of program
     * @param requestListPanel request Panel
     * @param mainPanel the middle panel
     * @param responsePanel response panel
     */
    public ControllerOptions(Window window, NewRequestWindow newRequest, OptionsWindow optionsWindow, AppData data,
                             RequestListPanel requestListPanel, MainPanel mainPanel, ResponsePanel responsePanel) {
        super(window, newRequest, optionsWindow, data, requestListPanel, mainPanel, responsePanel);
        setWindow();
        setController();
    }

    /**
     * set window properties according to app data
     */
    public  void setWindow(){
        optionsWindow.getFollowRedirect().setSelected(data.getSettings().isFollowRedirect());
        setFollowRedirect();
        optionsWindow.getSystemTray().setSelected(data.getSettings().isSystemTrayMode());
    }

    /**
     * set Listeners in options window
     */
    public void setController(){
        // follow redirect controller
        optionsWindow.getFollowRedirect().addActionListener(e -> setFollowRedirect());
        // system tray
        optionsWindow.getSystemTray().addActionListener(e -> setSystemTrayMode());
        // theme
        optionsWindow.getThemes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // mainPanel.getHeaderPanel().setBackground(Colors.mainColor);
                // window.getWindow().setSize(window.getWindow().getWidth() + 1, window.getWindow().getHeight() + 1);
                // window.getWindow().setSize(window.getWindow().getWidth() - 1, window.getWindow().getHeight() - 1);
            }
        });
    }

    /**
     * follow redirect controller
     */
    public void setFollowRedirect(){
        boolean follow;
        if (optionsWindow.getFollowRedirect().isSelected()){
            follow = true;
        }else {
            follow = false;
        }
        data.getSettings().setFollowRedirect(follow);
        for (Request request : data.getRequestList().values()){
            request.setFollowRedirect(follow);
        }
    }

    /**
     * system tray mode controller
     */
    public void setSystemTrayMode(){
        if (optionsWindow.getSystemTray().isSelected())
            data.getSettings().setSystemTrayMode(true);
        else
            data.getSettings().setSystemTrayMode(false);
    }
}