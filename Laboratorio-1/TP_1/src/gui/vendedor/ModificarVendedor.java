package gui.vendedor;

import gui.PanelManager;
import gui.PantallaPrincipal;
import model.Vendedor;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import service.ServiceExeption;
import service.ServiceVendedor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModificarVendedor extends JPanel {
    ServiceVendedor serviceVendedor = new ServiceVendedor();
    JPanel modificarVendedor = new JPanel();
    PanelManager panel;

    JComboBox<Vendedor> comboVendedor;
    ArrayList<Vendedor> vendedors = serviceVendedor.todosLosVendedores();

    JLabel titulo = new JLabel("Modificar Vendedor");
    JLabel instruccion = new JLabel("Seleccione un vendedor: ");
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


    public ModificarVendedor(PanelManager panelManager) throws ServiceExeption {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla() {
        modificarVendedor.setLayout(new GridLayout(8, 2,20,20));
        modificarVendedor.setBorder(new EmptyBorder(10,10,10,10));

        titulo.setFont(new Font("SansSerif", Font.PLAIN, 18));

        comboVendedor = new JComboBox<>(vendedors.toArray(new Vendedor[0]));

        campoDni.setEditable(false);

        //Fila 1
        modificarVendedor.add(titulo);
        modificarVendedor.add(new JLabel(""));

        //Fila 2
        modificarVendedor.add(instruccion);
        modificarVendedor.add(comboVendedor);

        //Fila 3
        modificarVendedor.add(dni);
        modificarVendedor.add(campoDni);

        //Fila 4
        modificarVendedor.add(nombre);
        modificarVendedor.add(campoNombre);

        //Fila 5
        modificarVendedor.add(apellido);
        modificarVendedor.add(campoApellido);

        //Fila 6
        modificarVendedor.add(telefono);
        modificarVendedor.add(campoTelefono);

        //Fila 7
        modificarVendedor.add(btnEliminar);
        modificarVendedor.add(btnModificar);

        //Fila 8
        modificarVendedor.add(new JLabel(""));
        modificarVendedor.add(btnCancelar);

        setLayout(new BorderLayout(10,10));
        add(modificarVendedor, BorderLayout.CENTER);


        comboVendedor.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Vendedor vendedorSeleccionado = (Vendedor) comboVendedor.getSelectedItem();
                if (vendedorSeleccionado != null) {
                    campoDni.setText(String.valueOf(vendedorSeleccionado.getDni()));
                    campoNombre.setText(vendedorSeleccionado.getNombre());
                    campoApellido.setText(vendedorSeleccionado.getApellido());
                    campoTelefono.setText(vendedorSeleccionado.getTelefono());
                }
            }
        });

        btnModificar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent) {

                try {
                    Vendedor vendedorSeleccionado = new Vendedor();
                    vendedorSeleccionado.setNombre(campoNombre.getText());
                    vendedorSeleccionado.setApellido(campoApellido.getText());
                    vendedorSeleccionado.setTelefono(campoTelefono.getText());
                    vendedorSeleccionado.setDni(Integer.parseInt(campoDni.getText()));

                    serviceVendedor.modificar(vendedorSeleccionado);

                    JOptionPane.showMessageDialog(null, "Vendedor modificado con éxito");
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }

                PantaVendedor pantaVendedor = new PantaVendedor(panel);
                panel.mostrar(pantaVendedor);
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Vendedor vendedorSeleccionado = (Vendedor) comboVendedor.getSelectedItem();
                try{
                    if (vendedorSeleccionado != null) {
                        serviceVendedor.eliminar(vendedorSeleccionado.getDni());
                        JOptionPane.showMessageDialog(null, "Vendedor eliminado con éxito");

                        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                        panel.mostrar(pantallaPrincipal);
                    }
                } catch (JdbcSQLIntegrityConstraintViolationException e){
                    JOptionPane.showMessageDialog(null, "No se puede eliminar el vendedor");
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                PantaVendedor pantaVendedor = new PantaVendedor(panel);
                panel.mostrar(pantaVendedor);
            }
        });
    }

}
