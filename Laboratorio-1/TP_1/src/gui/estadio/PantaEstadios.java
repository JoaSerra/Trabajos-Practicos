package gui.estadio;

import gui.*;
import service.ServiceEstadio;
import service.ServiceExeption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantaEstadios extends JPanel {
    PanelManager panel;
    JPanel pantaEstadio = new JPanel();;
    ServiceEstadio serviceEstadio = new ServiceEstadio();

    JLabel indicacion = new JLabel("Estadios");

    JButton btnCrear = new JButton("Crear estadio");
    JButton btnModificarEstadio = new JButton("Modificar estadio");
    JButton btnVer = new JButton("Ver estadios");
    JButton btnCrearUbi = new JButton("Crear ubicacion");
    JButton btnModificarUbi = new JButton("Modificar ubicacion");
    JButton btnVolver = new JButton("Volver al inicio");


    public PantaEstadios(PanelManager panelManager) {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla(){
        pantaEstadio.setLayout(new GridLayout(5,2,20,20));
        pantaEstadio.setBorder(new EmptyBorder(10,10,10,10));

        indicacion.setFont(new Font("SansSerif", Font.PLAIN, 18));


        //Fila 1
        pantaEstadio.add(indicacion);
        pantaEstadio.add(new JLabel(""));

        //Fila 2
        pantaEstadio.add(btnCrear);
        pantaEstadio.add(btnModificarEstadio);

        //Fila 3
        pantaEstadio.add(btnVer);
        pantaEstadio.add(new JLabel(""));

        //Fila 4
        pantaEstadio.add(btnCrearUbi);
        pantaEstadio.add(btnModificarUbi);

        //Fila 5
        pantaEstadio.add(new JLabel(""));
        pantaEstadio.add(btnVolver);

        setLayout(new BorderLayout());
        add(pantaEstadio, BorderLayout.CENTER);

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                panel.mostrar(pantallaPrincipal);
            }
        });

        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                CrearEstadio crearEstadio = new CrearEstadio(panel);
                panel.mostrar(crearEstadio);
            }
        });

        btnModificarEstadio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(serviceEstadio.todosLosEstadios().isEmpty()){
                        JOptionPane.showMessageDialog(null,"No hay estadios para modificar\nCree un estadio");
                        CrearEstadio crearEstadio = new CrearEstadio(panel);
                        panel.mostrar(crearEstadio);
                    }else{
                        ModificarEstadio modificarEstadio = new ModificarEstadio(panel);
                        panel.mostrar(modificarEstadio);
                    }
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnModificarUbi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ModificarUbicacion modificarUbicacion;
                try {
                    modificarUbicacion = new ModificarUbicacion(panel);
                    panel.mostrar(modificarUbicacion);
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnVer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(serviceEstadio.todosLosEstadios().isEmpty()){
                        JOptionPane.showMessageDialog(null, "No hay estadios\nCree un estadio");
                        CrearEstadio crearEstadio = new CrearEstadio(panel);
                        panel.mostrar(crearEstadio);
                    }else{
                        VerEstadio verEstadio = new VerEstadio(panel);
                        panel.mostrar(verEstadio);
                    }
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnCrearUbi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(serviceEstadio.todosLosEstadios().isEmpty()){
                        JOptionPane.showMessageDialog(null, "No hay estadios\nCree un estadio");
                        CrearEstadio crearEstadio = new CrearEstadio(panel);
                        panel.mostrar(crearEstadio);
                    }else{
                        CrearUbi crearUbi = new CrearUbi(panel);
                        panel.mostrar(crearUbi);
                    }
                }catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
