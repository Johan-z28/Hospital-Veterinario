public class Gato extends Paciente {
    private int nivelEstres;

    public Gato(String nombre, double peso, int edad) {
        super(nombre, peso, edad, 6);
        setNivelEstres(0);
    }

    @Override
    public double getPesoMaximo() {
        return 15.0;
    }

    public boolean setNivelEstres(int nivelEstres) {
        if (nivelEstres < 0) return false;
        if (nivelEstres > 10) return false;
        this.nivelEstres = nivelEstres;
        return true;
    }

    public int getNivelEstres() {
        return nivelEstres;
    }

    public void subirEstres() {
        nivelEstres +=2;
        if (nivelEstres > 10) {
            nivelEstres = 10;
        }
    }
}