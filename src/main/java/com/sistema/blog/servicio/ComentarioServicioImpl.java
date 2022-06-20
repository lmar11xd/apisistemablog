package com.sistema.blog.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.entidades.Comentario;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.excepciones.BlogAppException;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repositorio.ComentarioRepositorio;
import com.sistema.blog.repositorio.PublicacionRepositorio;

@Service
public class ComentarioServicioImpl implements ComentarioServicio {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Override
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDto) {
        Comentario comentario = mapearEntidad(comentarioDto);
        Publicacion publicacion = publicacionRepositorio
            .findById(publicacionId).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        
        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario = comentarioRepositorio.save(comentario);
        return mapearDTO(nuevoComentario);
    }
    
    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId) {
        List<Comentario> comentarios = comentarioRepositorio.findByPublicacionId(publicacionId);
        return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepositorio
            .findById(publicacionId).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        
        Comentario comentario = comentarioRepositorio
            .findById(comentarioId).orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));
        
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comentario no pertenece a la publicación");
        }
        
        return mapearDTO(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO comentarioDTO) {
        Publicacion publicacion = publicacionRepositorio
            .findById(publicacionId).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        
        Comentario comentario = comentarioRepositorio
            .findById(comentarioId).orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));
        
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comentario no pertenece a la publicación");
        }

        comentario.setNombre(comentarioDTO.getNombre());
        comentario.setEmail(comentarioDTO.getEmail());
        comentario.setCuerpo(comentarioDTO.getCuerpo());

        Comentario comentarioActualizado = comentarioRepositorio.save(comentario);
        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepositorio
            .findById(publicacionId).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        
        Comentario comentario = comentarioRepositorio
            .findById(comentarioId).orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));
        
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comentario no pertenece a la publicación");
        }
        
        comentarioRepositorio.delete(comentario);
    }

    private ComentarioDTO mapearDTO(Comentario comentario){
        return modelMapper.map(comentario, ComentarioDTO.class);
    }

    private Comentario mapearEntidad(ComentarioDTO comentarioDto){
        return modelMapper.map(comentarioDto, Comentario.class);
    }

}
