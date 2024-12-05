package gui.espectaculo;

import gui.PanelManager;
import gui.PantallaPrincipal;
import model.Entrada;
import model.Espectaculo;
import service.ServiceEntrada;
import service.ServiceEspectaculo;
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

public class VerEspectaculos extends JPanel {
    ServiceEspectaculo serviceEspectaculo = new ServiceEspectaculo();
    ServiceEntrada serviceEntrada = new ServiceEntrada();

    JPanel verEspectaculos = new JPanel();
    PanelManager panel;

    JLabel labelEspectaculos = new JLabel("Seleccione un espectaculo");
    JLabel labelReporte = new JLabel("Reporte de ventas");
    JLabel labelFechaInicio = new JLabel("Fecha de Inicio");
    JLabel labelFechaFin = new JLabel("Fecha de Fin");
    JLabel labelEstadio = new JLabel("Estadio");
    JLabel labelFechaEspect = new JLabel("Fecha del espectaculo");

    JTextField campoEstadio = new JTextField();
    JTextField campoFecha = new JTextField();

    ArrayList<Espectaculo> espectaculos = serviceEspectaculo.todosLosEspectaculos();
    JComboBox<Espectaculo> comboEspectaculo = new JComboBox<>(espectaculos.toArray(new Espectaculo[0]));;

    SpinnerDateModel dateModelEspectaculo = new SpinnerDateModel();
    SpinnerDateModel dateModel1 = new SpinnerDateModel();
    SpinnerDateModel dateModel2 = new SpinnerDateModel();

    JSpinner dateSpinnerEspectaculo = new JSpinner(dateModelEspectaculo);
    JSpinner dateSpinnerInicio = new JSpinner(dateModel1);
    JSpinner dateSpinnerFin = new JSpinner(dateModel2);

    JSpinner.DateEditor editorEspectaculo = new JSpinner.DateEditor(dateSpinnerEspectaculo, "dd/MM/yyyy");
    JSpinner.DateEditor editor1 = new JSpinner.DateEditor(dateSpinnerFin, "dd/MM/yyyy");
    JSpinner.DateEditor editor2 = new JSpinner.DateEditor(dateSpinnerInicio, "dd/MM/yyyy");

    JButton btnMostrar = new JButton("Mostrar");
    JButton btnVolver = new JButton("Volver al inicio");

    public VerEspectaculos(PanelManager panelManager) throws ServiceExeption {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla(){
        verEspectaculos.setLayout(new GridLayout(8,2,20,20));
        verEspectaculos.setBorder(new EmptyBorder(10,10,10,10));

        labelReporte.setFont(new Font("SansSerif", Font.BOLD, 18));

        dateSpinnerEspectaculo.setEditor(editorEspectaculo);
        dateSpinnerFin.setEditor(editor1);
        dateSpinnerInicio.setEditor(editor2);

        campoEstadio.setEditable(false);
        campoFecha.setEditable(false);

        //Fila 1
        verEspectaculos.add(labelEspectaculos);
        verEspectaculos.add(comboEspectaculo);

        //Fila 2
        verEspectaculos.add(labelEstadio);
        verEspectaculos.add(campoEstadio);

        //Fila 3
        verEspectaculos.add(labelFechaEspect);
        verEspectaculos.add(campoFecha);

        //Fila 4
        verEspectaculos.add(labelReporte);
        verEspectaculos.add(new JLabel(""));

        //Fila 5
        verEspectaculos.add(labelFechaInicio);
        verEspectaculos.add(dateSpinnerInicio);

        //Fila 6
        verEspectaculos.add(labelFechaFin);
        verEspectaculos.add(dateSpinnerFin);

        //Fila 7
        verEspectaculos.add(btnMostrar);
        verEspectaculos.add(new JLabel(""));

        //Fila 8
        verEspectaculos.add(new JLabel(""));
        verEspectaculos.add(btnVolver);

        setLayout(new BorderLayout());
        add(verEspectaculos, BorderLayout.CENTER);


        comboEspectaculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Espectaculo espectaculoSeleccionado = (Espectaculo) comboEspectaculo.getSelectedItem();

                if (espectaculoSeleccionado != null) {
                    Date fechaDate = Date.from(espectaculoSeleccionado.getFechaLocal().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

                    campoEstadio.setText(String.valueOf(espectaculoSeleccionado.getEstadio()));
                    //dateSpinnerEspectaculo.setValue(fechaDate);
                    campoFecha.setText(String.valueOf(espectaculoSeleccionado.getFechaLocal()));

                }
            }
        });


        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                panel.mostrar(pantallaPrincipal);
            }
        });

        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    //Transformo la fecha del dateSpinner a LocalDate
                    Date fechaInicio = (Date) dateSpinnerInicio.getValue();
                    LocalDate fechaInicioSeleccionada = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    Date fechaFin = (Date) dateSpinnerFin.getValue();
                    LocalDate fechaFinSeleccionada = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    Espectaculo espectaculoSeleccionado = (Espectaculo) comboEspectaculo.getSelectedItem();

                    if(serviceEntrada.todasLasEntradas().isEmpty()){
                        JOptionPane.showMessageDialog(null, "No hay ninguna entrada vendida");
                        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                        panel.mostrar(pantallaPrincipal);
                    }
                    //Valido que las fechas esten correctamente ingresadas
                    if (fechaInicioSeleccionada.isAfter(fechaFinSeleccionada) || fechaFinSeleccionada.isBefore(fechaInicioSeleccionada)) {
                        throw new IllegalArgumentException("Fechas incorrectas");
                    }
                    if (espectaculoSeleccionado != null) {
                        String mensaje = Entrada.ReporteEntradasEspectaculo(fechaInicioSeleccionada, fechaFinSeleccionada, espectaculoSeleccionado);
                        System.out.println("Mensaje: " + mensaje);
                        JOptionPane.showMessageDialog(null, mensaje);
                    }else{
                        throw new IllegalArgumentException("Seleccione un espectaculo");
                    }

                }catch (IllegalArgumentException | ServiceExeption e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });
    }
}
