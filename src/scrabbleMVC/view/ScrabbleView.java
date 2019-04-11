package scrabbleMVC.view;

import scrabbleMVC.model.ScrabbleCell;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import scrabbleMVC.model.ScrabbleBag;
import scrabbleMVC.controller.ScrabbleController;

/**
 *
 * @author Ajedrez
 */
public class ScrabbleView extends JPanel implements MouseMotionListener, MouseListener {

    private ScrabbleCell fondoTablero;
    private boolean flgIsMoving = false;
    //private ScrabbleBoard Tablero;
    private ScrabbleController controller;
    //private ScrabbleBag bolsa;
    private final int defaultY = 560;
    private final int MargenIzq = 85;
    private JFrame frame;

    private ArrayList<ScrabbleCell> Lista;
    public JLabel lblScore;
    //private JLabel lblPieces;
    public JLabel lblNombre;
    private JLabel lblMiTurno;

    public JLabel lblNombreP2;
    public JLabel lblScoreP2;

    public JLabel lblNombreP3;
    public JLabel lblScoreP3;

    public JLabel lblNombreP4;
    public JLabel lblScoreP4;

    public JPanel panelP4;
    public JPanel paneP3;
    public JPanel paneP2;

    private boolean panelP2Iniciado = false;
    private boolean panelP3Iniciado = false;
    private boolean panelP4Iniciado = false;

    private JLabel nickConexion;
    private JTextField txtNickConexion;

    private JLabel lblNumJugadores;
    private JTextField txtNumJugadores;

    private JPanel conexionPanel;

    public JPanel panelP1;
    private JButton btnEnv;
    private JButton btnPas;
    private JButton btnConectar;
    private JButton btnCliente;

    public void ponTxt(String txt) {
        // System.out.println("poniendo j2 " + txt);
        lblNombreP2.setText(txt);

    }
/**
 * Constructor de la vista
 * @param frame 
 */
    
    public ScrabbleView(JFrame frame) {
        super();
        frame = frame;

        setBackground(Color.black);
        setLayout(null);
        setOpaque(false);

        Lista = new ArrayList<ScrabbleCell>();

        //fondoTablero = new ScrabbleCell("resources/board.gif","null", 0, 0, 0);
        fondoTablero = new ScrabbleCell("resources/tablero.jpg", "null", 0, 0, 0);

        btnEnv = new JButton(new ScrabbleCell("resources/jugar.png"));
        btnEnv.setBounds(50, 610, 80, 40);
        btnEnv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (controller.isJuegoIniciado()) {
                    if (controller.isMiTurno()) {
                        if (controller.getMovements()) {
                            controller.cederTurno(false);

                        }
                    }
                } else {
                    //controller.iniciaJuego();
                }
            }

        });
        add(btnEnv);

        btnPas = new JButton(new ScrabbleCell("resources/pasar.png"));
        btnPas.setBounds(150, 610, 80, 40);

        btnPas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if (controller.isMiTurno()) {
                    controller.pasarTurno();

                }

            }

        }
        );
        add(btnPas);
        /*
         lblPieces = new JLabel("Fichas : 0");
         lblPieces.setBounds(550, 670, 200, 50);
         add(lblPieces);*/
        lblScore = new JLabel("Score : 0");
        lblScore.setBounds(450, 530, 200, 50);
        add(lblScore);

        lblMiTurno = new JLabel("Mi turno:");
        lblMiTurno.setBounds(550, 640, 200, 50);
        add(lblMiTurno);

        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(350, 530, 200, 50);
        add(lblNombre);

        panelP1 = new JPanel();
        panelP1.setLayout(new BorderLayout());
        panelP1.setBorder(BorderFactory.createLineBorder(Color.black));
        panelP1.add(lblNombre, BorderLayout.NORTH);
        panelP1.add(lblScore, BorderLayout.CENTER);
        //panelP1.add(lblMiTurno, BorderLayout.SOUTH);
        panelP1.setBounds(380, 550, 150, 100);
        add(panelP1);

        addMouseMotionListener(this);
        addMouseListener(this);

        muestraPanelConexion(true);
    }

    /**
     * Metodo para mostrar o no el panel de conexion al juego de Scrabble
     *
     * @param mostrarConexion decide si mostrar el panel o no
     */
    public void muestraPanelConexion(boolean mostrarConexion) {

        if (mostrarConexion) {

            conexionPanel = new JPanel();
            conexionPanel.setBounds(30, 550, 300, 150);
            conexionPanel.setBorder(BorderFactory.createLineBorder(Color.black));

            nickConexion = new JLabel("Nombre");
            txtNickConexion = new JTextField(20);
            txtNickConexion.setText("omar");
            lblNumJugadores = new JLabel("# jugadores");
            txtNumJugadores = new JTextField(2);
            txtNumJugadores.setText("2");

       // nickConexion.setBounds(50, 50, 10, 120);
            // txtNickConexion.setBounds(0,600, 20,30);
            conexionPanel.add(nickConexion);
            conexionPanel.add(txtNickConexion);
            conexionPanel.add(lblNumJugadores);
            conexionPanel.add(txtNumJugadores);

            setBtnConectar(new JButton(new ScrabbleCell("resources/servidor.png")));
            getBtnConectar().setBounds(550, 610, 60, 30);
            getBtnConectar().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {

                    controller.iniciarConexion(txtNickConexion.getText(), txtNumJugadores.getText(), true);

                }

            });
            conexionPanel.add(getBtnConectar());

            btnCliente = new JButton(new ScrabbleCell("resources/cliente.png"));
            btnCliente.setBounds(550, 710, 60, 30);
            btnCliente.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {

                    controller.iniciarConexion(txtNickConexion.getText(), txtNumJugadores.getText(), false);

                }

            });
            conexionPanel.add(btnCliente);

            add(conexionPanel);
        } else {

            conexionPanel.setVisible(false);

        }
        getBtnEnv().setVisible(!mostrarConexion);
        getBtnPas().setVisible(!mostrarConexion);

        validate();
        repaint();

    }
