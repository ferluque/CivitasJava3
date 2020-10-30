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
    
    Jugador propietario;
    
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
    
    //A implementar en prácticas posteriores
    boolean cancelarhipoteca (Jugador jugador) {
        return false;
    }
    
    /*
    @brief Método consultor numCasas+numHoteles
    @return numCasas+numHoteles
    */
    int cantidadCasasHoteles() {
        return getNumCasas() + getNumHoteles();
    }
    
    //A implementar en prácticas posteriores
    boolean comprar(Jugador jugador) {
        return false;
    }
    
    //A implementar en prácticas posteriores
    boolean construirCasa (Jugador jugador) {
        return false;
    }
    
    //A implementar en prácticas posteriores
    boolean construirHotel (Jugador jugador) {
        return false;
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
    
    //A implementar en prácticas posteriores
    boolean hipotecar(Jugador jugador) {
        return false;
    }
       
    /*
    @brief Comprueba si el propietario está encarcelado (método de la clase Jugador)
    @return true si está encarcelado, false si no, o si no tiene propietario
    */
    private boolean propietarioEncarcelado() {
        return (tienePropietario() && !propietario.isEncarcelado());            
    }
    
    boolean tienePropietario() {
        return propietario != null;
    }

    @Override
    public String toString() {
        return "TituloPropiedad{" + "alquilerBase=" + alquilerBase + ", factorRevalorizacion=" + factorRevalorizacion + ", hipotecaBase=" + hipotecaBase + ", hipotecado=" + hipotecado + ", nombre=" + nombre + ", numCasas=" + numCasas + ", numHoteles=" + numHoteles + ", precioCompra=" + precioCompra + ", precioEdificar=" + precioEdificar + ", propietario= "+propietario.toString() + '}';
    }
    
    void tramitarAlquiler (Jugador jugador) {
        if (tienePropietario() && (propietario.compareTo(jugador) != 0)) {
            float alquiler = getPrecioAlquiler();
            jugador.pagaAlquiler(alquiler);
            propietario.recibe(alquiler);
        }        
    }
    
    boolean vender (Jugador jugador) {
        if (jugador.compareTo(propietario) != 0 && !hipotecado) {
            propietario.recibe(getPrecioVenta());
            propietario = jugador;
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
