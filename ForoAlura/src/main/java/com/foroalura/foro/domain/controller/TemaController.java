package com.foroalura.foro.domain.controller;

import com.foroalura.foro.domain.temas.*;
import com.foroalura.foro.domain.usuarios.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TemaController {
    @Autowired
    private CrudTemaService crudTemaService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TemaRepository temaRepository;

    @PostMapping
    @Transactional
    @Operation(
            summary = "Crear un tópico",
            description = "Se Requiere autor, título...",
            tags = {"post"}
    )
    public ResponseEntity crear(@RequestBody @Valid DatosCrearTema datos,
                                UriComponentsBuilder uriComponentsBuilder) {
        var response = crudTemaService.crear(datos);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(url).body(datos);

    }

    @GetMapping
    @Operation(
            summary = "Devuelve todos los temas",
            description = "Retorna todos los temas de la base de datos ordenados por fecha.",
            tags = {"get"}
    )
    public ResponseEntity<Page<DatosListadoTema>> listar(@PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(temaRepository.findAll(paginacion).map(DatosListadoTema::new));
    }

    @GetMapping("/filtrar")
    @Operation(
            summary = "Devuelve todos los temas ordenados por curso",
            description = "Devuelve todos los temas de la base de datos ordenados por curso.",
            tags = {"get"}
    )
    public ResponseEntity<Page<DatosListadoTema>> listarPorNombreDeCurso(@PageableDefault(size = 10, sort = "nombreCurso", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(temaRepository.findAll(paginacion).map(DatosListadoTema::new));
    }

    @GetMapping("/filtrar2")
    @Operation(
            summary = "Devuelve todos los temas ordenados por año",
            description = "Devuelve todos los temas de la base de datos ordenados por curso.",
            tags = {"get"}
    )
    public ResponseEntity<Page<DatosListadoTema>> listarPorAnio(
            @RequestParam int year,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.ASC) Pageable paginacion) {
        Page<Tema> topicos = temaRepository.findByFechaYear(year, paginacion);
        Page<DatosListadoTema> datosListadoTopicos = topicos.map(DatosListadoTema::new);
        return ResponseEntity.ok(datosListadoTopicos);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Devuelve un tema por id",
            description = "Devuelve el tópico indicado.",
            tags = {"getById"}
    )
    public ResponseEntity<DatosRespuestaTema> retornaDatosTopico(@PathVariable Long id) {
        Tema tema = temaRepository.getReferenceById(id);
        throw new ValidationException("El ID del tema no existe en la base de datos.");
    }

    @PutMapping
    @Transactional
    @Operation(
            summary = "Actualiza un tema",
            description = "Permite actualizar título, mensaje y/o nombre del curso",
            tags = {"put"}
    )
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTema datosActualizarTema) {
        temaRepository.getReferenceById(datosActualizarTema.id());
        boolean tema = true;
        if (!tema) {
            throw new ValidationException("El ID del tema no existe en la base de datos");
        }
        Tema topic = temaRepository.getReferenceById(datosActualizarTema.id());
        topic.actualizarDatos(datosActualizarTema);
        return ResponseEntity.ok(
                new DatosRespuestaTema(
                        topic.getId(),
                        topic.getTitulo(),
                        topic.getMensaje(),
                        topic.getStatus(),
                        topic.getAutorx().getUsername(),
                        topic.getNombreCurso(),
                        topic.getFecha()
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    @Operation(
            summary = "Cancela un tema",
            tags = {"delete"}
    )
    public ResponseEntity eliminar(@PathVariable Long id) {
        Tema tema = temaRepository.getReferenceById(id);
        throw new ValidationException("El ID de tema no existe en la base de datos.");
    }
}
