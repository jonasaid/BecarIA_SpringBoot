package com.tt.becaria.controller.impl;

import com.tt.becaria.controller.InteraccionController;
import com.tt.becaria.model.Interaccion;
import com.tt.becaria.model.InsertionResult;
import com.tt.becaria.util.DbDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
public class InteraccionControllerImpl implements InteraccionController {
    private final DbDriver driver = DbDriver.getInstance();

    @Autowired
    public InteraccionControllerImpl() {
    }

    public boolean nuevaInteraccion(Interaccion interaccion) {
        Map<String, Object> dataInteraccion = new HashMap<>();
        dataInteraccion.put("id_consulta", interaccion.getId_consulta());
        dataInteraccion.put("pregunta", interaccion.getPregunta());
        dataInteraccion.put("respuesta", interaccion.getRespuesta());
        dataInteraccion.put("fecha_hora", LocalDateTime.now());  // Establece la fecha y hora actual

        InsertionResult result = driver.insertWithId("interaccion", dataInteraccion);  // Asumiendo que existe un método 'insertWithId' en DbDriver
        if (result.isSuccess()) {
            interaccion.setId(result.getMissingId());  // Asumiendo que queremos recuperar el ID generado por la inserción
        }
        return result.isSuccess();
    }

    public LinkedList<Interaccion> obtenerInteracciones(Map<String, Object> where, boolean isInteraccionLike) {
        LinkedList<Interaccion> interacciones = new LinkedList<>();
        LinkedList<Map<String, Object>> data;

        if (where == null) {
            data = driver.select("*", "interaccion");
        } else {
            data = driver.select("*", "interaccion", where, isInteraccionLike);
        }

        for (Map<String, Object> mapa : data) {
            Interaccion interaccion = new Interaccion();
            interaccion.setId(Integer.parseInt(mapa.get("id").toString()));
            interaccion.setId_consulta(Integer.parseInt(mapa.get("id_consulta").toString()));
            interaccion.setPregunta(mapa.get("pregunta").toString());
            interaccion.setRespuesta(mapa.get("respuesta").toString());
            interaccion.setFecha_hora((LocalDateTime) mapa.get("fecha_hora"));
            interacciones.add(interaccion);
        }

        return interacciones;
    }

    public boolean actualizaInteraccion(Interaccion interaccion) {
        Map<String, Object> dataInteraccion = new HashMap<>();
        dataInteraccion.put("id_consulta", interaccion.getId_consulta());
        dataInteraccion.put("pregunta", interaccion.getPregunta());
        dataInteraccion.put("respuesta", interaccion.getRespuesta());
        dataInteraccion.put("fecha_hora", interaccion.getFecha_hora());

        Map<String, Object> whereInteraccion = new HashMap<>();
        whereInteraccion.put("id", interaccion.getId());

        return driver.update("interaccion", dataInteraccion, whereInteraccion);
    }

    public boolean eliminaInteraccion(Interaccion interaccion) {
        Map<String, Object> whereInteraccion = new HashMap<>();
        whereInteraccion.put("id", interaccion.getId());
        return driver.delete("interaccion", whereInteraccion);
    }
}
