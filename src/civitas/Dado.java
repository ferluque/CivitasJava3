/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.Random;

/**
 *
 * @author ferluque
 */
public class Dado {
    static final private Dado instance = new Dado();
    static final private int SalidaCarcel = 5;

    private Random random = new Random();
    private int ultimoResultado;
    private boolean debug;

    private Dado() {
        ultimoResultado = 0;
        debug = false;
    }

    static public Dado getInstance() {
        return instance;
    }

    int tirar() {
        if (debug) 
            ultimoResultado = 1;
        else
            ultimoResultado = random.nextInt(6)+1;
        return ultimoResultado;
    }

    boolean salgoDeLaCarcel() {
        return (tirar() == SalidaCarcel);
    }

    int quienEmpieza(int n) {
        return random.nextInt(n)+1;
    }

    public void setDebug(boolean d) {
        debug = d;
        Diario.getInstance().ocurreEvento("Se ha cambiado el modo del dado a " + d);
    }

    int getUltimoResultado() {
        return ultimoResultado;
    }
    
    public static void main (String args[]) {
        final int n = 4;
        //1. Generamos 100 veces un número aleatorio entre 1- 4 y después mostramos su probabilidad
        if (true) {
            int ni[] = new int[n];
            for (int i = 0; i < 100; i++) {
                int resultado = Dado.getInstance().quienEmpieza(n);
                ni[resultado-1]++;
            }
            for (int i = 0; i < n; i++) {
                System.out.println("Jugador " + i + ": " + ni[i] + " resultados sobre 100");
            }
        }

        //2. Tiramos 5 veces el dado con debug y 5 sin debug
        if (true) {
            Dado.getInstance().setDebug(true);
            System.out.println("Debug true");
            for (int i = 0; i < 5; i++) {
                System.out.println(Dado.getInstance().tirar());
            }
            Dado.getInstance().setDebug(false);
            System.out.println("Debug false");
            for (int i = 0; i < 5; i++) {
                System.out.println(Dado.getInstance().tirar());
            }
        }
        
        //3. Tiramos el dado 5 veces para ver si salimos de la clase, y mostramos el resultado del dado cada vez
        if (true) {
            for (int i = 0; i < 5; i++) {
                if (Dado.getInstance().salgoDeLaCarcel()) {
                    System.out.println(Dado.getInstance().getUltimoResultado());
                    System.out.println("Sales de la cárcel");
                } else {
                    System.out.println(Dado.getInstance().getUltimoResultado());
                    System.out.println("No sales de la cárcel");
                }
            }
        }
    }
}
