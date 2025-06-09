package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo25.tp_obj2_hibernate.model.entities.Etiqueta;
import com.grupo25.tp_obj2_hibernate.service.EtiquetaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/etiquetas")
public class EtiquetaRestController {

    private EtiquetaService etiquetaService;

    public EtiquetaRestController(EtiquetaService etiquetaService) {
        this.etiquetaService = etiquetaService;
    }
    
    /**
     * Crear etiqueta
     * 
     * @param etiquetaDTO
     * @return ResponseEntity con la etiqueta creada, o un error si no existe
     * 
     * @author Ariel Serato
     */	
    @PostMapping("/crear")
    public ResponseEntity<Etiqueta> crearEtiqueta(@RequestBody Etiqueta etiquetaDTO) {
        try {
            Etiqueta etiqueta = etiquetaService.crearEtiqueta(etiquetaDTO);
            return ResponseEntity.ok(etiqueta);
        } catch (RuntimeException e) {
            log.error("Error al crear la etiqueta: {}", etiquetaDTO, e);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Etiqueta> actualizarEtiqueta(@RequestBody Etiqueta etiquetaDTO) {
        try {
            Etiqueta etiqueta = etiquetaService.actualizarEtiqueta(etiquetaDTO);
            return ResponseEntity.ok(etiqueta);
        } catch (RuntimeException e) {
            log.error("Error al actualizar la etiqueta: {}", etiquetaDTO, e);
            return ResponseEntity.notFound().build();
        }
    }
    
    
}