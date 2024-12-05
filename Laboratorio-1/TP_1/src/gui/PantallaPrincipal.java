package gui;

import gui.administrador.CrearAdmin;
import gui.administrador.PantaAdmin;
import gui.vendedor.CrearVendedor;
import gui.vendedor.PantaVendedor;
import model.Administrador;
import model.Vendedor;
import service.ServiceAdministrador;
import service.ServiceExeption;
import service.ServiceVendedor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PantallaPrincipal extends JPanel {
    ServiceAdministrador serviceAdministrador = new ServiceAdministrador();
    ServiceVendedor serviceVendedor = new ServiceVendedor();

    JPanel pantallaPrincipal = new JPanel();

    JLabel labelSelect = new JLabel("Seleccione su rol de usuario para ingresar");

    JButton btnAdmin = new JButton("Administrador");
    JButton btnVendedor = new JButton("Vendedor");

    PanelManager panel;

    public PantallaPrincipal(PanelManager panelManager) {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla() {
        pantallaPrincipal.setLayout(new GridLayout(2, 2,15,25));
        pantallaPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        labelSelect.setFont(new Font("SansSerif", Font.PLAIN, 18));

        //Fila 1
        pantallaPrincipal.add(labelSelect);
        pantallaPrincipal.add(new JLabel(""));

        //Fila 2
        pantallaPrincipal.add(btnAdmin);
        pantallaPrincipal.add(btnVendedor);

        setLayout(new BorderLayout());
        add(pantallaPrincipal, BorderLayout.CENTER);

        btnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                try {
                    //Si no hay admins, abrir CrearAdmin
                    ArrayList<Administrador> administradors = serviceAdministrador.todosLosAdministradores();
                    if(administradors.isEmpty()){
                        JOptionPane.showMessageDialog(null, "No hay administradores\nCree un usuario administrador");
                        CrearAdmin crearAdmin = new CrearAdmin(panel);
                        panel.mostrar(crearAdmin);
                    }else{
                        //Abrir PantaAdmin
                        PantaAdmin pantaAdmin = new PantaAdmin(panel);
                        panel.mostrar(pantaAdmin);
                    }
                } catch (ServiceExeption ex) {
                    JOptionPane.showMessageDialog(null, "Ocurrió un error al verificar los administradores");
                    ex.printStackTrace();
                }
            }
        });

        btnVendedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                try {
                    //Si no hay vendedor, abrir CrearVendedor
                    ArrayList<Vendedor> vendedors = serviceVendedor.todosLosVendedores();
                    if(vendedors.isEmpty()){
                        JOptionPane.showMessageDialog(null, "No hay vendedores\nCree un usuario vendedor");
                        CrearVendedor crearVendedor = new CrearVendedor(panel);
                        panel.mostrar(crearVendedor);
                    }else{
                        //Abrir PantaVendedor
                        PantaVendedor pantaVendedor = new PantaVendedor(panel);
                        panel.mostrar(pantaVendedor);
                    }
                } catch (ServiceExeption ex) {
                    JOptionPane.showMessageDialog(null, "Ocurrió un error al verificar los vendedores");
                    ex.printStackTrace();
                }

            }
        });

    }

    public void vaciarPantalla(){
        pantallaPrincipal.remove(pantallaPrincipal);
    }

}
