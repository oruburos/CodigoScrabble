
package scrabbleEGC.net;


import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Out {
    private PrintWriter out;

    // for stdout
    public Out(OutputStream os) { out = new PrintWriter(os, true); }
    public Out()                { this(System.out);                }

    // for Socket output
    public Out(Socket socket) {
        try                     { out = new PrintWriter(socket.getOutputStream(), true); }
        catch (IOException ioe) { ioe.printStackTrace();                                 }
    }
    // for file output
    public Out(String s) {
        try                     { out = new PrintWriter(new FileOutputStream(s), true);  }
        catch(IOException ioe)  { ioe.printStackTrace();                                 }
    }
    
    public void close() { out.close(); }

    public void println()          { out.println();  out.flush(); }
    public void println(Object x)  { out.println(x); out.flush(); }
    public void print()            {                 out.flush(); }
    public void print(Object x)    { out.print(x);   out.flush(); }



  

}

