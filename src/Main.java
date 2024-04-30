import clases.Conversiones;
import clases.Moneda;
import servicios.ConexionesDeApi;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int opciones;
        String opcion;
        Scanner lectura = new Scanner(System.in);

        while (true) {
            System.out.println("""
                     \nElige una opcion a convertir:
                     1) Convierte de Peso Argentino a Dolar.
                     2) Convierte de Peso Boliviano a Dolar.
                     3) Convierte de Real Brasileño a Dolar.
                     4) Convierte de Peso Chileno a Dolar.
                     5) Convierte de Peso Colombiano a Dolar.
                     0) Salir.
                     Ingrese el numero:
                    """);
            opciones = lectura.nextInt();
            try {
                ConexionesDeApi conexionesDeApi = new ConexionesDeApi();
                opcion = switch (opciones) {
                    case 1 -> "ARS";
                    case 2 -> "BOB";
                    case 3 -> "BRL";
                    case 4 -> "CLP";
                    case 5 -> "COP";
                    default -> "salir";
                };
                if (opcion.equalsIgnoreCase("salir")) {
                    System.out.println("Saliendo de la aplicacion...");
                    break;
                }
                Moneda moneda = conexionesDeApi.newConexionAPi(opcion);
                Map<String, Double> tarifasDeConversion = (Map<String, Double>) moneda.conversion_rates();
                System.out.println("Ingrese el monto a convertir");
                double monto = lectura.nextDouble();
                double resultado = Conversiones.convertirMonedaxUSD(monto, tarifasDeConversion.get("USD"));
                System.out.println("**********************************************");
                System.out.println("El monto de " + monto + " " + opcion + " equivale a " + resultado + " USD");
                System.out.println("**********************************************");

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}