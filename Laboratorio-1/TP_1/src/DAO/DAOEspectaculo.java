package DAO;

import model.Espectaculo;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;

public class DAOEspectaculo implements IDAO<Espectaculo> {
    private final String DB_JDBC_DRIVER = "org.h2.Driver";
    private final String DB_JDBC_URL = "jdbc:h2:C:\\Users\\joahs\\Escritorio\\TP_lab1\\BD_tp;AUTO_SERVER=TRUE";
    private final String DB_JDBC_USER = "sa";
    private final String DB_JDBC_PASS = "";


    @Override
    public void guardar(Espectaculo elemento) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL, DB_JDBC_USER, DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("INSERT INTO ESPECTACULO VALUES(?,?,?,?,?)"); //
            //Valores para setear
            preparedStatement.setInt(1, elemento.getIdEspectaculo());
            preparedStatement.setString(2, elemento.getNombre());
            preparedStatement.setDouble(3, elemento.getPrecioBase());
            preparedStatement.setDate(4, elemento.getFechaSQL());
            preparedStatement.setInt(5, elemento.getEstadio().getIdEstadio());

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
    public void modificar(Espectaculo elemento) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("UPDATE ESPECTACULO SET NOMBRE=?,PRECIO_BASE=?,FECHA=?,ID_ESTADIO=? WHERE ID_ESPECTACULO=?");

            //Valores a modificar
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setDouble(2, elemento.getPrecioBase());
            preparedStatement.setDate(3, elemento.getFechaSQL());
            preparedStatement.setInt(4, elemento.getEstadio().getIdEstadio());
            preparedStatement.setInt(5, elemento.getIdEspectaculo());

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
    public void eliminar(int idEspectaculo) throws DAOExeption, JdbcSQLIntegrityConstraintViolationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("DELETE FROM ESPECTACULO WHERE ID_ESPECTACULO=?");
            preparedStatement.setInt(1,idEspectaculo);

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
    public Espectaculo buscar(int idEspectaculo) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Espectaculo espectaculoAux = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("SELECT * FROM ESPECTACULO WHERE ID_ESPECTACULO=?");
            preparedStatement.setInt(1, idEspectaculo);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                espectaculoAux = new Espectaculo();
                espectaculoAux.setIdEspectaculo(rs.getInt("ID_ESPECTACULO"));
                espectaculoAux.setNombre(rs.getString("NOMBRE"));
                espectaculoAux.setPrecioBase(rs.getDouble("PRECIO_BASE"));
                espectaculoAux.setFechaConSQL(rs.getDate("FECHA"));
                espectaculoAux.setEstadio(rs.getInt("ID_ESTADIO"));
            }

        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            throw new DAOExeption("Error al acceder a la base de datos");
        } catch (Exception e) {
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
        return espectaculoAux;
    }

    @Override
    public ArrayList<Espectaculo> buscarTodos() throws DAOExeption{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Espectaculo espectaculoAux;
        ArrayList<Espectaculo> espectaculosAux = new ArrayList<>();

        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("SELECT * FROM ESPECTACULO");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                espectaculoAux = new Espectaculo();
                espectaculoAux.setIdEspectaculo(rs.getInt("ID_ESPECTACULO"));
                espectaculoAux.setNombre(rs.getString("NOMBRE"));
                espectaculoAux.setPrecioBase(rs.getDouble("PRECIO_BASE"));
                espectaculoAux.setFechaConSQL(rs.getDate("FECHA"));
                espectaculoAux.setEstadio(rs.getInt("ID_ESTADIO"));
                espectaculosAux.add(espectaculoAux);
            }
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOExeption(e.getMessage());
        } catch (Exception e) {
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
        return espectaculosAux;
    }
}
