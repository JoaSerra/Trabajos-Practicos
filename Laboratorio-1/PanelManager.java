package gui;

import javax.swing.*;
import java.awt.*;

public class PanelManager {
    private PantallaPrincipal pantallaPrincipal;

    JFrame frame;

    public PanelManager() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Sistema de venta de entradas");

        frame.setSize(800, 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        pantallaPrincipal = new PantallaPrincipal(this);
        mostrar(pantallaPrincipal);


    }

    public void mostrar(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        frame.setVisible(true);
        frame.pack();
    }

    /*
    public void mostrarLista(){
        pantaAdministrador.vaciarComponentes();
        mostrar(pantaAdministrador);
    }

    public void mostrarLista(Administrador admin) {
        pantaAdministrador.armarLista(admin);
        mostrar(pantaAdministrador);
    }
    */

}
