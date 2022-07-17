package gui;

import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.*;

/**
 * Header class represents a header
 *
 * @author ZK
 */
public class Header {

    private JPanel newHeader;
    private JTextField header;
    private JTextField value;
    private JCheckBox active;
    private JButton delete;

    /**
     * create a header
     */
    public Header() {
        setNewHeader();
        setHeader();
        setValue();
        setActive();
        setDelete();

        setProperties();
    }

    /**
     * set properties of header
     */
    private void setProperties() {
        newHeader.add(header);
        newHeader.add(value);
        newHeader.add(active);
        newHeader.add(delete);
    }

    /**
     * create new header panel
     */
    private void setNewHeader() {
        newHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        newHeader.setMaximumSize(new Dimension(478, 40));
        newHeader.setBackground(Colors.mainColor);
        newHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        newHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    /**
     * create header text field
     */
    private void setHeader() {
        header = new JTextField();
        PromptSupport.setPrompt("header", header);
        header.setBackground(Colors.mainColor);
        header.setForeground(Colors.sendPanelColor);
        header.setPreferredSize(new Dimension(180, 30));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.lightHideColor));
    }

    /**
     * create value text field
     */
    private void setValue() {
        value = new JTextField();
        PromptSupport.setPrompt("value", value);
        value.setBackground(Colors.mainColor);
        value.setForeground(Colors.sendPanelColor);
        value.setPreferredSize(new Dimension(180, 30));
        value.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.lightHideColor));
    }

    /**
     * create check box activation field
     */
    private void setActive() {
        active = new JCheckBox();
        active.setBackground(Colors.mainColor);
        active.setSelected(true);
    }

    /**
     * create delete button field
     */
    private void setDelete() {
        delete = new JButton("Del");
        delete.setBackground(Colors.insomniaColor);
        delete.setForeground(Colors.sendPanelColor);
        delete.setPreferredSize(new Dimension(40, 30));
        delete.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Colors.heavyInsomniaColor));
    }

    /**
     * get new header panel
     *
     * @return newHeader field
     */
    public JPanel getNewHeader() {
        return newHeader;
    }

    /**
     * get header text field
     *
     * @return header field
     */
    public JTextField getHeader() {
        return header;
    }

    /**
     * get value text field
     *
     * @return value field
     */
    public JTextField getValue() {
        return value;
    }

    /**
     * get activation check box
     *
     * @return active field
     */
    public JCheckBox getActive() {
        return active;
    }

    /**
     * get delete button field
     *
     * @return delete field
     */
    public JButton getDelete() {
        return delete;
    }

    /**
     * to set a new header
     *
     * @param header header
     * @param value  value
     * @param active activation of form data
     */
    public void setHeader(String header, String value, boolean active) {
        this.header.setText(header);
        this.value.setText(value);
        this.active.setSelected(active);
    }
}
