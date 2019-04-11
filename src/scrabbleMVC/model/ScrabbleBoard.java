/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabbleMVC.model;

import java.util.ArrayList;
import scrabbleMVC.model.ScrabbleDictionary;
import scrabbleMVC.model.ScrabbleMovement;
import scrabbleMVC.model.ScrabbleMovement.movement;

/**
 *
 * @author Omar Verduga Palencia
 */
public class ScrabbleBoard {

    private ScrabbleCell myBoard[][];
    private boolean primerTurno;

    
    /** Se construye como un arreglo de 15 por 15*/
    public ScrabbleBoard() {
        myBoard = new ScrabbleCell[15][15];
    }

    
    /** Se agrega a una pieza s a la poscion regla, columna
     * 
     * @param r renglon de colocacion
     * @param c columna de colocacion
     * @param s pieza
     */
    public void add(int r, int c, ScrabbleCell s) {
        myBoard[r][c] = s;
    }

    
    /** Metodo para llamar tras una jugada valida, de esta forma las fichas ya
     * no pueden ser reposicionadas una vez validadas en el tablero
     * 
     * 
     */
    public void lockAll() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                ScrabbleCell s = myBoard[i][j];
                if (s != null) {
                    s.setLocked(true);
                }
            }
        }
    }
    
    
    /** Metodo para imprimir el contenido de un ablero
     */
     public void printAll() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                ScrabbleCell s = myBoard[i][j];
                if (s != null) {
                    System.out.println("  renglon " + i + " columna  " + j + " letra " + s.getLetter());
                    
                }
            }
        }
    }
    
    /** La primera vez debe haber 0 y entonces acepto mascaras 00..00*/
      public boolean yaHayJuego() {
          
          
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                ScrabbleCell s = myBoard[i][j];
                if (s != null) {
                    if ( s.isLocked()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

      
    public ScrabbleCell remove(ScrabbleCell elemento) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                ScrabbleCell s = myBoard[i][j];
                if (s == elemento) {
                    myBoard[i][j] = null;
                    return s;
                }
            }
        }
        return null;
    }
    
    
    /** posicion cuando pasan como x, y que es un desorden volver a 
     * pensarlo como renglon y columna como usualmente se recorren las matrices
     * 
     * @param x 
     * @param y
     * @param elemento 
     */
    
    public void putPiece( int x , int y ,ScrabbleCell elemento) {
        //volteado por la logica renglon , columna
           if (myBoard[y][x]== null){                    
                    myBoard[y][x] = elemento;
            }else{
               System.out.println(" elementoe xistente " + myBoard[y][x].toString() );
           }
            
    }
    
    /** Metodo sobrecargado para construir una pieza desde la informacion dada en elemento
     * 
     * @param x
     * @param y
     * @param elemento 
     */
      
    public void putPiece( int x , int y ,String elemento) {
        
        
      //  System.out.println("colocando  " + elemento);
         ScrabbleCell  cartaObj;
                if ( elemento.equals( "*")){
                  cartaObj = new ScrabbleCell("resources/cmd.gif");
                  
                }else{ //System.out.println("recibiendo |"+letra+"|");
                  cartaObj = new ScrabbleCell( "resources/"+elemento.toUpperCase()+".gif");
                
                }
                cartaObj.setLetter(elemento);
                cartaObj.setX(x*35);
                cartaObj.setY(y*35);
            //volteado por la logica renglon , columna
           if (myBoard[y][x]== null){                    
                    myBoard[y][x] = cartaObj;
            }else{
               System.out.println(" elementoe xistente " + myBoard[y][x].toString() );
           }
            
    }

    
    /** Piezas de diferencia entre el tablero anterior y las piezas que el juador en turno puso
     * 
     * @return las piezas de diferencia, util para saber cuando hay que pedir que el jugado retire piezas en mov invalido o un par de casos mas
     * cuando son validas se necesita que repongamos la misma cantidad y ademas estas hay que borrarlas del mazo del jugador
     */
    public ArrayList<ScrabbleCell> getPiezasBajadas() {

        ArrayList<ScrabbleCell> piezas = new ArrayList<ScrabbleCell>();
        for (int r = 0; r < 15; r++) {
            for (int c = 0; c < 15; c++) {
                ScrabbleCell temp = myBoard[r][c];
                if (temp != null) {

                    if (!temp.isLocked()) {
                         piezas.add(temp );
                    }
                }
            }
        }

        return piezas;
    }

    
    
    /**Se generan todos los movimientos del jugador
     * 
     * @return se regresa un arreglo con movimientos validos o un arreglo vacio en otro caso
     */
    public ArrayList<ScrabbleMovement> getJugadas() {

        ArrayList<ScrabbleMovement> jugadas = new ArrayList<ScrabbleMovement>();
        String newLetter = new String();
        boolean allValid = true;
        boolean horizontalOneLetterValid = true;
        boolean verticalOneLetterValid = true;
        //int newValue = 0;

        String s = new String();
        int count = 0;
        //orden horizontal}+
        String mask = "";
        for (int r = 0; r < 15; r++) {
            mask = "";
            for (int c = 0; c < 15; c++) {

                // System.out.println("empezando r, c " + r +","+ c );
                ScrabbleCell temp = myBoard[r][c];
                if (temp != null) {

                    s = s.concat(temp.getLetter());
                    if (temp.isLocked() == false) {
                        count++;
                        mask = mask.concat("0");
                         // System.out.println(" hr "+ s + " count " + count );
                    } else {
                        //System.out.println(" sitio locked "+ s);
                        mask = mask.concat("1");
                    }
                } else {

                    if (count > 0) {

                       // System.out.println("palabra de renglon  "+ newLetter);
                        newLetter = s;
                       // newValue = count;

                        if (newLetter.length() > 1) {

                            if (ScrabbleDictionary.getInstance().existePalabra(newLetter) && ((mask.indexOf("1")>-1 && yaHayJuego() ) || !yaHayJuego()   ) && mask.length()>1) {

                                ScrabbleMovement move = new ScrabbleMovement(movement.Horizontal, c - newLetter.length(), r, newLetter, mask);
                                System.out.println(move.toString());
                                jugadas.add(move);
                              //  System.out.println(" agregando " + newLetter + " mascara " + mask);
                            } else {
                              //  System.out.println(newLetter + " NO VALIDA");
                                allValid = false;
                            }
                        }else{
                            //System.out.println("dafaq?"+newLetter);
                           // horizontalOneLetterValid=false;
                        }
                    } else {
                       
                       
                        mask = "";
                    }
                    count = 0;
                    s = "";
                }

            }
        }

       // System.out.println("Checando otro orden");
        //orden vertical
        // mask="";
        
        for (int c = 0; c < 15; c++) {

            mask = "";
            for (int r = 0; r < 15; r++) {

                ScrabbleCell temp = myBoard[r][c];
                if (temp != null) {
                    s = s.concat(temp.getLetter());
                    if (temp.isLocked() == false) {
                        count++;
                        mask = mask.concat("0");
                          //System.out.println(" letra libre "+ s +" m,ask" + mask);
                    } else {
                         //System.out.println(" sitio locked "+ s +" m,ask" + mask);
                        mask = mask.concat("1");
                    }
                } else {

                    if (count > 0) {

                        //  System.out.println("palabra de columna  "+ newLetter);
                        newLetter = s;
                       // newValue = count;
                        if (newLetter.length() > 1) {

                            if (ScrabbleDictionary.getInstance().existePalabra(newLetter) && (mask.indexOf("1")>-1 && yaHayJuego() ) || !yaHayJuego()   && mask.length()>1) {
                                ScrabbleMovement move = new ScrabbleMovement(movement.Vertical, c, r - newLetter.length(), newLetter, mask);
                                System.out.println(move.toString());
                                jugadas.add(move);
                              //  System.out.println(" agregando " + newLetter + " mascara " + mask);

                            } else {
                              //  System.out.println(newLetter + " NO VALIDA");
                                allValid = false;
                            }
                        }else{
                          //  System.out.println("dafaq2?"+newLetter);
                           // verticalOneLetterValid = false;
                        }
                    } else {
                        mask = "";
                    }
                    count = 0;
                    s = "";
                }
            }
        }
/*
        if ( verticalOneLetterValid || horizontalOneLetterValid){
        
            System.out.println("valido en alguna direccion");
           
        }else{
            System.out.println("invalida en todas");
            allValid = false;
        }
        */
        if (!allValid) {

            System.out.println("Hay jugadas no validas");
            return null;
        } else {
            
            return jugadas;
        }

    }

    public ScrabbleCell getElement(int f, int c) {
        ScrabbleCell s = myBoard[f][c];
        return s;
    }

    /**
     * @return the primerTurno
     */
    public boolean isPrimerTurno() {
        return primerTurno;
    }

    /**
     * @param primerTurno the primerTurno to set
     */
    public void setPrimerTurno(boolean primerTurno) {
        this.primerTurno = primerTurno;
    }
    
    
    /**El primer movimiento tiene que usar la casilla de enmedio a fuerza
     * 
     * @param mv
     * @return 
     */
    public boolean usaPrimeraCasilla ( ScrabbleMovement mv){
    
        
        System.out.println("checando la primera jugada " + mv.toString());
            if (mv.getDirection() == ScrabbleMovement.movement.Horizontal) {
                
                
                if ( mv.getStartX() <=7 && mv.getStartX()+ mv.getDescription().length()>7   && mv.getStartY()==7){
                    return true;
                }
            } else {
                
                if ( mv.getStartY() <=7 && mv.getStartY()+ mv.getDescription().length()>7 && mv.getStartX()==7){
                    return true;
                }                
            }
            return false;
    }
}
