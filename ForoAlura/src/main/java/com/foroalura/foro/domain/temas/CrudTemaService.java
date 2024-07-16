package com.foroalura.foro.domain.temas;

import com.foroalura.foro.domain.temas.validaciones.ValidadorDeTopicos;
import com.foroalura.foro.domain.usuarios.UsuarioRepository;
import com.foroalura.foro.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrudTemaService {
    @Autowired
    private TemaRepository temaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    List<ValidadorDeTopicos> validadores;

    public DatosDetalleTema crear(DatosCrearTema datos) {
        if (datos.idUsuario() != null && !usuarioRepository.existsById(datos.idUsuario())) {
            throw new ValidacionDeIntegridad("Id de usuario no encontrado");
        }
        validadores.forEach(v -> v.validar(datos));
        var usuario = usuarioRepository.findById(datos.idUsuario()).get();
        var tema = new Tema(
                datos.titulo(),
                datos.mensaje(),
                datos.status(),
                usuario,
                datos.nombreCurso()
        );
        temaRepository.save(tema);
        return new DatosDetalleTema(tema);
    }
}
