package com.pepinho.ad.jpa.paginacion;
import jakarta.ejb.Stateful;
import jakarta.ejb.Remove;
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
    private String nombreConsultaInforme;
    private long paginaActual;
    // O total de resultados da consulta
    private long totalResultados;
    private long tamañoPagina;

    // Devolve o tamaño da páxina
    public long getTamañoPagina() {
        return tamañoPagina;
    }

    // Devolve o número de páxinas que vai haber
    public long getTotalPaginas() {
        return totalResultados / tamañoPagina;
    }

    // Nos EJB Beans non funcionan os constructores, pois crean o obxecto automaticamente
    // e si se lle añaden parámetros ao constructor, non funciona.
    // Por eso se inicializan os atributos nun método init() que hai que
    // executar manualmente no main
    public void init(long tamañoPagina, String nombreConsultaRecuento, String nombreConsultaInforme) {
        this.tamañoPagina = tamañoPagina;
        this.nombreConsultaInforme = nombreConsultaInforme;
        totalResultados = em.createNamedQuery(nombreConsultaRecuento, Long.class).getSingleResult();
        paginaActual = 0;
    }

    // Resultados da páxina actual
    public List getResultadosActuales() {
        return em.createNamedQuery(nombreConsultaInforme)
                .setFirstResult((int) (paginaActual * tamañoPagina))
                .setMaxResults((int) tamañoPagina)
                .getResultList();
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
}
