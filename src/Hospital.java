public class Hospital {
    private Paciente[] pacientes = new Paciente[10];
    private int contador = 0;
    private Jaula[] jaulas = new Jaula[6];

    public Hospital() {
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        jaulas[0] = new Jaula(1, "S", "A");
        jaulas[1] = new Jaula(2, "M", "A");
        jaulas[2] = new Jaula(3, "M", "A");
        jaulas[3] = new Jaula(4, "L", "A");
        jaulas[4] = new Jaula(5, "S", "B");
        jaulas[5] = new Jaula(6, "M", "B");

        pacientes[contador] = new Perro("Rocky", 30.0, 4, "labrador", true);
        contador++;
        pacientes[contador] = new Gato("Misifu", 4.0, 3);
        contador++;
        pacientes[contador] = new AveExotica("Kiwi", 0.8, 2, 70);
        contador++;
    }

    private int buscar(String nombre) {
        for (int i = 0; i < contador; i++) {
            if (pacientes[i].getNombre().equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1;
    }

    public String reporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- REPORTE ---\n");
        sb.append("Pacientes: ").append(contador).append("/10\n");

        for (int i = 0; i < contador; i++) {
            Paciente p = pacientes[i];

            String tipo;
            if (p instanceof Perro) {
                tipo = "Perro";
            } else if (p instanceof Gato) {
                tipo = "Gato";
            } else {
                tipo = "Ave";
            }

            String jaula = (p.getJaula() == null) ? "-" : String.valueOf(p.getJaula().getNumero());

            sb.append(p.getNombre()).append(" | ")
                    .append(tipo).append(" | ")
                    .append(String.format(java.util.Locale.US, "%.1f", p.getPeso())).append(" kg | ")
                    .append(p.getEstado()).append(" | jaula: ")
                    .append(jaula).append("\n");
        }

        int libres = 0;
        for (int i = 0; i < jaulas.length; i++) {
            if (jaulas[i].estaLibre()) {
                libres++;
            }
        }
        sb.append("Jaulas libres: ").append(libres);

        return sb.toString();
    }

    public String ingresarGato(String nombre, double peso, int edad) {
        // 1. datos inválidos
        if (nombre == null || nombre.trim().isEmpty() || peso <= 0 || peso > 15 || edad < 0 || edad > 30) {
            return "ERROR: datos invalidos";
        }
        // 2. nombre duplicado
        if (buscar(nombre) != -1) {
            return "ERROR: nombre duplicado";
        }
        // 3. hospital lleno
        if (contador == 10) {
            return "ERROR: hospital lleno";
        }
        // 4. todo bien: crear y guardar
        pacientes[contador] = new Gato(nombre, peso, edad);
        contador++;
        return "OK: " + nombre + " ingresado";
    }

    public String ingresarAve(String nombre, double peso, int edad, double envergadura) {
        if (nombre == null || nombre.trim().isEmpty() || peso <= 0 || peso > 15
                || edad < 0 || edad > 30 || envergadura <= 0) {
            return "ERROR: datos invalidos";
        }
        if (buscar(nombre) != -1) return "ERROR: nombre duplicado";
        if (contador == 10) return "ERROR: hospital lleno";
        pacientes[contador] = new AveExotica(nombre, peso, edad, envergadura);
        contador++;
        return "OK: " + nombre + " ingresado";
    }

    public String ingresarPerro(String nombre, double peso, int edad, String raza, boolean vacunado) {
        if (nombre == null || nombre.trim().isEmpty() || peso <= 0 || peso > 80
                || edad < 0 || edad > 30) {
            return "ERROR: datos invalidos";
        }
        if (buscar(nombre) != -1) return "ERROR: nombre duplicado";
        if (contador == 10) return "ERROR: hospital lleno";
        pacientes[contador] = new Perro(nombre, peso, edad, raza, vacunado);
        contador++;
        if (!vacunado) return "OK: " + nombre + " ingresado en CUARENTENA";
        return "OK: " + nombre + " ingresado";
    }

    public String vacunar(String nombre) {
        int i = buscar(nombre);
        if (i == -1) return "ERROR: paciente no encontrado";
        if (!(pacientes[i] instanceof Perro)) return "ERROR: solo aplica a perros";
        Perro perro = (Perro) pacientes[i];
        if (perro.isVacunado()) return "ERROR: ya vacunado";
        perro.vacunar();
        return "OK: " + perro.getNombre() + " vacunado (pasa a INGRESADO)";
    }

    public String asignar(String nombre) {
        int i = buscar(nombre);
        if (i == -1) return "ERROR: paciente no encontrado";
        Paciente p = pacientes[i];
        if (p.getEstado().equals("CUARENTENA")) return "ERROR: paciente en cuarentena";
        if (p.getJaula() != null) return "ERROR: paciente ya tiene jaula";

        String tamano;
        if (p.getPeso() < 5) tamano = "S";
        else if (p.getPeso() <= 25) tamano = "M";
        else tamano = "L";
        if (p instanceof AveExotica && ((AveExotica) p).getEnvergadura() > 60 && tamano.equals("S")) {
            tamano = "M";
        }
        String zona = (p instanceof AveExotica) ? "B" : "A";

        for (int j = 0; j < jaulas.length; j++) {
            Jaula jaula = jaulas[j];
            if (jaula.estaLibre() && jaula.getTamano().equals(tamano) && jaula.getZona().equals(zona)) {
                jaula.setOcupante(p);
                p.setJaula(jaula);
                return "OK: " + p.getNombre() + " asignado a jaula " + jaula.getNumero();
            }
        }
        return "ERROR: no hay jaula compatible";
    }

    public String medicar(String nombre, double mg, String mgTexto) {
        int i = buscar(nombre);
        if (i == -1) return "ERROR: paciente no encontrado";
        Paciente p = pacientes[i];
        if (mg <= 0) return "ERROR: datos invalidos";
        if (p.getEstado().equals("CUARENTENA")) return "ERROR: paciente en cuarentena";
        if (p.getJaula() == null) return "ERROR: paciente sin jaula";
        if (p instanceof Gato && ((Gato) p).getNivelEstres() >= 8) return "ERROR: gato con estres alto";
        if (mg > p.getDosisMaxima()) return "ERROR: dosis excede el maximo";
        if (p.getDosisAcumulada() + mg > 3 * p.getDosisMaxima()) return "ERROR: tope acumulado superado";

        p.sumarDosis(mg);
        p.setEstado("EN_TRATAMIENTO");
        if (p instanceof Gato) ((Gato) p).subirEstres();
        return "OK: " + p.getNombre() + " medicado con " + mgTexto + " mg";
    }

    public String alta(String nombre) {
        int i = buscar(nombre);
        if (i == -1) return "ERROR: paciente no encontrado";
        Paciente p = pacientes[i];
        if (!p.getEstado().equals("EN_TRATAMIENTO")) return "ERROR: paciente no apto para alta";

        if (p.getJaula() != null) {
            p.getJaula().setOcupante(null);
            p.setJaula(null);
        }
        for (int j = i; j < contador - 1; j++) {
            pacientes[j] = pacientes[j + 1];
        }
        pacientes[contador - 1] = null;
        contador--;
        return "OK: " + p.getNombre() + " dado de alta";
    }
}