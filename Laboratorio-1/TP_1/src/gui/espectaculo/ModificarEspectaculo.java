package gui.espectaculo;

import gui.PanelManager;
import gui.administrador.PantaAdmin;
import gui.estadio.PantaEstadios;
import model.Espectaculo;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
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

public class ModificarEspectaculo extends JPanel {
    ServiceEspectaculo serviceEspectaculo = new ServiceEspectaculo();
    JPanel modificarEspectaculo = new JPanel();
    PanelManager panel;

    JLabel titulo = new JLabel("Modificar Espectaculo");
    JLabel instruccion = new JLabel("Seleccione un espectaculo: ");
    JLabel idEspectaculo = new JLabel("ID: ");
    JLabel nombre = new JLabel("Nombre del artista: ");
    JLabel estadio = new JLabel("ID del estadio: ");
    JLabel precio = new JLabel("Precio base: ");
    JLabel fecha = new JLabel("Fecha: ");

    JTextField campoIdEspectaculo = new JTextField();
    JTextField campoIdEstadio = new JTextField();
    JTextField campoNombre = new JTextField();
    JTextField campoPrecio = new JTextField();

    JButton btnModificar = new JButton("Modificar");
    JButton btnEliminar = new JButton("Eliminar");
    JButton btnCancelar = new JButton("Cancelar");


    SpinnerDateModel model = new SpinnerDateModel();
    JSpinner dateSpinner = new JSpinner(model);
    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");

    ArrayList<Espectaculo> espectaculos = serviceEspectaculo.todosLosEspectaculos();
    JComboBox<Espectaculo> comboEspectaculo = new JComboBox<>(espectaculos.toArray(new Espectaculo[0]));

    public ModificarEspectaculo(PanelManager panelManager) throws ServiceExeption {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla(){
        modificarEspectaculo.setLayout(new GridLayout(9,2,20,20));
        modificarEspectaculo.setBorder(new EmptyBorder(10,10,10,10));

        titulo.setFont(new Font("SansSerif", Font.PLAIN, 18));

        campoIdEspectaculo.setEditable(false);

        dateSpinner.setEditor(editor);

        //Fila 1
        modificarEspectaculo.add(titulo);
        modificarEspectaculo.add(new JLabel(""));

        //Fila 2
        modificarEspectaculo.add(instruccion);
        modificarEspectaculo.add(comboEspectaculo);

        //Fila 3
        modificarEspectaculo.add(idEspectaculo);
        modificarEspectaculo.add(campoIdEspectaculo);

        //Fila 4
        modificarEspectaculo.add(nombre);
        modificarEspectaculo.add(campoNombre);

        //Fila 5
        modificarEspectaculo.add(precio);
        modificarEspectaculo.add(campoPrecio);

        //Fila 6
        modificarEspectaculo.add(estadio);
        modificarEspectaculo.add(campoIdEstadio);

        //Fila 7
        modificarEspectaculo.add(fecha);
        modificarEspectaculo.add(dateSpinner);

        //Fila 8
        modificarEspectaculo.add(btnEliminar);
        modificarEspectaculo.add(btnModificar);

        //Fila 9
        modificarEspectaculo.add(new JLabel(""));
        modificarEspectaculo.add(btnCancelar);

        setLayout(new BorderLayout());
        add(modificarEspectaculo, BorderLayout.CENTER);

        comboEspectaculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Espectaculo espectaculoSeleccionado = (Espectaculo) comboEspectaculo.getSelectedItem();
                if(espectaculoSeleccionado != null){
                    Date fechaDate = Date.from(espectaculoSeleccionado.getFechaLocal().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

                    campoIdEspectaculo.setText(String.valueOf(espectaculoSeleccionado.getIdEspectaculo()));
                    campoIdEstadio.setText(String.valueOf(espectaculoSeleccionado.getEstadio().getIdEstadio()));
                    campoNombre.setText(espectaculoSeleccionado.getNombre());
                    campoPrecio.setText(String.valueOf(espectaculoSeleccionado.getPrecioBase()));
                    dateSpinner.setValue(fechaDate);
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Date fechaDate = (Date) dateSpinner.getValue();
                    LocalDate fechaLocal = fechaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    Espectaculo espectaculoModificado = new Espectaculo();

                    espectaculoModificado.setIdEspectaculo(Integer.parseInt(campoIdEspectaculo.getText()));
                    espectaculoModificado.setNombre(campoNombre.getText());
                    espectaculoModificado.setPrecioBase(Double.parseDouble(campoPrecio.getText()));
                    espectaculoModificado.setEstadio(Integer.parseInt(campoIdEstadio.getText()));
                    espectaculoModificado.setFechaConLocal(fechaLocal);

                    serviceEspectaculo.modificar(espectaculoModificado);

                    JOptionPane.showMessageDialog(modificarEspectaculo, "Espectaculo modificado");

                    PantaEstadios pantaEstadios = new PantaEstadios(panel);
                    panel.mostrar(pantaEstadios);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Espectaculo espectaculoSeleccionado = (Espectaculo) comboEspectaculo.getSelectedItem();
                try {
                    if(espectaculoSeleccionado != null){
                        serviceEspectaculo.eliminar(espectaculoSeleccionado.getIdEspectaculo());

                        JOptionPane.showMessageDialog(modificarEspectaculo, "Espectaculo eliminado");

                        PantaAdmin pantaAdmin = new PantaAdmin(panel);
                        panel.mostrar(pantaAdmin);
                    }
                } catch (JdbcSQLIntegrityConstraintViolationException e){
                    JOptionPane.showMessageDialog(null, "No se puede eliminar el espectaculo\nHay entradas vendidas");
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                PantaEspectaculos pantaEspectaculos = new PantaEspectaculos(panel);
                panel.mostrar(pantaEspectaculos);
            }
        });
    }
}
