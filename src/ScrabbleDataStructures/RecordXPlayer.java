
package ScrabbleDataStructures;

/**
 *
 * @author Omar Verduga Palencia
 */
public class RecordXPlayer {
    
    
        private String nombre;
        private int partidasJugadas;
        private int partidasGanadas;
        private int puntuacionMaxima;
        private String palabraMasLarga;
        private String palabraMasValiosa;
        private int puntosPalabraMasValiosa;

        
        public RecordXPlayer( String nombre){
            this.nombre = nombre;
            partidasGanadas= 0 ;
            partidasJugadas= 0 ;
            palabraMasLarga= "_";
            palabraMasValiosa = "_";
            puntuacionMaxima= 0;
            puntosPalabraMasValiosa= 0;
        }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the partidasJugadas
     */
    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    /**
     * @param partidasJugadas the partidasJugadas to set
     */
    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    /**
     * @return the partidasGanadas
     */
    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    /**
     * @param partidasGanadas the partidasGanadas to set
     */
    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    /**
     * @return the palabraMasLarga
     */
    public String getPalabraMasLarga() {
        return palabraMasLarga;
    }

    /**
     * @param palabraMasLarga the palabraMasLarga to set
     */
    public void setPalabraMasLarga(String palabraMasLarga) {
        this.palabraMasLarga = palabraMasLarga;
    }

    /**
     * @return the palabraMasValiosa
     */
    public String getPalabraMasValiosa() {
        return palabraMasValiosa;
    }

    /**
     * @param palabraMasValiosa the palabraMasValiosa to set
     */
    public void setPalabraMasValiosa(String palabraMasValiosa) {
        this.palabraMasValiosa = palabraMasValiosa;
    }

    /**
     * @return the puntuacionMaxima
     */
    public int getPuntuacionMaxima() {
        return puntuacionMaxima;
    }

    /**
     * @param puntuacionMaxima the puntuacionMaxima to set
     */
    public void setPuntuacionMaxima(int puntuacionMaxima) {
        this.puntuacionMaxima = puntuacionMaxima;
    }

    /**
     * @return the puntosPalabraMasValiosa
     */
    public int getPuntosPalabraMasValiosa() {
        return puntosPalabraMasValiosa;
    }

    /**
     * @param puntosPalabraMasValiosa the puntosPalabraMasValiosa to set
     */
    public void setPuntosPalabraMasValiosa(int puntosPalabraMasValiosa) {
        this.puntosPalabraMasValiosa = puntosPalabraMasValiosa;
    }

}
