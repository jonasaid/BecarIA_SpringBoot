package com.tt.becaria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Anotación de Lombok para generar automáticamente getters, setters y toString.
@NoArgsConstructor //Esta anotación genera un constructor sin argumentos.
@AllArgsConstructor //Esta anotación genera un constructor que acepta todos los atributos de la clase como argumentos.
public class PeticionHF {

    private String pregunta;
    private String contexto;

}
