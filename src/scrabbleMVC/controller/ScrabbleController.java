/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabbleMVC.controller;

import ScrabbleDataStructures.Estadisticas;
import ScrabbleDataStructures.PartidaDB;
import scrabbleMVC.model.ScrabbleBoard;
import scrabbleMVC.model.ScrabbleCell;
import ScrabbleIO.IO;
import java.awt.Color;
import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.HashMap;
import scrabbleEGC.ScrabbleConstants;
import scrabbleEGC.ScrabbleUtils;
import scrabbleEGC.net.ScrabbleClientRunnable;
import scrabbleEGC.net.ScrabbleMultiplayerServer;
import scrabbleMVC.model.ScrabbleModel;
import scrabbleMVC.model.ScrabbleMovement;
import scrabbleMVC.view.ScrabbleView;

/**
 *
 * @author Omar Verduga Palencia
 */
public class ScrabbleController {

    private ScrabbleView view;
    private ScrabbleModel model;

    private boolean miTurno;
    private int turnosConsecutivosPasados;
    private int scoreGamer;
    private boolean juegoIniciado;

    private int turnoActual;
    private int numJugadores;
    private String idNickname;
    private ArrayList< Integer> scores;

    private String nombreJugador;

    private boolean isConectado;

    /*conexiones*/
    private boolean soyServidor;
    private int numCliente;
    private String idConexion;
    private boolean juegoTerminado;

    /**Constructor del controller que interactuara con view y model
     * 
     * @param view la vista a controllar
     * @param model  el modelo sobre el que opera
     */    
    public ScrabbleController(ScrabbleView view, ScrabbleModel model) {

        this.view = view;
        this.model = model;
        model.initScrabbleBoard();

        turnosConsecutivosPasados = 0;

        // givePieces( 7 );
        scoreGamer = 0;
        miTurno = true;
        juegoIniciado = false;
        turnoActual = 0;
        // initPanels(numJug);
        scores = new ArrayList<Integer>();
        soyServidor = false;
        numCliente = -1;

        isConectado = false;
        juegoTerminado = false;

    }

    /*
     public void initScores(int j) {
        
     //System.out.println("iniciando scores del cliente " + numCliente);
     for (int index = 0; index < j; index++) {
            
     scores.add(index, 0);
     }
     }*/

    /*    private void actualizaScore(int score) {

     int jugador = getTurnoActual() % getNumJugadores();
     System.out.println("actualizando score de " + jugador + " turnoActual " + getTurnoActual());
     int scoreAnterior = scores.remove(jugador);

     scores.add(jugador, scoreAnterior + score);

     view.lblScore.setText("" + scores.get(0));
     view.lblScoreP2.setText("" + scores.get(1));
     if (numJugadores >= 3) {
     view.lblScoreP3.setText("" + scores.get(2));
     }
     if (numJugadores == 4) {
     view.lblScoreP4.setText("" + scores.get(3));
     }

     }
     */
    /**Metodo para iniciar el numero de paneles recibido
     * 
     * @param j  numero de paneles a activar
     */
    private void initPanels(int j) {
        if (j > 4 || j < 2) {
            System.out.println("error paneles " + j);
        } else {
            switch (j) {
                case 2:
                    view.initJugador2("n2");
                    break;
                case 3:
                    view.initJugador2("n2");
                    view.initJugador3("n3");
                    break;

                case 4:
                    view.initJugador2(" n2");
                    view.initJugador3("n3");
                    view.initJugador4("n4");
                    break;
                default:
                    break;

            }
        }

    }

    /**metodo para obtener piezas
     * 
     * @param num de piezas a pedir
     * @return las piezas obtenidas, 
     */
    private ArrayList<ScrabbleCell> getPieces(int num) {

        if (num > model.getBolsa().getLettersOnBag()) {
            num = model.getBolsa().getLettersOnBag();
        }
        ArrayList<ScrabbleCell> lista = new ArrayList<ScrabbleCell>();
        for (int i = 0; i < num; i++) {
            ScrabbleCell s = model.getBolsa().getRandomLetter();
            // System.out.println("se dio " + s.getLetter());
            lista.add(s);
        }
        return lista;
    }

