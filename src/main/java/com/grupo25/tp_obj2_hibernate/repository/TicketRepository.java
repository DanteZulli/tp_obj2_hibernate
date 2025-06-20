package com.grupo25.tp_obj2_hibernate.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>, JpaSpecificationExecutor<Ticket> {

    List<Ticket> findByCreador(Usuario creador);

    List<Ticket> findByAsignado(Usuario asignado);

    List<Ticket> findByEstado(String estado);

    String findEstadoById(int id);

    long countByEstado(String estado);

    long countByEstadoAndCreadorId(String estado, int creadorId);

    long countByPrioridad(String prioridad);

    long countByPrioridadAndCreadorId(String prioridad, int creadorId);

    /**
     * Busca tickets utilizando una combinación de filtros opcionales.
     *
     * @param fechaDesde La fecha de inicio del rango de creación.
     * @param fechaHasta La fecha de fin del rango de creación.
     * @param categoria  La categoría del ticket.
     * @param estado     El estado del ticket.
     * @param tecnicos   Lista de técnicos asignados para buscar.
     * @param sinAsignar Si es true, busca tickets sin asignar. Si es false, busca
     *                   tickets asignados. Se ignora si se provee la lista de
     *                   técnicos.
     * @param prioridad  La prioridad del ticket.
     * @return Una lista de tickets que coinciden con los criterios de búsqueda.
     */
    default List<Ticket> findWithFilters(LocalDateTime fechaDesde, LocalDateTime fechaHasta, Categoria categoria,
            String estado, List<Usuario> tecnicos, Boolean sinAsignar, String prioridad) {
        return findAll((Specification<Ticket>) (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (fechaDesde != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fechaCreacion"), fechaDesde));
            }
            if (fechaHasta != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fechaCreacion"), fechaHasta));
            }
            if (categoria != null) {
                predicates.add(cb.equal(root.get("categoria"), categoria));
            }
            if (estado != null && !estado.isBlank()) {
                predicates.add(cb.equal(root.get("estado"), estado));
            }
            if (prioridad != null && !prioridad.isBlank()) {
                predicates.add(cb.equal(root.get("prioridad"), prioridad));
            }
            if (tecnicos != null && !tecnicos.isEmpty()) {
                predicates.add(root.get("asignado").in(tecnicos));
            } else if (sinAsignar != null) {
                if (sinAsignar) {
                    predicates.add(cb.isNull(root.get("asignado")));
                } else {
                    predicates.add(cb.isNotNull(root.get("asignado")));
                }
            }

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        });
    }
}
