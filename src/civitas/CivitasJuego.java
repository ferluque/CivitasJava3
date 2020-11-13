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
public class CivitasJuego {

    private int indiceJugadorActual;

    private MazoSorpresas mazo;
    private Tablero tablero; 
    private ArrayList<Jugador> jugadores = new ArrayList(0);
    private EstadosJuego estado;
    private GestorEstados gestorEstados;

    private void avanzaJugador() {
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
        contabilizarPasosPorSalida(jugadorActual);
    }

    public boolean cancelarHipoteca(int ip) {
        return getJugadorActual().cancelarHipoteca(ip);
    }

    public CivitasJuego(ArrayList<String> nombres) {
        for (int i = 0; i < nombres.size(); i++) {
            jugadores.add(new Jugador(nombres.get(i)));
        }
        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();

        indiceJugadorActual = Dado.getInstance().quienEmpieza(nombres.size()) - 1;

        mazo = new MazoSorpresas();
        tablero = new Tablero(10);

        inicializarMazoSorpresas(tablero);
        inicializarTablero(mazo);
    }

    
    public boolean comprar() {
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        Casilla casilla = tablero.getCasilla(jugadorActual.getNumCasillaActual());
        TituloPropiedad titulo = casilla.getTituloPropiedad();
        return jugadorActual.comprar(titulo);
    }

    public boolean construirCasa(int ip) {
        return getJugadorActual().construirCasa(ip);
    }

    public boolean construirHotel(int ip) {
        return getJugadorActual().construirHotel(ip);
    }

    private void contabilizarPasosPorSalida(Jugador jugadorActual) {
        if (tablero.getPorSalida() > 0) {
            jugadorActual.pasaPorSalida();
        }
    }

    public boolean finalDelJuego() {
        boolean bancarrota = false;
        for (int i = 0; i < jugadores.size() && !bancarrota; i++) {
            bancarrota = jugadores.get(i).enBancarrota();
        }
        return bancarrota;
    }

    public Casilla getCasillaActual() {
        return tablero.getCasilla(jugadores.get(indiceJugadorActual).getNumCasillaActual());
    }

    public Jugador getJugadorActual() {
        return jugadores.get(indiceJugadorActual);
    }

    public boolean hipotecar(int ip) {
        return getJugadorActual().hipotecar(ip);
    }

    public String infoJugadorTexto() {
        return getJugadorActual().toString();
    }

    private void inicializarMazoSorpresas(Tablero tablero) {
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCARCEL, tablero));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA, tablero, 16, "El jugador se desplaza a la casilla nº 16"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.SALIRCARCEL, mazo));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, 50, "El jugador paga 50 por una multa"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, 10 * jugadores.get(indiceJugadorActual).cantidadCasasHoteles(), "El jugador paga 10 por cada casa y cada hotel"));

        mazo.alMazo(new Sorpresa(TipoSorpresa.PORJUGADOR, 20 * (jugadores.size() - 1), "El jugador recibe 20 por cada jugador"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, 100, "Regalo de cumpleaños: El jugador recibe 100"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORJUGADOR, 10 * (jugadores.size() - 1), "El jugador debe pagar 10 a cada jugador"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, 5 * jugadores.get(indiceJugadorActual).cantidadCasasHoteles(), "El jugador recibe 5 por cada casa y por cada hotel"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCARCEL, tablero));

    }

    private void inicializarTablero(MazoSorpresas mazo) {
        //Añadimos 19 casillas, que más laque se añade al construir el objeto (Casilla de salida) harán 20
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Calle Ganivet", (float) 8, (float) 1.1, (float) 35, (float) 50, (float) 10)));
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Calle Pedro Antonio de Alarcón", (float) 12, (float) 1.12, (float) 45, (float) 75, (float) 25)));
        tablero.aniadeCasilla(new Casilla(mazo, "Casilla Sorpresa"));
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Avenida Juan Pablo II", (float) 17, (float) 1.15, (float) 75, (float) 125, (float) 35)));
        tablero.aniadeCasilla(new Casilla((float) 50, "Impuesto de circulación"));
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Joaquina Eguaras", (float) 25, (float) 1.2, (float) 85, (float) 175, (float) 50)));
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Gran Vía", (float) 30, (float) 1.25, (float) 95, (float) 200, (float) 60)));
        tablero.aniadeCasilla(new Casilla(mazo, "Casilla Sorpresa"));
        //Se añade automáticamente la cárcel
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Avenida de Madrid", (float) 35, (float) 1.27, (float) 105, (float) 225, (float) 65)));
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Avenida de Andalucía", (float) 40, (float) 1.29, (float) 120, (float) 250, (float) 80)));
        tablero.aniadeCasilla(new Casilla((float) 150, "Impuesto de sociedades"));
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Avenida de la Constitución", (float) 50, (float) 1.31, (float) 140, (float) 275, (float) 90)));
        tablero.aniadeCasilla(new Casilla("Segundo descanso"));
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Avenida de Pulianas", (float) 60, (float) 1.33, (float) 160, (float) 300, (float) 100)));
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Acera del Darro", (float) 75, (float) 1.35, (float) 170, (float) 350, (float) 110)));
        tablero.aniadeCasilla(new Casilla((float) 200, "IRPF"));
        tablero.aniadeCasilla(new Casilla(mazo, "Casilla Sorpresa"));
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Paseo de los tristes", (float) 80, (float) 1.34, (float) 175, (float) 315, (float) 115)));
    }

    private void pasarTurno() {
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
    }

    public ArrayList<Jugador> ranking() {
        ArrayList<Jugador> ordenado = new ArrayList(jugadores);
        Collections.sort(ordenado);
        Collections.reverse(ordenado);
        return ordenado;
    }

    public boolean salirCarcelPagando() {
        return getJugadorActual().salirCarcelPagando();
    }

    public boolean salirCarcelTirando() {
        return getJugadorActual().salirCarcelTirando();
    }

    public OperacionesJuego siguientePaso() {
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual, estado);
        if (operacion == OperacionesJuego.PASAR_TURNO) {
            pasarTurno();
            siguientePasoCompletado(operacion);
        } else if (operacion == OperacionesJuego.AVANZAR) {
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }

        return operacion;
    }

    public void siguientePasoCompletado(OperacionesJuego operacion) {
        estado = gestorEstados.siguienteEstado(getJugadorActual(), estado, operacion);
    }

    public boolean vender(int ip) {
        return getJugadorActual().vender(ip);
    }

    @Override
    public String toString() {
        return "CivitasJuego{" + "indiceJugadorActual=" + indiceJugadorActual + ", mazo=" + mazo + ", tablero=" + tablero + ", jugadores=" + jugadores + ", estado=" + estado + ", gestorEstados=" + gestorEstados + '}';
    }

    public static void main(String args[]) {
        ArrayList<String> nombres = new ArrayList();
        nombres.add("Fernando");
        nombres.add("Israel");
        nombres.add("Pedro");
        CivitasJuego juego = new CivitasJuego(nombres);
        System.out.println(juego.toString());

    }
}
