package com.pepinho.ad.jpa;

import com.pepinho.ad.jpa.peliculas.Ocupacion;
import com.pepinho.ad.jpa.peliculas.Pais;
import com.pepinho.ad.jpa.peliculas.Pelicula;
import com.pepinho.ad.jpa.peliculas.Xenero;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String SEPARADOR = "\n************ ";

    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        EntityManagerFactory emf = JPAUtil.getEmFactory(JPAUtil.UNIDAD_PERSISTENCIA);
        EntityManager em = emf.createEntityManager();

/*
        // a) Obtener todas las películas que tienen una duración mayor a 120 minutos.
        TypedQuery<Pelicula> queryA = em.createQuery("SELECT p FROM Pelicula p WHERE p.duracion > 120", Pelicula.class);
        List<Pelicula> apartadoA = queryA.getResultList();
        System.out.println("\n********* APARTADO A *********");
        for (Pelicula p: apartadoA) {
            System.out.println(p);
        }

        // b) Obtener todas las películas que pertenecen a un género específico (por ejemplo, “Drama”).
        TypedQuery<Pelicula> queryB = em.createQuery(
                "SELECT p FROM Pelicula p WHERE p.xenero = :xenero", Pelicula.class);
        queryB.setParameter("xenero", Xenero.of("Drama"));

        List<Pelicula> apartadoB = queryB.getResultList();

        System.out.println("\n********* APARTADO B *********");
        for (Pelicula pelicula : apartadoB) {
            System.out.println(pelicula);
        }


        // c) Obtener todas las ocupaciones que tienen más de 5 películas asociadas.
        TypedQuery<Ocupacion> queryC = em.createQuery(
                "SELECT o FROM Ocupacion o WHERE SIZE(o.peliculaPersonaxes) > 5", Ocupacion.class);

        List<Ocupacion> apartadoC = queryC.getResultList();

        System.out.println("\n********* APARTADO C *********");
        for (Ocupacion ocupacion : apartadoC) {
            System.out.println(ocupacion);
        }

        // d) Obtener todas las películas que tienen un país específico (por ejemplo, “España”).
        TypedQuery<Pelicula> queryD = em.createQuery(
                "SELECT p FROM Pelicula p WHERE p.pais.pais = :nombrePais", Pelicula.class);
        queryD.setParameter("nombrePais", "España");

        List<Pelicula> apartadoD = queryD.getResultList();

        System.out.println("\n********* APARTADO D *********");
        for (Pelicula pelicula : apartadoD) {
            System.out.println(pelicula);
        }


        // e) Obtener todas las películas que tienen al menos un personaje interpretado por un
        // actor de un país específico (por ejemplo, “Francia”).
        TypedQuery<Pelicula> queryE = em.createQuery(
                "SELECT DISTINCT pp.pelicula FROM PeliculaPersonaxe pp " +
                        "WHERE pp.personaxe.paisNacemento = :nombrePais", Pelicula.class);

        queryE.setParameter("nombrePais", "Francia");

        List<Pelicula> apartadoE = queryE.getResultList();

        System.out.println("\n********* APARTADO E *********");
        for (Pelicula pelicula : apartadoE) {
            System.out.println(pelicula);
        }

        // f) Obtener todas las películas que tienen música compuesta por un compositor específico
        // (por ejemplo, “John Williams”).
        TypedQuery<Pelicula> queryF = em.createQuery(
                "SELECT p FROM Pelicula p WHERE p.musica = :nombreCompositor", Pelicula.class);

        queryF.setParameter("nombreCompositor", "John Williams");

        List<Pelicula> apartadoF = queryF.getResultList();

        System.out.println("\n********* APARTADO F *********");
        for (Pelicula pelicula : apartadoF) {
            System.out.println(pelicula);
        }

        // g) Obtener todas las películas que tienen un personaje interpretado por un actor
        // con un nombre específico (por ejemplo, “Tom Hanks”).
        TypedQuery<Pelicula> queryG = em.createQuery(
                "SELECT DISTINCT p FROM Pelicula p " +
                        "JOIN p.personaxes pp " + // Se une con la tabla intermedia
                        "JOIN pp.personaxe per " + // Se une con la entidad Personaxe
                        "WHERE per.nome = :nombreActor", Pelicula.class);

        queryG.setParameter("nombreActor", "Hanks, Tom");

        List<Pelicula> apartadoG = queryG.getResultList();

        System.out.println("\n********* APARTADO G *********");
        for (Pelicula pelicula : apartadoG) {
            System.out.println(pelicula);
        }


        // h) Obtener todas las películas que tienen un género específico y
        // que fueron producidas en un año específico (por ejemplo, “Acción” y 2005).
        TypedQuery<Pelicula> queryH = em.createQuery(
                "SELECT p FROM Pelicula p " +
                        "WHERE p.xenero = 'Comedia' " +
                        "AND p.anoFin = 1999", Pelicula.class);

        //queryH.setParameter("genero", Xenero.of("Comedia"));
        //queryH.setParameter("anio", (short) 1999);
        List<Pelicula> apartadoH = queryH.getResultList();
        System.out.println("\n********* APARTADO H *********");
        for (Pelicula p: apartadoH) {
            System.out.println(p);
        }

/*


        // i) Obtener todas las películas que tienen un personaje interpretado
        // por un actor de un género específico (por ejemplo, “Mujer”).
        TypedQuery<Pelicula> queryI = em.createQuery(
                "SELECT DISTINCT p FROM Pelicula p " +
                        "JOIN p.personaxes pp " +  // <== Cambia "peliculaPersonaxes" por "personaxes"
                        "JOIN pp.personaxe pe " +
                        "WHERE pe.sexo = 'Mujer'", Pelicula.class);

        List<Pelicula> apartadoI = queryI.getResultList();
        System.out.println("\n********* APARTADO I *********");
        for (Pelicula p : apartadoI) {
            System.out.println(p);
        }



        // k) Obtener todas las películas que tienen un personaje interpretado por
        // un actor que nació en un país específico y que tienen una duración mayor a 100 minutos.
        TypedQuery<Pelicula> queryK = em.createQuery(
                "SELECT p FROM Pelicula p " +
                        "JOIN p.personaxes pp " +
                        "JOIN pp.personaxe pe " +
                        "WHERE pe.paisNacemento = :pais " +
                        "AND p.duracion > 100", Pelicula.class);

        queryK.setParameter("pais", "España");
        List<Pelicula> apartadoK = queryK.getResultList();
        System.out.println("\n********* APARTADO K *********");
        for (Pelicula p: apartadoK) {
            System.out.println(p);
        }*/


        // l) Devolver todos los países que no tienen películas asociadas,
        // puedes usar una consulta JPQL que utilice una subconsulta o
        // un LEFT JOIN con una condición IS NULL.
        TypedQuery<Pais> queryL = em.createQuery(
                "SELECT p FROM Pais p " +
                        "LEFT JOIN Pelicula pe ON pe.pais = p " +
                        "WHERE pe.idPelicula IS NULL", Pais.class);

        List<Pais> apartadoL = queryL.getResultList();
        System.out.println("\n********* APARTADO L **********");
        for (Pais p: apartadoL) {
            System.out.println(p);
        }


        // Siguientes consultas (as que están en azul na aula virtual)

        // 1. Muestra la película solicitando el id:
        // Existe un id 123
        /*
        System.out.println(SEPARADOR + "CONSULTA 1 ***********");
        System.out.println("Introduce o ID da película: ");
        String idPeli = sc.nextLine();

        TypedQuery<Pelicula> query1 = em.createQuery(
                "SELECT p FROM Pelicula p " +
                        "WHERE p.idPelicula = :idPeli", Pelicula.class
        );
        query1.setParameter("idPeli", idPeli);
        List<Pelicula> resultadoQuery1 = query1.getResultList();
        System.out.println("\n\nRESULTADO: ");
        for (Pelicula p: resultadoQuery1){
            System.out.println(p);
        }
         */

        // 2. Muestra las películas que tienen algún personaje (IS EMPTY)
        // o no tienen personajes (IS NOT EMPTY).
        System.out.println(SEPARADOR + "CONSULTA 2 ***********");

        TypedQuery<Pelicula> query2 = em.createQuery("SELECT p FROM Pelicula p" +
                " WHERE p.personaxes IS EMPTY", Pelicula.class);
        List<Pelicula> resultadoQuery2 = query2.getResultList();
        System.out.println("\nPelículas sin personajes");
        for (Pelicula p: resultadoQuery2){
            System.out.println(p);
        }

        TypedQuery<Pelicula> query2B = em.createQuery("SELECT p FROM Pelicula p " +
                "WHERE p.personaxes IS NOT EMPTY", Pelicula.class);
        List<Pelicula> resultadoQuery2B = query2B.getResultList();
        System.out.println("\nPelículas con personajes");
        for (Pelicula p: resultadoQuery2B){
            System.out.println(p);
        }

        // 3. Muestra las películas que tienen personajes con una ocupación concreta:
        System.out.println(SEPARADOR + "CONSULTA 3 ***********");

        TypedQuery<Pelicula> query3 = em.createQuery(
                "SELECT p FROM Pelicula p " +
                        "JOIN p.personaxes pp " + // Hacemos el JOIN entre Pelicula y PeliculaPersonaxe (pp)
                        "JOIN pp.ocupacion o " + // Hacemos el JOIN entre PeliculaPersonaxe y Ocupacion
                        "WHERE o.ocupacion IS NOT NULL", Pelicula.class
        );

        // Outra opción
        TypedQuery<Pelicula> query3B = em.createQuery(
                "SELECT p FROM Pelicula p " +
                        "JOIN p.personaxes pp " + // Hacemos el JOIN entre Pelicula y PeliculaPersonaxe (pp)
                        "WHERE pp.ocupacion.ocupacion = 'Actor'", Pelicula.class
        );

        List<Pelicula> resultadoQuery3 = query3.getResultList();
        System.out.println("\n\nResultado consulta 3");
        for (Pelicula p: resultadoQuery3){
            System.out.println(p);
        }

        // 5. Muestra los títulos de las películas en las que ha trabajado un actor concreto.
        System.out.println(SEPARADOR + "CONSULTA 5 ***********");

        TypedQuery<Pelicula> query5 = em.createQuery(
                "SELECT p FROM Pelicula p " +
                        "JOIN p.personaxes pp " +
                        "JOIN pp.personaxe pe " +
                        "JOIN pp.ocupacion o " +
                        "WHERE o.ocupacion = 'Actor' " +
                        "AND pe.nome = 'Hanks, Tom'", Pelicula.class
        );

        List<Pelicula> resultadoQuery5 = query5.getResultList();
        System.out.println("\n\nResultado da Query5");
        for (Pelicula p: resultadoQuery5){
            System.out.println(p);
        }
    }
}
