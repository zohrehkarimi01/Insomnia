package gui;

import java.awt.*;
import java.io.Serializable;

/**
 * Setting class saves data from app
 *
 * @author ZK
 */
public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean systemTrayMode;
    private boolean toggleSideBarMode;
    private boolean toggleFullScreenMode;
    private boolean followRedirect;
    private Dimension size;
    private Point location;

    /**
     * create settings
     */
    public Settings() {
        systemTrayMode = false;
        toggleSideBarMode = false;
        toggleFullScreenMode = false;
        followRedirect = false;
        size = new Dimension(1096, 647);
        location = new Point(194, 55);
    }

    /**
     * @return system tray field
     */
    public boolean isSystemTrayMode() {
        return systemTrayMode;
    }

    /**
     * @return ToggleSideBar field
     */
    public boolean isToggleSideBarMode() {
        return toggleSideBarMode;
    }

    /**
     * @return ToggleFullScreen field
     */
    public boolean isToggleFullScreenMode() {
        return toggleFullScreenMode;
    }

    /**
     * @return size field
     */
    public Dimension getSize() {
        return size;
    }

    /**
     * @return location field
     */
    public Point getLocation() {
        return location;
    }


    /**
     * check if follow redirect is active or not
     *
     * @return followRedirect field
     */
    public boolean isFollowRedirect() {
        return followRedirect;
    }

    /**
     * set if program finishes or hides on system tray on close
     *
     * @param systemTrayMode systemTrayMode to be changed
     */
    public void setSystemTrayMode(boolean systemTrayMode) {
        this.systemTrayMode = systemTrayMode;
    }

    /**
     * set if side bar is shown or not
     *
     * @param toggleSideBarMode toggleSideBarMode to be changed
     */
    public void setToggleSideBarMode(boolean toggleSideBarMode) {
        this.toggleSideBarMode = toggleSideBarMode;
    }

    /**
     * set if window is on full screen mode
     *
     * @param toggleFullScreenMode toggleFullScreenMode to be changed
     */
    public void setToggleFullScreenMode(boolean toggleFullScreenMode) {
        this.toggleFullScreenMode = toggleFullScreenMode;
    }

    /**
     * set current size of main window
     *
     * @param size size to be changed
     */
    public void setSize(Dimension size) {
        this.size = size;
    }

    /**
     * set location of main window
     *
     * @param location location to be changed
     */
    public void setLocation(Point location) {
        this.location = location;
    }

    /**
     * set follow redirect
     *
     * @param followRedirect follow redirect field
     */
    public void setFollowRedirect(boolean followRedirect) {
        this.followRedirect = followRedirect;
    }
}
