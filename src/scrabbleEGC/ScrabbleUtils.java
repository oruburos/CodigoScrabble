package scrabbleEGC;

/**
 *
 * @author Omar Verduga Palencia
 */
public class ScrabbleUtils {    
        
    
    /** metodo auxiliar para pasar una cadena a numero
     * 
     * @param str cadena que puede representar un numero
     * @return el numero si str efectivamente era un numero, else -1
     */
    
    public static int StringToInt( String str){
        
        int valor = -1;
        try{
            valor = Integer.parseInt(str);
                }catch(Exception e){
                System.out.println(str+" no es un numero entero");
                }
        return valor;
    
    }
    
    /** metodo auxiliar para pasar un caracter a numero
     * 
     * @param str caracter que puede representar un numero
     * @return el numero si str efectivamente era un numero, else -1
     */
    
    public static int StringToInt( char str){
        
        return StringToInt(""+str);
    }
}
