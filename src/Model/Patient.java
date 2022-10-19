package Model;

public class Patient {
    String Cedula;
    String Nombre;
    int Edad;
    int Enfermedad;
    int Prioridad;
    public String Sexo;
    public boolean Embarazada;

    public Patient(String Cedula, String Nombre, int Edad, int Enfermedad, int p, String Sexo, boolean Embarazada) {
        this.Cedula = Cedula;
        this.Nombre = Nombre;
        this.Edad = Edad;
        this.Enfermedad = Enfermedad;
        this.Prioridad = p;
        this.Sexo = Sexo;
        this.Embarazada = Embarazada;
    }

    public Patient(){

    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String cedula) {
        Cedula = cedula;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    public int getEnfermedad() {
        return Enfermedad;
    }

    public void setEnfermedad(int enfermedad) {
        Enfermedad = enfermedad;
    }

    public int getPrioridad() {
        return Prioridad;
    }

    public void setPrioridad(int prioridad) {
        Prioridad = prioridad;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public boolean getEmbarazada() {
        return Embarazada;
    }

    public void setEmbarazada(boolean embarazada) {
        Embarazada = embarazada;

    }
}
