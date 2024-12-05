package service;

import DAO.DAOExeption;
import DAO.DAOUbicacion;
import model.Ubicacion;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.util.ArrayList;
import java.util.Map;

public class ServiceUbicacion {
    private final DAOUbicacion daoUbicacion;

    public ServiceUbicacion(){
        daoUbicacion = new DAOUbicacion();
    }

    public void guardar(Ubicacion ubicacion) throws ServiceExeption {
        try {
            daoUbicacion.guardar(ubicacion);
            System.out.println("Ubicacion guardada con exito");
        } catch(DAOExeption e) {
            throw new ServiceExeption(e.getMessage());
        }
    }

    public void modificar(Ubicacion ubicacion) throws ServiceExeption {
        try{
            daoUbicacion.modificar(ubicacion);
        } catch(DAOExeption e) {
            throw new ServiceExeption(e.getMessage());
        }
    }

    public Ubicacion buscarUbicacion(int id, int idEstadio) throws ServiceExeption{
        Ubicacion ubicacion;
        try{
            ubicacion = daoUbicacion.buscarUbicacion(id, idEstadio);
            return ubicacion;
        } catch(DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }

    public void eliminar(int id) throws ServiceExeption{
        try{
            daoUbicacion.eliminar(id);
        }catch (DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }

    public void eliminarUbicacion(int id, int idEstadio) throws ServiceExeption, JdbcSQLIntegrityConstraintViolationException {
        try{
            daoUbicacion.eliminarUbicacion(id, idEstadio);
            System.out.println("Ubicacion eliminada");
        }catch (DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }

    public ArrayList<Ubicacion> todasLasUbicaciones() throws ServiceExeption {
        ArrayList<Ubicacion> ubicacions;
        try {
            ubicacions = daoUbicacion.buscarTodos();
            return ubicacions;
        }catch (DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }

    public ArrayList<Ubicacion> obtenerUbicacionesPorEstadio(int idEstadio) throws ServiceExeption {
        ArrayList<Ubicacion> ubicaciones;
        try{
            ubicaciones = daoUbicacion.obtenerUbicacionesPorEstadio(idEstadio);
            return ubicaciones;
        }catch (DAOExeption e){
            throw new ServiceExeption(e.getMessage());
        }
    }
}
