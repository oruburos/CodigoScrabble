package ScrabbleIO;

import ScrabbleDataStructures.Estadisticas;
import ScrabbleDataStructures.PartidaDB;
import ScrabbleDataStructures.RecordXPlayer;
import scrabbleMVC.model.ScrabbleBoard;
import scrabbleMVC.model.ScrabbleCell;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import scrabbleEGC.ScrabbleConstants;
import scrabbleEGC.ScrabbleUtils;

/**
 *
 * @author Omar Verduga Palencia
 */
public class IO {

  
    /**
     * Guarda la partida en el archivo dado como parametro
     *
     * @param board la configuracion de la ultima partida
     * @param file archivo donde ponerlo
     */
    public static void savePartida(final PartidaDB partida, final String file) {
        try (
                final FileWriter writer = new FileWriter(file)) {

            ScrabbleBoard board = partida.getTablero();
            int idGanador = partida.getIdGanador();

            String header = "*winner=" + idGanador + "*";
            HashMap<String, String> stats4Player = partida.getJugadores();
            Iterator it = stats4Player.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry<String, String>) it.next();
                System.out.println(" cliente " + pairs.getKey() + " score " + pairs.getValue());

                header += "<" + pairs.getKey() + "|" + pairs.getValue() + ">";

            }
            writer.write(header);

