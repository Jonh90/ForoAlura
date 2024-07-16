package com.foroalura.foro.domain.temas;

import java.time.LocalDateTime;

public record DatosListadoTema(
        Long id,
        String titulo,
        String mensaje,
        Estado status,
        String autorx,
        String nombreCurso,
        LocalDateTime fecha){
    public DatosListadoTema(Tema tema){
        this(tema.getId(), tema.getTitulo(), tema.getMensaje(), tema.getStatus(), tema.getAutorx().getUsername(), tema.getNombreCurso(), tema.getFecha());
    }
}
