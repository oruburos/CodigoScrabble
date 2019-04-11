/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ScrabbleDataStructures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import scrabbleMVC.model.ScrabbleDictionary;

/**
 *
 * @author Omar Verduga Palencia
 */
public class Estadisticas {
    
    private static HashMap<String , RecordXPlayer > historia ;
    
    private static Estadisticas INSTANCE = null;
     private  static void Estadisticas() {
        if (INSTANCE == null) { 
            INSTANCE = new Estadisticas();
        }
    }
     /** Para manejo del singleton
      */
   private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new Estadisticas();
        }
    }
   /** Para manejo del singleton
      */
    public static Estadisticas getInstance() {
        createInstance();
        return INSTANCE;
    }
    
    /** @param nombre se crea en el historial una entrada para el nombre del jugador actual
      */    
    public static void InsertarJugador( String nombre){
    
    
        if ( getInstance().getHistoria().containsKey(nombre)){
            System.out.println("La clave ya existe, no se procede");
        }else{
            RecordXPlayer r = new RecordXPlayer(nombre);
            System.out.println("Creando historial para " + nombre );
            getInstance().getHistoria().put(nombre, r);
        }
    }
    
    
    /** @param nombre se crea en el historial una entrada con el record actual
     * este metodo esta sobrecargado
      */    
    public static void InsertarJugador( RecordXPlayer nombre){
    
    
        if ( getInstance().getHistoria().containsKey(nombre.getNombre())){
            System.out.println("La clave ya existe, no se procede");
        }else{
              getInstance().getHistoria().put(nombre.getNombre(), nombre);
        }
    }
    
    
    /** 
     * @param key la clave para buscar el registro
     * @param palabra palabra a checar
     * @param scorePalabra  score obtendo por la palabra a checar
     * 
      */        
    public static void checaPalabraMasValiosa( String key, String palabra, int scorePalabra){
            if (getInstance().getHistoria().containsKey(key) ){
                if( getInstance().historia.get(key).getPuntosPalabraMasValiosa() < scorePalabra){
                 getInstance().historia.get(key).setPuntosPalabraMasValiosa(scorePalabra);
                 getInstance().historia.get(key).setPalabraMasValiosa(palabra);
                }    
            }else{
                System.out.println("no hay record");
            }
    }
    
     /** 
     * @param key la clave para buscar el registro
     * @param palabra palabra a introducir
     * 
      */   
    public static void updatePalabraMasValiosa( String key, String palabra){
    /*optimismo a falta de tiempo*/
            if (getInstance().getHistoria().containsKey(key) ){
        getInstance().getHistoria().get(key).setPalabraMasValiosa(palabra);    
            }
            else{
                System.out.println("no hay clave : "  + key );
            }
    }
    
     /** 
     * @param key la clave para buscar el registro
     * @param palabra palabra a introducir en el campo palabraMasLarga asociado al registro
     * 
      */   
    public static void updatePalabraMasLarga( String key, String palabra){
    /*optimismo a falta de tiempo*/ if (getInstance().getHistoria().containsKey(key) ){
        getInstance().getHistoria().get(key).setPalabraMasLarga(palabra); }
            else{
                System.out.println("no hay clave : "  + key );
            }   
    }
    
    
     /** 
     * @param key la clave para buscar el registro
     * @param partidas nuevo valor a actualizar
     **/   
    public static  void updatePartidasGanadas( String key, int partidas){
        getInstance().getHistoria().get(key).setPartidasGanadas(partidas);            
    }
    
    
     /** 
     * @param key la clave para buscar el registro
     * @param partidas nuevo valor a actualizar
     **/
    public static void updatePartidasJugadas( String key, int partidas){
       getInstance(). getHistoria().get(key).setPartidasJugadas(partidas);    
        
    }

    /**
     * @return la historia 
     */
    public HashMap<String , RecordXPlayer > getHistoria() {
        return historia;
    }
    
     /** 
     * @param key la clave para buscar el registro
     * @param partidas usa el nuevo valor como el maximo del jugador key
     *
     **/
    public void updateMaxScore( String key, int partidas){
        getHistoria().get(key).setPuntuacionMaxima(partidas);            
    }
    
    
    
    
     /** 
     * @return el historial en formato de arreglo de arreglos para su uso en la JTable de historial
     **/
    public static String[][] toView(){
    
        
    int numRecords= getInstance().getHistoria().size();
    String[][] celdas = new String [numRecords][6];//la ancho de la tabla es 6
    
        System.out.println("Hay "+numRecords);
        
         
        int renglon = 0 ;
            Iterator it = getInstance().getHistoria().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry<String, RecordXPlayer >) it.next();
                System.out.println(" cliente " + pairs.getKey() + " score " + pairs.getValue());
                RecordXPlayer r = (RecordXPlayer) pairs.getValue();
                celdas[renglon][0]=  r.getNombre();
                celdas[renglon][1]=  ""+r.getPuntuacionMaxima();
                celdas[renglon][2]=  r.getPalabraMasLarga();
                celdas[renglon][3]=  r.getPalabraMasValiosa();
                celdas[renglon][4]= ""+ r.getPartidasJugadas();
                celdas[renglon][5]= ""+ r.getPartidasGanadas();
                
                renglon++;
            }
            
    return celdas;
    }

    /** Score por palabra punto de entrada desde fuera
     * 
     * @param key clave a actualizar
     * @param palabra palabra usada
     * @param scorePalabra  score obtenido
     */
    public static void actualizaEstadisticas(String key, String palabra , int scorePalabra){
    
        if ( getInstance().historia.containsKey(key)){
            //actualizo si aplica
            checaPalabraMasValiosa(key, palabra, scorePalabra);
            if (getInstance(). historia.get(key).getPalabraMasLarga().length()< palabra.length()){
                updatePalabraMasLarga(key, palabra);
            }
          
            
        }else{
            InsertarJugador(key);
            updatePalabraMasLarga(key, palabra);
            updatePalabraMasValiosa(key, palabra);
                        
        }
    
    }
    
    /* @param key clave a actualizar
     * @param ganado valor para saber si gano o no
     * @param scorePalabra score a actualizar
     */
    public static void actualizaEstadisticaFinJuego( String key , boolean ganado , int score){
        if ( getInstance().historia.containsKey(key)){
        
            getInstance().historia.get(key).setPartidasJugadas(INSTANCE. historia.get(key).getPartidasJugadas()+1 );
            if( ganado){
                    getInstance().historia.get(key).setPartidasGanadas(INSTANCE.historia.get(key).getPartidasGanadas()+1 );
            }
            
            if ( getInstance().historia.get( key).getPuntuacionMaxima()< score)
            getInstance().historia.get(key).setPuntuacionMaxima(score );
            
        }else{
            InsertarJugador(key);
            historia.get(key).setPartidasJugadas( 1 );
            if( ganado ){
            historia.get(key).setPartidasJugadas( 1 );
            }
            historia.get(key).setPuntuacionMaxima(score );
            
            
        }
    }
    
    public  Estadisticas(){
    
        historia = new  HashMap<String , RecordXPlayer > ();
    }
    
}
