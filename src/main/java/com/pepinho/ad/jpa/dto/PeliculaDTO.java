package com.pepinho.ad.jpa.dto;

// As clases DTO úsanse para devolver datos concretos en vez de a entidade enteira
public class PeliculaDTO {
    private Long idPelicula;
    private String castelan;
    private String orixinal;
    private short anoFin;
    private boolean tenPoster;

    public PeliculaDTO(Long idPelicula, String castelan, String orixinal, short anoFin, boolean tenPoster) {
        this.idPelicula = idPelicula;
        this.castelan = castelan;
        this.orixinal = orixinal;
        this.anoFin = anoFin;
        this.tenPoster = tenPoster;
    }

    // Getters y Setters
    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getCastelan() {
        return castelan;
    }

    public void setCastelan(String castelan) {
        this.castelan = castelan;
    }

    public String getOrixinal() {
        return orixinal;
    }

    public void setOrixinal(String orixinal) {
        this.orixinal = orixinal;
    }

    public short getAnoFin() {
        return anoFin;
    }

    public void setAnoFin(short anoFin) {
        this.anoFin = anoFin;
    }

    public boolean isTenPoster() {
        return tenPoster;
    }

    public void setTenPoster(boolean tenPoster) {
        this.tenPoster = tenPoster;
    }

    @Override
    public String toString() {
        return "PeliculaDTO{" +
                "idPelicula=" + idPelicula +
                ", castelan='" + castelan + '\'' +
                ", orixinal='" + orixinal + '\'' +
                ", anoFin=" + anoFin +
                ", tenPoster=" + tenPoster +
                '}';
    }
}
