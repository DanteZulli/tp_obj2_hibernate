package com.grupo25.tp_obj2_hibernate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

}