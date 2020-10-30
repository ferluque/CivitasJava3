/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author fernando
 */



public class TestP1 {

    final static int n = 4;
    
    public static void main(String[] args) {
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
        
        
        System.out.println();
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

        //4. Muestra al menos un valor de cada tipo enumerado
        if (true) {
            System.out.println("EstadosJuego.INICIO_TURNO");
            System.out.println(EstadosJuego.INICIO_TURNO);
            System.out.println("TipoCasilla.SORPRESA");
            System.out.println(TipoCasilla.SORPRESA);
            System.out.println("TipoSorpresa.SALIRCARCEL");
            System.out.println(TipoSorpresa.SALIRCARCEL);
        }

        /*5 y 6. Crea un objeto MazoSorpresas y haz las siguientes pruebas: añade dos sorpresas al mazo,
        obtén la siguiente sorpresa en juego, inhabilita y habilita la segunda carta añadida. Dado que
        MazoSorpresas*/
//        if (true) {
//            MazoSorpresas mazo = new MazoSorpresas();
//            Sorpresa sorpresa1 = new Sorpresa ("Multa");
//            Sorpresa sorpresa2 = new Sorpresa;
//            mazo.alMazo(sorpresa1);
//            mazo.alMazo(sorpresa2);
//            Diario.getInstance().ocurreEvento("Se añaden dos sorpresas a mazo");
//            System.out.println(mazo.string_Sorpresas());
//            
//            mazo.inhabilitarCartaEspecial(sorpresa2);
//            Diario.getInstance().ocurreEvento("Se elimina una sorpresa del mazo");
//            System.out.println(mazo.string_Sorpresas());
//        }
//        
//        while (Diario.getInstance().eventosPendientes()) {
//            System.out.println(Diario.getInstance().leerEvento());
//        }
        /*7. Crea un tablero, añádele varias casillas y comprueba con el depurador que efectivamente la
        estructura del mismo es la que esperabas. Intenta provocar las situaciones erróneas
        controladas en la clase Tablero (por ejemplo, que la posición de la cárcel sea mayor que el
        tamaño del tablero) y comprueba que la gestión de las mismas es la correcta. Finalmente,
        realiza distintas tiradas con el dado y asegúrate de que se calcula correctamente la posición
        de destino en el tablero.*/
        Tablero tablero = new Tablero(10);
        System.out.println(tablero.toString());
        Tablero tablero2 = new Tablero(-10);
        System.out.println(tablero2.toString());
        
        tablero.aniadeJuez();
        tablero.aniadeCasilla(new Casilla("Casa1"));
        tablero.aniadeCasilla(new Casilla("Casa2"));
        tablero.aniadeCasilla(new Casilla("Casa3"));
        tablero.aniadeCasilla(new Casilla("Casa4"));
        tablero.aniadeCasilla(new Casilla("Casa5"));
        tablero.aniadeCasilla(new Casilla("Casa6"));
        tablero.aniadeCasilla(new Casilla("Casa7"));
        tablero.aniadeCasilla(new Casilla("Casa8"));
        tablero.aniadeCasilla(new Casilla("Casa9"));
        System.out.println(tablero.toString());
        
        System.out.println("Nueva posicion: " + tablero.nuevaPosicion(0, Dado.getInstance().tirar()));
        System.out.println("Ultimo resultado: " + Dado.getInstance().getUltimoResultado());
        System.out.println();
        
        System.out.println("Nueva posicion: " + tablero.nuevaPosicion(Dado.getInstance().getUltimoResultado(), Dado.getInstance().tirar()));
        System.out.println("Ultimo resultado: " + Dado.getInstance().getUltimoResultado());
        System.out.println();
        
        
    }

}
