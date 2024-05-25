package com.tt.becaria.service.impl;

import com.tt.becaria.controller.impl.ConsultaControllerImpl;
import com.tt.becaria.model.Consulta;
import com.tt.becaria.service.ConsultaService;
import com.tt.becaria.model.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ConsultaServiceImpl implements ConsultaService {
    private final ConsultaControllerImpl controller;

    public ConsultaServiceImpl(ConsultaControllerImpl controller) {
        this.controller = controller;
    }

    @GetMapping("/consulta")
    public ResponseData getAllConsultas() {
        List<Consulta> consultas = controller.obtenerConsultas(null, false);
        if (consultas.isEmpty()) {
            return new ResponseData("error", HttpStatus.NOT_FOUND.value(), "No se encontraron registros de consultas.");
        } else {
            return new ResponseData("success", HttpStatus.OK.value(), consultas);
        }
    }

    @GetMapping("/consulta/{id}")
    public ResponseData getConsultaById(@PathVariable int id) {
        Map<String, Object> where = new HashMap<>();
        where.put("id", id);
        List<Consulta> result = controller.obtenerConsultas(where, false);
        if (result.isEmpty()) {
            return new ResponseData("error", HttpStatus.NOT_FOUND.value(),
                    "No se encontró la conversación con el ID especificado.");
        } else {
            return new ResponseData("success", HttpStatus.OK.value(), result.get(0));
        }
    }

    @PostMapping("/consulta")
    public ResponseData createConsulta(@RequestBody Consulta consulta) {
        boolean success = controller.nuevaConsulta(consulta);
        if (success) {
            return new ResponseData("success", HttpStatus.CREATED.value(), consulta);
        } else {
            return new ResponseData("error", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "No se pudo crear el registro de conversación.");
        }
    }

    @PutMapping("/consulta/{id}")
    public ResponseData updateConsulta(@RequestBody Consulta consulta, @PathVariable int id) {
        consulta.setId(id);
        boolean success = controller.actualizaConsulta(consulta);
        if (success) {
            return new ResponseData("success", HttpStatus.OK.value(), consulta);
        } else {
            return new ResponseData("error", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "No se pudo actualizar el registro de conversación.");
        }
    }

    @DeleteMapping("/consulta/{id}")
    public ResponseData deleteConsulta(@PathVariable int id) {
        Consulta consulta = new Consulta();
        consulta.setId(id);
        boolean success = controller.eliminaConsulta(consulta);
        if (success) {
            return new ResponseData("success", HttpStatus.OK.value(), "Registro de conversación eliminado exitosamente.");
        } else {
            return new ResponseData("error", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "No se pudo eliminar el registro de conversación.");
        }
    }
}
