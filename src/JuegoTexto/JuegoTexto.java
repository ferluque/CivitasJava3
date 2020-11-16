/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegoTexto;

import civitas.CivitasJuego;
import civitas.Dado;
import civitas.GestionesInmobiliarias;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ferluque
 */
public class JuegoTexto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VistaTextual vista = new VistaTextual();
        CivitasJuego juego = new CivitasJuego((new ArrayList<>(Arrays.asList("Fernando", "Israel"))));
        
        Dado.getInstance().setDebug(false);
        
        Controlador controlador = new Controlador(juego, vista);
        
        controlador.juega();
        
    }
    
}
