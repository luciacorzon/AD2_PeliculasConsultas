package com.pepinho.ad.jpa.dto;

public class PeliculaPaginaDTO {
    //Se debe mostrar el idPelicula, castelan, orixinal,
    // anoFin y el director (relacionado con PeliculaPersonaxe).
    private Long idPelicula;
    private String castelan;
    private String orixinal;
    private short anoFin;
    private String director; // Ocupacion.ocupacion

    public PeliculaPaginaDTO(Long idPelicula, String castelan, String orixinal, short anoFin, String director) {
        this.idPelicula = idPelicula;
        this.castelan = castelan;
        this.orixinal = orixinal;
        this.anoFin = anoFin;
        this.director = director;
    }

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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "PeliculaPaginaDTO{" +
                "idPelicula=" + idPelicula +
                ", castelan='" + castelan + '\'' +
                ", orixinal='" + orixinal + '\'' +
                ", anoFin=" + anoFin +
                ", director='" + director + '\'' +
                '}';
    }
}
