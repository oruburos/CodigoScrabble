/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabbleEGC.net;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import scrabbleEGC.ScrabbleUtils;
/**
 *
 * @author Omar Verduga Palencia
 */
public class ConnectionListener extends Thread {
    private Vector<Connection> connections;
    private int tamanoRoom;
    private Vector< String> jugadores;
    private int jugadoresConectados = 0;    
    private boolean inicioBroadcast = false;
    
    private HashMap< String , String > stats4Player ; 
    
    
    private int jugadoresReportandoFin = 0 ;
    
    public ConnectionListener(Vector<Connection> connections , int tamanoSaladeJuego) {
        this.connections = connections;
        this.tamanoRoom = tamanoSaladeJuego;
        this.jugadores = new Vector<String>();
        this.stats4Player = new HashMap<String, String>();
    }

    /** metodo que es llamado de la intercepcion del mensaje FIN y que broadcastea el ganador
     */
    public String enviandoResultadosFin(){
    
        String puntuacionesFinales = "@WINNER@";
       
        int maxScore = -1;
        String idGanador ="0";
        
        
        int scoreActual = 0;
         Iterator it = stats4Player.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry<String, String>)it.next();
           System.out.println(" cliente " + pairs.getKey() + " score " + pairs.getValue());
            
            
            scoreActual = ScrabbleUtils.StringToInt( pairs.getValue().toString());
                
            
            if ( scoreActual >= maxScore){
            
                scoreActual = maxScore;
                idGanador = pairs.getKey().toString();
            
            }
            
        }
        
        return puntuacionesFinales+idGanador;
        
  }

        
        
    /**
     * 
     * @param msg
     * @return el usuario id
     */
    
    
    public String parseaUsuario( String msg){            
        int indice= msg.indexOf(":");
        return msg.substring(indice+1, msg.length());            
    }
    // check for incoming messages and broadcast
    public void run() {
        while (true) {
            for (int i = 0; i < connections.size(); i++) {
                Connection ith = connections.get(i);

                if (!ith.isAlive()){
                    connections.remove(i);
                    jugadoresConectados--;
                }
                // broadcast to everyone
                String message = ith.getMessage();
                if (message != null){
                    
                    if( message.contains("@FIN@")){
                        
                        String interceptado =message.substring(5,  message.length());
                        //message= message.substring(5,  message.length());
                        String id =    interceptado.substring(0, 1);
                        String score =    interceptado.substring(2, interceptado.length());
                      //  System.out.println("Interceptando mensaje fin desde servidor del cliente " + message);
                        jugadoresReportandoFin++;
                        stats4Player.put(id, score);
                        
                        if( jugadoresReportandoFin == tamanoRoom){//todos ya reportaron
                           // System.out.println("enviando reporte fin juego : reportes " +stats4Player.size()+ " total " + tamanoRoom );
                            message = enviandoResultadosFin();                        
                        }
                    }
                    
                    
                    
                    if ( message.contains("id")){//metodo que llega solo al servidor para el shakedown
                        
                        jugadoresConectados++;
                        //me doy de alta en el servidor
                        
                        String usuario = parseaUsuario(message);
                    //    System.out.println("Usuario :"+ usuario+", id "+ ith.getIdConexion()+" jugadores coenctados " +jugadoresConectados +" de esperados " + tamanoRoom);                         
                        jugadores.add(ith.getIdConexion(), usuario);
                        for (Connection jth : connections){
                             if( jth.getIdConexion() == ith.getIdConexion()){
                               // System.out.println("Solo lo recibo yo "+ ith.getIdConexion());
                                jth.println("@ID@"+ith.getIdConexion()+"|"+ usuario+"<"+tamanoRoom);
                             }
                        }
                         
                         if ( jugadoresConectados == tamanoRoom){
                           //  System.out.println("llene cupo inicio juego");
                                for (Connection jth : connections){
                                    String mensajeJugadores="@START@";
                                    for ( int indiceJugador = 0 ; indiceJugador<jugadoresConectados; indiceJugador++){
                                        mensajeJugadores+="<"+indiceJugador+"|"+jugadores.get(indiceJugador)+">";
                                    }
                                    jth.println(mensajeJugadores);
                            }
                         }
                    }
                    else{
                    for (Connection jth : connections)
                        jth.println(message);
                    }
                }
            }

            try                 { Thread.sleep(100);   }
            catch (Exception e) { e.printStackTrace(); }
        }
    }

}
