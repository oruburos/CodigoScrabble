/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabbleMVC.model;

import java.io.*;
import java.util.ArrayList;
import scrabbleEGC.ScrabbleConstants;

/**
 *
 * @author Omar Verduga Palencia
 */
public class ScrabbleDictionary {

    private static ArrayList<String> validWords;

    private ScrabbleDictionary() {
        validWords = new ArrayList<String>();
        try {
            BufferedReader ArchiDic = new BufferedReader(new FileReader(ScrabbleConstants.NOMBRE_ARCHIVO_DICCIONARIO));
            while (ArchiDic.ready()) {
                String l = ArchiDic.readLine();
                validWords.add(l.toString());
            }
            ArchiDic.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("No se encontro el archivo " + fnfe);
        } catch (IOException ioe) {
            System.out.println("No se pudo leer el archivo");
        }
    }

    private static ScrabbleDictionary INSTANCE = null;

    private static void ScrabbleDictionary() {
        if (INSTANCE == null) {
            INSTANCE = new ScrabbleDictionary();
        }
    }

    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ScrabbleDictionary();
        }
    }

    public static ScrabbleDictionary getInstance() {
        createInstance();
        return INSTANCE;
    }

    public static ArrayList<String> getDiccionario() {
        return validWords;
    }

    public boolean existePalabra(String palabra) {
        for (int i = 0; i < validWords.size(); i++) {
            String s = validWords.get(i);
            if (palabra.compareTo(s) == 0) {
                return true;
            }
        }
        return false;
    }
}
