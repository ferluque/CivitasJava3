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
public class Jugador implements Comparable<Jugador> {

    static final protected int CasasMax = 4;
    static final protected int CasasPorHotel = 4;
    static final protected int HotelesMax = 4;
    static final protected float PasoPorSalida = (float) 1000;
    static final protected float PrecioLibertad = (float) 200;
    static final private float SaldoInicial = (float) 7500;

    protected boolean encarcelado;
    private String nombre;
    private int numCasillaActual;
    private boolean puedeComprar;
    private float saldo;

    private ArrayList<TituloPropiedad> propiedades = new ArrayList();
    private Sorpresa salvoconducto;

    boolean cancelarHipoteca(int ip) {
        boolean result = false;
        if (encarcelado) {
            return result;
        }
        if (existeLaPropiedad(ip)) {
            TituloPropiedad propiedad = propiedades.get(ip);
            float cantidad = propiedad.getImporteCancelarHipoteca();
            boolean puedoGastar = puedoGastar(cantidad);
            if (puedoGastar) {
                result = propiedad.cancelarHipoteca(this);
                if (result) {
                    Diario.getInstance().ocurreEvento("El jugador " + nombre + " cancela la hipoteca de la propiedad " + propiedad.getNombre());
                }
            }
        }
        return result;
    }

    int cantidadCasasHoteles() {
        int cantidad = 0;
        for (int i = 0; i < propiedades.size(); i++) {
            cantidad += propiedades.get(i).cantidadCasasHoteles();
        }
        return cantidad;
    }

    /**
     * @brief Compara el jugador referenciado (this) con otro
     * @param otro El jugador con el que se compara
     * @return -1 si this->saldo es menor, 0 si son iguales, 1 si this->saldo es
     * mayor (comparado con otro.saldo)
     */
    @Override
    public int compareTo(Jugador otro) {
        return Float.compare(this.saldo, otro.saldo);
    }

    boolean comprar(TituloPropiedad titulo) {
        boolean result = false;
        if (encarcelado) {
            return result;
        }
        if (puedeComprar) {
            if (puedoGastar(titulo.getPrecioCompra())) {
                result = titulo.comprar(this);
                if (result) {
                    propiedades.add(titulo);
                    Diario.getInstance().ocurreEvento("El jugador " + nombre + " compra la propiedad " + titulo.toString());
                }
                puedeComprar = false;
            }
        }
        return result;
    }

    boolean construirCasa(int ip) {
        boolean result = false, existe, puedoEdificarCasa = false;
        if (encarcelado) {
            return result;
        } else {
            existe = existeLaPropiedad(ip);
        }
        if (existe) {
            TituloPropiedad propiedad = propiedades.get(ip);
            puedoEdificarCasa = puedoEdificarCasa(propiedad);
            if (puedoEdificarCasa) {
                result = propiedad.construirCasa(this);
                if (result) {
                    Diario.getInstance().ocurreEvento("El jugador " + nombre + " construye casa en la propiedad " + ip);
                }
            }
        }
        return result;
    }

    boolean construirHotel(int ip) {
        boolean result = false;
        if (encarcelado) {
            return result;
        }
        if (existeLaPropiedad(ip)) {
            TituloPropiedad propiedad = propiedades.get(ip);
            if (puedoEdificarHotel(propiedad)) {
                result = propiedad.construirHotel(this);
                int casasPorHotel = getCasasPorHotel();
                propiedad.derruirCasas(casasPorHotel, this);
                Diario.getInstance().ocurreEvento("El jugador " + nombre + " construye un hotel en la propiedad " + ip);
            }
        }
        return result;
    }

    /**
     * @brief Analiza si el jugador debe ser encarcelado, teniendo en cuenta que
     * tenga o no SalvoConducto
     * @return true si !tiene SalvoConducto, false si encarcelado ||
     * (!encarcelado && tieneSalvoConducto)
     */
    protected boolean debeSerEncarcelado() {
        boolean resultado = false;
        if (!encarcelado) {
            if (tieneSalvoconducto()) {
                perderSalvoconducto();
                Diario.getInstance().ocurreEvento("Jugador " + nombre + " se libra de la cárcel");
            } else {
                resultado = true;
            }
        }
        return resultado;
    }

    /**
     * @brief Analiza si el jugador se ha quedado sin saldo
     * @return true if saldo negativo; false si no
     */
    boolean enBancarrota() {
        return saldo <= 0;
    }

    boolean encarcelar(int numCasillaCarcel) {
        if (debeSerEncarcelado()) {
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            Diario.getInstance().ocurreEvento("El jugador " + nombre + " es encarcelado");
        }
        return encarcelado;
    }

    //No sé lo que hace*********************
    private boolean existeLaPropiedad(int ip) {
        return (ip >= 0 && ip < propiedades.size());
    }

    /**
     * @brief Consultor CasasMax
     * @return CasasMax
     */
    private static int getCasasMax() {
        return CasasMax;
    }

    static int getCasasPorHotel() {
        return CasasPorHotel;
    }

    private static int getHotelesMax() {
        return HotelesMax;
    }

    public String getNombre() {
        return nombre;
    }

    int getNumCasillaActual() {
        return numCasillaActual;
    }

    private static float getPrecioLibertad() {
        return PrecioLibertad;
    }

    private static float getPremioPasoSalida() {
        return PasoPorSalida;
    }

    public ArrayList getPropiedades() {
        return propiedades;
    }

    boolean getPuedeComprar() {
        return puedeComprar;
    }

    protected float getSaldo() {
        return saldo;
    }

