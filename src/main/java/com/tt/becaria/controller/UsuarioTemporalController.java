package com.tt.becaria.controller;

import com.tt.becaria.model.UsuarioTemporal;

import java.util.LinkedList;
import java.util.Map;

public interface UsuarioTemporalController {
    boolean nuevoUsuarioTemporal(UsuarioTemporal usuario);
    LinkedList<UsuarioTemporal> obtenerUsuariosTemporales(Map<String, Object> where, boolean isConsultaLike);

    boolean eliminaUsuarioTemporal(UsuarioTemporal usuario);
}
