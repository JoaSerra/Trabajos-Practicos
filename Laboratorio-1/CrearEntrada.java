package gui.vendedor;

import gui.PanelManager;
import gui.PantallaPrincipal;
import model.*;
import service.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CrearEntrada extends JPanel {
    ServiceEspectaculo serviceEspectaculo = new ServiceEspectaculo();
    ServiceUbicacion serviceUbicacion = new ServiceUbicacion();
    ServiceEntrada serviceEntrada = new ServiceEntrada();

    JPanel crearEntrada = new JPanel();
    PanelManager panel;

    JLabel titulo = new JLabel("Vender Entrada");
    JLabel espectaculo = new JLabel("Seleccione un espectaculo: ");
    JLabel ubi = new JLabel("Seleccione una ubicacion: ");
    JLabel promocion = new JLabel("Seleccione una promocion");

    JCheckBox checkBox = new JCheckBox("Tiene abono? (El precio de la entrada será 0)");

    JButton btnCrear = new JButton("Crear");
    JButton btnVolver = new JButton("Volver al inicio");

    ArrayList<Espectaculo> espectaculos = serviceEspectaculo.todosLosEspectaculos();
    JComboBox<Espectaculo> comboEspectaculo = new JComboBox<>(espectaculos.toArray(new Espectaculo[0]));

    JComboBox<Ubicacion> comboUbicacion = new JComboBox<>();
    JComboBox<String> comboPromocion = new JComboBox<>(new String[]{"Sin Promocion","10%", "15%", "25%", "50%"});;


    public CrearEntrada(PanelManager panelManager) throws ServiceExeption {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla() throws ServiceExeption {
        crearEntrada.setLayout(new GridLayout(7,2,15,20));
        crearEntrada.setBorder(new EmptyBorder(10,10,10,10));

        titulo.setFont(new Font("SansSerif", Font.PLAIN, 18));

        //Fila 1
        crearEntrada.add(titulo);
        crearEntrada.add(new JLabel(""));

        //Fila 2
        crearEntrada.add(espectaculo);
        crearEntrada.add(comboEspectaculo);

        //Fila 3
        crearEntrada.add(ubi);
        crearEntrada.add(comboUbicacion);

        //Fila 4
        crearEntrada.add(promocion);
        crearEntrada.add(comboPromocion);

        //Fila 5
        crearEntrada.add(checkBox);
        crearEntrada.add(new JLabel(""));

        //Fila 6
        crearEntrada.add(btnCrear);
        crearEntrada.add(new JLabel(""));

        //Fila 7
        crearEntrada.add(new JLabel(""));
        crearEntrada.add(btnVolver);

        setLayout(new BorderLayout());
        add(crearEntrada,BorderLayout.CENTER);

        comboPromocion.setSelectedIndex(0);

        comboEspectaculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Espectaculo espectaculoSeleccionado = (Espectaculo) comboEspectaculo.getSelectedItem();
                if(espectaculoSeleccionado != null) {
                    Estadio estadioAux = espectaculoSeleccionado.getEstadio();

                    try {
                        ArrayList<Ubicacion> ubicaciones = serviceUbicacion.obtenerUbicacionesPorEstadio(estadioAux.getIdEstadio());
                        comboUbicacion.setModel(new DefaultComboBoxModel<>(ubicaciones.toArray(new Ubicacion[0])));
                    } catch (ServiceExeption e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    comboUbicacion.setModel(new DefaultComboBoxModel<>(new Ubicacion[0]));
                }
            }
        });


        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Espectaculo espectaculoSeleccionado = (Espectaculo) comboEspectaculo.getSelectedItem();
                Ubicacion ubicacionSeleccionada = (Ubicacion) comboUbicacion.getSelectedItem();
                String promocionSeleccionada = (String) comboPromocion.getSelectedItem();

                try {
                    //Valido que se haya seleccionado un espectaculo y una ubicacion
                    if(espectaculoSeleccionado == null || ubicacionSeleccionada == null) {
                        throw new IllegalArgumentException("Seleccione un espectaculo y ubicacion");
                    }

                    Estadio estAux = espectaculoSeleccionado.getEstadio();
                    Ubicacion ubiAux = Ubicacion.buscarUbicacion(ubicacionSeleccionada.getIdUbi(), estAux.getIdEstadio());

                    //Valido que haya capacidad disponible en la ubiAux
                    if(ubiAux.getCapacidad() < 1){
                        JOptionPane.showMessageDialog(crearEntrada, "No hay mas entradas para esta ubicacion");
                    }else{
                        //Creo la entrada
                        Entrada entrada = new Entrada(espectaculoSeleccionado, ubicacionSeleccionada);

                        // Aplicar descuento si hay uno seleccionado
                        if (comboPromocion.getSelectedIndex() != 0) {
                            int porcentajeDescuento = Integer.parseInt(promocionSeleccionada.replace("%", ""));
                            double precioOriginal = entrada.getPrecio();
                            double precioConDescuento = precioOriginal - (precioOriginal * porcentajeDescuento / 100);
                            entrada.setPrecio(precioConDescuento);
                        }


                        //Verifico si tiene abono
                        if(checkBox.isSelected()){
                            entrada.setPrecio(0);
                        }

                        //Muestro panel de confirmacion
                        int opcion = JOptionPane.showConfirmDialog(crearEntrada,
                                entrada + "\n\nConfirma la creacion de la entrada",
                                "Confirmar creacion",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE);

                        //Si el usuario presiona OK, se guarda la entrada en BBDD
                        if(opcion == JOptionPane.OK_OPTION){
                            serviceEntrada.guardar(entrada);

                            //Modifico la capacidad de la ubi en BBDD
                            ubiAux.setCapacidad(ubiAux.getCapacidad()-1);
                            serviceUbicacion.modificar(ubiAux);

                            JOptionPane.showMessageDialog(null,"Entrada creada con éxito");
                        }
                    }
                    CrearEntrada crearEntrada = new CrearEntrada(panel);
                    panel.mostrar(crearEntrada);

                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                panel.mostrar(pantallaPrincipal);
            }
        });
    }
}
