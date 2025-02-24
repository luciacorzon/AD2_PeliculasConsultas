package com.pepinho.ad.jpa.dao;

import com.pepinho.ad.jpa.dto.PeliculaPaginaDTO;
import com.pepinho.ad.jpa.peliculas.Pelicula;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

public class PeliculaDAO {
    @PersistenceContext(unitName="seriesDB")
    private EntityManager em;

    public PeliculaDAO(EntityManager em) {
        this.em = em;
    }

    // Devuelve el número total de películas que coinciden con la búsqueda
    // USAR EXACTAMENTE O MISMO QUERY NO COUNT PARA EVITAR PROBLEMAS, pois se
    // se usa unha consulta diferente no count poden dar diferentes cantidades
    // de peliculas e ter problemas ca paxinación
    public long contarPeliculasPorNombre(String palabraClave) {
        return em.createQuery(
                        "SELECT COUNT(DISTINCT p) FROM Pelicula p " +
                                "JOIN PeliculaPersonaxe pp ON p.idPelicula = pp.pelicula.idPelicula " +
                                "WHERE LOWER(p.castelan) LIKE LOWER(:nombre)", Long.class)
                .setParameter("nombre", "%" + palabraClave.toLowerCase() + "%")
                .getSingleResult();
    }


    // Devuelve una lista de películas de una página específica, ordenadas por año descendente
    public List<PeliculaPaginaDTO> obtenerPeliculasPorPagina(String palabraClave, int pagina, int tamañoPagina) {
        return em.createQuery(
                        "SELECT DISTINCT NEW com.pepinho.ad.jpa.dto.PeliculaPaginaDTO(p.idPelicula, p.castelan, p.orixinal, p.anoFin) " +
                                "FROM Pelicula p " +
                                "JOIN PeliculaPersonaxe pp ON p.idPelicula = pp.pelicula.idPelicula " +
                              //  "JOIN Director d ON pp.personaxe.idPersonaxe = d.idPersonaxe " +
                                "WHERE LOWER(p.castelan) LIKE LOWER(:nombre) " +
                                "ORDER BY p.anoFin DESC",
                        PeliculaPaginaDTO.class)
                .setParameter("nombre", "%" + palabraClave.toLowerCase() + "%")
                .setFirstResult(pagina * tamañoPagina)
                .setMaxResults(tamañoPagina)
                .getResultList();
    }
}
