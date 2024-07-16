package com.foroalura.foro.domain.temas;

import java.time.LocalDateTime;

public record DatosRespuestaTema(
        Long id,
        String titulo,
        String mensaje,
        Estado status,
        String autorx,
        String nombreCurso,
        LocalDateTime fecha
) {
}
