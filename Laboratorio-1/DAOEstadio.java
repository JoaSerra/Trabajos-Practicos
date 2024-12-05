package DAO;

import model.Estadio;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import service.ServiceExeption;

import java.sql.*;
import java.util.ArrayList;

public class DAOEstadio implements IDAO<Estadio> {
    private final String DB_JDBC_DRIVER = "org.h2.Driver";
    private final String DB_JDBC_URL = "jdbc:h2:C:\\Users\\joahs\\Escritorio\\TP_lab1\\BD_tp;AUTO_SERVER=TRUE";
    private final String DB_JDBC_USER = "sa";
    private final String DB_JDBC_PASS = "";

    @Override
    public void guardar(Estadio elemento) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL, DB_JDBC_USER, DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("INSERT INTO ESTADIO VALUES(?,?,?,?)"); //

            //Valores para setear
            preparedStatement.setInt(1, elemento.getIdEstadio());
            preparedStatement.setString(2, elemento.getNombre());
            preparedStatement.setString(3, elemento.getDireccion());
            preparedStatement.setInt(4, elemento.getUltimaUbicacion());

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
    public void modificar(Estadio elemento) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("UPDATE ESTADIO SET NOMBRE=?,DIRECCION=?,ULTIMA_UBICACION=? WHERE ID_ESTADIO = ?");

            //Valores a modificar
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setString(2, elemento.getDireccion());
            preparedStatement.setInt(3, elemento.getUltimaUbicacion());
            preparedStatement.setInt(4, elemento.getIdEstadio());

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
    public void eliminar(int idEstadio) throws DAOExeption, JdbcSQLIntegrityConstraintViolationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("DELETE FROM ESTADIO WHERE ID_ESTADIO=?");
            preparedStatement.setInt(1,idEstadio);

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            if(e instanceof JdbcSQLIntegrityConstraintViolationException)
                throw (JdbcSQLIntegrityConstraintViolationException) e;
            else
                throw new DAOExeption("Error al acceder a la base de datos");
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
    }


    @Override
    public Estadio buscar(int idEstadio) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Estadio estadioAux = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("SELECT * FROM ESTADIO WHERE ID_ESTADIO=?");
            preparedStatement.setInt(1, idEstadio);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                estadioAux = new Estadio();
                estadioAux.setIdEstadio(rs.getInt("ID_ESTADIO"));
                estadioAux.setNombre(rs.getString("NOMBRE"));
                estadioAux.setDireccion(rs.getString("DIRECCION"));
                estadioAux.setUltimaUbicacion(rs.getInt("ULTIMA_UBICACION"));
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
        return estadioAux;
    }

    @Override
    public ArrayList<Estadio> buscarTodos() throws DAOExeption{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Estadio estadioAux;
        ArrayList<Estadio> estadiosAux = new ArrayList<>();

        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("SELECT * FROM ESTADIO");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                estadioAux = new Estadio();
                estadioAux.setIdEstadio(rs.getInt("ID_ESTADIO"));
                estadioAux.setNombre(rs.getString("NOMBRE"));
                estadioAux.setDireccion(rs.getString("DIRECCION"));
                estadioAux.setUltimaUbicacion(rs.getInt("ULTIMA_UBICACION"));
                estadiosAux.add(estadioAux);
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
        return estadiosAux;
    }

    /*
    public void cargarUbicaciones(ArrayList<Estadio> estadios) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL, DB_JDBC_USER, DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("SELECT * FROM UBICACION");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                for (Estadio estadio : estadios) {
                    int idEstadio = rs.getInt("ID_ESTADIO");
                    if (estadio.getIdEstadio() == idEstadio) {
                        estadio.crearUbicacion(
                                rs.getString("NOMBRE"),
                                rs.getDouble("PRECIO"),
                                rs.getInt("CAPACIDAD")
                        );
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOExeption(e.getMessage());
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
    }
    */

}
