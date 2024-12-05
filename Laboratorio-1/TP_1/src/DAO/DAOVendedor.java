package DAO;

import model.Vendedor;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;

public class DAOVendedor implements IDAO<Vendedor>{
    private final String DB_JDBC_DRIVER = "org.h2.Driver";
    private final String DB_JDBC_URL = "jdbc:h2:C:\\Users\\joahs\\Escritorio\\TP_lab1\\BD_tp;AUTO_SERVER=TRUE";
    private final String DB_JDBC_USER = "sa";
    private final String DB_JDBC_PASS = "";

    @Override
    public void guardar(Vendedor elemento) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("INSERT INTO VENDEDOR VALUES(?,?,?,?)"); //

            //Valores para setear
            preparedStatement.setInt(1, elemento.getDni());
            preparedStatement.setString(2, elemento.getNombre());
            preparedStatement.setString(3, elemento.getApellido());
            preparedStatement.setString(4, elemento.getTelefono());

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
    public void modificar(Vendedor elemento) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("UPDATE VENDEDOR SET NOMBRE=?,APELLIDO=?,TELEFONO=? WHERE DNI=?");

            //Valores a modificar
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setString(2, elemento.getApellido());
            preparedStatement.setString(3, elemento.getTelefono());
            preparedStatement.setInt(4, elemento.getDni());

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
    public void eliminar(int dni) throws DAOExeption, JdbcSQLIntegrityConstraintViolationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("DELETE FROM VENDEDOR WHERE DNI=?");
            preparedStatement.setInt(1,dni);

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
    public Vendedor buscar(int dni) throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Vendedor admininAux = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("SELECT * FROM VENDEDOR WHERE DNI=?");
            preparedStatement.setInt(1, dni);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                admininAux = new Vendedor();
                admininAux.setDni(rs.getInt("DNI"));
                admininAux.setNombre(rs.getString("NOMBRE"));
                admininAux.setApellido(rs.getString("APELLIDO"));
                admininAux.setTelefono(rs.getString("TELEFONO"));
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
        return admininAux;
    }

    @Override
    public ArrayList<Vendedor> buscarTodos() throws DAOExeption {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Vendedor vendedorAux;
        ArrayList<Vendedor> vendedoresAux = new ArrayList<>();

        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_JDBC_URL,DB_JDBC_USER,DB_JDBC_PASS);
            preparedStatement = connection.prepareStatement("SELECT * FROM VENDEDOR");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                vendedorAux = new Vendedor();
                vendedorAux.setDni(rs.getInt("DNI"));
                vendedorAux.setNombre(rs.getString("NOMBRE"));
                vendedorAux.setApellido(rs.getString("APELLIDO"));
                vendedorAux.setTelefono(rs.getString("TELEFONO"));
                vendedoresAux.add(vendedorAux);
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
        return vendedoresAux;
    }
}

