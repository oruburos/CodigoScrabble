package scrabbleMVC.view;
 

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.beans.*; //property change stuff
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

/* 1.4 example used by DialogDemo.java. */
public class CustomDialog extends JDialog
                   implements ActionListener,
                              PropertyChangeListener {
    private String typedText = null;
    private JTextField textField;
    private JPanel dd;

    private JOptionPane optionPane;

    private String btnString1 = "Aceptar";
    private String btnString2 = "Cancelar";

    /**
     * Returns null if the typed string was invalid;
     * otherwise, returns the string as the user entered it.
     */
    public String getValidatedText() {
        return typedText;
    }

    /** Creates the reusable dialog. */
    public CustomDialog(Frame aFrame, JPanel parent) {
        super(aFrame, true);
        dd = parent;

        setTitle("Decide Comodin");

        textField = new JTextField(10);

        //Create an array of the text and components to be displayed.
        String msgString1 = "La pieza es el comodin, define que letra es?";
        String msgString2 = "(Letra para todo el juego)";
        Object[] array = {msgString1, msgString2, textField};

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {btnString1, btnString2};

        //Create the JOptionPane.
        optionPane = new JOptionPane(array,
                                    JOptionPane.QUESTION_MESSAGE,
                                    JOptionPane.YES_NO_OPTION,
                                    null,
                                    options,
                                    options[0]);

        //Make this dialog display it.
        setContentPane(optionPane);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                    optionPane.setValue(new Integer(
                                        JOptionPane.CLOSED_OPTION));
            }
        });

        //Ensure the text field always gets the first focus.
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                textField.requestFocusInWindow();
            }
        });

        //Register an event handler that puts the text into the option pane.
        textField.addActionListener(this);

        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
    }

    /** This method handles events for the text field. */
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(btnString1);
    }

    
    private boolean validate( String newPiece){
    if ( newPiece.length() !=1 ){
        return false;
    }
    if ( Character.isLetter( newPiece.charAt(0) )){
        System.out.println(" letra valda " + newPiece);
        return true;
    }
    return false;
    }
    /** This method reacts to state changes in the option pane. */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible()
         && (e.getSource() == optionPane)
         && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
             JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }

            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
            optionPane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);

            if (btnString1.equals(value)) {
                    typedText = textField.getText();
                String ucText = typedText.toUpperCase();
                if (validate(ucText)) {
                    //we're done; clear and dismiss the dialog
                    clearAndHide();
                } else {
                    //text was invalid
                    textField.selectAll();
                    JOptionPane.showMessageDialog(
                                    CustomDialog.this,
                                    "\"" + typedText + "\" "
                                    + " no es una pieza de Scrabble\n"
                                    ,
                                    "Otra vez",
                                    JOptionPane.ERROR_MESSAGE);
                    typedText = null;
                    textField.requestFocusInWindow();
                }
            } else { //user closed dialog or clicked cancel
            /*   JPanel.setLabel("It's OK.  "
                         + "We won't force you to type "
                        + magicWord + ".");
              */  typedText = null;
                //clearAndHide();
            }
        }
    }

    /** This method clears the dialog and hides it. */
    public void clearAndHide() {
        textField.setText(null);
        setVisible(false);
    }
}