    /**metodo para enviar a la vista n numero dado de pezas
     * 
     * @param numPieces numero de fichas a dar
     */
    private void givePieces(int numPieces) {

        if (numPieces < 7 || numPieces > 0) {

            ArrayList<ScrabbleCell> Lista;
            Lista = new ArrayList<ScrabbleCell>();

            for (ScrabbleCell pieza : view.getLista()) {

           //  System.out.println("Pieza actual " + pieza.toString());
            }
            Lista.addAll(view.getLista());

            for (int i = 0; i < numPieces; i++) {
                int x = (i * 35) + MargenIzq;
                int y = defaultY;
                ScrabbleCell s = model.getBolsa().getRandomLetter();
                if (s != null) {

                    //   System.out.println("Obtuve " + s.toString());
                    s.setX(x);
                    s.setY(y);
                    Lista.add(s);
                } else {

                    view.muestraMensaje("FIN DE BOLSA DE FICHAS");

                    break;
                }
            }

            //a estas alturas ya hay 7
            for (int i = 0; i < Lista.size(); i++) {
                int x = (i * 35) + MargenIzq;
                int y = defaultY;

                ScrabbleCell s = Lista.get(i);
                s.setX(x);
                s.setY(y);

            }

            // view.getLblPieces().setText(" fichas " + model.getBolsa().getLettersOnBag());
            view.setLista(Lista);
        }
    }

    
    /**Fichas que se borraran del mazo del usuario
     * 
     * @param piezasUsadas 
     */
    private void borraPiezas(ArrayList<ScrabbleCell> piezasUsadas) {

        view.getLista().removeAll(piezasUsadas);

    }

  

    /**
     * Logica del turno del jugador el jugador baja sus fichas , se comprueban
     * sus palabras, se genera el score y se reponen las fichas si existe esa
     * cantidad en el saco.
     */
    public boolean getMovements() {

        ArrayList<ScrabbleCell> piezasUsadas = model.getTablero().getPiezasBajadas();
        int totalPiezas = piezasUsadas.size();
        // System.out.println(" Piezas jugadas " + totalPiezas);
        if (totalPiezas == 0) {
            view.muestraMensaje("Jugada invalida: coloca piezas en el tablero ");
            return false;
        }

        ArrayList< ScrabbleMovement> moves = model.getTablero().getJugadas();

        //bool valida =
        if (moves == null) {

            view.muestraMensaje("Jugada invalida: ");

            return false;
        } else {

            int totalScore = 0;

            boolean usoPrimerTurno = false;

            for (ScrabbleMovement mv : moves) {

                if (!model.getTablero().yaHayJuego()) {
                    if (model.getTablero().usaPrimeraCasilla(mv)) {
                        usoPrimerTurno = true;

                    }
                }

                int score = model.scrabbleScore(mv);
                Estadisticas.getInstance().actualizaEstadisticas(this.getNombreJugador(), mv.getDescription(), score);
                //actualizo estadisticas

                totalScore += score;
            }

            if (!usoPrimerTurno && turnoActual == 0) {
                view.muestraMensaje("En el primer movimiento se debe usar la casilla central");
                return false;
            }

            model.getTablero().lockAll();

            if (totalPiezas == 7) {
                scoreGamer += 50;
                view.muestraMensaje("Felicidades lograste un SCRABBLE");
            }

            scoreGamer += totalScore;
            enviaScore();
            borraPiezas(piezasUsadas);

            // view.updateScore(scoreGamer);
//            actualizaScore(totalScore);
            updateScore(numCliente, "" + scoreGamer);
            view.muestraMensaje("Jugada(s) valida: " + totalScore + " nuevos puntos");
            turnosConsecutivosPasados = 0;

          // System.out.println("TURNO DE HJUGADA" + turnoActual);
            if (numCliente == 0) {
                //  System.out.println("solo el servidor guarda jugadas");
                givePieces(totalPiezas);
                view.updateCurrentPieces(model.getBolsa().getLettersOnBag());
//                model.saveTurno(getTurnoActual(), "", moves);
            } else {
                pidePiezas(totalPiezas);

            }

            System.out.println("Envio a actualizar partida");
            codificaTurno(getTurnoActual(), moves);

            setTurnoActual(getTurnoActual() + 1);//turno de alguien mas

            muestraTurnoActual();
            if (view.getLista().size() == 0) {
                System.out.println("ya no tengo fichas fin de juego");
                terminarJuego();
            }

            decideTurno();
            return true;
        }
    }


