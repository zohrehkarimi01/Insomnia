package gui;

import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.*;

/**
 * Form class represents a form data
 *
 * @author ZK
 */
public class Form {

    private JPanel newForm;
    private JTextField name;
    private JTextField value;
    private JCheckBox active;
    private JButton delete;

    /**
     * create a form
     */
    public Form() {
        setNewForm();
        setName();
        setValue();
        setActive();
        setDelete();

        setProperties();
    }

    /**
     * set form properties
     */
    private void setProperties() {
        newForm.add(name);
        newForm.add(value);
        newForm.add(active);
        newForm.add(delete);
    }

    /**
     * create new form panel
     */
    private void setNewForm() {
        newForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        newForm.setMaximumSize(new Dimension(478, 40));
        newForm.setBackground(Colors.mainColor);
        newForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        newForm.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    /**
     * create name field
     */
    private void setName() {
        name = new JTextField();
        PromptSupport.setPrompt("name", name);
        name.setBackground(Colors.mainColor);
        name.setForeground(Colors.sendPanelColor);
        name.setPreferredSize(new Dimension(180, 30));
        name.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.lightHideColor));
    }

    /**
     * create value filed
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
     * get new form panel
     *
     * @return newForm field
     */
    public JPanel getNewForm() {
        return newForm;
    }

    /**
     * get name text field
     *
     * @return name field
     */
    public JTextField getName() {
        return name;
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
     * to set a new form
     *
     * @param name   name of form data
     * @param value  value
     * @param active activation of form data
     */
    public void setForm(String name, String value, boolean active) {
        this.name.setText(name);
        this.value.setText(value);
        this.active.setSelected(active);
    }
}
