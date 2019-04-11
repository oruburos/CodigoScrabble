/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrabbleMVC.model;

import scrabbleEGC.ScrabbleUtils;

/**
 *
 * @author Omar Verduga Palencia
 */
public class ScrabbleMovement {

    
    
  
    private int startX;
    private int startY;
    private String description;
    private String mask;//letters used 
    private movement direction;
    
    
    /** Constructor de un movimiento valido de scrabble
     * la cual cuenta con direccion dada por direction, la posicion donde esta la primera letra de la palabra
     * la palabra en si y una bitmask para saber cuales bits ya estaban presentes
     */
    public ScrabbleMovement( movement dr , int x, int y , String descr , String used){
    
        direction = dr;
        startX = x;
        startY = y;
        description =descr;
        mask =used;
        
    }
    
    
    
    public String toString(){    
        return "<"+direction+ "|"+ description + "("+startX+","+startY+")"+mask+">";    
    }
    
    
    public ScrabbleMovement(  String mv){
    
       // System.out.println("constror movt con " + mv);
             mv = mv.substring(1,mv.length()-1);
            int indexInicio=mv.indexOf("|");
              
                String direccion = mv .substring(0, indexInicio);
              //  System.out.println("Direccion " + direccion);
               if ( direccion.equals("Horizontal")){
                   direction = movement.Horizontal;
               }else{
                    direction = movement.Vertical;
               }
                
                mv = mv.substring(indexInicio+1, mv.length());
               
         
              // System.out.println("mv actual " + mv);
                indexInicio=mv.indexOf("(");
                
                String palabra = mv.substring(0,indexInicio);
                description = palabra;
               // System.out.println("palanra " +palabra);
                int indexComa=mv.indexOf(",");
                String x = mv.substring(indexInicio+1,indexComa);                
                int indexFin=mv.indexOf(")");
                String y = mv.substring(indexComa+1, indexFin );
                
                String mask = mv.substring(indexFin+1, mv.length());
                
                startX = ScrabbleUtils.StringToInt(x);
                startY = ScrabbleUtils.StringToInt(y);
                this.mask = mask;
                //System.out.println(" palabra " + palabra + " x:"+ x + " y:"+y + " mask "+mask);
         
                
    }
    /**
     * @return the startX
     */
    public int getStartX() {
        return startX;
    }

    /**
     * @param startX the startX to set
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * @return the startY
     */
    public int getStartY() {
        return startY;
    }

    /**
     * @param startY the startY to set
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the direction
     */
    public movement getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(movement direction) {
        this.direction = direction;
    }

    /**
     * @return the mask
     */
    public String getMask() {
        return mask;
    }

    /**
     * @param mask the mask to set
     */
    public void setMask(String mask) {
        this.mask = mask;
    }
    
    public enum movement{
    Vertical,
    Horizontal,
}
    
}
