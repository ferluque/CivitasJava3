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
public class Casilla {

    private static int carcel;
    
    private float importe;
    private String nombre;

    TipoCasilla tipo;
    TituloPropiedad tituloPropiedad;
    Sorpresa sorpresa;
    MazoSorpresas mazo;

    //DESCANSO
    Casilla(String nombre) {
        init();
        this.nombre = nombre;
        tipo = TipoCasilla.DESCANSO;
    }
    //CALLE
    Casilla(TituloPropiedad titulo) {
        init();
        tituloPropiedad = titulo;
        tipo = TipoCasilla.CALLE;
    }
    //IMPUESTO
    Casilla(float cantidad, String nombre) {
        init();
        this.nombre = nombre;
        importe = cantidad;
        tipo = TipoCasilla.IMPUESTO;        
    }
    //JUEZ
    Casilla(int numCasillaCarcel, String nombre) {
        init ();
        this.nombre = nombre;
        tipo = TipoCasilla.JUEZ;
        carcel = numCasillaCarcel;
    }
    //SORPRESA
    Casilla(MazoSorpresas mazo, String nombre) {
        init();
        this.nombre = nombre;
        tipo = TipoCasilla.SORPRESA;
        this.mazo = mazo;
    }

    public String getNombre() {
        return nombre;
    }

    TituloPropiedad getTituloPropiedad() {
        return tituloPropiedad;
    }

    private void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("El jugador "+todos.get(actual).getNombre()+" ha caído en la casilla " + getNombre() + "\n"+toString());
    }

    private void init() {
        carcel = -1;
        importe = 0;
        nombre = "";
    }

    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        return (actual>= 0 && actual < todos.size());
    }

    //A implementar en la siguiente práctica
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        
    }
    private void recibeJugador_calle(int actual, ArrayList<Jugador> todos) {
        
    }

    private void recibeJugador_impuesto(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }

    private void recibeJugador_juez(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).encarcelar(carcel);
        }
    }

    //A implementar en la siguiente práctica
    private void recibeJugador_sorpresa(int actual, ArrayList<Jugador> todos) {

    }

    @Override
    public String toString() {
        return "Casilla{" + "importe=" + importe + ", nombre=" + nombre + ", tipo=" + tipo + ", tituloPropiedad=" + tituloPropiedad + ", sorpresa=" + sorpresa + ", mazo=" + mazo + '}';
    }
    
    public static void main (String args[]) {
        //Crea casilla DESCANSO
        Casilla descanso = new Casilla("Descanso");
        System.out.println(descanso.toString());
        
        //Crea casilla CALLE
        Casilla calle = new Casilla(new TituloPropiedad("Gran Vía", 150, (float)1.2, 200, (float)250, 50));
        System.out.println(calle.toString());
        
        //Crea casilla IMPUESTO
        Casilla impuesto = new Casilla((float)100, "Impuesto de luz");
        System.out.println(impuesto.toString());
        
        //Crea casilla JUEZ
        Casilla juez = new Casilla(10, "Cárcel");
        System.out.println(juez.toString());
        
        //Crea casilla SORPRESA
        Casilla sorpresa = new Casilla(new MazoSorpresas(), "Casilla Sorpresa");
        System.out.println(sorpresa.toString());
        
        
    }
    
}
