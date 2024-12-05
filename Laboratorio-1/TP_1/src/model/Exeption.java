package model;

public class Exeption extends Exception {

    public Exeption() {super();}
    public Exeption(String message) {
        super(message);
    }

    public Exeption(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString(){
        return "Ha ocurrido: " + this.getClass().getName() + " con el siguiente problema " + this.getMessage();
    }
}