    private final int defaultY = 560;
    private final int MargenIzq = 55;
    /*
     pub/lic void sendPlay() {
        
     System.out.println(" Piezas jugadas " + getTablero().getPiezasBajadas());
     }*/

    public void cederTurno(boolean b) {

        miTurno = b;
        view.updateTurno(b);

    }

    /**
     * @return the miTurno
     */
    public boolean isMiTurno() {
        return miTurno;
    }

    /**
     * @param miTurno the miTurno to set
     */
    public void setMiTurno(boolean miTurno) {
        this.miTurno = miTurno;
    }

    public void pasarTurno() {
        ArrayList<ScrabbleCell> piezasUsadas = model.getTablero().getPiezasBajadas();
        int totalPiezas = piezasUsadas.size();
        // System.out.println(" Piezas jugadas " + totalPiezas);

        if (totalPiezas > 0) {
            view.muestraMensaje("No puedes pasar, tienes que retirar tus piezas del tablero");
        } else {
            this.cederTurno(false);
            view.muestraMensaje(" Pasaste el turno");
//            model.saveTurno(getTurnoActual(), "usuario", new ArrayList<ScrabbleMovement>());
            setTurnoActual(getTurnoActual() + 1);
            muestraTurnoActual();
            turnosConsecutivosPasados++;
            if (turnosConsecutivosPasados >= ScrabbleConstants.TURNOS_PREGUNTA_FIN) {

                //informaProbableFin();
                view.preguntarFinJuego();
            }
            decideTurno(); // que es el que realmente lo envia

        }
    }

    /* Logica Online*/
    static ScrabbleMultiplayerServer server;
    static ScrabbleClientRunnable cliente;

    
    /** Metodo para iniciar un servidor, darse de alta a el con un nombre y fijar el limite de conexiones
     * 
     * @param nombre para conectarse a el
     * @param numConexiones numero maximo de conexiones
     * @return 
     */
    private static boolean initServidor(String nombre, int numConexiones) {
        try {
            server = new ScrabbleMultiplayerServer(numConexiones);
            Thread hiloServdor = new Thread(server);
            hiloServdor.start();
            return true;
        } catch (Exception e) {
            System.out.println("error de conexoipn " + e);
            return false;
        }
    }

    public void updateView(String s) {

        view.getLblNombre().setText(s);

    }

    /**Inicia cliente
     * 
     * @param nombre nombre de cliente 
     * @return true si se conecto al servidor, false si no hay servidor corriendo
     */
    private boolean initCliente(String nombre) {

        try {
            cliente = new ScrabbleClientRunnable(nombre, this);

            Thread hiloCliente = new Thread(cliente);
            hiloCliente.start();

            cliente.out.println("id:" + cliente.idCliente);
            return true;
        } catch (Exception e) {
            view.muestraMensaje("No hay servidor corriendo");
            return false;
        }
    }

    /**Funcionalidad para crear servidor e iniciar cliente
     * 
     * @param nombre
     * @param conexiones
     * @return un status dependiendo si me conecte o no
     * @throws IOException 
     */
    public boolean conectarServidor(String nombre, int conexiones) throws IOException {
        //  System.out.println("Hacer conexxion con datos");

        boolean iniciaServidor = initServidor(nombre, conexiones);

        //System.out.println("Inicio servidor " + iniciaServidor);
        if (iniciaServidor) {
            // view.muestraMensaje("Soy servidor!!");
            soyServidor = iniciaServidor;
            //doy de alta mi cliente
            //

            initCliente(nombre);
            numCliente = 0;
        } else {
            view.muestraMensaje("Error, ya hay servidor");
            view.getBtnConectar().setEnabled(false);
        }

        return iniciaServidor;

    }

    /** Metodo de prueba para checar conexion de servidor*/ 
    public static void saluda(String string) {
        if (cliente != null) {
            cliente.out.println(cliente.idCliente + "|[saludos scrabble]|");
        }
    }

    /**
     * @return the juegoIniciado
     */
    public boolean isJuegoIniciado() {
        return juegoIniciado;
    }

    /**
     * @param juegoIniciado the juegoIniciado to set
     */
    public void setJuegoIniciado(boolean juegoIniciado) {
        this.juegoIniciado = juegoIniciado;
    }

    /*metodo para iniciar el juego en el servidor*/
    public void iniciaJuego() {
        givePieces(7);
//        initScores(numJugadores);
        juegoIniciado = true;
        setTurnoActual(0);
        muestraTurnoActual();
        view.getBtnEnv().setEnabled(true);
        view.getBtnPas().setEnabled(true);
        view.validate();
        view.repaint();

        // System.out.println("solo entro una vez?");
        reparteJuego(numJugadores);
    }

