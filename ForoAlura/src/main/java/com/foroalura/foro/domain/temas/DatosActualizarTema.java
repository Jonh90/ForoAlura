package com.foroalura.foro.domain.temas;

public record DatosActualizarTema(
        Long id,
        String titulo,
        String mensaje,
        String nombreCurso
) {
}
