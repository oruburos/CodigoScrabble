/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabbleMVC.view;

import scrabbleMVC.model.ScrabbleCell;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import scrabbleMVC.controller.SimpleController;

/**
 *
 * @author Omar Verduga Palencia
 */
public class SimpleScrabbleView extends JPanel {

    private ScrabbleCell fondoTablero;
    private SimpleController controller;

    private JFrame frame;

    public JLabel lblScore;
    public JLabel lblNombre;

    public JLabel lblScore2;
    public JLabel lblScore3;
    public JLabel lblScore4;

    private boolean controllerAsignado;

    /**
     * Constructor para generar una vista simple
     *
     * @param frame
     */
    public SimpleScrabbleView(JFrame frame) {
        super();
        frame = frame;
        controllerAsignado = false;
        setBackground(Color.black);
        setLayout(null);
        setOpaque(false);
        fondoTablero = new ScrabbleCell("resources/tablero.jpg", "null", 0, 0, 0);

        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(350, 530, 200, 50);
        add(lblNombre);

        lblScore = new JLabel("");
        lblScore.setBounds(50, 550, 200, 50);
        add(lblScore);
        lblScore2 = new JLabel("");
        lblScore2.setBounds(350, 550, 200, 50);
        add(lblScore2);
        lblScore3 = new JLabel("");
        lblScore3.setBounds(50, 650, 200, 50);
        add(lblScore3);
        lblScore4 = new JLabel("");
        lblScore4.setBounds(350, 650, 200, 50);
        add(lblScore4);

    }

    public void paintComponent(Graphics g) {
        //Dibujar el fondo del formulario 
        super.paintComponents(g);

        //Dibujar el tablero
        try {
            g.drawImage(fondoTablero.getImage(), 0, 0, 526, 526, this);

        } catch (Exception e) {

            System.out.println("error fondo tablero");
        }

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
              //  System.out.println("controller null" + controller==null);
                // System.out.println("tablero null" + controller.getTablero()==null);
                ScrabbleCell temp = controller.getTablero().getElement(i, j);
                if (temp != null) {
                    g.drawImage(temp.getImage(), temp.getX(), temp.getY(), 35, 35, this);
                }
            }
        }
    }

    /**
     * Metodo para asignar un controlador simple a la vista simple SimpleView
     *
     * @param controller el controlador que controlara esta vista
     */
    void setController(SimpleController controller) {
        this.controller = controller;
        controllerAsignado = true;
    }

}
