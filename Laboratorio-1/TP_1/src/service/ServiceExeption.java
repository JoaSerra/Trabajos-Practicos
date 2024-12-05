package service;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

public class ServiceExeption extends Exception {
    public ServiceExeption() {
        super();
    }
    public ServiceExeption(String message) {
        super(message);
    }

}
