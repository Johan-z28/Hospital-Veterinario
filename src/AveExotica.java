public class AveExotica extends Paciente {
    private double envergadura;

    public AveExotica(String nombre, double peso, int edad, double envergadura) {
        super(nombre, peso, edad, 3);
        setEnvergadura(envergadura);
    }

    @Override
    public double getPesoMaximo() {
        return 15.0;
    }

    public boolean setEnvergadura(double envergadura) {
        if (envergadura <= 0) return false;
        this.envergadura = envergadura;
        return true;
    }

    public double getEnvergadura() {
        return envergadura;
    }
}
