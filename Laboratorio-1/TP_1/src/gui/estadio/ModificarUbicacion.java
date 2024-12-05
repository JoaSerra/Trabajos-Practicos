package gui.estadio;

import gui.PanelManager;
import model.Estadio;
import model.Ubicacion;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import service.ServiceEstadio;
import service.ServiceExeption;
import service.ServiceUbicacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModificarUbicacion extends JPanel {
    ServiceEstadio serviceEstadio = new ServiceEstadio();
    ServiceUbicacion serviceUbicacion = new ServiceUbicacion();
    JPanel modificarUbicacion = new JPanel();
    PanelManager panel;


    JLabel titulo = new JLabel("Modificar Ubicacion");
    JLabel instruccion = new JLabel("Seleccione un estadio: ");
    JLabel instruccion2 = new JLabel("Seleccione una ubicacion");
    JLabel idEstadio = new JLabel("ID del estadio: ");
    JLabel idUbicacion = new JLabel("ID del ubicacion: ");
    JLabel nombre = new JLabel("Nombre: ");
    JLabel precio = new JLabel("Precio: ");
    JLabel capacidad = new JLabel("Capacidad: ");

    JTextField campoIdEstadio = new JTextField();
    JTextField campoIdUbicacion = new JTextField();
    JTextField campoNombre = new JTextField();
    JTextField campoPrecio = new JTextField();
    JTextField campoCapacidad = new JTextField();

    JButton btnModificar = new JButton("Modificar");
    JButton btnEliminar = new JButton("Eliminar");
    JButton btnCancelar = new JButton("Cancelar");

    ArrayList<Estadio> estadios = serviceEstadio.todosLosEstadios();
    JComboBox<Estadio> comboEstadio = new JComboBox<>(estadios.toArray(new Estadio[0]));

    JComboBox<Ubicacion> comboUbicaciones = new JComboBox<>();

    public ModificarUbicacion(PanelManager panelManager) throws ServiceExeption {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla(){
        modificarUbicacion.setLayout(new GridLayout(10,2,20,20));
        modificarUbicacion.setBorder(new EmptyBorder(10,10,10,10));

        titulo.setFont(new Font("SansSerif", Font.PLAIN, 18));

        campoIdEstadio.setEditable(false);
        campoIdUbicacion.setEditable(false);

        //Fila 1
        modificarUbicacion.add(titulo);
        modificarUbicacion.add(new JLabel(""));

        //Fila 2
        modificarUbicacion.add(instruccion);
        modificarUbicacion.add(comboEstadio);

        //Fila 3
        modificarUbicacion.add(instruccion2);
        modificarUbicacion.add(comboUbicaciones);

        //Fila 4
        modificarUbicacion.add(idEstadio);
        modificarUbicacion.add(campoIdEstadio);

        //Fila 5
        modificarUbicacion.add(idUbicacion);
        modificarUbicacion.add(campoIdUbicacion);

        //Fila 6
        modificarUbicacion.add(nombre);
        modificarUbicacion.add(campoNombre);

        //Fila 7
        modificarUbicacion.add(precio);
        modificarUbicacion.add(campoPrecio);

        //Fila 8
        modificarUbicacion.add(capacidad);
        modificarUbicacion.add(campoCapacidad);

        //Fila 9
        modificarUbicacion.add(btnEliminar);
        modificarUbicacion.add(btnModificar);

        //Fila 10
        modificarUbicacion.add(new JLabel(""));
        modificarUbicacion.add(btnCancelar);

        setLayout(new BorderLayout());
        add(modificarUbicacion, BorderLayout.CENTER);

        comboEstadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Estadio estadioSeleccionado = (Estadio) comboEstadio.getSelectedItem();
                if(estadioSeleccionado != null){
                    try{
                        ArrayList<Ubicacion> ubicaciones = serviceUbicacion.obtenerUbicacionesPorEstadio(estadioSeleccionado.getIdEstadio());
                        comboUbicaciones.setModel(new DefaultComboBoxModel<>(ubicaciones.toArray(new Ubicacion[0])));

                        campoIdEstadio.setText(String.valueOf(estadioSeleccionado.getIdEstadio()));

                    } catch (ServiceExeption e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    comboUbicaciones.setModel(new DefaultComboBoxModel<>(new Ubicacion[0]));
                }
            }
        });

        comboUbicaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Ubicacion ubicacionSeleccionada = (Ubicacion) comboUbicaciones.getSelectedItem();
                if(ubicacionSeleccionada != null){
                    campoIdUbicacion.setText(String.valueOf(ubicacionSeleccionada.getIdUbi()));
                    campoNombre.setText(ubicacionSeleccionada.getNombreUbi());
                    campoPrecio.setText(String.valueOf(ubicacionSeleccionada.getPrecioUbi()));
                    campoCapacidad.setText(String.valueOf(ubicacionSeleccionada.getCapacidad()));
                }
            }
        });


        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Ubicacion ubicacionModificada = new Ubicacion();

                    ubicacionModificada.setIdUbi(Integer.parseInt(campoIdUbicacion.getText()));
                    ubicacionModificada.setIdEstadio(Integer.parseInt(campoIdEstadio.getText()));
                    ubicacionModificada.setNombreUbi(campoNombre.getText());
                    ubicacionModificada.setPrecioUbi(Double.parseDouble(campoPrecio.getText()));
                    ubicacionModificada.setCapacidad(Integer.parseInt(campoCapacidad.getText()));

                    serviceUbicacion.modificar(ubicacionModificada);

                    JOptionPane.showMessageDialog(modificarUbicacion, "Ubicacion Modificada");

                    PantaEstadios pantaEstadios = new PantaEstadios(panel);
                    panel.mostrar(pantaEstadios);
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Ubicacion ubicacionSeleccionada = (Ubicacion) comboUbicaciones.getSelectedItem();
                try {
                    if(ubicacionSeleccionada != null){
                        int idUbi = Integer.parseInt(campoIdUbicacion.getText());
                        int idEstadio = Integer.parseInt(campoIdEstadio.getText());
                        serviceUbicacion.eliminarUbicacion(idUbi,idEstadio);

                        JOptionPane.showMessageDialog(null,"Ubicacion eliminada");

                        PantaEstadios pantaEstadios = new PantaEstadios(panel);
                        panel.mostrar(pantaEstadios);
                    }
                } catch (JdbcSQLIntegrityConstraintViolationException e){
                    JOptionPane.showMessageDialog(null, "No se puede eliminar la ubicacion\nHay entradas vendidas");
                } catch (ServiceExeption e) {
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
