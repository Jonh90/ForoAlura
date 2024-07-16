package com.foroalura.foro.domain.temas.validaciones;

import com.foroalura.foro.domain.temas.DatosCrearTema;
import com.foroalura.foro.domain.temas.TemaRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrosDuplicados implements ValidadorDeTopicos {
    @Autowired
    private TemaRepository temaRepository;

    public void validar(DatosCrearTema datos) {
        var titulo = datos.titulo();
        var mensaje = datos.mensaje();

        var registroDuplicado = temaRepository.existsByTituloAndMensaje(titulo, mensaje);

        if (registroDuplicado) {
            throw new ValidationException("Mensaje duplicado en este título");
        }
    }
}
