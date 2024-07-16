package com.foroalura.foro.domain.temas;

import com.foroalura.foro.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "temas")
@Entity(name = "Temas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Tema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fecha;
    @Enumerated(EnumType.STRING)
    private Estado status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autores_id")
    private Usuario autores;

    private String nombreCurso;

    public Tema(
            String titulo,
            String mensaje,
            Estado status,
            Usuario autores,
            String nombreCurso
    ) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.status = status;
        this.autores = autores;
        this.nombreCurso = nombreCurso;
        this.fecha = LocalDateTime.now();

    }

    public void actualizarDatos(DatosActualizarTema datosActualizarTema) {
        if (datosActualizarTema.titulo() != null) {
            this.titulo = datosActualizarTema.titulo();
        }
        if (datosActualizarTema.mensaje() != null) {
            this.mensaje = datosActualizarTema.mensaje();
        }
        if (datosActualizarTema.nombreCurso() != null) {
            this.nombreCurso = datosActualizarTema.nombreCurso();
        }
    }

}

