import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String linea = sc.nextLine().trim();
            if (linea.isEmpty()) continue;
            if (linea.equals("salir")) break;

            String[] t = linea.split("\\s+");

            switch (t[0]) {
                case "ingresar":
                    System.out.println(procesarIngreso(hospital, t));
                    break;
                case "vacunar":
                    if (t.length != 2) System.out.println("ERROR: datos invalidos");
                    else System.out.println(hospital.vacunar(t[1]));
                    break;
                case "asignar":
                    if (t.length != 2) System.out.println("ERROR: datos invalidos");
                    else System.out.println(hospital.asignar(t[1]));
                    break;
                case "medicar":
                    if (t.length != 3) {
                        System.out.println("ERROR: datos invalidos");
                    } else {
                        try {
                            double mg = Double.parseDouble(t[2]);
                            System.out.println(hospital.medicar(t[1], mg, t[2]));
                        } catch (NumberFormatException e) {
                            System.out.println("ERROR: datos invalidos");
                        }
                    }
                    break;
                case "alta":
                    if (t.length != 2) System.out.println("ERROR: datos invalidos");
                    else System.out.println(hospital.alta(t[1]));
                    break;
                case "reporte":
                    System.out.println(hospital.reporte());
                    break;
                default:
                    System.out.println("ERROR: comando desconocido");
            }
        }
        sc.close();
    }

    private static String procesarIngreso(Hospital hospital, String[] t) {
        if (t.length < 2) return "ERROR: datos invalidos";
        try {
            switch (t[1]) {
                case "perro":
                    if (t.length != 7) return "ERROR: datos invalidos";
                    if (!t[6].equals("si") && !t[6].equals("no")) return "ERROR: datos invalidos";
                    return hospital.ingresarPerro(t[2], Double.parseDouble(t[3]),
                            Integer.parseInt(t[4]), t[5], t[6].equals("si"));
                case "gato":
                    if (t.length != 5) return "ERROR: datos invalidos";
                    return hospital.ingresarGato(t[2], Double.parseDouble(t[3]),
                            Integer.parseInt(t[4]));
                case "ave":
                    if (t.length != 6) return "ERROR: datos invalidos";
                    return hospital.ingresarAve(t[2], Double.parseDouble(t[3]),
                            Integer.parseInt(t[4]), Double.parseDouble(t[5]));
                default:
                    return "ERROR: comando desconocido";
            }
        } catch (NumberFormatException e) {
            return "ERROR: datos invalidos";
        }
    }
}