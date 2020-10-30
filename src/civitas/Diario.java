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
public class Diario {

    static final private Diario instance = new Diario();

    private ArrayList<String> eventos;

    static public Diario getInstance() {
        return instance;
    }

    private Diario() {
        eventos = new ArrayList<>();
    }

    void ocurreEvento(String e) {
        eventos.add(e);
    }

    public boolean eventosPendientes() {
        return !eventos.isEmpty();
    }

    public String leerEvento() {
        String salida = "";
        if (!eventos.isEmpty()) {
            salida = eventos.remove(0);
        }
        return salida;
    }
    
    public static void main (String args[]) {
        for (int i=0; i<5; i++)
            Diario.getInstance().ocurreEvento("Evento"+i);
        while (Diario.getInstance().eventosPendientes())
            System.out.println(Diario.getInstance().leerEvento());
        
    }
}
