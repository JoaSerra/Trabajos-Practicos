package gui.vendedor;

import gui.PanelManager;
import gui.espectaculo.PantaEspectaculos;
import model.Entrada;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import service.ServiceEntrada;
import service.ServiceExeption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EliminarEntrada extends JPanel {
    ServiceEntrada serviceEntrada = new ServiceEntrada();
    JPanel eliminarEntrada = new JPanel();
    PanelManager panel;

    JLabel indicacion = new JLabel("Seleccione una entrada");

    ArrayList<Entrada> entradas = serviceEntrada.todasLasEntradas();
    JComboBox<Entrada> comboEntrada = new JComboBox<>(entradas.toArray(new Entrada[0]));

    JButton btnEliminar = new JButton("Eliminar");
    JButton btnCancelar = new JButton("Cancelar");

    public EliminarEntrada(PanelManager panelManager) throws ServiceExeption {
        panel = panelManager;
        armarPantalla();
    }

    public void armarPantalla(){
        eliminarEntrada.setLayout(new GridLayout(3,2,20,20));
        eliminarEntrada.setBorder(new EmptyBorder(20,20,20,20));

        //Fila 1
        eliminarEntrada.add(indicacion);
        eliminarEntrada.add(new JLabel(""));

        //Fila 2
        eliminarEntrada.add(comboEntrada);
        eliminarEntrada.add(new JLabel(""));

        //Fila 3
        eliminarEntrada.add(btnEliminar);
        eliminarEntrada.add(btnCancelar);

        setLayout(new BorderLayout());
        add(eliminarEntrada, BorderLayout.CENTER);

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Entrada entradaSeleccionada = (Entrada) comboEntrada.getSelectedItem();

                try{
                    if(entradaSeleccionada != null){
                        serviceEntrada.eliminar(entradaSeleccionada.getNroEntrada());

                        JOptionPane.showMessageDialog(null, "Entrada eliminada");

                        PantaVendedor pantaVendedor = new PantaVendedor(panel);
                        panel.mostrar(pantaVendedor);
                    }
                } catch (JdbcSQLIntegrityConstraintViolationException e) {
                    JOptionPane.showMessageDialog(null, "No se puede eliminar la entrada");
                } catch (ServiceExeption e) {
                    throw new RuntimeException(e);
                }

            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                PantaVendedor pantaVendedor = new PantaVendedor(panel);
                panel.mostrar(pantaVendedor);
            }
        });

    }

}
