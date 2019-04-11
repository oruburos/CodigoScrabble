
package scrabbleMVC.model;

import java.util.ArrayList;

/**
 *
 * @author Omar Verduga Palencia
 */
public class ScrabbleBag {
    
        private static ArrayList<ScrabbleCell> scrabblePieces;
        //private boolean inGame;
        
        
        private static ScrabbleBag INSTANCE = null;
 
 
    private  static void ScrabbleBag() {
        if (INSTANCE == null) { 
            INSTANCE = new ScrabbleBag();
        }
    }
   private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new ScrabbleBag();
        }
    }
    public static ScrabbleBag getInstance() {
        createInstance();
        return INSTANCE;
    }
	
        /**
         * Constructor default
         */
	private ScrabbleBag(){
            scrabblePieces = new ArrayList<ScrabbleCell>();
                    
        }
        
        /**Se inicia la bolsa
         * 
         */
        public static void initScrabbleBag(){
           	scrabblePieces = new ArrayList<ScrabbleCell>();
       
		scrabblePieces.add( new ScrabbleCell("resources/cmd.gif", "*", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/cmd.gif", "*", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/A.gif", "A", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/A.gif", "A", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/A.gif", "A", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/A.gif", "A", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/A.gif", "A", 0, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/A.gif", "A", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/A.gif", "A", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/A.gif", "A", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/A.gif", "A", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/A.gif", "A", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/A.gif", "A", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/S.gif", "S", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/S.gif", "S", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/S.gif", "S", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/S.gif", "S", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/S.gif", "S", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/S.gif", "S", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/I.gif", "I", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/I.gif", "I", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/I.gif", "I", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/I.gif", "I", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/I.gif", "I", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/I.gif", "I", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/N.gif", "N", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/N.gif", "N", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/N.gif", "N", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/N.gif", "N", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/N.gif", "N", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/K.gif", "K", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/Q.gif", "Q", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/L.gif", "L", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/L.gif", "L", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/L.gif", "L", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/L.gif", "L", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/Z.gif", "Z", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/O.gif", "O", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/O.gif", "O", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/O.gif", "O", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/O.gif", "O", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/O.gif", "O", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/O.gif", "O", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/O.gif", "O", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/O.gif", "O", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/O.gif", "O", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/J.gif", "J", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/X.gif", "X", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/H.gif", "H", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/H.gif", "H", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/F.gif", "F", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/V.gif", "V", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/V.gif", "V", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/Y.gif", "Y", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/D.gif", "D", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/G.gif", "G", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/D.gif", "D", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/G.gif", "G", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/U.gif", "U", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/U.gif", "U", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/U.gif", "U", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/U.gif", "U", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/U.gif", "U", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/C.gif", "C", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/C.gif", "C", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/C.gif", "C", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/C.gif", "C", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/B.gif", "B", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/B.gif", "B", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/M.gif", "M", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/M.gif", "M", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/P.gif", "P", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/P.gif", "P", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/E.gif", "E", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/E.gif", "E", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/E.gif", "E", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/E.gif", "E", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/E.gif", "E", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/E.gif", "E", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/E.gif", "E", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/E.gif", "E", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/E.gif", "E", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/E.gif", "E", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/E.gif", "E", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/E.gif", "E", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/T.gif", "T", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/T.gif", "T", 1, 0, 0) );
		scrabblePieces.add( new ScrabbleCell("resources/T.gif", "T", 1, 0, 0) );
	}
        
        /**Metodo para saber el numero de letras en la bolsa
         * 
         * @return 
         */
        
	public static int getLettersOnBag(){
		return scrabblePieces.size();
	}
        
        
        
        
        /**
         * 
         * @param numOfPeces
         * @return un arreglo de letras
         */
        public static ArrayList<ScrabbleCell> getScrabblePieces( int numOfPeces){
            ArrayList<ScrabbleCell> morePieces = new ArrayList<>();
            
            for ( int i = 0 ; i < numOfPeces; i++){
                
                morePieces.add(getRandomLetter());            
                
            }
            
            return morePieces;
            
        
        }
        
        /**Obtiene una ficha de la bolsa si aun hay piezas
         * 
         * @return una ficha aleatoria o nul si no hay mas
         */
        public static ScrabbleCell getRandomLetter(){
            
		int key=(int)(Math.random()*scrabblePieces.size()-1);                
                if ( key !=-1){
                    return scrabblePieces.remove(key);
                }else{
                return null;
                        }
	}	
}
