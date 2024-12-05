package service;

import DAO.DAOAdministrador;
import DAO.DAOExeption;
import model.Administrador;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.util.ArrayList;

public class ServiceAdministrador {
    private final DAOAdministrador daoAdministrador;

    public ServiceAdministrador(){
        daoAdministrador = new DAOAdministrador();
    }

    public void guardar(Administrador administrador) throws ServiceExeption {
        try {
            daoAdministrador.guardar(administrador);
            System.out.println("Administrador guardado");
        } catch(DAOExeption e) {
            throw new ServiceExeption(e.getMessage());
        }
    }

    public void modificar(Administrador administrador) throws ServiceExeption {
        try{
            daoAdministrador.modificar(administrador);
            System.out.println("Administrador modificado");
        } catch(DAOExeption e) {
            throw new ServiceExeption(e.getMessage());
        }
    }

    public Administrador buscarAdministrador(int dni) throws ServiceExeption{
        Administrador administrador;
        try{
            administrador = daoAdministrador.buscar(dni);
            return administrador;
        } catch(DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }

    public void eliminar(int id) throws ServiceExeption, JdbcSQLIntegrityConstraintViolationException {
        try{
            daoAdministrador.eliminar(id);
            System.out.println("Administrador eliminado");
        }catch (DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }

    public ArrayList<Administrador> todosLosAdministradores() throws ServiceExeption {
        ArrayList<Administrador> administradores;
        try {
            administradores = daoAdministrador.buscarTodos();
            return administradores;
        }catch (DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }
}
