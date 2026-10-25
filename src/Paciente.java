public abstract class Paciente {
    private String nombre;
    private double peso;
    private int edad;
    private String estado;          // "INGRESADO", "CUARENTENA", "EN_TRATAMIENTO"
    private double factorDosis;
    private double dosisAcumulada;
    private Jaula jaula;

    public Paciente(String nombre, double peso, int edad, double factorDosis) {
        setNombre(nombre);
        setPeso(peso);
        setEdad(edad);
        this.factorDosis = factorDosis;
        this.estado = "INGRESADO";
        this.dosisAcumulada = 0;
        this.jaula = null;
    }

    public abstract double getPesoMaximo();

    public boolean setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        this.nombre = nombre.trim();
        return true;
    }

    public boolean setPeso(double peso) {
        if (peso <= 0 || peso > getPesoMaximo()) return false;
        this.peso = peso;
        return true;
    }

    public boolean setEdad(int edad) {
        if (edad < 0) return false;
        if (edad > 30) return false;
        this.edad = edad;
        return true;
    }

    public boolean setEstado(String estado) {
        if (!estado.equals("INGRESADO") && !estado.equals("CUARENTENA") && !estado.equals("EN_TRATAMIENTO")) {
            return false;
        }
        this.estado = estado;
        return true;
    }

    public double getFactorDosis() {
        return factorDosis;
    }

    public double getDosisAcumulada() {
        return dosisAcumulada;
    }

    public Jaula getJaula() {
        return jaula;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPeso() {
        return peso;
    }

    public int getEdad() {
        return edad;
    }

    public String getEstado() {
        return estado;
    }

    public void setJaula(Jaula jaula) {
        this.jaula = jaula;
    }

    public double getDosisMaxima() {
        return peso * factorDosis;
    }

    public void sumarDosis(double mg) {
        dosisAcumulada += mg;
    }
}