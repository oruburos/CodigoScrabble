/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrabbleMVC.model;

import java.awt.Image;
import javax.swing.ImageIcon;
import scrabbleEGC.ScrabbleUtils;
import scrabbleMVC.model.ScrabbleModel;

/**
 *
 * @author Omar Verduga Palencia
 */
public class ScrabbleCell extends ImageIcon {
    
    	private int x;
	private int y;
	private String letter;
	private int value;
	private boolean moving;        
	private boolean locked;
	
	public void setLocked(boolean status){
		this.locked = status;
	}
	public boolean isLocked(){
		return this.locked;
	}
	public ScrabbleCell(String imagePath){
		super(imagePath);
                        this.setDescription(letter);

		this.letter = "";
		this.value = 0;
		this.x = 0;
		this.y = 0;
		this.moving = false;
		this.locked = false;
                
                validarValor();
	}
        
        /**Constructor usado para la info de red
         * 
         * @param imagePath
         * @param demasInfo
         * @param fromRed 
         */
        
        public ScrabbleCell(String imagePath , String demasInfo , boolean fromRed){
		super(imagePath);         
                
                int indexInicio=demasInfo.indexOf("|");
              
                String carta = demasInfo .substring(1, indexInicio);
               // System.out.println(" letra " + carta);
                this.letter = carta;
                this.setDescription(letter);
                demasInfo = demasInfo.substring(indexInicio+1, demasInfo.length());
               indexInicio=demasInfo.indexOf("|");
                
		
                this.value = ScrabbleUtils.StringToInt( demasInfo.substring(0, indexInicio));
                //System.out.println("value " + value);
               demasInfo = demasInfo.substring(indexInicio+1, demasInfo.length());
                //System.out.println("restante " + demasInfo);
                indexInicio=demasInfo.indexOf("|");
                
		this.x = ScrabbleUtils.StringToInt( demasInfo.substring(0, indexInicio));
               // System.out.println("x" + x);
                demasInfo = demasInfo.substring(indexInicio+1, demasInfo.length());
               // System.out.println("restante 2 " + demasInfo);
                
		this.y = ScrabbleUtils.StringToInt( demasInfo.substring(0, indexInicio));
  //  System.out.println("y" + y);
                
                
                
                //System.out.println("carta creada  " + this.toString());
		this.moving = false;
		this.locked = false;
                validarValor();
	}
        /* este metodo es lo que se pasara como segundo parametro al constructor dearriba*/
        public String toNetMSG(){
        
            String carta="";
            carta+=this.getLetter()+"|"+getValor()+"|"+getX()+"|"+getY();
            return carta;
        }        
	public ScrabbleCell(String imagePath, int xp, int yp){
		super(imagePath);
                        this.setDescription(letter);

		this.letter = "";
		this.value = 0;
		this.x = xp*35;
		this.y = yp*35;
		this.moving = false;
		this.locked = true;
	}
	public ScrabbleCell(String imagePath, String letter, int valor, int x, int y){
	super(imagePath);
            this.setDescription(letter);
		this.letter = letter;
		this.value = valor;
		this.x = x;
		this.y = y;
		this.moving = false;
		this.locked = false;
	}
       
        
        
        
	
    /**
     * @return the moving
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * @param moving the moving to set
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    
    public String toString(){
     return "letra "+getLetter() + " value "+ getValor();
    }

    /**
     * @param letter the letter to set
     */
    public void setLetter(String letter) {
        this.letter = letter;
    }
    
    
    private void validarValor(){
    
        if (!letter.equals("")){
        char letra = this.getLetter().charAt(0);
        int valorLetraOficial= ScrabbleModel.valorLetra(letra);
        this.setValue(valorLetraOficial);}
        //metodo pa cubrirme las espaldas en caso de que se me hayatraslapado el valor de la ficha
    }
    
    
    
    
    //regresa el numero de fichas adyacentes a una misma, de 0 a 4
    public int getNumAdyacentes()
    {

        int count = 0;
        for ( int i = 0; i < 4; i++ )
        {
            /*
            if ( getGrid().isValid( getLocation().getAdjacentLocation( i * 90 ) ) )
            {
                Location loc = getLocation().getAdjacentLocation( i * 90 );
                if ( getGrid().get( loc ) instanceof Letter )
                {
                    count++;
                }
            }*/
        }
        
        
        return count;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the letter
     */
    public String getLetter() {
        return letter;
    }

    /**
     * @return the value
     */
    public int getValor() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }
}
