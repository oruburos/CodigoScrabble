/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabbleEGC.net;

import scrabbleMVC.model.ScrabbleCell;
import java.net.Socket;
import java.util.ArrayList;
import scrabbleEGC.ScrabbleConstants;
import scrabbleEGC.ScrabbleUtils;
import scrabbleMVC.controller.ScrabbleController;
import scrabbleMVC.model.ScrabbleMovement;


/**
 *
 * @author Omar Verduga Palencia
 */
public class ScrabbleClientRunnable implements Runnable{

    private String screenName;

    // socket for connection to chat server
    private Socket socket;
    
    
    public String saludo;
    
    // for writing to and reading from the server
    public Out out;
    public In in;
    public String idCliente;
    public ScrabbleController controller;
    
    public ScrabbleClientRunnable( String id , ScrabbleController c){
       
           this(id, "localhost");
        
        controller = c;
       // System.out.println("identificador " + id );
        idCliente = id;
     
    }
    
    public ScrabbleClientRunnable(String screenName, String hostName) {

        // connect to server
        try {
            
           // System.out.println("creando socket " +hostName);
            socket = new Socket( ScrabbleConstants.HOST, ScrabbleConstants.PUERTO);            
            out    = new Out(socket);
            in     = new In(socket);
        }
        catch (Exception ex) { 
        controller.muestraMensaje("No hay ningun servidor");
        }
        /*
        System.out.println("socket creado "+screenName);
        this.screenName = screenName;*/


    }


    @Override
    public void run() {
        String s=saludo;
        //System.out.println("en listen " );
        while ((s = in.readLine()) != null) {
            
           // System.out.println( "cliente "+screenName +" recibe:"+s );
            procesaMsgNT( s );
        }
       
        
            System.out.println("Saliendo" );
        out.close();
        in.close();
        try                 { socket.close();      }
        catch (Exception e) { e.printStackTrace(); }
        System.err.println("Closed client socket");
        
    }
    
    /** Metodo que es llamado cada vez que llega un mensaje de red
     * este metodo solo encausa al metodo necesario el mensaje
     * 
     * @param msg 
     */
    public void procesaMsgNT( String msg) {
        
           // System.out.println("------------------------------Mensaje a procesar "  + msg );
          if ( msg.contains("@WINNER@")){
            //  System.out.println("recibiendo terminar");
            terminaJuego( msg);
          }
        else
          if ( msg.contains("@REPORTAR@")){
            procesaIsFin( msg);
        }else 
         if ( msg.contains("@ISFIN@")){
            procesaIsFin( msg);
        }else 
         if ( msg.contains("@FIN@")){
            procesaFin( msg);
        }else 
        
        if ( msg.contains("@SCORE@")){
            procesaScore( msg);
        }else 
        
        if ( msg.contains("@REPONER@")){
            procesaReponer( msg);
        }else 
        if ( msg.contains("@JUGADA@")){
            procesaJugada( msg);
        }else 
        if ( msg.contains("@TURNO@")){
            procesaTurno( msg);
        }else 
         
        if ( msg.contains("@MANO@")){
            procesaPrimeraMano( msg);
        }else 
        if ( msg.contains("@START@")){
            procesaInicioJuego( msg);
        }else 
        if ( msg.contains("@ID@")){
            procesaID( msg);
        }
        else{
        //System.out.println(" Procesando paquete " + msg);
        int indice =  msg.indexOf("|");
        
        if ( indice != 1 && indice!=-1){
            System.out.println("otro tipode paquete");
            String user = msg.substring( 0 ,indice);
          //  System.out.println("Usuario que lo envio" + user);
        
            
            if ( !user.equals(this.screenName)){
            
                String mensajeReal = msg.substring(indice+1, msg.length() );
                controller.updateView( mensajeReal);
                //System.out.println("posible id :" + );
            
            }
        }
        }
    }
    /*
    public void parseaMsgNT(String msg){
    
        int indice = msg.indexOf(">");
        if ( indice !=-1 ){
        //mensaje de id
            String idConexion = msg.substring( 0 ,indice);
           // System.out.println("Conexion que lo envio" + idConexion);
        
            
            //int jugadorCliente = ScrabbleUtils.StringToInt(idConexion);
            int partePaneles = msg.indexOf("<");
            
            String paneles = msg.substring(partePaneles+1, msg.length());
            int cuantosPaneles = ScrabbleUtils.StringToInt( paneles);
            
           // System.out.println(" paneles a activar segun red" + cuantosPaneles);
            
            String nick = msg.substring(indice+1, msg.length() );
            
            controller.setNumJugadores(cuantosPaneles);
            controller.updatePanelJugador( idConexion, nick, cuantosPaneles);
            
        }
    
    
    }*/

