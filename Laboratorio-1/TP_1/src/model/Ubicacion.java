package model;

import service.ServiceExeption;
import service.ServiceUbicacion;

import java.util.ArrayList;

public class Ubicacion {
    static ServiceUbicacion serviceUbicacion = new ServiceUbicacion();
    private String nombreUbi;
    private double precioUbi;
    private int capacidad;
    private int idUbi;
    private int idEstadio;

    public static ArrayList<Ubicacion> ubicaciones;


    public Ubicacion(){
        ubicaciones = new ArrayList<>();
    }
    public Ubicacion(int idEstadio, int idUbi, String  nombreUbi, double precioUbi, int capacidad) {
        this.idEstadio = idEstadio;
        this.idUbi = idUbi;
        this.nombreUbi = nombreUbi;
        this.precioUbi = precioUbi;
        this.capacidad = capacidad;
        ubicaciones = new ArrayList<>();
    }

    public int getIdEstadio() {
        return idEstadio;
    }
    public void setIdEstadio(int idEstadio) {
        this.idEstadio = idEstadio;
    }
    public int getIdUbi() {
        return idUbi;
    }
    public void setIdUbi(int idUbi) {
        this.idUbi = idUbi;
    }
    public double getPrecioUbi() {
        return precioUbi;
    }
    public void setPrecioUbi(double precio) {
        this.precioUbi = precio;
    }
    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    public String getNombreUbi() {
        return nombreUbi;
    }
    public void setNombreUbi(String nombreUbi) {
        this.nombreUbi = nombreUbi;
    }
    public static void agregarUbi(Ubicacion ubicacion) {
        ubicaciones.add(ubicacion);
    }


    public static Ubicacion buscarUbicacion(int idUbi, int idEstadio) throws ServiceExeption {
        Ubicacion ubicacion = null;

        ubicaciones = serviceUbicacion.todasLasUbicaciones();

        for(Ubicacion ubi: ubicaciones){
            if(ubi.getIdUbi() == idUbi || ubi.getIdEstadio() == idEstadio){
                ubicacion = ubi;
            }
        }
        if(ubicacion == null){
            System.out.println("No se encontro la ubicacion");
        }

        return ubicacion;
    }


    @Override
    public String toString(){
        return nombreUbi + ", precio adicional: $" + precioUbi;
    }

}
