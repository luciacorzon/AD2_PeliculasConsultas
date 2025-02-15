package com.pepinho.ad.jpa;

import com.pepinho.ad.jpa.peliculas.Ocupacion;
import com.pepinho.ad.jpa.peliculas.Pais;
import com.pepinho.ad.jpa.peliculas.Pelicula;
import com.pepinho.ad.jpa.peliculas.Xenero;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = JPAUtil.getEmFactory(JPAUtil.UNIDAD_PERSISTENCIA);
        EntityManager em = emf.createEntityManager();


        // a) Obtener todas las películas que tienen una duración mayor a 120 minutos.
        TypedQuery<Pelicula> queryA = em.createQuery("SELECT p FROM Pelicula p WHERE p.duracion > 120", Pelicula.class);
        List<Pelicula> apartadoA = queryA.getResultList();
        System.out.println("APARTADO A");
        for (Pelicula p: apartadoA) {
            System.out.println(p);
        }

        // b) Obtener todas las películas que pertenecen a un género específico (por ejemplo, “Drama”).
        TypedQuery<Pelicula> queryB = em.createQuery(
                "SELECT p FROM Pelicula p WHERE p.xenero = :xenero", Pelicula.class);
        queryB.setParameter("xenero", Xenero.of("Drama"));

        List<Pelicula> apartadoB = queryB.getResultList();

        System.out.println("APARTADO B");
        for (Pelicula pelicula : apartadoB) {
            System.out.println(pelicula);
        }


        // c) Obtener todas las ocupaciones que tienen más de 5 películas asociadas.
        TypedQuery<Ocupacion> queryC = em.createQuery(
                "SELECT o FROM Ocupacion o WHERE SIZE(o.peliculaPersonaxes) > 5", Ocupacion.class);

        List<Ocupacion> apartadoC = queryC.getResultList();

        System.out.println("APARTADO C");
        for (Ocupacion ocupacion : apartadoC) {
            System.out.println(ocupacion);
        }

        // d) Obtener todas las películas que tienen un país específico (por ejemplo, “España”).
        TypedQuery<Pelicula> queryD = em.createQuery(
                "SELECT p FROM Pelicula p WHERE p.pais.pais = :nombrePais", Pelicula.class);
        queryD.setParameter("nombrePais", "España");

        List<Pelicula> apartadoD = queryD.getResultList();

        System.out.println("APARTADO D");
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

        System.out.println("APARTADO E");
        for (Pelicula pelicula : apartadoE) {
            System.out.println(pelicula);
        }

        // f) Obtener todas las películas que tienen música compuesta por un compositor específico
        // (por ejemplo, “John Williams”).
        TypedQuery<Pelicula> queryF = em.createQuery(
                "SELECT p FROM Pelicula p WHERE p.musica = :nombreCompositor", Pelicula.class);

        queryF.setParameter("nombreCompositor", "John Williams");

        List<Pelicula> apartadoF = queryF.getResultList();

        System.out.println("APARTADO F");
        for (Pelicula pelicula : apartadoF) {
            System.out.println(pelicula);
        }

        // g) Obtener todas las películas que tienen un personaje interpretado por un actor
        // con un nombre específico (por ejemplo, “Tom Hanks”).
        TypedQuery<Pelicula> queryG = em.createQuery(
                "SELECT DISTINCT p FROM Pelicula p " +
                        "JOIN p.personaxes pp " +
                        "JOIN pp.personaxe per " +
                        "WHERE per.nome = :nombreActor", Pelicula.class);

        queryG.setParameter("nombreActor", "Tom Hanks");

        List<Pelicula> apartadoG = queryG.getResultList();

        System.out.println("APARTADO G");
        for (Pelicula pelicula : apartadoG) {
            System.out.println(pelicula);
        }


        // h) Obtener todas las películas que tienen un género específico y
        // que fueron producidas en un año específico (por ejemplo, “Acción” y 2005).
        TypedQuery<Pelicula> queryH = em.createQuery(
                "SELECT p FROM Pelicula p " +
                        "WHERE p.xenero = :genero " +
                        "AND p.anoFin = :anio", Pelicula.class);

        queryH.setParameter("genero", Xenero.of("Acción"));
        queryH.setParameter("anio", (short) 2005);
        System.out.println("APARTADO H");
        List<Pelicula> apartadoH = queryH.getResultList();
        for (Pelicula p: apartadoH) {
            System.out.println(p);
        }

        // i) Obtener todas las películas que tienen un personaje interpretado
        // por un actor de un género específico (por ejemplo, “Mujer”).
        TypedQuery<Pelicula> queryI = em.createQuery(
                "SELECT p FROM Pelicula p " +
                        "JOIN PeliculaPersonaxe pp " +
                        "JOIN Personaxe pe WHERE pe.sexo = 'Mujer'", Pelicula.class);
        System.out.println("APARTADO I");
        List<Pelicula> apartadoI = queryI.getResultList();
        for (Pelicula p: apartadoI) {
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

        System.out.println("APARTADO K");
        List<Pelicula> apartadoK = queryK.getResultList();
        for (Pelicula p: apartadoK) {
            System.out.println(p);
        }


        // l) Devolver todos los países que no tienen películas asociadas,
        // puedes usar una consulta JPQL que utilice una subconsulta o
        // un LEFT JOIN con una condición IS NULL.
        TypedQuery<Pais> queryL = em.createQuery(
                "SELECT p FROM Pais p " +
                        "LEFT JOIN Pelicula pel ON pel.pais = p " +
                        "WHERE pel.idPelicula IS NULL", Pais.class); 

        System.out.println("APARTADO L");
        List<Pais> apartadoL = queryL.getResultList();
        for (Pais p: apartadoL) {
            System.out.println(p);
        }

    }
}
