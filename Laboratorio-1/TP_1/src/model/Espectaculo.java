package model;

import service.ServiceEspectaculo;
import service.ServiceExeption;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Espectaculo {
    static ServiceEspectaculo serviceEspectaculo = new ServiceEspectaculo();

    private int idEspectaculo;
    private String nombre;
    private double precioBase;
    private LocalDate fecha;
    private Estadio estadio;
    private static ArrayList<Espectaculo> espectaculos;

    public Espectaculo() {
        espectaculos = new ArrayList<>();
    }

    public Espectaculo(int idEspectaculo, String nombre, double precioBase, LocalDate fecha, Estadio estadio) {
        this.idEspectaculo = idEspectaculo;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.fecha = fecha;
        this.estadio = estadio;
        espectaculos = new ArrayList<>();
    }


    public int getIdEspectaculo() {
        return idEspectaculo;
    }
    public void setIdEspectaculo(int idEspectaculo) {
        this.idEspectaculo = idEspectaculo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getPrecioBase() {
        return precioBase;
    }
    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }
    public Estadio getEstadio() {
        return estadio;
    }
    public void setEstadio(int idEstadio) throws Exception {
        this.estadio = Estadio.buscarEstadio(idEstadio);
    }
    public void addEspectaculo(Espectaculo espectaculo) {
        espectaculos.add(espectaculo);
    }


    public LocalDate getFechaLocal() {
        return fecha;
    }
    public void setFechaConLocal(LocalDate fecha) {
        this.fecha = fecha;
    }
    public Date getFechaSQL(){
        return Date.valueOf(this.fecha);
    }
    public void setFechaConSQL(Date fechaSQL){
        this.fecha = fechaSQL.toLocalDate();
    }


    public static Espectaculo buscarEspectaculo(int idEspectaculo) throws ServiceExeption {
        Espectaculo espectaculoAux = null;

        espectaculos = serviceEspectaculo.todosLosEspectaculos();

        for(Espectaculo espectaculo : espectaculos) {
            if(espectaculo.getIdEspectaculo() == idEspectaculo) {
                espectaculoAux = espectaculo;
                espectaculoAux.setFechaConSQL(Date.valueOf(espectaculo.getFechaLocal()));
            }
        }
        if(espectaculoAux == null) {
            System.out.println("No se encontró el espetaculo");
        }
        return espectaculoAux;
    }


    @Override
    public String toString() {
        return nombre + "\n Estadio: " + estadio.getNombre();
    }
}
