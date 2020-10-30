/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ferluque
 */
public class MazoSorpresas {

    private boolean barajada;
    private int usadas;
    private boolean debug;
    private Sorpresa ultimaSorpresa;

    private ArrayList<Sorpresa> sorpresas;
    private ArrayList<Sorpresa> cartasEspeciales;

    private void init() {
        sorpresas = new ArrayList();
        cartasEspeciales = new ArrayList();
        barajada = false;
        usadas = 0;
    }

    MazoSorpresas(boolean d) {
        debug = d;
        init();
        if (debug) {
            Diario.getInstance().ocurreEvento("El modo debug(Mazo) está activado");
        }
    }

    MazoSorpresas() {
        init();
        debug = false;
    }

    void alMazo(Sorpresa s) {
        if (!barajada) {
            sorpresas.add(s);
        }
    }

    Sorpresa siguiente() {
        if ((!barajada || (sorpresas.size() == usadas)) && !debug) {
            Collections.shuffle(sorpresas);
            barajada = true;
            usadas = 0;
        }
        usadas++;
        ultimaSorpresa = sorpresas.remove(0);
        sorpresas.add(ultimaSorpresa);
        return ultimaSorpresa;
    }

    void inhabilitarCartaEspecial(Sorpresa sorpresa) {
        if (sorpresas.contains(sorpresa)) {
            sorpresas.remove(sorpresa);
            cartasEspeciales.add(sorpresa);
            Diario.getInstance().ocurreEvento("Se ha anulado una carta especial");
        }

    }

    void habilitarCartaEspecial(Sorpresa sorpresa) {
        if (cartasEspeciales.contains(sorpresa)) {
            cartasEspeciales.remove(sorpresa);
            sorpresas.add(sorpresa);
            Diario.getInstance().ocurreEvento("Se ha habilitado una carta especial");

        }
    }

    @Override
    public String toString() {
        return "MazoSorpresas{" + "barajada=" + barajada + ", usadas=" + usadas + ", debug=" + debug + ", ultimaSorpresa=" + ultimaSorpresa + ", sorpresas=" + sorpresas + ", cartasEspeciales=" + cartasEspeciales + '}';
    }
    
    

    public static void main(String args[]) {
        MazoSorpresas mazo = new MazoSorpresas();
        Sorpresa sorpresa1 = new Sorpresa(TipoSorpresa.IRCARCEL, new Tablero(-1));
        Sorpresa sorpresa2 = new Sorpresa(TipoSorpresa.IRCASILLA, new Tablero(-1), 5, "El jugador se cambia de casilla");
        mazo.alMazo(sorpresa1);
        mazo.alMazo(sorpresa2);
        System.out.println(mazo.toString());
        
        mazo.inhabilitarCartaEspecial(sorpresa2);
        System.out.println("Se anula carta especial:");
        System.out.println(mazo.toString());
        
        
        mazo.habilitarCartaEspecial(sorpresa2);
        System.out.println("Se habilita carta especial");
        System.out.println(mazo.toString());
        
        while (Diario.getInstance().eventosPendientes()) {
            System.out.println(Diario.getInstance().leerEvento());
        }
    }

}
