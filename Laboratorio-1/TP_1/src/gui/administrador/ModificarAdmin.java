package gui.administrador;

import gui.PanelManager;
import gui.PantallaPrincipal;
import model.Administrador;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import service.ServiceAdministrador;
import service.ServiceExeption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModificarAdmin extends JPanel {
    ServiceAdministrador serviceAdministrador = new ServiceAdministrador();
    JPanel modificarAdmin = new JPanel();
    PanelManager panel;

    JComboBox<Administrador> comboAdmin;
    ArrayList<Administrador> administradors = serviceAdministrador.todosLosAdministradores();

    JLabel titulo = new JLabel("Modificar Administrador");
    JLabel instruccion = new JLabel("Seleccione un administrador: ");
    JLabel dni = new JLabel("DNI: ");
    JLabel nombre = new JLabel("Nombre: ");
    JLabel apellido = new JLabel("Apellido: ");
    JLabel telefono = new JLabel("Telefono: ");

    JTextField campoDni = new JTextField();
    JTextField campoNombre = new JTextField();
    JTextField campoApellido = new JTextField();
    JTextField campoTelefono = new JTextField();

    JButton btnModificar = new JButton("Modificar");
    JButton btnEliminar = new JButton("Eliminar");
    JButton btnCancelar = new JButton("Cancelar");

    public ModificarAdmin(PanelManager panelManager) throws ServiceExeption {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla() {
        modificarAdmin.setLayout(new GridLayout(8, 2,20,20));
        modificarAdmin.setBorder(new EmptyBorder(10,10,10,10));

        titulo.setFont(new Font("SansSerif", Font.PLAIN, 18));

        comboAdmin = new JComboBox<>(administradors.toArray(new Administrador[0]));

        campoDni.setEditable(false);

        //Fila 1
        modificarAdmin.add(titulo);
        modificarAdmin.add(new JLabel(""));

        //Fila 2
        modificarAdmin.add(instruccion);
        modificarAdmin.add(comboAdmin);

        //Fila 3
        modificarAdmin.add(dni);
        modificarAdmin.add(campoDni);

        //Fila 4
        modificarAdmin.add(nombre);
        modificarAdmin.add(campoNombre);

        //Fila 5
        modificarAdmin.add(apellido);
        modificarAdmin.add(campoApellido);

        //Fila 6
        modificarAdmin.add(telefono);
        modificarAdmin.add(campoTelefono);

        //Fila 7
        modificarAdmin.add(btnEliminar);
        modificarAdmin.add(btnModificar);

        //Fila 8
        modificarAdmin.add(new JLabel(""));
        modificarAdmin.add(btnCancelar);

        setLayout(new BorderLayout());
        add(modificarAdmin, BorderLayout.CENTER);

        Administrador adminSeleccionado = (Administrador) comboAdmin.getSelectedItem();

        comboAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(adminSeleccionado != null){
                    campoDni.setText(String.valueOf(adminSeleccionado.getDni()));
                    campoNombre.setText(adminSeleccionado.getNombre());
                    campoApellido.setText(adminSeleccionado.getApellido());
                    campoTelefono.setText(adminSeleccionado.getTelefono());
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(adminSeleccionado == null){
                    throw new RuntimeException("Seleccione un administrador");
                }

                try {
                    Administrador adminModificado = new Administrador();
                    adminModificado.setNombre(campoNombre.getText());
                    adminModificado.setApellido(campoApellido.getText());
                    adminModificado.setTelefono(campoTelefono.getText());
                    adminModificado.setDni(Integer.parseInt(campoDni.getText()));

                    serviceAdministrador.modificar(adminModificado);

                    JOptionPane.showMessageDialog(null, "Administrador modificado con éxito");
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }

                PantaAdmin pantaAdmin = new PantaAdmin(panel);
                panel.mostrar(pantaAdmin);
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Administrador adminSeleccionado = (Administrador) comboAdmin.getSelectedItem();
                try{
                    if (adminSeleccionado != null) {
                        serviceAdministrador.eliminar(adminSeleccionado.getDni());
                        JOptionPane.showMessageDialog(null, "Administrador eliminado con éxito");

                        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                        panel.mostrar(pantallaPrincipal);
                    }
                } catch (JdbcSQLIntegrityConstraintViolationException e){
                    JOptionPane.showMessageDialog(null, "No se puede eliminar el administrador");
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                panel.mostrar(pantallaPrincipal);
            }
        });
    }
}
