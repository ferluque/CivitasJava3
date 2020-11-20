/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegoTexto;

import civitas.CivitasJuego;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.GestionesInmobiliarias;
import civitas.OperacionInmobiliaria;
import civitas.SalidasCarcel;

/**
 *
 * @author ferluque
 */
public class Controlador {

    private CivitasJuego juego;
    private VistaTextual vista;
    private boolean EsFinal;

    Controlador(CivitasJuego juego, VistaTextual vista) {
        this.juego = juego;
        this.vista = vista;
    }

    void juega() {
        final String separador = "-----------------------------------------------------";
        vista.setCivitasJuego(juego);
       
        while (!juego.finalDelJuego()) {
            vista.actualizarVista();
            vista.pausa();

            OperacionesJuego operacion = juego.siguientePaso();
            
            System.out.println(separador);
            System.out.println(juego.getJugadorActual().getNombre());
            vista.mostrarSiguienteOperacion(operacion);
            
            if (operacion != OperacionesJuego.PASAR_TURNO) {
                vista.mostrarEventos();
            }
            if (!juego.finalDelJuego()) {
                if (operacion == OperacionesJuego.COMPRAR) {
                    Respuestas respuesta = vista.comprar();
                    if (respuesta == Respuestas.SI) {
                        juego.comprar();
                        //Estaba aquí dentro
                    }
                    juego.siguientePasoCompletado(operacion); ///////////////////Estaba mal
                }
                if (operacion == OperacionesJuego.GESTIONAR) {
                    vista.gestionar();
                    GestionesInmobiliarias gestion = GestionesInmobiliarias.values()[vista.getGestion()];
                    int ip = vista.getPropiedad();
                    OperacionInmobiliaria opInm = new OperacionInmobiliaria(gestion, ip);

                    if (opInm.getGestion() == GestionesInmobiliarias.CANCELAR_HIPOTECA) {
                        juego.cancelarHipoteca(opInm.getNumPropiedad());
                    }
                    if (opInm.getGestion() == GestionesInmobiliarias.CONSTRUIR_CASA) {
                        juego.construirCasa(opInm.getNumPropiedad());
                    }
                    if (opInm.getGestion() == GestionesInmobiliarias.CONSTRUIR_HOTEL) {
                        juego.construirHotel(opInm.getNumPropiedad());
                    }
                    if (opInm.getGestion() == GestionesInmobiliarias.HIPOTECAR) {
                        juego.hipotecar(opInm.getNumPropiedad());
                    }
                    if (opInm.getGestion() == GestionesInmobiliarias.VENDER) {
                        juego.vender(opInm.getNumPropiedad());
                    }
                    if (opInm.getGestion() == GestionesInmobiliarias.TERMINAR) {
                        juego.siguientePasoCompletado(operacion);
                    }
                }

                if (operacion == OperacionesJuego.SALIR_CARCEL) {
                    SalidasCarcel salida = vista.salirCarcel();
                    if (salida == SalidasCarcel.PAGANDO) {
                        juego.salirCarcelPagando();
                    }
                    if (salida == SalidasCarcel.TIRANDO) {
                        juego.salirCarcelTirando();
                    }
                    juego.siguientePasoCompletado(operacion);
                }
            }
        }
        System.out.println(juego.ranking());
    }
}
