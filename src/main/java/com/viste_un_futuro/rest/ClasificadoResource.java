package com.viste_un_futuro.rest;

import com.viste_un_futuro.model.ClasificadoDTO;
import com.viste_un_futuro.service.ClasificadoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/clasificados", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClasificadoResource {

    private final ClasificadoService clasificadoService;

    public ClasificadoResource(final ClasificadoService clasificadoService) {
        this.clasificadoService = clasificadoService;
    }

    @GetMapping
    public ResponseEntity<List<ClasificadoDTO>> getAllClasificados() {
        return ResponseEntity.ok(clasificadoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasificadoDTO> getClasificado(@PathVariable final Long id) {
        return ResponseEntity.ok(clasificadoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createClasificado(
            @RequestBody @Valid final ClasificadoDTO clasificadoDTO) {
        return new ResponseEntity<>(clasificadoService.create(clasificadoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClasificado(@PathVariable final Long id,
            @RequestBody @Valid final ClasificadoDTO clasificadoDTO) {
        clasificadoService.update(id, clasificadoDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteClasificado(@PathVariable final Long id) {
        clasificadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
