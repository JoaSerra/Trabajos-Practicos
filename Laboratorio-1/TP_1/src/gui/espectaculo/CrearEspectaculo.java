package gui.espectaculo;

import gui.PanelManager;
import gui.PantallaPrincipal;
import model.Espectaculo;
import model.Estadio;
import service.ServiceEspectaculo;
import service.ServiceEstadio;
import service.ServiceExeption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class CrearEspectaculo extends JPanel {
    ServiceEspectaculo serviceEspectaculo = new ServiceEspectaculo();
    ServiceEstadio serviceEstadio = new ServiceEstadio();

    JPanel crearEspectaculo = new JPanel();
    PanelManager panel;


    //Etiquetas
    JLabel labelIndicacion = new JLabel("Ingrese los datos del espectaculo");
    JLabel labelId = new JLabel("ID: ");
    JLabel labelNombre = new JLabel("Nombre: ");
    JLabel labelPrecio = new JLabel("Precio: ");
    JLabel labelFecha = new JLabel("Fecha: ");
    JLabel labelEstadios = new JLabel("Estadios: ");

    //Campos de texto
    JTextField campoID = new JTextField();
    JTextField campoNombre = new JTextField();
    JTextField campoPrecio = new JTextField();

    //Botones
    JButton btnCrear = new JButton("Crear");
    JButton btnVolver = new JButton("Volver al inicio");

    //Seleccionador de fechas
    SpinnerDateModel model = new SpinnerDateModel();
    JSpinner dateSpinner = new JSpinner(model);
    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");

    //Seleccionador de Estadio
    ArrayList<Estadio>estadios = serviceEstadio.todosLosEstadios();
    JComboBox<Estadio> comboEstadio = new JComboBox<>(estadios.toArray(new Estadio[0]));


    public CrearEspectaculo(PanelManager panelManager) throws ServiceExeption {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla(){
        crearEspectaculo.setLayout(new GridLayout(7,3,20,20));
        crearEspectaculo.setBorder(new EmptyBorder(10,10,10,10));

        labelIndicacion.setFont(new Font("SansSerif",Font.PLAIN,18));

        dateSpinner.setEditor(editor);

        //Fila 1
        crearEspectaculo.add(labelIndicacion);
        crearEspectaculo.add(new JLabel(""));
        crearEspectaculo.add(new JLabel(""));

        //Fila 2
        crearEspectaculo.add(labelId);
        crearEspectaculo.add(campoID);
        crearEspectaculo.add(new JLabel(""));

        //Fila 3
        crearEspectaculo.add(labelNombre);
        crearEspectaculo.add(campoNombre);
        crearEspectaculo.add(new JLabel(""));

        //Fila 4
        crearEspectaculo.add(labelPrecio);
        crearEspectaculo.add(campoPrecio);
        crearEspectaculo.add(new JLabel(""));

        //Fila 5
        crearEspectaculo.add(labelFecha);
        crearEspectaculo.add(dateSpinner);
        crearEspectaculo.add(new JLabel(""));

        //Fila 6
        crearEspectaculo.add(labelEstadios);
        crearEspectaculo.add(comboEstadio);
        crearEspectaculo.add(btnCrear);

        //Fila 7
        crearEspectaculo.add(new JLabel(""));
        crearEspectaculo.add(new JLabel(""));
        crearEspectaculo.add(btnVolver);

        setLayout(new BorderLayout());
        add(crearEspectaculo, BorderLayout.CENTER);

        //Boton volver al inicio
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                panel.mostrar(pantallaPrincipal);
            }
        });

        //Boton crear espectaculo
        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    //Valido que esten los campos completos
                    if(campoID.getText().trim().isEmpty() || campoNombre.getText().trim().isEmpty() || campoPrecio.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Todos los campos deben estar completos");
                    }else {
                        //Valido que la fecha este correctamente ingresada
                        Date fechaSeleccionada = (Date) dateSpinner.getValue();
                        LocalDate fecha = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        if (fecha.isBefore(LocalDate.now())) {
                            throw new IllegalArgumentException("El espectaculo no puede ser programado para una fecha que ya pasó");
                        }
                        //Valido que haya un Estadio seleccionado
                        Estadio estadioSeleccionado = (Estadio) comboEstadio.getSelectedItem();
                        if(estadioSeleccionado == null) {
                            throw new IllegalArgumentException("Seleccione un estadio");
                        }
                        //Creo el espectaculo
                        Espectaculo espectaculo = new Espectaculo(
                                Integer.parseInt(campoID.getText()),
                                campoNombre.getText(),
                                Double.parseDouble(campoPrecio.getText()),
                                fecha, estadioSeleccionado);

                        //Guardo el Espectaculo en la BBDD
                        serviceEspectaculo.guardar(espectaculo);
                        JOptionPane.showMessageDialog(crearEspectaculo, "Datos guardados exitosamente");

                        vaciarComponentes();
                    }
                }catch(NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Datos incorrectos");
                    vaciarComponentes();
                }catch (IllegalArgumentException | ServiceExeption e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });
    }

    public void vaciarComponentes(){
        campoID.setText("");
        campoNombre.setText("");
        campoPrecio.setText("");
    }
}
