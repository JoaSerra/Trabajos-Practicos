package gui.estadio;

import gui.PanelManager;
import gui.PantallaPrincipal;
import model.Estadio;
import model.Ubicacion;
import service.ServiceEstadio;
import service.ServiceExeption;
import service.ServiceUbicacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CrearUbi extends JPanel {
    ServiceUbicacion serviceUbicacion = new ServiceUbicacion();
    ServiceEstadio serviceEstadio = new ServiceEstadio();

    JPanel crearUbi = new JPanel();;
    PanelManager panel;

    JLabel indicacion = new JLabel("Ingrese los datos de la ubicacion");
    JLabel nombre = new JLabel("Ingrese el nombre del ubicacion: ");
    JLabel precio = new JLabel("Precio: ");
    JLabel capacidad = new JLabel("Capacidad: ");
    JLabel estadio = new JLabel("Crear la ubicacion en: ");

    JButton btnCrear = new JButton("Crear");
    JButton btnVolver = new JButton("Volver al inicio");

    JTextField campoNombre = new JTextField();
    JTextField campoPrecio = new JTextField();
    JTextField campoCapacidad = new JTextField();

    JComboBox<Estadio> comboEstadio;
    ArrayList<Estadio> estadios = serviceEstadio.todosLosEstadios();


    public CrearUbi(PanelManager panelManager) throws ServiceExeption {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla(){
        crearUbi.setLayout(new GridLayout(6,3,15,20));
        crearUbi.setBorder(new EmptyBorder(10,10,10,10));

        indicacion.setFont(new Font("SansSerif",Font.PLAIN,18));

        comboEstadio = new JComboBox<>(estadios.toArray(new Estadio[0]));


        //Fila 1
        crearUbi.add(indicacion);
        crearUbi.add(new JLabel(""));
        crearUbi.add(new JLabel(""));

        //Fila 2
        crearUbi.add(estadio);
        crearUbi.add(comboEstadio);
        crearUbi.add(new JLabel(""));

        //Fila 3
        crearUbi.add(nombre);
        crearUbi.add(campoNombre);
        crearUbi.add(new JLabel(""));

        //Fila 4
        crearUbi.add(precio);
        crearUbi.add(campoPrecio);
        crearUbi.add(new JLabel(""));

        //Fila 5
        crearUbi.add(capacidad);
        crearUbi.add(campoCapacidad);
        crearUbi.add(btnCrear);

        //Fila 6
        crearUbi.add(new JLabel(""));
        crearUbi.add(new JLabel(""));
        crearUbi.add(btnVolver);

        setLayout(new BorderLayout());
        add(crearUbi, BorderLayout.CENTER);


        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                panel.mostrar(pantallaPrincipal);
            }
        });

        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    //Valido que este completo
                    if (campoNombre.getText().trim().isEmpty() || campoCapacidad.getText().trim().isEmpty() || campoPrecio.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Todos los campos deben estar completos");
                    }
                    Estadio estadioSeleccionado = (Estadio) comboEstadio.getSelectedItem();
                    if (estadioSeleccionado == null) {
                        throw new IllegalArgumentException("Seleccione un estadio");
                    } else {
                        Ubicacion ubicacion = new Ubicacion(
                                estadioSeleccionado.getIdEstadio(),
                                estadioSeleccionado.getUltimaUbicacion()+1,
                                campoNombre.getText(),
                                Double.parseDouble(campoPrecio.getText()),
                                Integer.parseInt(campoCapacidad.getText()));


                        serviceUbicacion.guardar(ubicacion);
                        estadioSeleccionado.setUltimaUbicacion(estadioSeleccionado.getUltimaUbicacion()+1);
                        JOptionPane.showMessageDialog(null, "Datos guardados exitosamente");

                        vaciarComponentes();
                    }
                }catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Datos incorrectos");
                    vaciarComponentes();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });
    }

    public void vaciarComponentes(){
        campoNombre.setText("");
        campoCapacidad.setText("");
        campoPrecio.setText("");
    }

}