    /* 0 cotrol de errroes*/
    private void procesaInicioJuego(String msg) {
        
        String cadenaActual = msg.substring(7, msg.length());
     
       // System.out.println("Mensaje inicio jeugo " + msg);
        for (int i = 0 ; i < controller.getNumJugadores(); i++){    
            int indexFin=cadenaActual.indexOf(">");
            int divisor=cadenaActual.indexOf("|");
            
            String idConexion =cadenaActual.substring(1, divisor);
            String nick =  cadenaActual.substring(divisor+1, indexFin);
           // System.out.println(" jugador "+idConexion +" nombre "+ nick +" cliente " + controller.getNumCliente());                        
            cadenaActual= cadenaActual.substring( indexFin+1, cadenaActual.length());
            if ( (idConexion+"|"+nick).equals( controller.getIdNickname())){
                controller.setNumCliente(ScrabbleUtils.StringToInt(idConexion));
              //  System.out.println("Mi numero de cliente es " + controller.getNumCliente());
                
            }
            
            controller.updatePanelJugador( idConexion, nick , controller.getNumJugadores());
//            controller.initScores(controller.getNumJugadores());
           
         }
     controller.iniciaJuegoOnline();
    
    
    }
    /** procesa paquete con informacio para
     * procesar el ID
     * 
     * @param msg paquete a parsear
     */

    private void procesaID(String msg) {
       // System.out.println("procesa ID");
        msg = msg.substring(4, msg.length());
    
        String idCliente ="";
       int indice = msg.indexOf("|");
        if ( indice !=-1 ){
        //mensaje de id
            String idConexion = msg.substring( indice-1 ,indice);
            //System.out.println("Conexion que lo envio" + idConexion);
        
            
            //int jugadorCliente = ScrabbleUtils.StringToInt(idConexion);
            int partePaneles = msg.indexOf("<");
            String nick= msg.substring(indice+1, partePaneles);
           // System.out.println("Nick" + msg.substring(indice+1, partePaneles));
            
            String paneles = msg.substring(partePaneles+1, msg.length());
            int cuantosPaneles = ScrabbleUtils.StringToInt( paneles);
            
            
            controller.setNumJugadores(cuantosPaneles);
            controller.updatePanelJugador( idConexion, nick, cuantosPaneles);
            controller.setIdNickname(idConexion+"|"+nick);
            controller.setNombreJugador(nick);
            controller.setIdConexion( idConexion);
            
            this.screenName = controller.getIdNickname();
            this.idCliente = idCliente;
        }
    }

    
        /** procesa paquete con informacio para
     * procesar el turno adecuado
     * 
     * @param msg paquete a parsear
     */

    private void procesaTurno(String msg) {
       
        
        //este es para todos los escuchas
   //    System.out.println("proceso turno>: " + msg);
        msg = msg.substring(7, msg.length());
    
        int turnoCliente = ScrabbleUtils.StringToInt(msg.charAt(0));
        int indice = msg.indexOf("|");
        String turnoActualString = msg.substring(indice+1,indice+2);
        
       // System.out.println("turno global " + turnoActualString);
        controller.setTurnoActual(ScrabbleUtils.StringToInt(turnoActualString));
        if( turnoCliente >0){
            if ( !controller.isJuegoIniciado() ){
            controller.setJuegoIniciado(true);
            //System.out.println("marco como iniciado");
            }
        }
        if ( turnoCliente == controller.getNumCliente()){  
            //controller.setTurnoActual( controller.getTurnoActual()+1);
            // System.out.println("ES MI TURNO cliente " +controller.getNumCliente() +" turno cliente"+turnoCliente+ " turno Global " + controller.getTurnoActual());
               
            if (! controller.isJuegoIniciado()){
             //   controller.enNuevoTurno();
                controller.iniciaJuego();
            }else{
                controller.setMiTurno(true);
                //System.out.println("regresando a mi turno");            
            }
        }else{
 //               System.out.println("turno del cliente "+ turnoCliente + " y yo soy "+controller.getNumCliente() );
 
        }
         controller.muestraTurnoActual();
        
    }
    /** procesa paquete con informacio para
     * procesar las piezas de la primera mano
     * 
     * @param msg paquete a parsear
     */

    private void procesaPrimeraMano(String msg) {
        
    
        
        ArrayList<ScrabbleCell> mazoEnviado = new ArrayList<ScrabbleCell>();
        
        String cadenaActual = msg.substring(6, msg.length());
        String idConexion = ""+cadenaActual.charAt(0);
      //  System.out.println(" idConexion de msg " + idConexion + " id cliente "+ controller.getIdConexion());
        
        if ( idConexion.equals(controller.getIdConexion() )){
        //System.out.println(" Se recibio mazo " + msg);
            int numCartas = ScrabbleUtils.StringToInt(""+ cadenaActual.charAt(2));
          //  System.out.println(" id conexion"+ idConexion +" num cartas "+ numCartas);
            cadenaActual = cadenaActual.substring(3,cadenaActual.length());
           // System.out.println("Ahora se procesa mazo " +cadenaActual );
            for (int i = 0 ; i < numCartas; i++){    
                int indexInicio=cadenaActual.indexOf("<");
                int indexFin=cadenaActual.indexOf(">");
                String carta = cadenaActual .substring(indexInicio, indexFin+1);
               // System.out.println(" Carta " + carta);
                
                String letra = carta.substring(1,2);
                cadenaActual= cadenaActual.substring(indexFin+1, cadenaActual.length());
                 ScrabbleCell  cartaObj;
                if ( letra.equals( "*")){
                  cartaObj = new ScrabbleCell("resources/cmd.gif" ,  carta, true);
                }else{ 
                    //System.out.println("recibiendo |"+letra+"|");
                  cartaObj = new ScrabbleCell( "resources/"+letra.toUpperCase()+".gif",  carta, true);
                
                }
                mazoEnviado.add(cartaObj);
                 // System.out.println(" resta  " + cadenaActual);
            }
            
            controller.updateMazoCliente( mazoEnviado);
            
         }
    
    }

