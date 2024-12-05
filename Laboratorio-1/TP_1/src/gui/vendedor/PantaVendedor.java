package gui.vendedor;

import gui.PanelManager;
import gui.PantallaPrincipal;
import gui.espectaculo.PantaEspectaculos;
import gui.estadio.PantaEstadios;
import service.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantaVendedor extends JPanel{
    ServiceVendedor serviceVendedor = new ServiceVendedor();
    ServiceEntrada serviceEntrada = new ServiceEntrada();
    ServiceUbicacion serviceUbicacion = new ServiceUbicacion();
    ServiceEspectaculo serviceEspectaculo = new ServiceEspectaculo();
    JPanel pantaVendedor = new JPanel();
    PanelManager panel;

    JLabel Lvendedor = new JLabel("Vendedor");

    JButton crear = new JButton("Crear nuevo vendedor");
    JButton modificar = new JButton("Modificar vendedor");
    JButton vender = new JButton("Vender entrada");
    JButton eliminar = new JButton("Eliminar entrada");
    JButton volver = new JButton("Volver");

    public PantaVendedor(PanelManager panelManager) {
        panel = panelManager;
        armarPantalla();
    }
    public void armarPantalla() {
        pantaVendedor.setLayout(new GridLayout(4,2,20,20));
        pantaVendedor.setBorder(new EmptyBorder(10,10,10,10));

        Lvendedor.setFont(new Font("SansSerif",Font.PLAIN,18));

        //Fila 1
        pantaVendedor.add(Lvendedor);
        pantaVendedor.add(new JLabel(""));

        //Fila 2
        pantaVendedor.add(crear);
        pantaVendedor.add(modificar);

        //Fila 3
        pantaVendedor.add(vender);
        pantaVendedor.add(eliminar);


        //Fila 4
        pantaVendedor.add(new JLabel(""));
        pantaVendedor.add(volver);

        setLayout(new BorderLayout());
        add(pantaVendedor, BorderLayout.CENTER);

        crear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                CrearVendedor crearVendedor = new CrearVendedor(panel);
                panel.mostrar(crearVendedor);
            }
        });

        modificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ModificarVendedor modificarVendedor;
                try{
                    modificarVendedor = new ModificarVendedor(panel);
                    panel.mostrar(modificarVendedor);
                }catch(ServiceExeption e){
                    throw new RuntimeException(e);
                }
            }
        });

        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EliminarEntrada eliminarEntrada;
                try {
                    if(serviceEntrada.todasLasEntradas().isEmpty()){
                        JOptionPane.showMessageDialog(null,"No hay entradas");
                        CrearEntrada crearEntrada = new CrearEntrada(panel);
                        panel.mostrar(crearEntrada);
                    }else{
                        eliminarEntrada = new EliminarEntrada(panel);
                        panel.mostrar(eliminarEntrada);
                    }
                }catch (ServiceExeption e){
                    throw new RuntimeException(e);
                }
            }
        });

        vender.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                //abrir CrearEntradas
                try {
                    if(serviceEspectaculo.todosLosEspectaculos().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay espectaculos para vender\nCree un espectaculo");
                        PantaEspectaculos pantaEspectaculos = new PantaEspectaculos(panel);
                        panel.mostrar(pantaEspectaculos);
                    }else if(serviceUbicacion.todasLasUbicaciones().isEmpty()){
                        JOptionPane.showMessageDialog(null, "No hay ubicaciones disponibles para vender\nCree una ubicacion");
                        PantaEstadios pantaEstadios = new PantaEstadios(panel);
                        panel.mostrar(pantaEstadios);
                    }else{
                        CrearEntrada crearEntrada = new CrearEntrada(panel);
                        panel.mostrar(crearEntrada);
                    }
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });

        volver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                panel.mostrar(pantallaPrincipal);
            }
        });



    }

}
