package com.viste_un_futuro.service;

import com.viste_un_futuro.domain.Usuario;
import com.viste_un_futuro.model.UsuarioDTO;
import com.viste_un_futuro.repos.UsuarioRepository;
import com.viste_un_futuro.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll(Sort.by("id"))
                .stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .collect(Collectors.toList());
    }

    public UsuarioDTO get(final Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getId();
    }

    public void update(final Long id, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombres(usuario.getNombres());
        usuarioDTO.setApellidos(usuario.getApellidos());
        usuarioDTO.setEMail(usuario.getEMail());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setDireccion(usuario.getDireccion());
        usuarioDTO.setCiudad(usuario.getCiudad());
        usuarioDTO.setContrasena(usuario.getContrasena());
        usuarioDTO.setPreguntaSeguridad(usuario.getPreguntaSeguridad());
        usuarioDTO.setRespuestaPregunta(usuario.getRespuestaPregunta());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombres(usuarioDTO.getNombres());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setEMail(usuarioDTO.getEMail());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccion(usuarioDTO.getDireccion());
        usuario.setCiudad(usuarioDTO.getCiudad());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setPreguntaSeguridad(usuarioDTO.getPreguntaSeguridad());
        usuario.setRespuestaPregunta(usuarioDTO.getRespuestaPregunta());
        return usuario;
    }

    @Transactional
    public String getReferencedWarning(final Long id) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!usuario.getUsuarioClasificados().isEmpty()) {
            return WebUtils.getMessage("usuario.clasificado.oneToMany.referenced", usuario.getUsuarioClasificados().iterator().next().getId());
        }
        return null;
    }

}
