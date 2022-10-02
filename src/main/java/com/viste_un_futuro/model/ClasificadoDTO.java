package com.viste_un_futuro.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClasificadoDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String titulo;

    @NotNull
    @Size(max = 250)
    private String descripcion;

    @NotNull
    @Size(max = 10)
    private String tipoPrenda;

    @NotNull
    @Size(max = 10)
    private String generoPrenda;

    @NotNull
    @Size(max = 3)
    private String tallaPrenda;

    @NotNull
    @Size(max = 10)
    private String estadoPrenda;

    @NotNull
    private Integer cantidadPrenda;

    @NotNull
    @Size(max = 100)
    private String fotoPrenda;

    @NotNull
    private Long usuario;

}
