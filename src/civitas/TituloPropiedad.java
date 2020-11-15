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
public class TituloPropiedad {
    static private final float factorInteresesHipoteca = (float)1.1;
    
    private float alquilerBase;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    
    private Jugador propietario;
    
    /*
    @brief Constructor con parámetros iniciales
    @params (nombre, alquilerBase, factorRevalorizacion, hipotecaBase, precioCompra, precioEdificar
    */
    TituloPropiedad(String nom, float ab, float fr, float hb, float pc, float pe) {
        nombre = nom;
        alquilerBase = ab;
        factorRevalorizacion = fr;
        hipotecaBase = hb;
        precioCompra = pc;
        precioEdificar = pe;
        
        hipotecado = false;
        numCasas = numHoteles = 0;
        propietario = new Jugador("");
    }
    
    /*
    @brief Método (paquete) para actualizar el atributo de rol (propietario)
    */
    void actualizaPropietarioPorConversion(Jugador jugador) {
        propietario = jugador;
    }
    
    boolean cancelarHipoteca (Jugador jugador) {
        boolean result = false;
        if (hipotecado && esEsteElPropietario(jugador)) {
            jugador.paga(getImporteCancelarHipoteca());
            hipotecado = false;
            result = true;
        }
        return result;
    }
    
    /*
    @brief Método consultor numCasas+numHoteles
    @return numCasas+numHoteles
    */
    int cantidadCasasHoteles() {
        return getNumCasas() + getNumHoteles();
    }
    
    boolean comprar(Jugador jugador) {
        boolean result = false;
        if (!tienePropietario()) {
            propietario = jugador;
            result = true;
            propietario.paga(getPrecioCompra());
        }
        return result;
    }
    
    //A implementar en prácticas posteriores
    boolean construirCasa (Jugador jugador) {
        boolean result = false;
        if (esEsteElPropietario(jugador)){
            propietario.paga(precioEdificar);
            numCasas++;
            result = true;
        }
        return result;
    }
    
    boolean construirHotel (Jugador jugador) {
        boolean result = false;
        if (esEsteElPropietario(jugador)) {
            propietario.paga(precioEdificar);
            numHoteles++;
            result = true;
        }
        return result;
    }
    
    /*
    @brief Se eliminan n casas si: el jugador es el propietario, no está hipotecado 
           y hay las suficientes casas
    @param jugador El jugador que quiere eliminar las casas
    @param n La cantidad de casas que desea eliminar
    @return true si se han podido eliminar, false si no
    */
    boolean derruirCasas (int n, Jugador jugador) {
        boolean derruidas = false;
        if (esEsteElPropietario(jugador) && !hipotecado && getNumCasas() >= n) {
            numCasas -= n;
            derruidas = true;            
        }
        return derruidas;
    }
    
    /*
    @brief Método que comprueba si el jugador es el propietario del título
    @param jugador El jugador que se va a comprobar
    @return true si es el propietario, false si no
    */
    private boolean esEsteElPropietario (Jugador jugador) {
        return jugador == propietario;
    }
    
    public boolean getHipotecado () {
        return hipotecado;
    }
    
    /*
    @brief Calcula el importe que se obtiene al hipotecar el título multiplicado
           por factorInteresesHipoteca
    @return El importe mencionado
    */
    float getImporteCancelarHipoteca() {
        return hipotecaBase*factorInteresesHipoteca;
    }
    
    /*
    @brief Método consultor de hipotecaBase
    @return hipotecaBase
    */
    private float getImporteHipoteca() {
        return hipotecaBase;
    }
    
    /*
    @brief Consultor de nombre
    @return nombre
    */
    String getNombre() {
        return nombre;
    }
    
    /*
    @brief Consultor de numCasas
    @return numCasas
    */
    int getNumCasas () {
        return numCasas;
    }
    
