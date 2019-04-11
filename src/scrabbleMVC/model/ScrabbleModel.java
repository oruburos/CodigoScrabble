/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrabbleMVC.model;

import java.util.ArrayList;

/**
 *
 * @author Omar Verduga Palencia
 */
public class ScrabbleModel {

    
    
    /**
     * @return the bolsa
     */
    public ScrabbleBag getBolsa() {
        return bolsa;
    }

    /**
     * @param bolsa the bolsa to set
     */
    public void setBolsa(ScrabbleBag bolsa) {
        this.bolsa = bolsa;
    }
    
      public void initScrabbleBoard() {
        setTablero(new ScrabbleBoard());
    }

    /**
     * @return the tablero
     */
    public ScrabbleBoard getTablero() {
        return tablero;
    }

    /**
     * @param Tablero the tablero to set
     */
    public void setTablero(ScrabbleBoard Tablero) {
        this.tablero = Tablero;
    }
   
    
 public enum TypeSquare{
        DL,
        TL,
        DW,
        TW,
        NONE,
    }
           
     private static ScrabbleModel INSTANCE = null;
     private boolean prueba;
     private TypeSquare[][] specialSquares ; 
     
     private ScrabbleBoard tablero;
     private ScrabbleBag bolsa ;
     
    private ScrabbleModel(){
        
       prueba = false;
       bolsa = ScrabbleBag.getInstance();//aqu va singleton
       bolsa.initScrabbleBag();
       initPowerUps();
       
    }
    
   
 
    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    // otra prueba para evitar instanciación múltiple 
    private static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new ScrabbleModel();
        }
    }
 
    public static ScrabbleModel getInstance() {
        createInstance();
        return INSTANCE;
    }
    
    
    public boolean boardValidate(String palabra) {
      
		//Verificar que "palabra" existe en el diccionario, sino lanzar ScrabException
		if(ScrabbleDictionary.getInstance().existePalabra(palabra)==false){
                    
                    System.out.println("Palabra NO valida");
                    return false;
                }else{
                    
                    System.out.println("PalabraValida");
                    return true;
                }
			
}

    
    
    
    
    
    
    public int scrabbleScore( ScrabbleMovement scrabbleMovement) {
        
        int score = 0;
        String scrabbleWord= scrabbleMovement.getDescription().toUpperCase();
        String mask=  scrabbleMovement.getMask();
        /*
        if ( scrabbleWord.length() != mask.length()){
            System.out.println("ERROR, problema de movement");
            throw Exception(" Error ");
        }
        */
        int index = 0   ;
        int wordMultiplier = 1;
        int letterMultiplier = 1;
        
        if ( scrabbleMovement.getDirection()== ScrabbleMovement.movement.Horizontal){
                index = scrabbleMovement.getStartX();
                System.out.println("empezando en horizontal " + index);
        }else 
        if ( scrabbleMovement.getDirection()== ScrabbleMovement.movement.Vertical){
                index = scrabbleMovement.getStartY();
                System.out.println("empezando en vertical " + index);
        }
        
        index--;
        for (int i = 0; i < scrabbleWord.length(); i++){
            index++;
            
            
            char calculatedLetter = scrabbleWord.charAt(i);
            
            System.out.println("checando letra " + calculatedLetter);
            
           // if ( mask.charAt(i) =='0'){
            
                if ( scrabbleMovement.getDirection()== ScrabbleMovement.movement.Horizontal){
                 TypeSquare celda = specialSquares[index][scrabbleMovement.getStartY()];
                 if ( celda != TypeSquare.NONE){
                     specialSquares[index][scrabbleMovement.getStartY()] = TypeSquare.NONE;//solo se usan una ve;
                    
                    
                     System.out.println("*****************cekda distinta a NONe");
                      if (specialSquares[scrabbleMovement.getStartX()][index] != TypeSquare.NONE){
                          System.out.println("Que onda, deberia cambiar");
                      }
                     
                     if ( celda == TypeSquare.DL){
                         System.out.println(" usando doble letra con " + calculatedLetter);
                         letterMultiplier = 2;
                     }
                     if ( celda == TypeSquare.TL){
                         System.out.println(" usando triple letra con " + calculatedLetter);
                         letterMultiplier = 3;
                     }
                     if ( celda == TypeSquare.TW){
                         System.out.println(" usando triepl palabra con " + calculatedLetter);
                         wordMultiplier *= 3;
                     }
                     
                     if ( celda == TypeSquare.DW){
                         System.out.println(" usando doble palabra con " + calculatedLetter);
                         wordMultiplier *= 2;
                     }
                 }
                 
                }
                
                
                if ( mask.charAt(i) =='1'){
                    letterMultiplier = 1;
                    wordMultiplier = 1;//?
                }
                
                
                   if ( scrabbleMovement.getDirection()== ScrabbleMovement.movement.Vertical){
                 TypeSquare celda = specialSquares[scrabbleMovement.getStartX()][index];
                 if ( celda != TypeSquare.NONE){
                     specialSquares[scrabbleMovement.getStartX()][index] = TypeSquare.NONE;//solo se usan una ve;
                     
                     System.out.println("*****************cekda distinta a NONe");
                      if (specialSquares[scrabbleMovement.getStartX()][index] != TypeSquare.NONE){
                          System.out.println("Que onda, deberia cambiar");
                      }
                     if ( celda == TypeSquare.DL){
                             System.out.println(" usando doble letra v con " + calculatedLetter);
                         letterMultiplier = 2;
                     }
                     if ( celda == TypeSquare.TL){  System.out.println(" usando tripli  v letra con " + calculatedLetter);
                         letterMultiplier = 3;
                     }
                     if ( celda == TypeSquare.TW){
                            System.out.println(" usandvdo triepl palabra con " + calculatedLetter);
                     
                         wordMultiplier *= 3;
                     }
                     
                     if ( celda == TypeSquare.DW){
                           System.out.println("v usandvdo doble palabra con " + calculatedLetter);
                     
                         wordMultiplier *= 2;
                     }
                 }
                 
                }
                
                
                
                
            
            switch (calculatedLetter) {
                case 'A':
                case 'E':
                case 'I':
                case 'L':
                case 'N':
                case 'O':
                case 'R':
                case 'S':
                case 'T':
                case 'U': 
                    score +=1* letterMultiplier; break;
                case 'D':
                case 'G':
                    score +=2* letterMultiplier; break;
                case 'B':
                case 'C':
                case 'M':
                case 'P':
                    score +=3* letterMultiplier; break;
                case 'F':
                case 'H':
                case 'V':
                case 'W':
                case 'Y':
                    score +=4* letterMultiplier; break;
                case 'K':
                    score +=5* letterMultiplier; break;
                case 'J':
                case 'X':
                    score +=8* letterMultiplier; break;
                case 'Q':
                case 'Z':
                    score +=10* letterMultiplier; break;
                default: break;
            }
        }
        return score*wordMultiplier;
    }
    
    
    public static int valorLetra ( char letra){
        int valor=-1;
       switch (letra) {
                case 'A':
                case 'E':
                case 'I':
                case 'L':
                case 'N':
                case 'O':
                case 'R':
                case 'S':
                case 'T':
                case 'U': 
                    valor =1; break;
                case 'D':
                case 'G':
                    valor = 2; break;
                case 'B':
                case 'C':
                case 'M':
                case 'P':
                    valor = 3; break;
                case 'F':
                case 'H':
                case 'V':
                case 'W':
                case 'Y':valor = 4; break;
                case 'K':
                    valor = 5; break;
                case 'J':
                case 'X':
                    valor = 8; break;
                case 'Q':
                case 'Z':
                    valor =     0; break;
                default: break;
            }
        
        return valor;
    }
    
    
    public int scrabbleScore( String scrabbleWord ) {
        
        int score = 0;
        scrabbleWord= scrabbleWord.toUpperCase();
        for (int i = 0; i < scrabbleWord.length(); i++){
            char calculatedLetter = scrabbleWord.charAt(i);
            
            //char EnglishScoreTable[26] = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };
    
            switch (calculatedLetter) {
                case 'A':
                case 'E':
                case 'I':
                case 'L':
                case 'N':
                case 'O':
                case 'R':
                case 'S':
                case 'T':
                case 'U': 
                    score +=1; break;
                case 'D':
                case 'G':
                    score +=2; break;
                case 'B':
                case 'C':
                case 'M':
                case 'P':
                    score +=3; break;
                case 'F':
                case 'H':
                case 'V':
                case 'W':
                case 'Y':
                    score +=4; break;
                case 'K':
                    score +=5; break;
                case 'J':
                case 'X':
                    score +=8; break;
                case 'Q':
                case 'Z':
                    score +=10; break;
                default: break;
            }
        }
        return score;
    }
    /**
     * @return the prueba
     */
    public boolean isPrueba() {
        return prueba;
    }

   

    private void initPowerUps(){
        specialSquares = new TypeSquare[15][15];
        
        for( int i = 0 ; i< 15 ;i ++){
            for( int j = 0 ; j< 15 ;j ++){
                specialSquares[i][j] = TypeSquare.NONE;
            }
        }
       
        //triple words powerup
        specialSquares[0][0] = TypeSquare.TW;
        specialSquares[0][7] = TypeSquare.TW;
        specialSquares[0][14] = TypeSquare.TW;
        specialSquares[7][0] = TypeSquare.TW;
        specialSquares[7][14] = TypeSquare.TW;
        specialSquares[14][0] = TypeSquare.TW;
        specialSquares[14][7] = TypeSquare.TW;
        specialSquares[14][14] = TypeSquare.TW;
        
        
        //double words powerup
        specialSquares[1][1] = TypeSquare.DW;
        specialSquares[2][2] = TypeSquare.DW;
        specialSquares[3][3] = TypeSquare.DW;
        specialSquares[4][4] = TypeSquare.DW;
        
        specialSquares[13][1] = TypeSquare.DW;
        specialSquares[12][2] = TypeSquare.DW;
        specialSquares[11][3] = TypeSquare.DW;
        specialSquares[10][4] = TypeSquare.DW;
        
        specialSquares[1][13] = TypeSquare.DW;
        specialSquares[2][12] = TypeSquare.DW;
        specialSquares[3][11] = TypeSquare.DW;
        specialSquares[4][10] = TypeSquare.DW;
 
        specialSquares[10][10] = TypeSquare.DW;
        specialSquares[11][11] = TypeSquare.DW;
        specialSquares[12][12] = TypeSquare.DW;
        specialSquares[13][13] = TypeSquare.DW;
        
        specialSquares[7][7] = TypeSquare.DW;
        
        //triple letter
         specialSquares[1][5] = TypeSquare.TL;
         specialSquares[1][9] = TypeSquare.TL;
         specialSquares[5][5] = TypeSquare.TL;
         specialSquares[5][9] = TypeSquare.TL;
        
         specialSquares[9][5] = TypeSquare.TL;
         specialSquares[9][9] = TypeSquare.TL;
        
         specialSquares[13][5] = TypeSquare.TL;
         specialSquares[13][9] = TypeSquare.TL;
        
         specialSquares[5][1] = TypeSquare.TL;
         specialSquares[5][13] = TypeSquare.TL;
         specialSquares[9][1] = TypeSquare.TL;
         specialSquares[9][13] = TypeSquare.TL;
        

          //double letter
         specialSquares[3][0] = TypeSquare.DL;
         specialSquares[11][0] = TypeSquare.DL;
         specialSquares[3][14] = TypeSquare.DL;
         specialSquares[11][14] = TypeSquare.DL;
        
         specialSquares[6][2] = TypeSquare.DL;
         specialSquares[8][2] = TypeSquare.DL;
        
         
         specialSquares[6][13] = TypeSquare.DL;
         specialSquares[8][13] = TypeSquare.DL;
        
         
         specialSquares[0][3] = TypeSquare.DL;
         specialSquares[7][3] = TypeSquare.DL;
         specialSquares[14][3] = TypeSquare.DL;
         
         
         specialSquares[0][11] = TypeSquare.DL;
         specialSquares[7][11] = TypeSquare.DL;
         specialSquares[14][11] = TypeSquare.DL;
        
         
         specialSquares[2][6] = TypeSquare.DL;
         specialSquares[6][6] = TypeSquare.DL;
         specialSquares[8][6] = TypeSquare.DL;
         specialSquares[12][6] = TypeSquare.DL;
         
         
         specialSquares[2][8] = TypeSquare.DL;
         specialSquares[6][8] = TypeSquare.DL;
         specialSquares[8][8] = TypeSquare.DL;
         specialSquares[12][8] = TypeSquare.DL;
         
         
         specialSquares[3][7] = TypeSquare.DL;
         specialSquares[11][7] = TypeSquare.DL;
         
    }
    
    
    
    
    public void defineGanador( ArrayList<Integer> scores){
    
        
        int mayor = 0;//asumo que viene bonito        
        for ( int i = 0; i < scores.size() ; i++ ){
            if (scores.get(mayor) < scores.get(i) ){
                mayor=i;        
            }            
        }
        System.out.println("El ganador es el cliente " + mayor);
    
    }
    
}