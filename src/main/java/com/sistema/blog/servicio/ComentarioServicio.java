package com.sistema.blog.servicio;

import java.util.List;

import com.sistema.blog.dto.ComentarioDTO;

public interface ComentarioServicio {
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO);
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId);
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId);
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO comentarioDTO);
    public void eliminarComentario(Long publicacionId, Long comentarioId);
}