/**Se actualiza el panel del jugador 3
 * 
 * @param np2
 */
    public void initJugador2(String nP2) {

        if (!panelP2Iniciado) {
            panelP2Iniciado = true;
            lblNombreP2 = new JLabel("(Nombre: " + nP2);
            lblNombreP2.setBounds(50, 60, 200, 50);
            //add(lblNombreP2);
            lblScoreP2 = new JLabel("Score");
            lblScoreP2.setBounds(50, 90, 200, 50);
            //  add(lblScoreP2);

            paneP2 = new JPanel();
            paneP2.setLayout(new BorderLayout());
            paneP2.setBorder(BorderFactory.createLineBorder(Color.black));
            paneP2.add(lblNombreP2, BorderLayout.NORTH);
            paneP2.add(lblScoreP2, BorderLayout.CENTER);

            paneP2.setBounds(530, 10, 150, 100);
            add(paneP2);
        }

    }
/**Se actualiza el panel del jugador 3
 * 
 * @param np3 
 */
    public void initJugador3(String np3) {
        if (!panelP3Iniciado) {
            panelP3Iniciado = true;
            lblNombreP3 = new JLabel("(p3) Nombre:" + np3);
            lblNombreP3.setBounds(50, 150, 200, 50);

            lblScoreP3 = new JLabel("Score");
            lblScoreP3.setBounds(50, 180, 200, 50);

            paneP3 = new JPanel();
            paneP3.setLayout(new BorderLayout());
            paneP3.setBorder(BorderFactory.createLineBorder(Color.black));
            paneP3.add(lblNombreP3, BorderLayout.NORTH);
            paneP3.add(lblScoreP3, BorderLayout.CENTER);

            paneP3.setBounds(530, 120, 150, 100);
            add(paneP3);
            paneP3.setVisible(true);
        }
    }
