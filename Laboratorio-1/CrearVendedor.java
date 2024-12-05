package gui.vendedor;

import gui.PanelManager;
import gui.PantallaPrincipal;
import model.Vendedor;
import service.ServiceExeption;
import service.ServiceVendedor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearVendedor extends JPanel {
    ServiceVendedor serviceVendedor = new ServiceVendedor();
    JPanel crearVendedor = new JPanel();
    PanelManager panel;

    JButton btnCrear = new JButton("Crear Vendedor");
    JButton btnVolver = new JButton("Volver al inicio");

    JLabel labelIndicacion = new JLabel("Ingrese los datos del vendedor: ");
    JLabel labelDni = new JLabel("DNI: ");
    JLabel labelNombre = new JLabel("Nombre: ");
    JLabel labelApellido = new JLabel("Apellido: ");
    JLabel labelTelefono = new JLabel("Telefono: ");

    JTextField campoDNI = new JTextField();
    JTextField campoNombre = new JTextField();
    JTextField campoApellido = new JTextField();
    JTextField campoTelefono = new JTextField();

    public CrearVendedor(PanelManager panelManager) {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla() {
        crearVendedor.setLayout(new GridLayout(6, 3,20,20));
        crearVendedor.setBorder(new EmptyBorder(10,10,10,10));

        labelIndicacion.setFont(new Font("SansSerif", Font.PLAIN, 18));

        //Fila 1
        crearVendedor.add(labelIndicacion);
        crearVendedor.add(new JLabel(""));  // Espacio vacío
        crearVendedor.add(new JLabel(""));  // Espacio vacío

        //Fila 2
        crearVendedor.add(labelDni);
        crearVendedor.add(campoDNI);
        crearVendedor.add(new JLabel(""));  // Espacio vacío

        //Fila 3
        crearVendedor.add(labelNombre);
        crearVendedor.add(campoNombre);
        crearVendedor.add(new JLabel(""));  // Espacio vacío

        //Fila 4
        crearVendedor.add(labelApellido);
        crearVendedor.add(campoApellido);
        crearVendedor.add(new JLabel(""));  // Espacio vacío

        //Fila 5
        crearVendedor.add(labelTelefono);
        crearVendedor.add(campoTelefono);
        crearVendedor.add(btnCrear);

        //Fila 6 --> volver
        crearVendedor.add(new JLabel(""));  // Espacio vacío
        crearVendedor.add(new JLabel(""));  // Espacio vacío
        crearVendedor.add(btnVolver);

        setLayout(new BorderLayout());
        add(crearVendedor, BorderLayout.CENTER);


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

                    Vendedor vendedor = new Vendedor();
                    vendedor.setDni(Integer.parseInt(campoDNI.getText()));
                    vendedor.setNombre(campoNombre.getText());
                    vendedor.setApellido(campoApellido.getText());
                    vendedor.setTelefono(campoTelefono.getText());

                    serviceVendedor.guardar(vendedor);
                    JOptionPane.showMessageDialog(crearVendedor, "Datos guardados exitosamente");

                    //abrir PantaVendedor
                    PantaVendedor pantaVendedor = new PantaVendedor(panel);
                    panel.mostrar(pantaVendedor);
                }catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(crearVendedor, "El DNI debe ser un valor entero");
                    vaciarComponentes();

                }catch (IllegalArgumentException | ServiceExeption e){
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
