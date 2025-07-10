package com.grupo25.tp_obj2_hibernate.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RevisionDTO (

       Integer id,
	    
	    
	    @Size(min = 2, max = 500, message = "El mensaje debe tener entre 2 y 50 caracteres")
	    String username,
	    
	     @NotBlank(message = "Observaciones no puede estar vacío")
	    @Size(min = 2, max = 500, message = "El mensaje debe tener entre 2 y 50 caracteres")
	    String observaciones

){}
