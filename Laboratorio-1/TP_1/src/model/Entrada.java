package model;

import service.ServiceEntrada;
import service.ServiceExeption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Entrada {
    static ServiceEntrada serviceEntrada = new ServiceEntrada();

    private int nroEntrada;
    private static int ultimaEntrada = 1;
    private Espectaculo espectaculo;
    private Ubicacion ubicacion;
    private double precio;

    private static ArrayList<Entrada> entradas = new ArrayList<>();;

    public Entrada(){
        this.nroEntrada = ultimaEntrada;
        incrementarUltimaEntrada();
        Vendedor.incrementarNroVenta();
    }
    public Entrada(Espectaculo espectaculo, Ubicacion ubicacion) {
        this.nroEntrada = ultimaEntrada;
        incrementarUltimaEntrada();
        Vendedor.incrementarNroVenta();
        this.espectaculo = espectaculo;
        this.ubicacion = ubicacion;
        this.precio = calcularPrecio();
    }

    public int getNroEntrada() {
        return nroEntrada;
    }
    public void setNroEntrada(int nroEntrada) {
        this.nroEntrada = nroEntrada;
    }
    public static int getUltimaEntrada() {return ultimaEntrada;}
    public static void setUltimaEntrada(int ultimaEntrada) {Entrada.ultimaEntrada = ultimaEntrada;}
    public Espectaculo getEspectaculo() {
        return espectaculo;
    }
    public void setEspectaculo(Espectaculo espectaculo) {
        this.espectaculo = espectaculo;
    }
    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public ArrayList<Entrada> getEntradas() {
        return entradas;
    }
    public void agregarEntrada(Entrada entrada){
        entradas.add(entrada);
    }

    public void incrementarUltimaEntrada(){
        ultimaEntrada++;
    }

    public void setEspectaculoId(int idEspectaculo) throws ServiceExeption {
        Espectaculo espectaculoAux = Espectaculo.buscarEspectaculo(idEspectaculo);
        setEspectaculo(espectaculoAux);
    }

    public void setUbicacionId(int idUbicacion) throws ServiceExeption {
        Estadio estAux = espectaculo.getEstadio();
        Ubicacion ubiAux = Ubicacion.buscarUbicacion(idUbicacion,estAux.getIdEstadio());
        setUbicacion(ubiAux);
    }

    public double calcularPrecio() {
        return espectaculo.getPrecioBase() + ubicacion.getPrecioUbi();
    }

    public static String ReporteEntradasEspectaculo(LocalDate inicio, LocalDate fin, Espectaculo espectaculo) throws ServiceExeption {
        entradas = serviceEntrada.todasLasEntradas();

        double monto = 0;
        for (Entrada entrada : entradas) {
            if(espectaculo.getIdEspectaculo() == entrada.getEspectaculo().getIdEspectaculo()){
                if (espectaculo.getFechaLocal().isAfter(inicio) && espectaculo.getFechaLocal().isBefore(fin)){
                    monto += entrada.getPrecio();
                }
            }

        }
        return "Se recaudaron: $" + monto + ", del espectaculo de " + espectaculo.getNombre();
    }

    @Override
    public String toString(){
        return "Entrada " + this.nroEntrada + ", para ver a: " + espectaculo.getNombre() +
                "\n fecha: " + this.espectaculo.getFechaLocal() +
                "\n en estadio: "+ this.espectaculo.getEstadio().getNombre() + " --> " + this.ubicacion.getNombreUbi() +
                "\n Precio total de la entrada: $" + precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrada entrada = (Entrada) o;
        return nroEntrada == entrada.nroEntrada && Double.compare(precio, entrada.precio) == 0 && Objects.equals(espectaculo, entrada.espectaculo) && Objects.equals(ubicacion, entrada.ubicacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nroEntrada, espectaculo, ubicacion, precio);
    }
}