    /**
     * @return the numJugadores
     */
    public int getNumJugadores() {
        return numJugadores;
    }

    /**
     * @param numJugadores the numJugadores to set
     */
    public void setNumJugadores(int numJugadores) {
        this.numJugadores = numJugadores;
    }

    /**
     * Metodo para elegir y mostrar de que instancia es el turno
     */
    public void muestraTurnoActual() {
        int turno = getTurnoActual() % getNumJugadores();

        // System.out.println("  muestraTurnoActual turno del jugador " + turno + " turno actual " + getTurnoActual() + " jugadores " + getNumJugadores());
        showTurno(turno);

    }

    
    /**Validacion para conectarse a un servidor
     * 
     * @param nick se espera nombre mayor a 1 caracter
     * @param players se esperam valores de 2 a 4
     * @param comoServidor si quieres conextarte como servidor o cliente
     * @return 
     */
    public boolean iniciarConexion(String nick, String players, boolean comoServidor) {

        if (nick.length() < 1) {
            view.muestraMensaje("Nick muy corto");
            return false;
        }

        int numPlayers = ScrabbleUtils.StringToInt(players);
        if (comoServidor) {

            try {
                if (numPlayers != -1 && (numPlayers < 5 || numPlayers > 1)) {
                    isConectado = this.conectarServidor(nick, numPlayers);
                    setIdNickname("0|" + nick);
                    view.lblNombre.setText(getIdNickname());
                } else {
                    view.muestraMensaje("Numero no valido");

                }
            } catch (IOException ex) {
                return false;
            }
        } else {

            isConectado = initCliente(nick);
        }

        if (isConectado) {
            view.muestraPanelConexion(false);
            initPanels(numPlayers);
            view.getBtnEnv().setEnabled(false);
            view.getBtnPas().setEnabled(false);
            return true;
        }
        return false;
    }

    /**Metodo para obtener el update de datos de conexion y nick, asi como cuantos paneles iniciar
     * 
     * @param idConexion
     * @param nick
     * @param cuantosPaneles 
     */
    public void updatePanelJugador(String idConexion, String nick, int cuantosPaneles) {

        initPanels(cuantosPaneles);

        // System.out.println("iniciando panel con id " + idConexion +" nick "+ nick +  " num paneles "+ cuantosPaneles );
        switch (idConexion) {
            case "0":
                // System.out.println("iniciando panel 0");
                view.lblNombre.setText("Conexion(" + idConexion + ") " + nick);
                break;

            case "1":
                // System.out.println("iniciando panel 1");
                view.ponTxt("Conexion(" + idConexion + ") " + nick);
                //view.lblNombreP2.setText("Conexion(" + idConexion + ") " + nick);
                break;
            case "2":
                //  System.out.println("iniciando panel 2");
                view.lblNombreP3.setText("Conexion(" + idConexion + ") " + nick);
                break;

            case "3":
                //  System.out.println("iniciando 3");
                view.lblNombreP4.setText("Conexion(" + idConexion + ") " + nick);
                break;

        }

        view.validate();
        view.repaint();
    }

    /**
     * @return the idNickname
     */
    public String getIdNickname() {
        return idNickname;
    }

    /**
     * @param idNickname the idNickname to set
     */
    public void setIdNickname(String idNickname) {
        this.idNickname = idNickname;
    }

    public void setIdConexion(String idConexion) {
        this.idConexion = idConexion;
    }

    /**
     * @return the numCliente
     */
    public int getNumCliente() {
        return numCliente;
    }

    /**
     * @param numCliente the numCliente to set
     */
    public void setNumCliente(int numCliente) {
        this.numCliente = numCliente;
    }

    public void iniciaJuegoOnline() {

        decideTurno();
    }

