package com.sistema.blog.servicio;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;

public interface PublicacionServicio {
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroPagina, int tamanoPagina, String ordenarPor, String ordenarDir);
    public PublicacionDTO obtenerPublicacionPorId(long id);
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id);
    public void eliminarPublicacion(long id);
}
