package com.tt.becaria.service;

import com.tt.becaria.model.ResponseData;
import com.tt.becaria.model.UsuarioTemporal;

public interface UsuarioTemporalService {
    ResponseData getAllUsuariosTemporales();
    ResponseData getUsuarioById(int id);
    ResponseData createUsuarioTemporal(UsuarioTemporal usuario);
    ResponseData deleteUsuarioTemporal(int id);
}
