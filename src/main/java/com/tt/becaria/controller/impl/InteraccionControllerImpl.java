package com.tt.becaria.controller.impl;

import com.tt.becaria.controller.InteraccionController;
import com.tt.becaria.model.Contextos;
import com.tt.becaria.model.Interaccion;
import com.tt.becaria.model.InsertionResult;
import com.tt.becaria.model.PeticionHF;
import com.tt.becaria.service.impl.HuggingFaceService;
import com.tt.becaria.util.DbDriver;
import com.tt.becaria.util.ExtraerRespuestaJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
public class InteraccionControllerImpl implements InteraccionController {
    private final DbDriver driver = DbDriver.getInstance();
    private HuggingFaceController huggingFaceController = new HuggingFaceController();
    private HuggingFaceService huggingFaceService = new HuggingFaceService();
    @Autowired
    public InteraccionControllerImpl(HuggingFaceController huggingFaceController) {
        this.huggingFaceController = huggingFaceController;
    }

    public boolean nuevaInteraccion(Interaccion interaccion) {
        Map<String, Object> dataInteraccion = new HashMap<>();
        dataInteraccion.put("id_consulta", interaccion.getId_consulta());
        dataInteraccion.put("pregunta", interaccion.getPregunta());
        dataInteraccion.put("titulo_contexto", interaccion.getTitulo_contexto());
//        dataInteraccion.put("respuesta", interaccion.getRespuesta());

        Map<String, Object> where = new HashMap<>();
        where.put("titulo", interaccion.getTitulo_contexto());

        ContextosCotrollerImpl contextosCotroller = new ContextosCotrollerImpl();
        LinkedList<Contextos> contextos = contextosCotroller.obtenerContextos(where, false);

        if (contextos.isEmpty()) {
            return false; // Retorna false si el titulo del contexto es erroneo
        }
        Contextos contexto = contextos.getFirst();

        PeticionHF peticion = new PeticionHF(interaccion.getPregunta(), contexto.getContexto());
        String respuesta = ExtraerRespuestaJSON.extractAnswerFromResponse(huggingFaceController.sendQuery(peticion));

        dataInteraccion.put("respuesta", respuesta);
        dataInteraccion.put("fecha_hora", LocalDateTime.now().toString());

        InsertionResult result = driver.insertWithId("interaccion", dataInteraccion);
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