    boolean hipotecar(int ip) {
        boolean result = false;
        if (encarcelado) {
            return result;
        }
        if (existeLaPropiedad(ip)) {
            TituloPropiedad propiedad = propiedades.get(ip);
            result = propiedad.hipotecar(this);
        }
        if (result) {
            Diario.getInstance().ocurreEvento("El jugador" + nombre + " hipoteca la propiedad " + ip);
        }
        return result;
    }

    public boolean isEncarcelado() {
        return encarcelado;
    }

    Jugador(String nombre) {
        this.nombre = nombre;
        this.saldo = SaldoInicial;
    }

    protected Jugador(Jugador otro) {
        this.encarcelado = otro.encarcelado;
        this.nombre = otro.nombre;
        this.numCasillaActual = otro.numCasillaActual;
        this.puedeComprar = otro.puedeComprar;
        this.saldo = otro.saldo;
    }

    boolean modificarSaldo(float cantidad) {
        saldo += cantidad;
        if (cantidad > 0) {
            Diario.getInstance().ocurreEvento("Se añaden " + cantidad + " al saldo del jugador " + nombre);
        }
        else {
            Diario.getInstance().ocurreEvento("Se retiran " + (-cantidad) + " del saldo del jugador " + nombre);
        }
        return true;
    }

    boolean moverACasilla(int numCasilla) {
        if (encarcelado) {
            return false;
        } else {
            numCasillaActual = numCasilla;
            puedeComprar = false;
            Diario.getInstance().ocurreEvento("El jugador " + nombre + " se desplaza a la casilla " + numCasilla);
            return true;
        }
    }

    boolean obtenerSalvoconducto(Sorpresa sorpresa) {
        if (encarcelado) {
            return false;
        } else {
            salvoconducto = sorpresa;
            return true;
        }
    }

    boolean paga(float cantidad) {
        boolean resultado = modificarSaldo(cantidad * (-1));
        return resultado;
    }

    boolean pagaAlquiler(float cantidad) {
        if (encarcelado) {
            return false;
        } else {
            return paga(cantidad);
        }
    }

    boolean pagaImpuesto(float cantidad) {
        if (encarcelado) {
            return false;
        } else {
            return paga(cantidad);
        }
    }

    boolean pasaPorSalida() {
        modificarSaldo(PasoPorSalida);
        Diario.getInstance().ocurreEvento("El jugador " + nombre + " pasa por salida");
        return true;
    }

    private void perderSalvoconducto() {
        salvoconducto.usada();
        salvoconducto = null;
    }

    boolean puedeComprarCasilla() {
        puedeComprar = !encarcelado;
        return puedeComprar;
    }

    private boolean puedeSalirCarcelPagando() {
        return saldo >= PrecioLibertad;
    }

    private boolean puedoEdificarCasa(TituloPropiedad propiedad) {
        assert (propiedades.contains(propiedad));
        return (puedoGastar(propiedad.getPrecioEdificar()) && propiedad.getNumCasas() < getCasasMax());
    }

    private boolean puedoEdificarHotel(TituloPropiedad propiedad) {
        assert (propiedades.contains(propiedad));
        float precio = propiedad.getPrecioEdificar();
        return (puedoGastar(precio) && (propiedad.getNumHoteles() < getHotelesMax()) && (propiedad.getNumCasas() >= getCasasPorHotel()));
    }

    private boolean puedoGastar(float precio) {
        if (encarcelado) {
            return false;
        } else {
            return saldo >= precio;
        }
    }

    boolean recibe(float cantidad) {
        if (encarcelado) {
            return false;
        } else {
            return modificarSaldo(cantidad);
        }
    }

    boolean salirCarcelPagando() {
        if (encarcelado && puedeSalirCarcelPagando()) {
            paga(PrecioLibertad);
            Diario.getInstance().ocurreEvento("El jugador " + nombre + " paga " + PrecioLibertad + " por salir de la cárcel");
            encarcelado = false;
        }
        return encarcelado;
    }

    boolean salirCarcelTirando() {
        if (encarcelado && Dado.getInstance().salgoDeLaCarcel()) {
            encarcelado = false;
            Diario.getInstance().ocurreEvento("El jugador " + nombre + " sale de la cárcel tirando el dado");
        }
        return !encarcelado;
    }

    boolean tieneAlgoQueGestionar() {
        return propiedades.size() > 0;
    }

    boolean tieneSalvoconducto() {
        return (salvoconducto != null);
    }

    @Override
    public String toString() {
        return "Jugador{" + "encarcelado=" + encarcelado + ", nombre=" + nombre + ", numCasillaActual=" + numCasillaActual + ", puedeComprar=" + puedeComprar + ", saldo=" + saldo + ", propiedades=" + propiedades + ", salvoconducto=" + salvoconducto + '}';
    }

    boolean vender(int ip) {
        if (!encarcelado && existeLaPropiedad(ip)) {
            if (propiedades.get(ip).vender(this)) {
                String evento = nombre + " ha vendido la propiedad " + propiedades.get(ip).getNombre();
                Diario.getInstance().ocurreEvento(evento);
                propiedades.remove(ip);
                return true;
            }
        }
        return false;
    }

    public static void main(String args[]) {
        Jugador jugador = new Jugador("Fernando");
        boolean encarcelar;

        System.out.println(jugador.toString());

        jugador.obtenerSalvoconducto(new Sorpresa(TipoSorpresa.SALIRCARCEL, new MazoSorpresas()));
        System.out.println(jugador.toString());
        jugador.encarcelar(10);
        System.out.println(Diario.getInstance().leerEvento());
        System.out.println(jugador.toString());

        jugador.pasaPorSalida();
        System.out.println(Diario.getInstance().leerEvento());
        System.out.println(Diario.getInstance().leerEvento());
        System.out.println(jugador.toString());

        //En futuras prácticas se incluirán los tests de los métodos comprar, vender, hipotecar, construir...
    }
}
