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
import java.io.File;
import java.util.ArrayList;

public class VerEstadio extends JPanel {
    ServiceEstadio serviceEstadio = new ServiceEstadio();

    JPanel verEstadio = new JPanel();;
    PanelManager panel;

    JLabel labelEstadio = new JLabel("Seleccione un estadio");;
    JLabel imagen = new JLabel("Imagen");
    JLabel imagenCargada= new JLabel("Imagen cargada correctamente");

    JComboBox<Estadio> comboEstadio;
    ArrayList<Estadio> estadios = serviceEstadio.todosLosEstadios();

    JButton btnMostrar = new JButton("Mostrar");
    JButton btnVolver = new JButton("Volver al inicio");
    JButton btnCargarImagen = new JButton("Subir imagen");

    String imagenRuta = null;

    public VerEstadio(PanelManager panelManager) throws ServiceExeption {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla(){
        verEstadio.setLayout(new GridLayout(5, 2,15,20));
        verEstadio.setBorder(new EmptyBorder(10,10,10,10));

        comboEstadio = new JComboBox<>(estadios.toArray(new Estadio[0]));


        //Fila 1
        verEstadio.add(labelEstadio);
        verEstadio.add(comboEstadio);

        //Fila 2
        verEstadio.add(imagen);
        verEstadio.add(btnCargarImagen);

        //Fila 3
        imagenCargada.setVisible(false);
        verEstadio.add(imagenCargada);
        verEstadio.add(new JLabel(""));

        //Fila 4
        verEstadio.add(btnMostrar);
        verEstadio.add(new JLabel(""));

        //Fila 5
        verEstadio.add(new JLabel(""));
        verEstadio.add(btnVolver);

        setLayout(new BorderLayout());
        add(verEstadio, BorderLayout.CENTER);


        btnCargarImagen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int option = fileChooser.showOpenDialog(null);

                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    imagenRuta = file.getAbsolutePath();
                    imagenCargada.setVisible(true);

                    JOptionPane.showMessageDialog(null, "Imagen cargada correctamente");
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
                Estadio estadioSeleccionado = (Estadio) comboEstadio.getSelectedItem();
                MostrarEstadio mostrarEstadio;
                try {
                    if(imagenRuta != null) {
                        mostrarEstadio = new MostrarEstadio(panel, estadioSeleccionado, imagenRuta);
                        panel.mostrar(mostrarEstadio);
                    }else{
                        JOptionPane.showMessageDialog(verEstadio,"Debe cargar la imagen del estadio");
                    }
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

