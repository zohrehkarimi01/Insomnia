package gui;

import java.awt.*;

/**
 * Colors class keeps all the colors used in app view
 *
 * @author ZK
 */
public class Colors {

    public static Color requestPanelColor;
    public static Color mainColor;
    public static Color insomniaColor;
    public static Color heavyInsomniaColor;
    public static Color sendPanelColor;
    public static Color responseLabelsBack;
    public static Color responseLabelsFore;
    public static Color lightHideColor;
    public static Color statusCodeOK;
    public static Color statusCodeError;
    public static Color statusCodeBad;
    public static Color requestListFore;
    public static Color requestListSelected;

    /**
     * create color class and set color variables with default values
     */
    public Colors() {
        defaultTheme();
    }

    /**
     * set default colors
     */
    public static void defaultTheme() {
        requestPanelColor = new Color(46, 47, 43);
        requestListSelected = new Color(54, 55, 52);
        requestListFore = new Color(140, 140, 140);
        mainColor = new Color(40, 41, 37);
        insomniaColor = new Color(107, 97, 178);
        heavyInsomniaColor = new Color(73, 66, 122);
        sendPanelColor = new Color(250, 250, 250);
        responseLabelsBack = new Color(224, 224, 224);
        responseLabelsFore = new Color(140, 140, 140);
        lightHideColor = new Color(71, 72, 68);
        statusCodeOK = new Color(116, 187, 38);
        statusCodeBad = new Color(236, 135, 2);
        statusCodeError = new Color(225, 82, 81);
    }
}