            for (int r = 0; r < 15; r++) {
                for (int c = 0; c < 15; c++) {
                    ScrabbleCell temp = board.getElement(r, c);
                    if (temp != null) {
                        final String letter = temp.getLetter();
                        writer.write(c + "#" + r + "#" + letter + ", ");
                    }
                }
            }
            writer.close();
        } catch (final IOException ex) {
            System.err.println("Unable to save tiles!");
        }
    }

    /**
     * 
     * @param board es la ultima posicion mas scores y ganadores
     */
    public static void guardaUltimaPosicion(final PartidaDB board) {

        System.out.println("guardando posiciones");
        savePartida(board, "ultimaPartida.txt");
        System.out.println("ultima posicion guardada");

    }

    /** Recupera el archivo de la ultima partida y lo regresa como PartidaDB
     */
    public static PartidaDB recuperaUltimaPosicion() {

        // System.out.println("recueprando posiciones");
        PartidaDB tablero = cargaPartida("ultimaPartida.txt");
        // System.out.println("recuperado tal vez");

        return tablero;

    }
    
    
    /**Se cera una partida de un archivo dado
     * 
     * @param filePath donde esta la partida
     * @return objeto partidaDB
     */
    
    public static PartidaDB cargaPartida(final String filePath) {
        PartidaDB partida = new PartidaDB();
        ScrabbleBoard board = new ScrabbleBoard();
        File file = new File(filePath);
        if (file.exists()) {
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    ScrabbleCell s = board.getElement(i, j);
                    if (s != null) {
                        s.setLetter("");
                    }
                }
            }
            try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {

                    int indexInicio = line.indexOf("*");
                    int indexFin = line.lastIndexOf(">");

                    if (indexInicio == -1 || indexFin == -1) {
                        System.out.println("linea de jugadas");
                    } else {
                        System.out.println("parsear encabezado");
                        String header = line.substring(indexInicio, indexFin + 1);
                        System.out.println("parseare encabezad " + header);

                        int ganador = ScrabbleUtils.StringToInt(header.charAt(8));
                        System.out.println("el ganador es : " + ganador);

                        partida.setIdGanador(ganador);

                        String scores = header.substring(10, header.length());//optimismo again

                        int iteraciones = 0;

                        for (int i = 0; i < scores.length(); i++) {
                            if (scores.charAt(i) == '<') {
                                iteraciones++;
                            }
                        }
                        System.out.println("hay iteraciones" + iteraciones);

                        HashMap<String, String> datos = new HashMap<String, String>();
                        for (int j = 0; j < iteraciones; j++) {

                            int sepRecord = scores.indexOf("|");
                            int finRecord = scores.indexOf(">");

                            String jugador = scores.substring(1, sepRecord);
                            String score = scores.substring(sepRecord + 1, finRecord);

                            System.out.println("valores " + jugador + "/" + score);
                            datos.put(jugador, score);
                            scores = scores.substring(finRecord + 1, scores.length());
                        }

                        partida.setJugadores(datos);

                    }

                    line = line.substring(indexFin + 1, line.length());
                    final String[] splits = line.split(", ");
                    for (final String inf : splits) {
                        final String[] vars = inf.split("#");
                        //System.out.println("poniendo " +ScrabbleUtils.StringToInt(vars[0])+" " + ScrabbleUtils.StringToInt(vars[1])+" "+vars[2] );                      
                        board.putPiece(ScrabbleUtils.StringToInt(vars[0]), ScrabbleUtils.StringToInt(vars[1]), vars[2]);
                    }
                }
                board.printAll();
                br.close();
                partida.setTablero(board);
            } catch (final IOException | NumberFormatException e) {//son excepciones de java7, ojo con esto
                System.err.println("Unable to load saved tiles!");
            }
        } else {

            System.out.println("archivo no hallado");
        }

        return partida;
    }
    
    /*
     private static void parseaHeader(String header) {

     System.out.println("parseare encabezad " + header);

     int ganador = ScrabbleUtils.StringToInt(header.charAt(8));
     System.out.println("el ganador es : " + ganador);

     String scores = header.substring(10, header.length());//optimismo again

     int iteraciones = 0;

     for (int i = 0; i < scores.length(); i++) {
     if (scores.charAt(i) == '<') {
     iteraciones++;
     }

     System.out.println("hay iteraciones" + iteraciones);

     for (i = 0; i < iteraciones; i++) {

     int sepRecord = scores.indexOf("|");
     int finRecord = scores.indexOf(">");

     String jugador = scores.substring(1, sepRecord);
     String score = scores.substring(sepRecord + 1, finRecord);

     System.out.println("valores " + jugador + "/" + score);
     scores = scores.substring(finRecord + 1, scores.length());
     }

     }

     }

    
     */

    
    /**
     * Metodo que carga el archivo de ayuda definido en ScrabbleConstants
     * se usa StringBuffer para evitar tanta referencia a cadena
     * @return el archivo de ayuda en formato txt
     * 
     */
    public static String loadAyuda() {

        StringBuffer sb = new StringBuffer("");
        File file = new File(ScrabbleConstants.NOMBRE_ARCHIVO_AYUDA);
        if (file.exists()) {
            try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
            } catch (final IOException | NumberFormatException e) {//son excepciones de java7, ojo con esto
                System.err.println("Error, no pude cargar archivo ayuda");
            }
        } else {

            System.out.println("archivo no hallado");
        }

        return sb.toString();
    }

    /**Metodo que envia las estadisticas actuales a disco
     * 
     * 
     * @param historia 
     */
    public static void guardaHistorico(final Estadisticas historia) {

        System.out.println("guardando posiciones");
        salvaHistoria(historia, ScrabbleConstants.NOMBRE_ARCHIVO_HISTORICO);
        System.out.println("ultima posicion guardada");

    }
    
