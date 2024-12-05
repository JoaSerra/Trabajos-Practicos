package DAO;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.util.ArrayList;

public interface IDAO <T>{

    public void guardar(T elemento) throws DAOExeption;
    public void modificar(T elemento) throws DAOExeption;
    public void eliminar(int dni) throws DAOExeption, JdbcSQLIntegrityConstraintViolationException;
    public T buscar(int dni) throws DAOExeption;

    public ArrayList<T> buscarTodos() throws DAOExeption;
}