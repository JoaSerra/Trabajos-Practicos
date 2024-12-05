package DAO;

import model.Ubicacion;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DAOUbicacion implements IDAO<Ubicacion>{
    private final String DB_JDBC_DRIVER = "org.h2.Driver";
    private final String DB_JDBC_URL = "jdbc:h2:C:\\Users\\joahs\\Escritorio\\TP_lab1\\BD_tp;AUTO_SERVER=TRUE";
    private final String DB_JDBC_USER = "sa";
    private final String DB_JDBC_PASS = "";

    @Override
    public void guardar(Ubicacion elemento) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            String sql = "INSERT INTO UBICACION (ID_ESTADIO, ID_UBICACION, NOMBRE, PRECIO, CAPACIDAD) VALUES(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Valores para setear
            preparedStatement.setInt(1, elemento.getIdEstadio());
            preparedStatement.setInt(2, elemento.getIdUbi());
            preparedStatement.setString(3, elemento.getNombreUbi());
            preparedStatement.setDouble(4, elemento.getPrecioUbi());
            preparedStatement.setInt(5, elemento.getCapacidad());

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
    public void modificar(Ubicacion elemento) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("UPDATE UBICACION SET NOMBRE = ?, PRECIO = ?, CAPACIDAD = ? WHERE ID_ESTADIO = ? AND ID_UBICACION = ?");

            //Valores a modificar
            preparedStatement.setInt(4, elemento.getIdEstadio());
            preparedStatement.setInt(5, elemento.getIdUbi());
            preparedStatement.setDouble(2, elemento.getPrecioUbi());
            preparedStatement.setInt(3, elemento.getCapacidad());
            preparedStatement.setString(1, elemento.getNombreUbi());

            preparedStatement.executeUpdate(); //ejecuto SQL

            System.out.println("Ubicacion modificada");
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
    public void eliminar(int idUbi) throws DAOExeption{
        System.out.println("Este método no funciona para la clase Ubicacion\nUtilice el metodo: eliminarUbicacion(int idUbi, int idEstadio)");
    }

    public void eliminarUbicacion(int idUbi, int idEstadio) throws DAOExeption, JdbcSQLIntegrityConstraintViolationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("DELETE FROM UBICACION WHERE ID_ESTADIO = ? AND ID_UBICACION = ?");
            preparedStatement.setInt(1, idEstadio);
            preparedStatement.setInt(2, idUbi);

            preparedStatement.executeUpdate();

            System.out.println("Ubicacion eliminada");
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
    public Ubicacion buscar(int dni) throws DAOExeption {
        System.out.println("Este metodo no funciona en la clase Ubicacion\nUtilice buscarUbicacion(int idUbi, int idEstadio)");
        return null;
    }

    public Ubicacion buscarUbicacion(int idUbi, int idEstadio) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Ubicacion UbiAux = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("SELECT * FROM UBICACION WHERE ID_ESTADIO = ? AND ID_UBICACION = ?");
            preparedStatement.setInt(1, idEstadio);
            preparedStatement.setInt(2, idUbi);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                UbiAux = new Ubicacion();
                UbiAux.setIdEstadio(rs.getInt("ID_ESTADIO"));
                UbiAux.setIdUbi(rs.getInt("ID_UBICACION"));
                UbiAux.setNombreUbi(rs.getString("NOMBRE"));
                UbiAux.setPrecioUbi(rs.getDouble("PRECIO"));
                UbiAux.setCapacidad(rs.getInt("CAPACIDAD"));
            }

        }catch (ClassNotFoundException | SQLException e){
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
        return UbiAux;
    }

    @Override
    public ArrayList<Ubicacion> buscarTodos() throws DAOExeption{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Ubicacion ubiAux;
        ArrayList<Ubicacion> ubicaionesAux = new ArrayList<>();

        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("SELECT * FROM UBICACION");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                ubiAux = new Ubicacion();
                ubiAux.setIdEstadio(rs.getInt("ID_ESTADIO"));
                ubiAux.setIdUbi(rs.getInt("ID_UBICACION"));
                ubiAux.setNombreUbi(rs.getString("NOMBRE"));
                ubiAux.setPrecioUbi(rs.getDouble("PRECIO"));
                ubiAux.setCapacidad(rs.getInt("CAPACIDAD"));
                ubicaionesAux.add(ubiAux);
            }
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOExeption(e.getMessage());
        }
        finally {
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
        return ubicaionesAux;
    }

    public ArrayList<Ubicacion> obtenerUbicacionesPorEstadio(int idEstadio) throws DAOExeption {
        ArrayList<Ubicacion> ubicaciones = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL, DB_JDBC_USER, DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM UBICACION WHERE ID_ESTADIO = ?"
            );
            preparedStatement.setInt(1, idEstadio);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Ubicacion ubicacion = new Ubicacion(
                        idEstadio,
                        rs.getInt("ID_UBICACION"),
                        rs.getString("NOMBRE"),
                        rs.getDouble("PRECIO"),
                        rs.getInt("CAPACIDAD"));
                ubicaciones.add(ubicacion);
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
        return ubicaciones;
    }
}