    /* metodo para decidir el turno y enviar el mensaje a red*/
    public void decideTurno() {

        int turnoCliente = this.getTurnoActual() % numJugadores;
        //System.out.println("decide turno dice " +turnoCliente);
        cliente.out.println("@TURNO@" + turnoCliente + "|" + turnoActual);

    }

    
    /*metodo para desplegar y cambiar colores de paneles de acuerdo al turno*/
    public void showTurno(int turno) {

        // System.out.println("marcando en rojo turno " + turno +" en el cliente " + this.getNumCliente());
        view.getLblMiTurno().setText("Mi turno: " + (turno == getNumCliente()));
        switch (turno) {

            case 0:
                view.panelP1.setBackground(Color.red);
                view.paneP2.setBackground(Color.gray);
                if (numJugadores >= 3) {
                    view.paneP3.setBackground(Color.gray);
                }
                if (numJugadores == 4) {
                    view.panelP4.setBackground(Color.gray);
                }
                break;
            case 1:
                view.panelP1.setBackground(Color.gray);
                view.paneP2.setBackground(Color.red);
                if (numJugadores >= 3) {
                    view.paneP3.setBackground(Color.gray);
                }
                if (numJugadores == 4) {
                    view.panelP4.setBackground(Color.gray);
                }
                break;
            case 2:
                view.panelP1.setBackground(Color.gray);
                view.paneP2.setBackground(Color.gray);
                if (numJugadores >= 3) {
                    view.paneP3.setBackground(Color.red);
                }
                if (numJugadores == 4) {
                    view.panelP4.setBackground(Color.gray);
                }
                break;
            case 3:
                view.panelP1.setBackground(Color.gray);
                view.paneP2.setBackground(Color.gray);
                if (numJugadores >= 3) {
                    view.paneP3.setBackground(Color.gray);
                }
                if (numJugadores == 4) {
                    view.panelP4.setBackground(Color.red);
                }
                break;

        }

    }

    
    /**Reparte cartas a los siguientes clientes
     * 
     * @param numClientes numero de clientes a los que hay que enviar fichas
     */
    private void reparteJuego(int numClientes) {

        //  System.out.println("repartiendo juego para " + numClientes + " recoradd que 1 ya inicio reparto");
        for (int i = 1; i < numClientes; i++) {
            ArrayList<ScrabbleCell> manoParaCliente = getPieces(7);
            String mano = "@MANO@" + i + "," + 7;

            for (ScrabbleCell pieza : manoParaCliente) {

                mano += "<" + pieza.toNetMSG() + ">";
            }
            cliente.out.println(mano);
            //  System.out.println("Quedan " + model.getBolsa().getLettersOnBag() + " fichas");
        }
    }

    /**Metodo auxuliar para desplegar un mensaje en la vista
     * 
     * @param s mensaje a mostrar
     */
    public void muestraMensaje(String s) {

        view.muestraMensaje(s);
    }
    
    
    /**Metodo para enviar por red el numero de letras requerido
     * 
     * @param numPiezas 
     */
    private void pidePiezas(int numPiezas) {

        //  System.out.println("repartiendo juego para " + numClientes + " recoradd que 1 ya inicio reparto");
        ArrayList<ScrabbleCell> manoParaCliente = getPieces(numPiezas);
        String mano = "@REPONER@" + numCliente + "," + numPiezas;//pido reponer al cliente n piezas

        for (ScrabbleCell pieza : manoParaCliente) {

            mano += "<" + pieza.toNetMSG() + ">";
        }
        cliente.out.println(mano);
            //  System.out.println("Quedan " + model.getBolsa().getLettersOnBag() + " fichas");

    }

    /**
     * @return the idConexion
     */
    public String getIdConexion() {
        return idConexion;
    }

    /* Metodo llamado desde cliente de online, no debe llamarse si es servidor*/
    public void updateMazoCliente(ArrayList<ScrabbleCell> mazoEnviado) {

        ArrayList<ScrabbleCell> Lista;
        Lista = new ArrayList<ScrabbleCell>();

        Lista.addAll(view.getLista());

        Lista.addAll(mazoEnviado);

        //a estas alturas ya hay 7
        for (int i = 0; i < Lista.size(); i++) {
            int x = (i * 35) + MargenIzq;
            int y = defaultY;

            ScrabbleCell s = Lista.get(i);
            s.setX(x);
            s.setY(y);

        }

        view.setLista(Lista);
        miTurno = false;
        view.getBtnEnv().setEnabled(true);
        view.getBtnPas().setEnabled(true);
        view.validate();
        view.repaint();

    }

