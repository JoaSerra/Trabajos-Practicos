package gui.estadio;

import gui.PanelManager;
import gui.PantallaPrincipal;
import model.Estadio;
import model.Ubicacion;
import service.ServiceExeption;
import service.ServiceUbicacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MostrarEstadio extends JPanel {
    ServiceUbicacion serviceUbicacion = new ServiceUbicacion();

    JPanel mostrarEstadio = new JPanel();;
    PanelManager panel;

    JLabel idEstadio;
    JLabel nombreEstadio;
    JLabel direccionEstadio;
    JLabel imagenEstadio = new JLabel();
    JLabel imagenAlternativo = new JLabel("Imagen del estadio");

    DefaultListModel<Ubicacion> listaModel = new DefaultListModel<>();
    JList<Ubicacion> listaUbis = new JList<>(listaModel);
    JScrollPane scroll = new JScrollPane(listaUbis);

    ArrayList<Ubicacion> ubicacionArray;

    JButton btnVolver = new JButton("<--");
    JButton btnVolverInicio = new JButton("Volver al inicio");

    public MostrarEstadio(PanelManager panelManager, Estadio estadio, String imagenRuta) throws ServiceExeption {
        panel = panelManager;
        armarpantalla(estadio, imagenRuta);
    }

    public void armarpantalla(Estadio estadio, String imagenRuta) throws ServiceExeption {
        mostrarEstadio.setLayout(new GridLayout(6, 2,15,20));
        mostrarEstadio.setBorder(new EmptyBorder(10,10,10,10));

        idEstadio = new JLabel("ID estadio: " + estadio.getIdEstadio());
        nombreEstadio = new JLabel("Estadio " + estadio.getNombre());
        direccionEstadio = new JLabel("Direccion: " + estadio.getDireccion());

        //Fila 1
        mostrarEstadio.add(btnVolver);
        mostrarEstadio.add(new JLabel(""));

        //Fila 2
        mostrarEstadio.add(idEstadio);
        mostrarEstadio.add(new JLabel(""));

        //Fila 3
        mostrarEstadio.add(nombreEstadio);
        if(imagenRuta != null) {
            ImageIcon imageIcon = new ImageIcon(imagenRuta);
            Image imagen = imageIcon.getImage();
            Image newImg = imagen.getScaledInstance(250, 130, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newImg);
            imagenEstadio.setIcon(imageIcon);
            mostrarEstadio.add(imagenEstadio);
        }else{
            mostrarEstadio.add(imagenAlternativo);
        }


        //Fila 4
        mostrarEstadio.add(direccionEstadio);
        mostrarEstadio.add(new JLabel(""));

        //Fila 5
        ubicacionArray = serviceUbicacion.obtenerUbicacionesPorEstadio(estadio.getIdEstadio());
        for (Ubicacion ubicacion : ubicacionArray) {
            listaModel.addElement(ubicacion);
        }
        mostrarEstadio.add(scroll);
        mostrarEstadio.add(new JLabel(""));

        //Fila 6
        mostrarEstadio.add(new JLabel(""));
        mostrarEstadio.add(btnVolverInicio);

        setLayout(new BorderLayout());
        add(mostrarEstadio, BorderLayout.CENTER);


        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                VerEstadio verEstadio = null;
                try {
                    verEstadio = new VerEstadio(panel);
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }
                panel.mostrar(verEstadio);
            }
        });

        btnVolverInicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(panel);
                panel.mostrar(pantallaPrincipal);
            }
        });
    }
}
