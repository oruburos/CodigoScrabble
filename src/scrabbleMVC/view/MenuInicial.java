/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabbleMVC.view;

import ScrabbleDataStructures.Estadisticas;
import ScrabbleIO.IO;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import scrabbleEGC.ScrabbleRunner;
import scrabbleMVC.controller.SimpleController;
import scrabbleMVC.model.ScrabbleDictionary;

/**
 *
 * @author Omar Verduga Palencia
 */
public class MenuInicial extends JFrame {

    JLabel label = new JLabel();
    JPanel panel = new JPanel(new GridLayout(7, 1));
    JButton button1 = new JButton("Jugar");
    JButton button2 = new JButton("Estadisticas");
    JButton button3 = new JButton("Puntuacion por jugador");
    JButton button4 = new JButton("Ayuda");

    boolean isShowingM1 = false;
    boolean isShowingM2 = false;
    boolean isShowingM3 = false;
    boolean isShowingM4 = false;

    public MenuInicial() {
        label.setText("Selecione opcion:");
        panel.add(label);

        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);

        add(panel, BorderLayout.CENTER);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new ScrabbleRunner().setVisible(true);

                    }
                });
            }
        }
        );

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (!isShowingM2) {
                    JPanel pan = new JPanel();
                    JFrame frame1 = getFrameTablero();
                    frame1.setVisible(true);
                    frame1.add(pan);
                    isShowingM2 = true;
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                // dummyHistorico();
                Estadisticas e = IO.cargaHistorico();

                String[][] cellData = e.toView();

                String[] columnNames = {"Nombre", "Score Maximo", "Palabra mas larga", "Palabra mas valiosa", "Partidas jugadas", "Partidas ganadas"};

                JTable table = new JTable(cellData, columnNames);

                table.setEnabled(false);

                JFrame f = new JFrame();
                f.setTitle(" Puntuacion historica de jugadores");
                f.setSize(700, 300);
                f.add(new JScrollPane(table));
                f.setVisible(true);
                f.setResizable(false);
            }

        });

        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                JFrame frame1 = new JFrame();
                frame1.setVisible(true);
                frame1.setTitle("Reglas usadas");

                JTextArea txt = new JTextArea(IO.loadAyuda());
                txt.setEditable(false);
                JScrollPane scrollBar = new JScrollPane(txt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

                frame1.add(scrollBar);
                frame1.setResizable(false);
                //Set JFrame size
                frame1.setSize(500, 300);

                //Make JFrame get to center
                frame1.setLocationRelativeTo(null);

            }
        });

    }

    /**
     *
     * @return un JFrame donde desplegar las posicioes y valores de la ultima
     * partida
     */
    public JFrame getFrameTablero() {

        JFrame j = new JFrame();
        j.setTitle("Ultima Partida de Scrabble");
        SimpleScrabbleView view = new SimpleScrabbleView(j);
        j.add(view);
        SimpleController controller = new SimpleController(view);
        view.setController(controller);
        j.setResizable(false);

        j.setSize(700, 750);
        return j;

    }

    public static void main(String arg[]) {
        MenuInicial frame = new MenuInicial();
        frame.setTitle("EGC Scrabble");
        frame.setSize(300, 300);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * metodo para probar funcionalidad
     */
    private void dummyHistorico() {

        IO.testDummy();
        Estadisticas e = IO.cargaHistorico();

        e.updatePalabraMasLarga("omar", "oximoroasdanoso");
        IO.guardaHistorico(e);

    }
}
