/**
 *
 * @author Omar Verduga Palencia
 */
package ScrabbleDataStructures;

import scrabbleMVC.model.ScrabbleBoard;
import java.util.ArrayList;
import java.util.HashMap;
import scrabbleMVC.model.ScrabbleMovement;

/**
 *
 * @author Omar Verduga Palencia
 */
public class PartidaDB {
    
    private ScrabbleBoard tablero;    
    private HashMap<String,String> jugadores;        
    private int idGanador;
    
    /**constructor
     * 
     */
    public PartidaDB(){
        tablero= new ScrabbleBoard();   
        jugadores = new HashMap<String,String>();
        idGanador = -1;
    }
    
    public PartidaDB( ScrabbleBoard tab,  ArrayList< String> jug ,  int id ){
       
        tablero= new ScrabbleBoard();   
        jugadores = new HashMap<String,String>();
        idGanador = -1;
    
    }

    /**
     * @return the tablero
     */
    public ScrabbleBoard getTablero() {
        return tablero;
    }

    /**
     * @param tablero the tablero to set
     */
    public void setTablero(ScrabbleBoard tablero) {
        this.tablero = tablero;
    }

    /**
     * @return the jugadores
     */
    public HashMap<String,String> getJugadores() {
        return jugadores;
    }

    /**
     * @param jugadores the jugadores to set
     */
    public void setJugadores(HashMap<String,String> jugadores) {
        this.jugadores = jugadores;
    }

    /**
     * @return the idGanador
     */
    public int getIdGanador() {
        return idGanador;
    }

    /**
     * @param idGanador the idGanador to set
     */
    public void setIdGanador(int idGanador) {
        this.idGanador = idGanador;
    }
    
    
  
}
