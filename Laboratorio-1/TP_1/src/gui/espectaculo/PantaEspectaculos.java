package gui.espectaculo;

import gui.*;
import gui.estadio.CrearEstadio;
import service.ServiceEspectaculo;
import service.ServiceEstadio;
import service.ServiceExeption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantaEspectaculos extends JPanel {
    ServiceEspectaculo serviceEspectaculo = new ServiceEspectaculo();
    ServiceEstadio serviceEstadio = new ServiceEstadio();
    JPanel pantaEspectaculos = new JPanel();
    PanelManager panel;

    JLabel indicacion = new JLabel("Espectaculos");

    JButton btnCrear = new JButton("Crear espectaculo");
    JButton btnVer = new JButton("Ver espectaculos");
    JButton btnModificar = new JButton("Modificar espectaculo");
    JButton btnVolver = new JButton("Volver al inicio");

    public PantaEspectaculos(PanelManager panelManager) {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla(){
        pantaEspectaculos.setLayout(new GridLayout(4,2,20,20));
        pantaEspectaculos.setBorder(new EmptyBorder(10,10,10,10));

        indicacion.setFont(new Font("SansSerif", Font.PLAIN, 18));

        //Fila 1
        pantaEspectaculos.add(indicacion);
        pantaEspectaculos.add(new JLabel(""));

        //Fila 2
        pantaEspectaculos.add(btnCrear);
        pantaEspectaculos.add(btnModificar);

        //Fila 3
        pantaEspectaculos.add(btnVer);
        pantaEspectaculos.add(new JLabel(""));

        //Fila 4
        pantaEspectaculos.add(new JLabel(""));
        pantaEspectaculos.add(btnVolver);

        setLayout(new BorderLayout());
        add(pantaEspectaculos, BorderLayout.CENTER);

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                panel.mostrar(pantallaPrincipal);
            }
        });

        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                CrearEspectaculo crearEspectaculo;
                try {
                    if(serviceEstadio.todosLosEstadios().isEmpty()){
                        JOptionPane.showMessageDialog(null, "No hay estadios\nCree un estadio");
                        CrearEstadio crearEstadio = new CrearEstadio(panel);
                        panel.mostrar(crearEstadio);
                    }else{
                        crearEspectaculo = new CrearEspectaculo(panel);
                        panel.mostrar(crearEspectaculo);
                    }
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(serviceEspectaculo.todosLosEspectaculos().isEmpty()){
                        JOptionPane.showMessageDialog(null, "No hay espectaculos para modificar");
                    }else{
                        ModificarEspectaculo modificarEspectaculo = new ModificarEspectaculo(panel);
                        panel.mostrar(modificarEspectaculo);
                    }
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnVer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    //Si no hay espectaculos
                    if(serviceEstadio.todosLosEstadios().isEmpty()){
                        JOptionPane.showMessageDialog(null, "No hay espectaculos\nCree un estadio");
                        CrearEstadio crearEstadio = new CrearEstadio(panel);
                        panel.mostrar(crearEstadio);

                    //Si no hay espectaculos
                    }else if (serviceEspectaculo.todosLosEspectaculos().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay espectaculos\nCree un espectaculo");
                        CrearEspectaculo crearEspectaculo = new CrearEspectaculo(panel);
                        panel.mostrar(crearEspectaculo);
                    }else {
                        VerEspectaculos verEspectaculos = new VerEspectaculos(panel);
                        panel.mostrar(verEspectaculos);
                    }
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
