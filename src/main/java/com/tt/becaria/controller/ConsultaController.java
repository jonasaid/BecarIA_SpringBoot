package com.tt.becaria.controller;

import com.tt.becaria.model.Consulta;

import java.util.LinkedList;
import java.util.Map;

public interface ConsultaController {
    boolean nuevaConsulta(Consulta consulta);
    LinkedList<Consulta> obtenerConsultas(Map<String, Object> where, boolean isConsultaLike);
    boolean actualizaConsulta(Consulta consulta);
    boolean eliminaConsulta(Consulta consulta);
}
