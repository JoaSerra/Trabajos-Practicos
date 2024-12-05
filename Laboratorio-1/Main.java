import gui.*;
import model.*;
import service.*;

public class Main {
    public static void main(String[] args) throws Exception {

        ServiceAdministrador serviceAdministrador = new ServiceAdministrador();
        ServiceEspectaculo serviceEspectaculo = new ServiceEspectaculo();
        ServiceEstadio serviceEstadio =  new ServiceEstadio();
        ServiceUbicacion serviceUbicacion = new ServiceUbicacion();
        ServiceVendedor serviceVendedor = new ServiceVendedor();
        
        //Entrada.setUltimaEntrada(2);

        new PanelManager();
    }
}

