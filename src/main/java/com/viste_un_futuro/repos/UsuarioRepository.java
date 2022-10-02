package com.viste_un_futuro.repos;

import com.viste_un_futuro.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
