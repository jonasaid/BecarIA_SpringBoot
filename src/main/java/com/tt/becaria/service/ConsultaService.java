package com.tt.becaria.service;

import com.tt.becaria.model.Consulta;
import com.tt.becaria.model.ResponseData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ConsultaService {
    ResponseData getAllConsultas();
    ResponseData getConsultaById(@PathVariable int id);
    ResponseData createConsulta(@RequestBody Consulta consulta);
    ResponseData updateConsulta(@RequestBody Consulta consulta, @PathVariable int id);
    ResponseData deleteConsulta(@PathVariable int id);
}
