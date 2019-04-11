/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrabbleMVC.model;

import java.util.ArrayList;
import scrabbleMVC.model.ScrabbleMovement;

/**
 *
 * @author Omar Verduga Palencia
 */
public class ScrabbleTurno {

    private String autorMovimiento;
    private ArrayList<ScrabbleMovement> movimientos;

    public ScrabbleTurno( String a, ArrayList<ScrabbleMovement> m) {
        
        autorMovimiento = a;
        movimientos = m;                 
    }

    
    
    /**
     * @return the autorMovimiento
     */
    public String getAutorMovimiento() {
        return autorMovimiento;
    }

    /**
     * @param autorMovimiento the autorMovimiento to set
     */
    public void setAutorMovimiento(String autorMovimiento) {
        this.autorMovimiento = autorMovimiento;
    }

    /**
     * @return the movimiento
     */
    public ArrayList<ScrabbleMovement> getMovimientos() {
        return movimientos;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimientos(ArrayList<ScrabbleMovement> movimientos) {
        this.movimientos = movimientos;
    }
    
            
    
}