     /** procesa paquete con informacio para
     * procesar un numero diverso de jugadas
     * 
     * @param msg paquete a parsear
     */


    private void procesaJugada(String msg) {
        
        
        ArrayList<ScrabbleMovement> mazoEnviado = new ArrayList<ScrabbleMovement>();
        
        String cadenaActual = msg.substring(8, msg.length());
        String idConexion = ""+cadenaActual.charAt(0);
        
        
        if ( !idConexion.equals(controller.getIdConexion() )){//yo no quiero procesar lo que envie
           // System.out.println("se recibio jugada" + msg);
            cadenaActual= cadenaActual.substring(2, cadenaActual.length());
              int  numMovs = ScrabbleUtils.StringToInt(cadenaActual.substring( 0,1));
             
            
            for ( int i =0 ; i < numMovs ; i++){
                int firstIndex = cadenaActual.indexOf("<");
                int lastIndex = cadenaActual.indexOf(">");
                
                 
                ScrabbleMovement mv = new ScrabbleMovement( cadenaActual.substring(firstIndex, lastIndex+1));
                cadenaActual = cadenaActual.substring(lastIndex+1, cadenaActual.length());
              //  System.out.println(" cadena ahora " +cadenaActual );
                mazoEnviado.add(mv);
            }
           
      //  System.out.println(" cliente " + controller.getNumCliente() +" turno actual en el cliente " + controller.getTurnoActual());    
        controller.aplicaMovimientos( mazoEnviado );
         }
        
        
        controller.muestraTurnoActual(); 
        controller.actualizaTurno();
    }
    /** procesa paquete con informacio para
     *reponer piezas
     * 
     * @param msg paquete a parsear
     */

    private void procesaReponer(String msg) {
        msg= msg.substring(9, msg.length());
        
        int enviadoA = ScrabbleUtils.StringToInt(msg.charAt(0) );
        
        if ( enviadoA!= controller.getNumCliente() ){
           // System.out.println("Descartar, no es pa mi");
        }else{
           
      //  System.out.println(" procesando reponer con  " + msg);
        ArrayList<ScrabbleCell> mazoEnviado = new ArrayList<ScrabbleCell>();
        
      int numCartas = ScrabbleUtils.StringToInt(""+ msg.charAt(2));
            msg = msg.substring(3,msg.length());
            for (int i = 0 ; i < numCartas; i++){    
                int indexInicio=msg.indexOf("<");
                int indexFin=msg.indexOf(">");
                String carta = msg .substring(indexInicio, indexFin+1);
               // System.out.println(" Carta " + carta);
                
                String letra = carta.substring(1,2);
                msg= msg.substring(indexFin+1, msg.length());
                 ScrabbleCell  cartaObj;
                if ( letra == "*"){
                  cartaObj = new ScrabbleCell("resources/cmd.gif" ,  carta, true);
                }else{
                  cartaObj = new ScrabbleCell( "resources/"+letra.toUpperCase()+".gif",  carta, true);
                
                }
                mazoEnviado.add(cartaObj);
            }
            
            controller.updateMazoCliente( mazoEnviado);
            
         
            
        }
        
        
        
    }
    /** procesa paquete con informacio para
     * procesar el score
     * 
     * @param msg paquete a parsear
     */

    private void procesaScore(String msg) {
        
        
        msg = msg.substring(7, msg.length());
        
        int indexBarra = msg.indexOf("|");
        
        int numCliente = ScrabbleUtils.StringToInt(msg.substring(0,indexBarra));
        
        if ( numCliente!= controller.getNumCliente()){
            String score        = msg.substring(indexBarra +1 , msg.length());
           
            System.out.println("actualizar cliente "+ numCliente + " score " + score );
            
            controller.updateScore( numCliente , score);
        }
    }

    private void procesaIsFin(String msg) {
  
    //    controller.informa
    
    }
    
    /** Enviando orden de terminar juego
     * 
     * @param msg 
     */
    private void procesaFin(String msg) {
        
        System.out.println("Terminando partida");
         controller.terminarJuego();
        
    
    
    }

    private void terminaJuego(String msg) {
        System.out.println("***********************************************************"+msg);
        controller.muestraGanador( msg.substring(8,9));//optimismo incorregible
        
    
    }

   
}

