package com.sistema.blog.dto;

import java.util.List;

public class PublicacionRespuesta {
    private List<PublicacionDTO> contenido;
    private int numeroPagina;
    private int tamanoPagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;
    
    public List<PublicacionDTO> getContenido() {
        return contenido;
    }
    public void setContenido(List<PublicacionDTO> contenido) {
        this.contenido = contenido;
    }
    public int getNumeroPagina() {
        return numeroPagina;
    }
    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }
    public int getTamanoPagina() {
        return tamanoPagina;
    }
    public void setTamanoPagina(int tamanoPagina) {
        this.tamanoPagina = tamanoPagina;
    }
    public long getTotalElementos() {
        return totalElementos;
    }
    public void setTotalElementos(long totalElementos) {
        this.totalElementos = totalElementos;
    }
    public int getTotalPaginas() {
        return totalPaginas;
    }
    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }
    public boolean getUltima() {
        return ultima;
    }
    public void setUltima(boolean ultima) {
        this.ultima = ultima;
    }

    public PublicacionRespuesta() {
    }

}
