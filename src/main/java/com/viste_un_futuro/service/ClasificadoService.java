package com.viste_un_futuro.service;

import com.viste_un_futuro.domain.Clasificado;
import com.viste_un_futuro.domain.Usuario;
import com.viste_un_futuro.model.ClasificadoDTO;
import com.viste_un_futuro.repos.ClasificadoRepository;
import com.viste_un_futuro.repos.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ClasificadoService {

    private final ClasificadoRepository clasificadoRepository;
    private final UsuarioRepository usuarioRepository;

    public ClasificadoService(final ClasificadoRepository clasificadoRepository,
            final UsuarioRepository usuarioRepository) {
        this.clasificadoRepository = clasificadoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ClasificadoDTO> findAll() {
        return clasificadoRepository.findAll(Sort.by("id"))
                .stream()
                .map(clasificado -> mapToDTO(clasificado, new ClasificadoDTO()))
                .collect(Collectors.toList());
    }

    public ClasificadoDTO get(final Long id) {
        return clasificadoRepository.findById(id)
                .map(clasificado -> mapToDTO(clasificado, new ClasificadoDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final ClasificadoDTO clasificadoDTO) {
        final Clasificado clasificado = new Clasificado();
        mapToEntity(clasificadoDTO, clasificado);
        return clasificadoRepository.save(clasificado).getId();
    }

    public void update(final Long id, final ClasificadoDTO clasificadoDTO) {
        final Clasificado clasificado = clasificadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(clasificadoDTO, clasificado);
        clasificadoRepository.save(clasificado);
    }

    public void delete(final Long id) {
        clasificadoRepository.deleteById(id);
    }

    private ClasificadoDTO mapToDTO(final Clasificado clasificado,
            final ClasificadoDTO clasificadoDTO) {
        clasificadoDTO.setId(clasificado.getId());
        clasificadoDTO.setTitulo(clasificado.getTitulo());
        clasificadoDTO.setDescripcion(clasificado.getDescripcion());
        clasificadoDTO.setTipoPrenda(clasificado.getTipoPrenda());
        clasificadoDTO.setGeneroPrenda(clasificado.getGeneroPrenda());
        clasificadoDTO.setTallaPrenda(clasificado.getTallaPrenda());
        clasificadoDTO.setEstadoPrenda(clasificado.getEstadoPrenda());
        clasificadoDTO.setCantidadPrenda(clasificado.getCantidadPrenda());
        clasificadoDTO.setFotoPrenda(clasificado.getFotoPrenda());
        clasificadoDTO.setUsuario(clasificado.getUsuario() == null ? null : clasificado.getUsuario().getId());
        return clasificadoDTO;
    }

    private Clasificado mapToEntity(final ClasificadoDTO clasificadoDTO,
            final Clasificado clasificado) {
        clasificado.setTitulo(clasificadoDTO.getTitulo());
        clasificado.setDescripcion(clasificadoDTO.getDescripcion());
        clasificado.setTipoPrenda(clasificadoDTO.getTipoPrenda());
        clasificado.setGeneroPrenda(clasificadoDTO.getGeneroPrenda());
        clasificado.setTallaPrenda(clasificadoDTO.getTallaPrenda());
        clasificado.setEstadoPrenda(clasificadoDTO.getEstadoPrenda());
        clasificado.setCantidadPrenda(clasificadoDTO.getCantidadPrenda());
        clasificado.setFotoPrenda(clasificadoDTO.getFotoPrenda());
        final Usuario usuario = clasificadoDTO.getUsuario() == null ? null : usuarioRepository.findById(clasificadoDTO.getUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "usuario not found"));
        clasificado.setUsuario(usuario);
        return clasificado;
    }

}
