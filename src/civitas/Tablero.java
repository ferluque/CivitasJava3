/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author ferluque
 */
public class Tablero {
    private int numCasillaCarcel = 1;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private boolean tieneJuez;
    
    public Tablero(int carcel) {
        if (carcel>=1) //&&carcel < 20?
            numCasillaCarcel = carcel;
        casillas = new ArrayList();
        casillas.add(new Casilla("Salida"));
        porSalida = 0;
        tieneJuez = false;
    }
    
    private boolean correcto() {
        return ((casillas.size()>numCasillaCarcel) && tieneJuez);
    }
    
    private boolean correcto(int numCasilla) {
        return (correcto() && (numCasilla >= 0) && (numCasilla < casillas.size()));
    }

    int getCarcel() {
        return numCasillaCarcel;
    }
    
    int getPorSalida() {
        return porSalida;
    }
    
    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }
    
    private void consultaCarcel () {
        if (casillas.size() == getCarcel())
            aniadeJuez();
    }
    
    void aniadeCasilla (Casilla casilla) {
        consultaCarcel();
        casillas.add(casilla);
        consultaCarcel();
    }
      
    void aniadeJuez() {
        if (!tieneJuez)
            casillas.add(new Casilla(getCarcel(), "Cárcel"));
        tieneJuez = true;
    }
    
    Casilla getCasilla(int numCasilla) {
        assert (numCasilla > 0 && numCasilla < 20);
        return casillas.get(numCasilla);
    }
    
    int nuevaPosicion (int actual, int tirada) {
        if (!correcto())
            return -1;
        else { 
            if ((actual+tirada)>20)
                porSalida++;
            return (actual+tirada)%20;
        }
    }
    
    int calculartirada (int origen, int destino) {
        if (destino > origen)
            return (destino-origen);
        else
            return (20-origen+destino);
    }

    @Override
    public String toString() {
        return "Tablero{" + "numCasillaCarcel=" + numCasillaCarcel + ", casillas.size=" + casillas.size() + ", porSalida=" + porSalida + ", tieneJuez=" + tieneJuez + '}';
    }
    
        
    
    
}
