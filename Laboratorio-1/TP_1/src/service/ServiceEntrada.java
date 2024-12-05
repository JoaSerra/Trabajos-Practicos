package service;

import DAO.DAOEntrada;
import DAO.DAOExeption;
import model.Entrada;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.util.ArrayList;

public class ServiceEntrada {
    private final DAOEntrada daoEntrada;

    public ServiceEntrada(){
        daoEntrada = new DAOEntrada();
    }

    public void guardar(Entrada entrada) throws ServiceExeption {
        try {
            daoEntrada.guardar(entrada);
            System.out.println("Entrada guardada");
        } catch(DAOExeption e) {
            throw new ServiceExeption(e.getMessage());
        }
    }

    public void modificar(Entrada entrada) throws ServiceExeption {
        try{
            daoEntrada.modificar(entrada);
        } catch(DAOExeption e) {
            throw new ServiceExeption(e.getMessage());
        }
    }

    public Entrada buscarAdministrador(int id) throws ServiceExeption{
        Entrada entrada;
        try{
            entrada = daoEntrada.buscar(id);
            return entrada;
        } catch(DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }

    public void eliminar(int id) throws ServiceExeption, JdbcSQLIntegrityConstraintViolationException {
        try{
            daoEntrada.eliminar(id);
            System.out.println("Entrada eliminada");
        }catch (DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }

    public ArrayList<Entrada> todasLasEntradas() throws ServiceExeption {
        ArrayList<Entrada> entradas;
        try {
            entradas = daoEntrada.buscarTodos();
            return entradas;
        }catch (DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }
}
