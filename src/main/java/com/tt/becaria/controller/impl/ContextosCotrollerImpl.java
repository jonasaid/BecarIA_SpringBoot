package com.tt.becaria.controller.impl;

import com.tt.becaria.model.Contextos;
import com.tt.becaria.util.DbDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
public class ContextosCotrollerImpl {
    private final DbDriver driver = DbDriver.getInstance();

//    public boolean nuevoContexto(Contextos contexto) {
//        Map<String, Object> dataContexto = new HashMap<>();
//        dataContexto.put("titulo", contexto.getTitulo());
//        dataContexto.put("contexto", contexto.getContexto());
//
//        return driver.insert("contextos", dataContexto);
//    }

    public LinkedList<Contextos> obtenerContextos(Map<String, Object> where, boolean isContextoLike) {
        LinkedList<Contextos> contextos = new LinkedList<>();
        LinkedList<Map<String, Object>> data;

        if (where == null) {
            data = driver.select("*", "contextos");
        } else {
            data = driver.select("*", "contextos", where, isContextoLike);
        }

        for (Map<String, Object> mapa : data) {
            Contextos contexto = new Contextos();
            contexto.setTitulo(mapa.get("titulo").toString());
            contexto.setContexto(mapa.get("contexto").toString());
            contextos.add(contexto);
        }

        return contextos;
    }

//    public boolean actualizaContexto(Contextos contexto) {
//        Map<String, Object> dataContexto = new HashMap<>();
//        dataContexto.put("contexto", contexto.getContexto());
//
//        Map<String, Object> whereContexto = new HashMap<>();
//        whereContexto.put("titulo", contexto.getTitulo());
//
//        return driver.update("contextos", dataContexto, whereContexto);
//    }

//    public boolean eliminaContexto(Contextos contexto) {
//        Map<String, Object> whereContexto = new HashMap<>();
//        whereContexto.put("titulo", contexto.getTitulo());
//        return driver.delete("contextos", whereContexto);
//    }
}
