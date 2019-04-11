/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrabbleMVC.controller;



import ScrabbleDataStructures.PartidaDB;
import scrabbleMVC.model.ScrabbleBoard;
import scrabbleMVC.model.ScrabbleCell;
import ScrabbleIO.IO;
import java.awt.Color;
import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JPanel;
import scrabbleEGC.ScrabbleConstants;
import scrabbleEGC.ScrabbleUtils;
import scrabbleEGC.net.ScrabbleClientRunnable;
import scrabbleEGC.net.ScrabbleMultiplayerServer;
import scrabbleMVC.model.ScrabbleModel;
import scrabbleMVC.model.ScrabbleMovement;
import scrabbleMVC.view.ScrabbleView;
import scrabbleMVC.view.SimpleScrabbleView;

/**
 *
 * @author Omar Verduga Palencia
 */
public class SimpleController extends JPanel {

    private SimpleScrabbleView view;
    private ScrabbleBoard Tablero;

  public SimpleController(SimpleScrabbleView view)  {

        this.view = view;
        initScrabbleBoard();
       System.out.println("Constuctor terminado");
    }

    private void initScrabbleBoard() {
        
        
        PartidaDB partida = IO.recuperaUltimaPosicion();
        setTablero(partida.getTablero());
        view.lblNombre.setText(" el ganador fue el cliente: " + partida.getIdGanador());
        
        String info = "";
        Iterator it = partida.getJugadores().entrySet().iterator();
        int renglon = 0 ;
        while (it.hasNext()) {
           Map.Entry pairs = (Map.Entry<String, String>)it.next();
           info= pairs.getKey() + " score:" + pairs.getValue()+"\n";
            System.out.println("info " + info);
           if( renglon== 0 ){
               System.out.println("seteando 0");
           view.lblScore.setText(info );
           }
           if( renglon == 1 ){
               System.out.println("seteando 1");
           view.lblScore2.setText(info );
           }
           if( renglon == 2 ){
               System.out.println("seteando 2");
           view.lblScore3.setText(info );
           }
           if( renglon == 3 ){
               System.out.println("seteando 3");
           view.lblScore4.setText(info );
           }
           renglon++;
        }
        
     
        
        //getTablero().printAll();
        
    }

    /**
     * @return the Tablero
     */
    public ScrabbleBoard getTablero() {
        return Tablero;
    }

    /**
     * @param Tablero the Tablero to set
     */
    public void setTablero(ScrabbleBoard Tablero) {
        this.Tablero = Tablero;
    }


}

