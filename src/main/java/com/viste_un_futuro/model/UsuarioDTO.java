package com.viste_un_futuro.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String nombres;

    @NotNull
    @Size(max = 50)
    private String apellidos;

    @NotNull
    @Size(max = 50)
    @JsonProperty("eMail")
    private String eMail;

    @NotNull
    private Long telefono;

    @NotNull
    @Size(max = 50)
    private String direccion;

    @NotNull
    @Size(max = 50)
    private String ciudad;

    @NotNull
    @Size(max = 50)
    private String contrasena;

    @NotNull
    @Size(max = 50)
    private String preguntaSeguridad;

    @NotNull
    @Size(max = 50)
    private String respuestaPregunta;

}
