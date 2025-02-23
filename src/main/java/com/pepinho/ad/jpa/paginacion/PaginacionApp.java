package com.pepinho.ad.jpa.paginacion;

import com.pepinho.ad.jpa.JPAUtil;
import com.pepinho.ad.jpa.dto.PeliculaPaginaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.Scanner;

public class PaginacionApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Busca unha película por nombre:");
        System.out.print("--> ");
        String nombrePeli = sc.nextLine();

        EntityManagerFactory emf = JPAUtil.getEmFactory(JPAUtil.UNIDAD_PERSISTENCIA);
        EntityManager em = emf.createEntityManager();

        // Crear a query
        TypedQuery<PeliculaPaginaDTO> query = em.createQuery("SELECT new com.pepinho.ad.jpa.dto.PeliculaPaginaDTO(p.idPelicula, " +
                "p.castelan, p.orixinal, p.anoFin, pp.ocupacion.ocupacion) FROM Pelicula p " +
                "JOIN PeliculaPersonaxe pp " +
                "WHERE p.castelan LIKE %:nombrePeli% " +
                "AND pp.ocupacion.ocupacion = 'DIRECTOR'", PeliculaPaginaDTO.class);
        query.setParameter(nombrePeli, ":nombrePeli");

        // Inicializar Pelicula
    }
}
