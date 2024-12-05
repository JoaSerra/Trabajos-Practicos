package service;

import DAO.DAOEstadio;
import DAO.DAOExeption;
import model.Estadio;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class ServiceEstadio {
    private final DAOEstadio daoEstadio;

    public ServiceEstadio(){
        daoEstadio = new DAOEstadio();
    }

    public void guardar(Estadio estadio) throws ServiceExeption {
        try {
            daoEstadio.guardar(estadio);
            System.out.println("Estadio guardado");
        } catch(DAOExeption e) {
            throw new ServiceExeption(e.getMessage());
        }
    }

    public void modificar(Estadio estadio) throws ServiceExeption {
        try{
            daoEstadio.modificar(estadio);
        } catch(DAOExeption e) {
            throw new ServiceExeption(e.getMessage());
        }
    }

    public Estadio buscarEstadio(int id) throws ServiceExeption{
        Estadio estadio;
        try{
            estadio = daoEstadio.buscar(id);
            return estadio;
        } catch(DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }

    public void eliminar(int id) throws ServiceExeption, JdbcSQLIntegrityConstraintViolationException {
        try{
            daoEstadio.eliminar(id);
            System.out.println("Estadio eliminado");
        }catch (DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }

    public ArrayList<Estadio> todosLosEstadios() throws ServiceExeption {
        ArrayList<Estadio> estadios;
        try {
            estadios = daoEstadio.buscarTodos();
            return estadios;
        }catch (DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }

}
