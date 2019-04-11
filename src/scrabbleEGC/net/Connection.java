
package scrabbleEGC.net;

import java.net.Socket;

public class Connection extends Thread {
    private Socket socket;
    private int idConexion;
    private Out out;
    private In in;
    private String message;     

    public Connection(Socket socket , int i) {
        in  = new In(socket);
        out = new Out(socket);
        this.socket = socket;
        idConexion = i;
       // System.out.println("Creando conexion : " + idConexion );
    }

    public void println(String s) { 
     //   System.out.println("Recibiendo "+idConexion +"->"  +s );
        out.println(s);
    }

    public void run() {
        String s;
        while ((s = in.readLine()) != null) {
            setMessage(s);
        }
        out.close();
        in.close();
        try                 { socket.close();      }
        catch (Exception e) { e.printStackTrace(); }
        System.err.println("closing socket");
    }


   /*
    *  Se puede leer pero no se puede poner el mensaje en cualquier isntante, se necesita sincronizar
    * 
    **/
    public synchronized String getMessage() {
        if (message == null) return null;
        String temp = message;
        message = null;
        notifyAll();
        return temp;
    }

    public synchronized void setMessage(String s) {
        if (message != null) {
            try                  { wait();               }
            catch (Exception ex) { ex.printStackTrace(); }
        }
        message = s;
    }

    /**
     * @return the idConexion
     */
    public int getIdConexion() {
        return idConexion;
    }

    /**
     * @param idConexion the idConexion to set
     */
    public void setIdConexion(int idConexion) {
        this.idConexion = idConexion;
    }

}
