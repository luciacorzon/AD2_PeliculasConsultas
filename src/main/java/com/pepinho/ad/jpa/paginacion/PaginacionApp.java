package com.pepinho.ad.jpa.paginacion;

import com.pepinho.ad.jpa.JPAUtil;
import com.pepinho.ad.jpa.dto.PeliculaPaginaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Scanner;

public class PaginacionApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EntityManagerFactory emf = JPAUtil.getEmFactory(JPAUtil.UNIDAD_PERSISTENCIA);
        EntityManager em = emf.createEntityManager();

        // Crear o Paginador
        Paginador paginador = new Paginador();

        // Pedir input de usuario para buscar
        System.out.println("🔍 Busca unha película por nombre:");
        System.out.print("--> ");
        String nombrePeli = sc.nextLine();

        // Inicializar o Paginador co tamaño da páxina, a búsqueda do uduario e o EntityManager
        paginador.init(10, nombrePeli, em);

        while (true) {

            // Obter a lista de películas da páxina actual
            List<PeliculaPaginaDTO> peliculas = paginador.getResultadosActuales();

            // Se a lista de películas da páxina actual está vacia, comentámosllo ao usuario
            if (peliculas.isEmpty()) {
                System.out.println("\n❌ Non se atoparon películas.\n");
                break;
            }

            // Obter a páxina actual e o número de páxinas, ademais do total de resultados
            System.out.println("\n📄 Página " + (paginador.getPaginaActual() + 1) + " de " + paginador.getTotalPaginas() + "\n" +
            "🎬 Número total de películas encontradas: " + paginador.getPeliculaDAO().contarPeliculasPorNombre(nombrePeli));

            // Se a lista de películas da páxina actual non está vacía, imprimimos con for-each
            for (PeliculaPaginaDTO peli : peliculas) {
                System.out.println(peli + "\n");
            }

            // Despois de imprimir, amosamos o menú de opcións: anterior, siguiente e salir
            System.out.println("\n📖 Opciones:");
            if (paginador.getPaginaActual() > 0) {
                System.out.println("1️⃣ Anterior");
            }
            if (paginador.getPaginaActual() < paginador.getTotalPaginas() - 1) {
                System.out.println("2️⃣ Siguiente");
            }
            System.out.println("3️⃣ Salir");

            // Ler a opción do usuario
            System.out.print("--> ");
            int opcion = sc.nextInt();
            sc.nextLine();  // Consumir a nova línea para evitar problemas polo nextInt

            // Procesar a opción escollida polo usuario
            if (opcion == 1 && paginador.getPaginaActual() > 0) {
                paginador.previous(); // páxina anterior
            } else if (opcion == 2 && paginador.getPaginaActual() < paginador.getTotalPaginas() - 1) {
                paginador.next(); // páxina seguinte
            } else if (opcion == 3) {
                break; // salir do bucle e da aplicación
            }
        }

        // Cerrar recursos: paginador, entitymanager, entitymanagerfactory e scanner
        paginador.finished();
        em.close();
        emf.close();
        sc.close();
    }
}
