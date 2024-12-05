package gui.administrador;

import gui.PanelManager;
import gui.PantallaPrincipal;
import gui.espectaculo.PantaEspectaculos;
import gui.estadio.PantaEstadios;
import service.ServiceEspectaculo;
import service.ServiceEstadio;
import service.ServiceExeption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantaAdmin extends JPanel {
    JPanel pantaAdmin = new JPanel();;
    PanelManager panel;

    //Etiquetas
    JLabel titulo = new JLabel("Administrador");;

    //Botones
    JButton buttonNuevo = new JButton("Crear nuevo administrador");
    JButton buttonEspectaculos = new JButton("Espectaculos");
    JButton buttonEstadios = new JButton("Estadios");
    JButton buttonVolver = new JButton("Volver");
    JButton buttonModificar = new JButton("Modificar administrador");

    public PantaAdmin(PanelManager panelManager) {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla() {
        pantaAdmin.setLayout(new GridLayout(4, 2,20,20));
        pantaAdmin.setBorder(new EmptyBorder(10, 10, 10, 10));

        titulo.setFont(new Font("SansSerif", Font.PLAIN, 18));


        //Fila 1
        pantaAdmin.add(titulo);
        pantaAdmin.add(new JLabel(""));

        //Fila 2
        pantaAdmin.add(buttonNuevo);
        pantaAdmin.add(buttonModificar);

        //Fila 3
        pantaAdmin.add(buttonEspectaculos);
        pantaAdmin.add(buttonEstadios);

        //Fila 4
        pantaAdmin.add(new JLabel(""));
        pantaAdmin.add(buttonVolver);

        setLayout(new BorderLayout());
        add(pantaAdmin, BorderLayout.CENTER);

        buttonNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                CrearAdmin crearAdmin = new CrearAdmin(panel);
                panel.mostrar(crearAdmin);
            }
        });

        buttonEspectaculos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                //abrir PantaEspectaculos
                PantaEspectaculos pantaEspectaculos = new PantaEspectaculos(panel);
                panel.mostrar(pantaEspectaculos);
            }
        });

        buttonEstadios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                //abrir PantaEstadio
                PantaEstadios pantaEstadios = new PantaEstadios(panel);
                panel.mostrar(pantaEstadios);
            }
        });

        buttonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                panel.mostrar(pantallaPrincipal);
            }
        });

        buttonModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ModificarAdmin modificarAdmin;
                try {
                    modificarAdmin = new ModificarAdmin(panel);
                    panel.mostrar(modificarAdmin);
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
