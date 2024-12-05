package gui.estadio;

import gui.PanelManager;
import gui.PantallaPrincipal;
import model.Estadio;
import service.ServiceEstadio;
import service.ServiceExeption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearEstadio extends JPanel {
    ServiceEstadio serviceEstadio = new ServiceEstadio();
    JPanel crearEstadio = new JPanel();;
    PanelManager panel;

    JLabel indicacion = new JLabel("Ingrese los datos del estadio");
    JLabel id = new JLabel("Id del estadio: ");
    JLabel nombre = new JLabel("Nombre: ");
    JLabel direccion = new JLabel("Direccion: ");

    JTextField campoId = new JTextField();
    JTextField campoNombre = new JTextField();
    JTextField campoDireccion = new JTextField();

    JButton btnCrear = new JButton("Crear");
    JButton btnVolver = new JButton("Volver al inicio");

    public CrearEstadio(PanelManager panelManager) {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla(){
        crearEstadio.setLayout(new GridLayout(5,3, 20,20));
        crearEstadio.setBorder(new EmptyBorder(10,10,10,10));

        indicacion.setFont(new Font("SansSerif", Font.PLAIN, 18));

        //Fila 1
        crearEstadio.add(indicacion);
        crearEstadio.add(new JLabel(""));
        crearEstadio.add(new JLabel(""));

        //Fila 2
        crearEstadio.add(id);
        crearEstadio.add(campoId);
        crearEstadio.add(new JLabel(""));

        //Fila 3
        crearEstadio.add(nombre);
        crearEstadio.add(campoNombre);
        crearEstadio.add(new JLabel(""));

        //Fila 4
        crearEstadio.add(direccion);
        crearEstadio.add(campoDireccion);
        crearEstadio.add(btnCrear);

        //Fila 5 -> volver
        crearEstadio.add(new JLabel(""));
        crearEstadio.add(new JLabel(""));
        crearEstadio.add(btnVolver);

        setLayout(new BorderLayout());
        add(crearEstadio, BorderLayout.CENTER);

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                panel.mostrar(pantallaPrincipal);
            }
        });

        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(campoId.getText().trim().isEmpty() || campoNombre.getText().trim().isEmpty() || campoDireccion.getText().trim().isEmpty()){
                        throw new IllegalArgumentException("Todos los campos deben estar completos");
                    }

                    Estadio estadio = new Estadio(
                            Integer.parseInt(campoId.getText()),
                            campoNombre.getText(),
                            campoDireccion.getText());

                    serviceEstadio.guardar(estadio);
                    JOptionPane.showMessageDialog(crearEstadio, "Estadio guardado correctamente");

                    PantaEstadios pantaEstadios = new PantaEstadios(panel);
                    panel.mostrar(pantaEstadios);

                }catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "El Id debe ser un número entero.");
                }catch (IllegalArgumentException | ServiceExeption e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });

    }

    public void vaciarComponentes(){
            campoId.setText("");
            campoNombre.setText("");
            campoDireccion.setText("");
        }
}