    /* Metodo llamado desde cliente de online, no debe llamarse si es servidor*/
    /*public void updateMazoCliente(ArrayList<ScrabbleCell> mazoEnviado) {

     //a estas alturas ya hay 7
     for (int i = 0; i < mazoEnviado.size(); i++) {
     int x = (i * 35) + MargenIzq;
     int y = defaultY;

     ScrabbleCell s = mazoEnviado.get(i);
     s.setX(x);
     s.setY(y);

     }

     view.setLista(mazoEnviado);
     miTurno = false;
     view.getBtnEnv().setEnabled(true);
     view.getBtnPas().setEnabled(true);
     view.validate();
     view.repaint();

     }*/
    //lo envio desde el cliente
    public void enviaScore() {
        String jugada = "@SCORE@" + getNumCliente() + "|" + scoreGamer;    //podria usar el turno actual para validar la coherencia de l apartida
        cliente.out.println(jugada);
    }

    /**Metodo que codigica el arreglo de movimientos moves e informa del turno en que paso,
     * originalmente era para usar el turno actual con fines de validacion
     * 
     * @param turnoActual turno en que se hcieron lso movimientos
     * @param moves  movimientos hechos en el turno
     */
    public void codificaTurno(int turnoActual, ArrayList<ScrabbleMovement> moves) {

        // System.out.println(" TURNO actual " + turnoActual + getIdNickname());
        String jugada = "@JUGADA@" + turnoActual + "|" + moves.size();
        for (ScrabbleMovement mv : moves) {

            jugada += mv.toString();
            System.out.println(" move " + mv.toString());
        }
        cliente.out.println(jugada);
    }

    
    /**Aplicar los movimientos recibidos por red en la instancia
     * 
     * @param mazoEnviado  lista de movimientos a aplicar
     */
    public void aplicaMovimientos(ArrayList<ScrabbleMovement> mazoEnviado) {

        //System.out.println(" cliente " + numCliente + " aplicando " + mazoEnviado.size()              + " jugadas" );
        boolean horizontal = false;

        for (ScrabbleMovement mv : mazoEnviado) {

            if (mv.getDirection() == ScrabbleMovement.movement.Horizontal) {
                horizontal = true;
            } else {
                horizontal = false;
            }

            String mask = mv.getMask();

            for (int i = 0; i < mask.length(); i++) {

                char actual = mv.getDescription().charAt(i);
                // System.out.println(" imprimiendo ficha:"+ actual);
                if (mask.charAt(i) == '0') {

                    ScrabbleCell cartaObj;
                    if (mv.getDescription().charAt(i) == '*') {
                        cartaObj = new ScrabbleCell("resources/cmd.gif");

                    } else {
                        cartaObj = new ScrabbleCell("resources/" + actual + ".gif");
                    }
                    cartaObj.setDescription("" + mv.getDescription().charAt(i));
                    cartaObj.setLetter("" + mv.getDescription().charAt(i));

                    if (horizontal) {
                        // System.out.println(" Horizontarl x, y " + ( mv.getStartX()+i) + " " + mv.getStartY());
                        model.getTablero().putPiece(mv.getStartX() + i, mv.getStartY(), cartaObj);
                        cartaObj.setX((mv.getStartX() + i) * 35);
                        cartaObj.setY(mv.getStartY() * 35);

                    } else {
                        // System.out.println("vert x, y " + mv.getStartX() + " " + (mv.getStartY()+i));
                        model.getTablero().putPiece(mv.getStartX(), mv.getStartY() + i, cartaObj);
                        cartaObj.setX(mv.getStartX() * 35);
                        cartaObj.setY((mv.getStartY() + i) * 35);

                    }
                }
            }

        }
        model.getTablero().lockAll();
        /*
         for ( int i= 0; i< 15; i++){
         for ( int j = 0 ; j<15 ; j++){
         ScrabbleCell cas = Tablero.getElement(i, j);
         if (cas != null)
         System.out.println(" pieza " + cas.getLetter() + " en  " + i + " , " +  j);
         }
         }
    
         */
        view.validate();
        view.repaint();

    }

    public void enNuevoTurno() {
        setTurnoActual(getTurnoActual() + 1);
    }

    /**
     * @return the turnoActual
     */
    public int getTurnoActual() {
        return turnoActual;
    }

    /**
     * @param turnoActual the turnoActual to set
     */
    public void setTurnoActual(int turnoActual) {
        this.turnoActual = turnoActual;
    }

    public void actualizaTurno() {
        miTurno = turnoActual == numCliente;

        if (miTurno) {

            System.out.println("turno del cliente " + numCliente);
        }

        muestraTurnoActual();
    }

