package model;

import service.ServiceExeption;

import java.time.LocalDate;
import java.util.ArrayList;

public class Administrador extends Usuario{


    public Administrador(){
        super();
    }
    public Administrador(int dni, String nombre, String apellido, String telefono) {
        super(dni, nombre, apellido, telefono);
    }


}
