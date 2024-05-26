package com.tt.becaria.service.impl;

import com.tt.becaria.controller.impl.InteraccionControllerImpl;
import com.tt.becaria.model.Interaccion;
import com.tt.becaria.model.ResponseData;
import com.tt.becaria.service.InteraccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class InteraccionServiceImpl implements InteraccionService {
    private final InteraccionControllerImpl controller;

    @Autowired
    public InteraccionServiceImpl(InteraccionControllerImpl controller) {
        this.controller = controller;
    }

    @GetMapping("/interaccion")
    public ResponseData getAllInteracciones() {
        LinkedList<Interaccion> interacciones = controller.obtenerInteracciones(null, false);
        if (interacciones.isEmpty()) {
            return new ResponseData("error", HttpStatus.NOT_FOUND.value(), "No se encontraron registros de interacciones.");
        } else {
            return new ResponseData("success", HttpStatus.OK.value(), interacciones);
        }
    }

    @GetMapping("/interaccion/{id}")
    public ResponseData getInteraccionById(@PathVariable int id) {
        Map<String, Object> where = new HashMap<>();
        where.put("id", id);
        LinkedList<Interaccion> result = controller.obtenerInteracciones(where, false);
        if (result.isEmpty()) {
            return new ResponseData("error", HttpStatus.NOT_FOUND.value(),
                    "No se encontró la interacción con el ID especificado.");
        } else {
            return new ResponseData("success", HttpStatus.OK.value(), result.getFirst());
        }
    }

    @PostMapping("/interaccion")
    public ResponseData createInteraccion(@RequestBody Interaccion interaccion) {
        boolean success = controller.nuevaInteraccion(interaccion);
        if (success) {
            return new ResponseData("success", HttpStatus.CREATED.value(), interaccion);
        } else {
            return new ResponseData("error", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "No se pudo crear el registro de interacción.");
        }
    }

    @PutMapping("/interaccion/{id}")
    public ResponseData updateInteraccion(@RequestBody Interaccion interaccion, @PathVariable int id) {
        interaccion.setId(id);
        boolean success = controller.actualizaInteraccion(interaccion);
        if (success) {
            return new ResponseData("success", HttpStatus.OK.value(), interaccion);
        } else {
            return new ResponseData("error", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "No se pudo actualizar el registro de interacción.");
        }
    }

    @DeleteMapping("/interaccion/{id}")
    public ResponseData deleteInteraccion(@PathVariable int id) {
        Interaccion interaccion = new Interaccion();
        interaccion.setId(id);
        boolean success = controller.eliminaInteraccion(interaccion);
        if (success) {
            return new ResponseData("success", HttpStatus.OK.value(), "Registro de interacción eliminado exitosamente.");
        } else {
            return new ResponseData("error", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "No se pudo eliminar el registro de interacción.");
        }
    }
}
