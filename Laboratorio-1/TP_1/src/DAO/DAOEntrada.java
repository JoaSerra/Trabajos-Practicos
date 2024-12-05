package DAO;

import model.Entrada;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import service.ServiceExeption;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DAOEntrada implements IDAO<Entrada> {
    private final String DB_JDBC_DRIVER = "org.h2.Driver";
    private final String DB_JDBC_URL = "jdbc:h2:C:\\Users\\joahs\\Escritorio\\TP_lab1\\BD_tp;AUTO_SERVER=TRUE";
    private final String DB_JDBC_USER = "sa";
    private final String DB_JDBC_PASS = "";


    @Override
    public void guardar(Entrada elemento) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL, DB_JDBC_USER, DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("INSERT INTO ENTRADA VALUES(?,?,?,?)"); //
            //Valores para setear
            preparedStatement.setInt(1, elemento.getNroEntrada());
            preparedStatement.setInt(2, elemento.getEspectaculo().getIdEspectaculo());
            preparedStatement.setDouble(3, elemento.getPrecio());
            preparedStatement.setInt(4, elemento.getUbicacion().getIdUbi());

            preparedStatement.executeUpdate(); //ejecuto SQL

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new DAOExeption("Error al acceder a la base de datos");
        }finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
                throw new DAOExeption("ERROR");
            }
        }
    }

    @Override
    public void modificar(Entrada elemento) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("UPDATE ENTRADA SET ID_ESPECTACULO=?,PRECIO=?,UBICACION=? WHERE NRO_ENTRADA=?");

            //Valores a modificar
            preparedStatement.setInt(1, elemento.getEspectaculo().getIdEspectaculo());
            preparedStatement.setDouble(2, elemento.getPrecio());
            preparedStatement.setInt(3, elemento.getUbicacion().getIdUbi());
            preparedStatement.setInt(4, elemento.getNroEntrada());

            preparedStatement.executeUpdate(); //ejecuto SQL

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new DAOExeption("Error al acceder a la base de datos");
        }finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
                throw new DAOExeption("ERROR");
            }
        }
    }


    @Override
    public void eliminar(int nroEntrada) throws DAOExeption, JdbcSQLIntegrityConstraintViolationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("DELETE FROM ENTRADA WHERE NRO_ENTRADA=?");
            preparedStatement.setInt(1,nroEntrada);

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            if(e instanceof JdbcSQLIntegrityConstraintViolationException)
                throw (JdbcSQLIntegrityConstraintViolationException) e;
            else
                throw new DAOExeption("Error al acceder a la base de datos");
        }finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
                throw new DAOExeption("ERROR");
            }
        }
    }

    @Override
    public Entrada buscar(int nroEntrada) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Entrada entradaAux = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("SELECT * FROM ENTRADA WHERE NRO_ENTRADA=?");
            preparedStatement.setInt(1, nroEntrada);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                entradaAux = new Entrada();
                entradaAux.setNroEntrada(rs.getInt("NRO_ENTRADA"));
                entradaAux.setEspectaculoId(rs.getInt("ID_ESPECTACULO"));
                entradaAux.setPrecio(rs.getDouble("PRECIO"));
                entradaAux.setUbicacionId(rs.getInt("UBICACION"));
            }

        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            throw new DAOExeption("Error al acceder a la base de datos");
        } catch (ServiceExeption e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
                throw new DAOExeption("ERROR");
            }
        }
        return entradaAux;
    }

    @Override
    public ArrayList<Entrada> buscarTodos() throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Entrada entradaAux;
        ArrayList<Entrada> entradasAux = new ArrayList<>();

        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("SELECT * FROM ENTRADA");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                entradaAux = new Entrada();
                entradaAux.setNroEntrada(rs.getInt("NRO_ENTRADA"));
                entradaAux.setEspectaculoId(rs.getInt("ID_ESPECTACULO"));
                entradaAux.setPrecio(rs.getDouble("PRECIO"));
                entradaAux.setUbicacionId(rs.getInt("UBICACION"));
                entradasAux.add(entradaAux);
            }
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOExeption(e.getMessage());
        } catch (ServiceExeption e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new DAOExeption(e.getMessage());
            }
        }
        return entradasAux;
    }

}
