package model;

public class Vendedor extends Usuario{

    private static int nroVenta = 1;

    public Vendedor(){
        super();
    }
    public Vendedor(int dni, String nombre, String apellido, String telefono) {
        super(dni, nombre, apellido, telefono);
    }

    public static int getNroVenta() {
        return nroVenta;
    }
    public static void setNroVenta(int nroVenta) {
        Vendedor.nroVenta = nroVenta;
    }
    public static void incrementarNroVenta(){
        Vendedor.nroVenta++;
    }


}
