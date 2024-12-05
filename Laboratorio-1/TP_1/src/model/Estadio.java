package model;

import service.ServiceEstadio;
import service.ServiceExeption;

import java.util.ArrayList;
import java.util.Objects;

public class Estadio {
    static ServiceEstadio serviceEstadio = new ServiceEstadio();

    private int idEstadio;
    private String nombre;
    private String direccion;
    private int ultimaUbicacion = 1;
    public static ArrayList<Estadio> estadios;

    public Estadio() throws ServiceExeption {
        ultimaUbicacion++;
        estadios = new ArrayList<>();
    }

    public Estadio(int idEstadio, String nombre, String direccion) throws ServiceExeption {
        this.idEstadio = idEstadio;
        this.nombre = nombre;
        this.direccion = direccion;
        ultimaUbicacion++;
        estadios = new ArrayList<>();
    }

    public int getIdEstadio() {
        return idEstadio;
    }
    public void setIdEstadio(int idEstadio) {
        this.idEstadio = idEstadio;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public int getUltimaUbicacion() {
        return ultimaUbicacion;
    }
    public void setUltimaUbicacion(int ultimaUbicacion) {
        this.ultimaUbicacion = ultimaUbicacion;
    }
    public static void agregarEstadio(Estadio estadio) {
        estadios.add(estadio);
    }


    public static Estadio buscarEstadio(int idEstadio) throws ServiceExeption {
        Estadio estadioAux = null;

        estadios = serviceEstadio.todosLosEstadios();

        for (Estadio estadio : estadios) {
            if (estadio.getIdEstadio() == idEstadio)
                estadioAux = estadio;
        }
        if (estadioAux == null){
            System.out.println("No se encontro el estadio");
        }
        return estadioAux;
    }

    @Override
    public String toString() {
        return "Estadio " + nombre + ", ID: " + idEstadio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estadio estadio = (Estadio) o;
        return idEstadio == estadio.idEstadio && ultimaUbicacion == estadio.ultimaUbicacion && Objects.equals(nombre, estadio.nombre) && Objects.equals(direccion, estadio.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEstadio, nombre, direccion, ultimaUbicacion);
    }
}