    public void updateScore(int numCliente, String score) {

        switch (numCliente) {

            case 0:
                view.lblScore.setText(score);
                break;
            case 1:
                view.lblScoreP2.setText(score);
                break;
            case 2:
                view.lblScoreP3.setText(score);
                break;
            case 3:
                view.lblScoreP4.setText(score);
                break;
        }

    }

    public void informaProbableFin() {

        String msg = "@ISFIN@" + this.getNumCliente();
        cliente.out.println(msg);

    }

    /**Metodo para terminar el jeugo y pedir que el usuario informe su score local al servidor
     */
    public void terminarJuego() {

        if (!juegoTerminado) {
            String msg = "@FIN@" + this.getNumCliente() + "|" + this.calculaScoreFinal();

            //this.calculaScoreFinal();
            System.out.println(" valor del usuario al terminar " + this.calculaScoreFinal());
            cliente.out.println(msg);
            juegoTerminado = true;

            bloqueaBotones();

        }

    }

    /** Metodo para bloquear botones de la interfa
     */
    public void bloqueaBotones() {
        view.getBtnEnv().setEnabled(false);
        view.getBtnPas().setEnabled(false);
    }

    public void buscarGanador() {

        System.out.println("Envioseñal de que todos reporten sus scores y sus listas");
        String msg = "@REPORTAR@" + this.getNumCliente();
        cliente.out.println(msg);

    }

    public void muestraGanador(String substring) {

        
        
        
        if (numCliente == ScrabbleUtils.StringToInt(substring)) {

            view.muestraMensaje("Ganaste " + this.idNickname);
            Estadisticas.getInstance().actualizaEstadisticaFinJuego(this.getNombreJugador(), true, scoreGamer);

        } else {
            Estadisticas.getInstance().actualizaEstadisticaFinJuego(this.getNombreJugador(), false, scoreGamer);

            view.muestraMensaje("Perdiste " + this.idNickname);
        }

        PartidaDB partida = new PartidaDB();
        partida.setTablero(model.getTablero());
        partida.setIdGanador(ScrabbleUtils.StringToInt(substring));

        // ArrayList<String>
        HashMap<String, String> posicionesJugador = new HashMap<String, String>();
        System.out.println("el juego fue entre " + numJugadores);

        posicionesJugador.put(this.getIdNickname().substring(2, idNickname.length()), "" + this.scoreGamer);
        posicionesJugador.put(this.view.lblNombreP2.getText(), this.view.lblScoreP2.getText());

        System.out.println(" jugador: " + this.view.lblNombreP2.getText() + " puntaje:" + this.view.lblScoreP2.getText());

        if (numJugadores >= 3) {
            posicionesJugador.put(this.view.lblNombreP3.getText(), this.view.lblScoreP3.getText());
            System.out.println(" jugador: " + this.view.lblNombreP3.getText() + " puntaje:" + this.view.lblScoreP3.getText());
        }
        if (numJugadores == 4) {
            posicionesJugador.put(this.view.lblNombreP4.getText(), this.view.lblScoreP4.getText());
            System.out.println(" jugador: " + this.view.lblNombreP4.getText() + " puntaje:" + this.view.lblScoreP4.getText());
        }

        partida.setJugadores(posicionesJugador);
        if (soyServidor) {
            IO.guardaUltimaPosicion(partida);
            IO.guardaHistorico(Estadisticas.getInstance());
        }

    }
    
    
    public ScrabbleBoard getTablero(){
        return model.getTablero();
    }

    
    /** metodo que regresa el valor del score del jugador MENOS las fichas que aun tiene en su poder
     * 
     * @return el score final del jugador
     */
    private int calculaScoreFinal() {

        ArrayList<ScrabbleCell> lasQueMeQuedan = view.getLista();

        int valorMisPiezas = 0;

        for (ScrabbleCell ficha : lasQueMeQuedan) {
            valorMisPiezas += ficha.getValor();
        }

        System.out.println("Soy el cliente" + this.getIdConexion() + " mi Score fue " + scoreGamer + " mi mazo vale " + valorMisPiezas);
        return scoreGamer - valorMisPiezas;

    }

    /**
     * @return the nombreJugador
     */
    public String getNombreJugador() {
        return nombreJugador;
    }

    /**
     * @param nombreJugador the nombreJugador to set
     */
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

}
