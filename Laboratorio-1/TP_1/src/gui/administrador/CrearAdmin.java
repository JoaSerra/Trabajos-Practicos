package gui.administrador;

import gui.PanelManager;
import gui.PantallaPrincipal;
import model.Administrador;
import service.ServiceAdministrador;
import service.ServiceExeption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearAdmin extends JPanel {
    ServiceAdministrador serviceAdministrador = new ServiceAdministrador();
    JPanel crearAdmin = new JPanel();
    PanelManager panel;

    JTextField campoDNI = new JTextField();
    JTextField campoNombre = new JTextField();
    JTextField campoApellido = new JTextField();
    JTextField campoTelefono = new JTextField();

    JButton btnCrear = new JButton("Crear Administrador");
    JButton btnVolver = new JButton("Volver al inicio");

    JLabel labelIndicacion = new JLabel("Ingrese los datos del administrador");
    JLabel labelDni = new JLabel("DNI: ");
    JLabel labelNombre = new JLabel("Nombre: ");
    JLabel labelApellido = new JLabel("Apellido: ");
    JLabel labelTelefono = new JLabel("Telefono: ");

    public CrearAdmin(PanelManager panelManager) {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla(){
        crearAdmin.setLayout(new GridLayout(6,3,5,10));
        crearAdmin.setBorder(new EmptyBorder(10,10,10,10));

        labelIndicacion.setFont(new Font("SansSerif", Font.PLAIN, 18));

        //Fila 1
        crearAdmin.add(labelIndicacion);
        crearAdmin.add(new JLabel(""));  // Espacio vacío
        crearAdmin.add(new JLabel(""));  // Espacio vacío

        //Fila 2
        crearAdmin.add(labelDni);
        crearAdmin.add(campoDNI);
        crearAdmin.add(new JLabel(""));  // Espacio vacío

        //Fila 3
        crearAdmin.add(labelNombre);
        crearAdmin.add(campoNombre);
        crearAdmin.add(new JLabel(""));  // Espacio vacío

        //Fila 4
        crearAdmin.add(labelApellido);
        crearAdmin.add(campoApellido);
        crearAdmin.add(new JLabel(""));  // Espacio vacío

        //Fila 5
        crearAdmin.add(labelTelefono);
        crearAdmin.add(campoTelefono);
        crearAdmin.add(btnCrear);

        //Fila 6 --> volver
        crearAdmin.add(new JLabel(""));  // Espacio vacío
        crearAdmin.add(new JLabel(""));  // Espacio vacío
        crearAdmin.add(btnVolver);

        setLayout(new BorderLayout());
        add(crearAdmin, BorderLayout.CENTER);


        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                panel.mostrar(pantallaPrincipal);
            }
        });

        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    //Valido que este completo
                    if (campoDNI.getText().trim().isEmpty() || campoNombre.getText().trim().isEmpty() || campoApellido.getText().trim().isEmpty() || campoTelefono.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Todos los campos deben estar completos");
                    }
                    if (!campoTelefono.getText().matches("\\d+")) {
                        throw new IllegalArgumentException("El telefono debe contener solo numeros");
                    }

                    Administrador admin = new Administrador();
                    admin.setDni(Integer.parseInt(campoDNI.getText()));
                    admin.setNombre(campoNombre.getText());
                    admin.setApellido(campoApellido.getText());
                    admin.setTelefono(campoTelefono.getText());

                    serviceAdministrador.guardar(admin);
                    JOptionPane.showMessageDialog(crearAdmin, "Administrador creado correctamente");

                    //Abrir PantaAdmin
                    PantaAdmin pantaAdmin = new PantaAdmin(panel);
                    panel.mostrar(pantaAdmin);
                }catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Ingrese un valor valido.");
                    vaciarComponentes();
                }catch (IllegalArgumentException | ServiceExeption e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    vaciarComponentes();
                }
            }
        });
    }
    public void vaciarComponentes(){
        campoDNI.setText("");
        campoNombre.setText("");
        campoApellido.setText("");
        campoTelefono.setText("");
    }
}