/**Se actualiza el panel del jugador 4
 * 
 * @param np4 
 */
    public void initJugador4(String np4) {
        if (!panelP4Iniciado) {
            panelP4Iniciado = true;
            lblNombreP4 = new JLabel("(p4) Nombre:" + np4);

            lblNombreP4.setBounds(50, 240, 200, 50);
            lblScoreP4 = new JLabel("Score");
            lblScoreP4.setBounds(50, 270, 200, 50);

            panelP4 = new JPanel();
            panelP4.setLayout(new BorderLayout());
            panelP4.setBorder(BorderFactory.createLineBorder(Color.black));
            panelP4.add(lblNombreP4, BorderLayout.NORTH);
            panelP4.add(lblScoreP4, BorderLayout.CENTER);

            panelP4.setBounds(530, 230, 150, 100);
            add(panelP4);
        }

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
        //Dibujar las fichas que se encuentran sobre el tablero
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                ScrabbleCell temp = controller.getTablero().getElement(i, j);
                if (temp != null) {
                    g.drawImage(temp.getImage(), temp.getX(), temp.getY(), 35, 35, this);
                }
            }
        }
		//g.drawString("Quedan en la Bolsa: " + bolsa.getLettersOnBag() /*+ " dic:" + ScrabbleDictionary.getDiccionario().size()*/, 600, 300);

        //Dibujar las fichas que no se encuentran en el tablero
        for (int i = 0; i < getLista().size(); i++) {

            ScrabbleCell temp = getLista().get(i);
            g.drawImage(temp.getImage(), temp.getX(), temp.getY(), 35, 35, this);
        }

    }

    public void mouseDragged(MouseEvent e) {
        if (controller.isMiTurno()) {
            for (int i = 0; i < getLista().size(); i++) {
                ScrabbleCell temp = getLista().get(i);
                if (temp.isMoving() == true) {
                    temp.setX(e.getX() - 15);
                    temp.setY(e.getY() - 15);
                    repaint();
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {

        if (flgIsMoving == false && controller.isMiTurno()) {
            int x, y, xi, yi, xf, yf;
            for (int i = 0; i < getLista().size(); i++) {
                ScrabbleCell temp = getLista().get(i);
                xi = temp.getX();
                yi = temp.getY();
                xf = xi + 35;
                yf = yi + 35;
                x = e.getX();
                y = e.getY();
                if ((x >= xi && x <= xf) && (y >= yi && y <= yf) && (temp.isLocked() == false)) {
                    temp.setMoving(true);
                    flgIsMoving = true;
                }
            }
        }
    }

    private int getDefaultPosition(int defaultX) {
        for (int i = 0; i < 7; i++) {
            int count = 0;
            int xi = (i * 35) + MargenIzq;
            for (int j = 0; j < getLista().size(); j++) {
                ScrabbleCell temp = getLista().get(j);
                if (temp.getX() == xi) {
                    count++;
                }
            }
            if (count == 0) {
                return xi;
            }
        }
        return defaultX;
    }

    private boolean estaOcupado(int x, int y) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                ScrabbleCell temp = controller.getTablero().getElement(i, j);
                if (temp != null) {
                    if ((x == temp.getX()) && (y == temp.getY())) {
                        return true;
                    }
                }
            }
        }
        for (int i = 0; i < getLista().size(); i++) {
            ScrabbleCell temp = getLista().get(i);
            if ((x == temp.getX()) && (y == temp.getY())) {
                return true;
            }
        }
        return false;
    }

    /**Funcionalidad para cuando sueltas el boton del raton*/
    public void mouseReleased(MouseEvent e) {
        if (controller.isMiTurno()) {
            // System.out.println("SIES es mi turno");
            for (int i = 0; i < getLista().size(); i++) {

                // System.out.println("SI tengo fichas");
                ScrabbleCell temp = getLista().get(i);
                if (temp.isMoving() == true) {
                    int x = e.getX();
                    int y = e.getY();
                    x = (int) Math.ceil(x / 35);
                    y = (int) Math.ceil(y / 35);

                    if (x > 14 || y > 14 || estaOcupado(x * 35, y * 35)) {

                        //System.out.println("SIES esTA OCUPADO");
                        temp.setX(getDefaultPosition(temp.getX()));
                        temp.setY(defaultY);
                        controller.getTablero().remove(temp);
                    } else {

                        //System.out.println("ficha arrastrada: " + temp.getLetter());
                        if (temp.getLetter().equals("*")) {
                            //    System.out.println("Comodin");
                            CustomDialog dialogo = new CustomDialog(frame, this);
                            dialogo.pack();
                            dialogo.setLocationRelativeTo(frame);
                            dialogo.setVisible(true);

                            String s = dialogo.getValidatedText();

                            // System.out.println(" string obtenido " + s.toUpperCase());
                            ScrabbleCell temp2 = new ScrabbleCell("resources/" + s.toUpperCase() + ".gif", s.toUpperCase(), 0, temp.getX(), temp.getY());
                            getLista().remove(temp);
                            getLista().add(i, temp2);
                            temp = temp2;

                        }
                        temp.setX(x * 35);
                        temp.setY(y * 35);
                        ScrabbleCell s = controller.getTablero().remove(temp);
                        if (s == null) {
                            controller.getTablero().add(y, x, temp);//antes era add
                        } else {
                            controller.getTablero().add(y, x, s);//antes era add
                        }
                    }

                    temp.setMoving(false);
                    //  System.out.println("Deberia borrar " + temp.getLetter());
                    flgIsMoving = false;
                }
            }
            validate();
            repaint();
        } else {

            System.out.println("no es mi turno");
        }
    }

    /**
     * @return the controller
     */
    public ScrabbleController getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(ScrabbleController controller) {
        this.controller = controller;
    }

    
    /** Metodo para preguntar si se quiere terminar el juego*/
    public void preguntarFinJuego() {

        Object[] options = {"Si", "No"};

        int n = JOptionPane.showOptionDialog(this,
                "Has pasado muchas veces, quieres terminar juego?",
                "Terminar juego",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, //do not use a custom Icon
                options, //the titles of buttons
                options[0]); //default button title

        if (n == 0) {
            controller.terminarJuego();
        }
    }

    /**Metodo para desplegar un mensaje con el puntaje obtenido por una palabra dada
     * 
     * @param palabra
     * @param puntajePalabra 
     */
    public void showScore(String palabra, int puntajePalabra) {

        JOptionPane.showMessageDialog(null, " Tu score por " + palabra + " fue " + puntajePalabra);
        validate();
        repaint();
    }
    
    /**Update del cliente 0 
     * 
     * @param totalScore valor a actualizar
     */

    public void updateScore(int totalScore) {

        lblScore.setText("Score: " + totalScore);
        validate();
        repaint();

    }

    
    /**Metodo para actualizar la vista actual respecto a si tengo o no el turno actual
     * 
     * @param turo 
     */
    public void updateTurno(boolean turo) {

        getLblMiTurno().setText("Mi turno: " + turo);
        validate();
        repaint();

    }

   
    public void updateCurrentPieces(int pieces) {

        validate();
        repaint();

    }

    /**
     * @return the Lista
     */
    public ArrayList<ScrabbleCell> getLista() {
        return Lista;
    }

    /** 
     * @param Lista the Lista to set
     */
    public void setLista(ArrayList<ScrabbleCell> Lista) {
        this.Lista = Lista;
        // System.out.println("Actualizando lista");
        validate();
        repaint();
    }

    public void muestraMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    /*
     public void cederTurno( boolean miTurno){
    
     this.miTurno = miTurno;
    
     }*/
    /**
     * @return the lblNombre
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * @param lblNombre the lblNombre to set
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * @return the btnEnv
     */
    public JButton getBtnEnv() {
        return btnEnv;
    }

    /**
     * @param btnEnv the btnEnv to set
     */
    public void setBtnEnv(JButton btnEnv) {
        this.btnEnv = btnEnv;
    }

    /**
     * @return the btnPas
     */
    public JButton getBtnPas() {
        return btnPas;
    }

    /**
     * @param btnPas the btnPas to set
     */
    public void setBtnPas(JButton btnPas) {
        this.btnPas = btnPas;
    }

    /**
     * @return the lblMiTurno
     */
    public JLabel getLblMiTurno() {
        return lblMiTurno;
    }

    /**
     * @param lblMiTurno the lblMiTurno to set
     */
    public void setLblMiTurno(JLabel lblMiTurno) {
        this.lblMiTurno = lblMiTurno;
    }

    /**
     * @return the btnConectar
     */
    public JButton getBtnConectar() {
        return btnConectar;
    }

    /**
     * @param btnConectar the btnConectar to set
     */
    public void setBtnConectar(JButton btnConectar) {
        this.btnConectar = btnConectar;
    }

}
