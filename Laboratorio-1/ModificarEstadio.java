package gui.estadio;

import gui.PanelManager;
import gui.administrador.PantaAdmin;
import model.Estadio;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import service.ServiceEstadio;
import service.ServiceExeption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class ModificarEstadio extends JPanel {
    ServiceEstadio serviceEstadio = new ServiceEstadio();
    JPanel modificarEstadio = new JPanel();
    PanelManager panel;

    JComboBox<Estadio> comboEstadio;
    ArrayList<Estadio> estadios = serviceEstadio.todosLosEstadios();

    JLabel titulo = new JLabel("Modificar Estadio");
    JLabel instruccion = new JLabel("Seleccione un estadio: ");
    JLabel idEstadio = new JLabel("ID: ");
    JLabel nombre = new JLabel("Nombre: ");
    JLabel direccion = new JLabel("Direccion: ");

    JTextField campoIdEstadio = new JTextField();
    JTextField campoNombre = new JTextField();
    JTextField campoDireccion = new JTextField();

    JButton btnModificar = new JButton("Modificar");
    JButton btnEliminar = new JButton("Eliminar");
    JButton btnCancelar = new JButton("Cancelar");

    public ModificarEstadio(PanelManager panelManager) throws ServiceExeption {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla(){
        modificarEstadio.setLayout(new GridLayout(7,2,20,20));
        modificarEstadio.setBorder(new EmptyBorder(10,10,10,10));

        titulo.setFont(new Font("SansSerif", Font.PLAIN, 18));

        comboEstadio = new JComboBox<>(estadios.toArray(new Estadio[0]));

        campoIdEstadio.setEditable(false);


        //Fila 1
        modificarEstadio.add(titulo);
        modificarEstadio.add(new JLabel(""));

        //Fila 2
        modificarEstadio.add(instruccion);
        modificarEstadio.add(comboEstadio);

        //Fila 3
        modificarEstadio.add(idEstadio);
        modificarEstadio.add(campoIdEstadio);

        //Fila 4
        modificarEstadio.add(nombre);
        modificarEstadio.add(campoNombre);

        //Fila 5
        modificarEstadio.add(direccion);
        modificarEstadio.add(campoDireccion);

        //Fila 6
        modificarEstadio.add(btnEliminar);
        modificarEstadio.add(btnModificar);

        //Fila 8
        modificarEstadio.add(new JLabel(""));
        modificarEstadio.add(btnCancelar);

        setLayout(new BorderLayout());
        add(modificarEstadio, BorderLayout.CENTER);

        comboEstadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Estadio estadioSeleccionado = (Estadio) comboEstadio.getSelectedItem();
                if(estadioSeleccionado != null){
                    campoIdEstadio.setText(String.valueOf(estadioSeleccionado.getIdEstadio()));
                    campoNombre.setText(estadioSeleccionado.getNombre());
                    campoDireccion.setText(estadioSeleccionado.getDireccion());
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Estadio estadioModificado = new Estadio();
                    estadioModificado.setIdEstadio(Integer.parseInt(campoIdEstadio.getText()));
                    estadioModificado.setNombre(campoNombre.getText());
                    estadioModificado.setDireccion(campoDireccion.getText());

                    serviceEstadio.modificar(estadioModificado);

                    JOptionPane.showMessageDialog(modificarEstadio, "Estadio modificado");

                    PantaEstadios pantaEstadios = new PantaEstadios(panel);
                    panel.mostrar(pantaEstadios);
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Estadio estadioSeleccionado = (Estadio) comboEstadio.getSelectedItem();
                try {
                    if(estadioSeleccionado != null){
                        serviceEstadio.eliminar(estadioSeleccionado.getIdEstadio());
                        JOptionPane.showMessageDialog(null, "Estadio eliminado");

                        PantaAdmin pantaAdmin = new PantaAdmin(panel);
                        panel.mostrar(pantaAdmin);
                    }
                }catch (JdbcSQLIntegrityConstraintViolationException e){
                    JOptionPane.showMessageDialog(null, "No se puede eliminar el estadio\nTiene ubicaciones o espectaculos asignados");
                } catch (ServiceExeption e) {
                    System.out.println("Error al eliminar el estadio");
                    throw new RuntimeException(e);
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                PantaEstadios pantaEstadios = new PantaEstadios(panel);
                panel.mostrar(pantaEstadios);
            }
        });
    }
}