/**
 * Metodo que regresa un archivo de disco conteniendo los datos historicos de juego
 *, dicho archivo es obtenido de las constantes de ScrabbleConstants
 * @return Un objeto Estadisticas
 */
    public static Estadisticas cargaHistorico() {

        System.out.println("guardando posiciones");
        Estadisticas a = cargaHistoria(ScrabbleConstants.NOMBRE_ARCHIVO_HISTORICO);
        System.out.println("ultima posicion guardada");
        return a;
    }

    /** Metodo para guardar historia a un archivo dado
     * 
     * @param historia actual
     * @param file archivo donde se quiere guardar
     */
    private static void salvaHistoria(final Estadisticas historia, String file) {

        try (
                final FileWriter writer = new FileWriter(file ,true)) {

            String header = "";
            HashMap<String, RecordXPlayer> stats4Player = historia.getHistoria();
            Iterator it = stats4Player.entrySet().iterator();
            RecordXPlayer r = null;
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry<String, RecordXPlayer>) it.next();
                //System.out.println(" cliente " + pairs.getKey() + " score " + pairs.getKey());
                r = (RecordXPlayer) pairs.getValue();
                header += "<" + pairs.getKey() + "&" + r.getPalabraMasLarga() + "&" + r.getPalabraMasValiosa() + "&" + r.getPartidasGanadas() + "&" + r.getPuntuacionMaxima() + "&" + r.getPartidasJugadas() + ">";

            }
            System.out.println("escribiendo" + header);
            writer.write(header);

            writer.close();
        } catch (final IOException ex) {
            System.err.println("Historia!");
        }

    }

    
    
    /**Metodo para obtener un objeto estadistcas de un archivo dado
     * 
     * @param filePath donde bsuco el archiuvo
     * @return Las estadisticas cargadas
     */
    private static Estadisticas cargaHistoria(String filePath) {

        Estadisticas historial = new Estadisticas();
        StringBuffer sb = new StringBuffer();
        File file = new File(filePath);
        if (file.exists()) {
            try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                int iteraciones = 0;
                String txtDB = sb.toString();

                for (int i = 0; i < txtDB.length(); i++) {
                    if (txtDB.charAt(i) == '<') {
                        iteraciones++;
                    }
                }
                System.out.println("hay iteraciones" + iteraciones);

                for (int i = 0; i < iteraciones; i++) {

                    int indexFin = txtDB.indexOf(">");

                    String record = txtDB.substring(0, indexFin + 1);
                    System.out.println(" record " + i + ":" +record );

                    
                    String[] inf = record.split("&");
                        
                    System.out.println( + inf.length+" poniendo " +inf[0]+" "+inf[1]+" "+inf[2]+" "+inf[3] +" "+inf[4]+" "+inf[5] );
                                  
                    
                    RecordXPlayer r = new RecordXPlayer(inf[0].substring(1, inf[0].length()));
                    r.setPalabraMasLarga(inf[1]);
                    r.setPalabraMasValiosa(inf[2]);
                    r.setPartidasGanadas(ScrabbleUtils.StringToInt(inf[3]));
                    
                    r.setPuntuacionMaxima(ScrabbleUtils.StringToInt(inf[4]));
                    r.setPartidasJugadas(ScrabbleUtils.StringToInt(inf[5].substring(0 , inf[5].length()-1)));
                    
                    txtDB = txtDB.substring(indexFin + 1, txtDB.length());
                    historial.InsertarJugador( r);
                }

                br.close();
            } catch (final IOException | NumberFormatException e) {//son excepciones de java7, ojo con esto
                System.err.println("Unable to load historia");
            }
        } else {

            System.out.println("archivo no hallado");
        }

        return historial;

    }

    /* metodo para probar grabado y carga */
    public static void testDummy() {

        Estadisticas e = new Estadisticas();
        String key = "omar";
        e.InsertarJugador(key);
        e.updatePartidasJugadas(key, 3);
        e.updatePartidasGanadas(key, 2);
        e.updateMaxScore(key,3);
        e.updatePalabraMasValiosa(key, "descanso");
        e.updatePalabraMasLarga(key, "cansancio");
        key = "otro";
        e.InsertarJugador(key);
        e.updatePartidasJugadas(key, 3);
        e.updatePartidasGanadas(key, 2);
        e.updatePalabraMasValiosa(key, "descanso");
        e.updatePalabraMasLarga(key, "cansancio");

        key = "tercero";
        e.InsertarJugador(key);

        guardaHistorico(e);

    }

}
