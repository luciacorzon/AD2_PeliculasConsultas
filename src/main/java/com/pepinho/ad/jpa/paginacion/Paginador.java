package com.pepinho.ad.jpa.paginacion;
import com.pepinho.ad.jpa.dao.PeliculaDAO;
import com.pepinho.ad.jpa.dto.PeliculaPaginaDTO;
import com.pepinho.ad.jpa.peliculas.Pelicula;
import jakarta.ejb.Stateful;
import jakarta.ejb.Remove;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

// Con Stateful indicamos que se gardan os datos entre chamadas para
// cada cliente, en este caso, a páxina actual, o número de resultados por páxina
// e a consulta que se está facendo
@Stateful
public class Paginador {
    // Inyección do EntityManager para poder interactuar ca BD
    @PersistenceContext(unitName="seriesDB")
    private EntityManager em;
    private String palabraClave;
    private String nombreConsultaInforme;
    private long paginaActual;
    // O total de resultados da consulta
    private long totalResultados;
    private long tamañoPagina;

    private PeliculaDAO peliculaDAO;

    // Devolve o tamaño da páxina
    public long getTamañoPagina() {
        return tamañoPagina;
    }

    // Devolve o número de páxinas que vai haber
    // Se hai 0 resultados porque non hai peliculas, o total de páxinas é 0
    // Se hai menos películas en total que por páxina (por exemplo, solo 5 películas e tamaño de
    // páxina de 10 pelis por páxina, redondéase hacia arriba con Math.ceil 5pelis/10pelisporpaxina == 1 páxina
    // Math.ceil sempre redondea hacia o siguiente enteiro, é decir, hacia arriba, solo necesita convertir un dos
    // números a double
    // Ao dividir entre enteiros ou long trúncase hacia abaixo, polo que se pode dar o caso de estar na "páxina 1 de 0"
    public long getTotalPaginas() {
        return totalResultados == 0 ? 1 : (long) Math.ceil((double) totalResultados / tamañoPagina);
    }


    // Nos EJB Beans non funcionan os constructores, pois crean o obxecto automaticamente
    // e si se lle añaden parámetros ao constructor, non funciona.
    // Por eso se inicializan os atributos nun método init() que hai que
    // executar manualmente no main
    public void init(long tamañoPagina, String palabraClave, EntityManager em) {
        this.tamañoPagina = tamañoPagina;
        this.palabraClave = palabraClave;
        this.peliculaDAO = new PeliculaDAO(em); // Se debería inyectar en un entorno EJB
        this.totalResultados = peliculaDAO.contarPeliculasPorNombre(palabraClave);
        this.paginaActual = 0;
    }

    // Resultados da páxina actual
    public List<PeliculaPaginaDTO> getResultadosActuales() {
        return peliculaDAO.obtenerPeliculasPorPagina(palabraClave, (int) paginaActual, (int) tamañoPagina);
    }

    // Pasar á siguiente páxina
    public void next() {
        paginaActual++;
    }

    // Retroceder á páxina anterior
    public void previous() {
        paginaActual--;
        if (paginaActual < 0) {
            paginaActual = 0;
        }
    }

    // Devolve o número da páxina actual
    public long getPaginaActual() {
        return paginaActual;
    }

    // Permite establecer manualmente a páxina actual
    public void setPaginaActual(long paginaActual) {
        this.paginaActual = paginaActual;
    }

    // Método de limpieza de estado
    // Indica que a instancia do bean pode ser borrada cando non se necesite
    @Remove
    public void finished() {}

    public PeliculaDAO getPeliculaDAO() {
        return peliculaDAO;
    }

    public void setPeliculaDAO(PeliculaDAO peliculaDAO) {
        this.peliculaDAO = peliculaDAO;
    }
}
