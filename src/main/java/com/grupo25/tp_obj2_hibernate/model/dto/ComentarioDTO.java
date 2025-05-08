package com.grupo25.tp_obj2_hibernate.model.dto;
import java.time.LocalDateTime;
import com.grupo25.tp_obj2_hibernate.model.entities.Comentario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {
	
	 	private int id;
	    private String mensaje;
	    private LocalDateTime fecha;
	    // TODO: Agregar Categoria, Etiquetas y Comentarios al mappeo
	    // como está hecho en TecnicoDTO

	    public ComentarioDTO(Comentario comentario) {
	        this.id = comentario.getId();
	        this.mensaje = comentario.getMensaje();
	        this.fecha = comentario.getFecha();
	    }

}
