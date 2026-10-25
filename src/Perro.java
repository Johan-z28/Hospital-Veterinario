public class Perro extends Paciente{
    private String raza;
    private boolean vacunado;

    public Perro(String nombre,  double peso, int edad, String raza, boolean vacunado){
        super(nombre, peso, edad, 10);
        this.raza = raza;
        this.vacunado = vacunado;
        if (!vacunado) {
            setEstado("CUARENTENA");
        }
    }

    @Override
    public double getPesoMaximo() {
        return 80.0;
    }

    public String getRaza() {
        return raza;
    }

    public boolean isVacunado() {
        return vacunado;
    }

    public void vacunar() {
        this.vacunado = true;
        setEstado("INGRESADO");
    }


}
