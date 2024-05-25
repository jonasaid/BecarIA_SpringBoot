package com.tt.becaria.controller;

import com.tt.becaria.model.Interaccion;

import java.util.LinkedList;
import java.util.Map;

public interface InteraccionController {
    boolean nuevaInteraccion(Interaccion interaccion);
    LinkedList<Interaccion> obtenerInteracciones(Map<String, Object> where, boolean isInteraccionLike);
    boolean actualizaInteraccion(Interaccion interaccion);
    boolean eliminaInteraccion(Interaccion interaccion);

}
