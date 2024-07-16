package com.foroalura.foro.domain.temas;


import java.time.LocalDateTime;

public record DatosDetalleTema(
        Long id,
        Long idUsuario,
        String titulo,
        String mensaje,
        String nombreCurso,
        LocalDateTime fecha
){
    public DatosDetalleTema(Tema tema){
        this(tema.getId(), tema.getAutor().getId(), tema.getTitulo(), tema.getMensaje(), tema.getNombreCurso(), tema.getFecha());
    }
}