    /*
    @brief Consultor de numHoteles
    @return numHoteles
    */
    int getNumHoteles() {
        return numHoteles;
    }
    
    /*
    @brief Cálculo del precio a pagar de alquiler en función de casas y hoteles
    @return El precio a pagar
    */
    private float getPrecioAlquiler() {
        float precio = (float)0.0;
        if (!(propietarioEncarcelado() && hipotecado))
            precio = alquilerBase*(1+(getNumCasas()*(float)0.5)+(getNumHoteles()*(float)2.5));
        return precio;
    }
    
    /*
    @brief Consultor de precioCompra
    @return precioCompra
    */
    float getPrecioCompra () {
        return precioCompra;
    }
    
    /*
    @brief Consultor de precioEdificar
    @return precioEdificar
    */
    float getPrecioEdificar() {
        return precioEdificar;
    }
    
    /*
    @brief Cálculo del precio de venta con casas y hoteles
    @return precioCompra+factorRevalorizacion*precioEdificar*(numCasas+numHoteles)
    */
    private float getPrecioVenta() {
        return (getPrecioCompra()+factorRevalorizacion*((getNumCasas()+getNumHoteles())*getPrecioEdificar()));
    }
    
    /*
    @brief Consultor de propietario
    @return propietario
    */    
    Jugador getPropietario() {
        return propietario;
    }
    
    boolean hipotecar(Jugador jugador) {
        boolean salida = false;
        if (!hipotecado && esEsteElPropietario(jugador)) {
            propietario.recibe(getImporteHipoteca());
            hipotecado = true;
            salida = true;
        }
        return salida;
    }
       
    /*
    @brief Comprueba si el propietario está encarcelado (método de la clase Jugador)
    @return true si está encarcelado, false si no, o si no tiene propietario
    */
    private boolean propietarioEncarcelado() {
        return (tienePropietario() && !propietario.isEncarcelado());            
    }
    
    boolean tienePropietario() {
        return propietario.getNombre() != "";
    }

    @Override
    public String toString() {
        return "TituloPropiedad{" + "alquilerBase=" + alquilerBase + ", factorRevalorizacion=" + factorRevalorizacion + ", hipotecaBase=" + hipotecaBase + ", hipotecado=" + hipotecado + ", nombre=" + nombre + ", numCasas=" + numCasas + ", numHoteles=" + numHoteles + ", precioCompra=" + precioCompra + ", precioEdificar=" + precioEdificar + ", propietario= "+propietario.getNombre() + '}';
    }
    
    void tramitarAlquiler (Jugador jugador) {
        if (tienePropietario() && !(esEsteElPropietario(jugador))) {
            float alquiler = getPrecioAlquiler();
            jugador.pagaAlquiler(alquiler);
            propietario.recibe(alquiler);
        }        
    }
    
    boolean vender (Jugador jugador) {
        if (esEsteElPropietario(jugador)&& !hipotecado) {
            propietario.recibe(getPrecioVenta());
            propietario = new Jugador("");
            return true;
        }
        return false;
    }
    
    public static void main (String args[]) {
        TituloPropiedad propiedad = new TituloPropiedad("Estación de Granada", (float)100, (float)1.1, (float)250, (float)350, (float)50);
        ArrayList<Jugador> jugadores = new ArrayList();
        
        jugadores.add(new Jugador("Fernando"));
        jugadores.add(new Jugador("Israel"));
        
        System.out.println("\nMétodo actualizaPropietarioPorConversion: ");
        propiedad.actualizaPropietarioPorConversion(jugadores.get(0));
        System.out.println(propiedad.toString());
        
        jugadores.get(1).modificarSaldo(1000);
        
        System.out.println("\nMétodo tramitarAlquiler: ");
        propiedad.tramitarAlquiler(jugadores.get(1));
        System.out.println(propiedad.toString());
        
        //Los tests de los otros métodos se implementarán en prácticas posteriores en las que la clase esté completa 
        
        
    }
}
