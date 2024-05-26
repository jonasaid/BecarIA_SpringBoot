package com.tt.becaria.controller.impl;

import com.tt.becaria.controller.ConsultaController;
import com.tt.becaria.controller.UsuarioController;
import com.tt.becaria.model.Consulta;
import com.tt.becaria.model.InsertionResult;
import com.tt.becaria.util.DbDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Esta clase es el controlador para la gestión de la entidad Consulta.
 * Permite realizar operaciones de creación, consulta, actualización y eliminación de registros de consultas en la base de datos.
 */
@Component
public class ConsultaControllerImpl implements ConsultaController {
    /**
     * Atributo de la clase DbDriver que permite manejar la información en la base de datos, a través,
     * de usar los métodos asociados al CRUD.
     */
    private final DbDriver driver = DbDriver.getInstance();

    /**
     * Controlador para gestionar las operaciones relacionadas con la entidad Usuario.
     */
    private final UsuarioController usuarioController;

    /**
     * Constructor de la clase ConsultaControllerImpl.
     *
     * @param usuarioController El servicio de UsuarioController utilizado para realizar operaciones relacionadas con usuarios.
     */
    @Autowired
    public ConsultaControllerImpl(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    /**
     * Crea un nuevo registro de conversación en la base de datos con la fecha y hora actual.
     *
     * @param consulta El objeto Consulta que contiene los datos de la conversación a insertar.
     * @return true si la inserción es exitosa, false en caso contrario.
     */
    public boolean nuevaConsulta(Consulta consulta) {
        // Obtiene la fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Asigna la fecha y hora actual al objeto Consulta
        consulta.setFecha_hora(fechaHoraActual);

        // Prepara los datos para la inserción
        Map<String, Object> dataConsulta = new HashMap<>();
        dataConsulta.put("id_usuario", consulta.getId_usuario());
        dataConsulta.put("fecha_hora", fechaHoraActual);

        // Realiza la inserción en la base de datos
        InsertionResult insertionResult = driver.insertWithId("consulta", dataConsulta);
        return insertionResult.isSuccess();
    }


    /**
     * Obtiene una lista de registros de consultas de la base de datos.
     *
     * @param where           Un mapa que contiene las condiciones de consulta opcionales.
     * @param isConsultaLike  Indica si se debe realizar una consulta con coincidencia parcial (LIKE) en las condiciones.
     * @return Una lista de objetos Consulta que cumplen con las condiciones de consulta.
     */
    public LinkedList<Consulta> obtenerConsultas(Map<String, Object> where, boolean isConsultaLike) {
        LinkedList<Consulta> consultas = new LinkedList<>();
        LinkedList<Map<String, Object>> data;

        if (where == null) {
            data = driver.select("*", "consulta");
        } else {
            data = driver.select("*", "consulta", where, isConsultaLike);
        }

        for (Map<String, Object> mapa : data) {
            Consulta consulta = new Consulta();
            consulta.setId(Integer.parseInt(mapa.get("id").toString()));
            consulta.setId_usuario(Integer.parseInt(mapa.get("id_usuario").toString()));
            consulta.setTitulo(mapa.get("titulo").toString());
            consulta.setVer(Boolean.parseBoolean(mapa.get("ver").toString()));
            consulta.setFecha_hora((LocalDateTime) mapa.get("fecha_hora"));
            consultas.add(consulta);
        }

        return consultas;
    }

    /**
     * Actualiza un registro de conversación existente en la base de datos.
     *
     * @param consulta El objeto Consulta con los datos actualizados.
     * @return true si la actualización es exitosa, false en caso contrario.
     */
    public boolean actualizaConsulta(Consulta consulta) {
        Map<String, Object> dataConsulta = new HashMap<>();
        Map<String, Object> whereConsulta = new HashMap<>();

        consulta.setVer(consulta.isVer());

        dataConsulta.put("titulo", consulta.getTitulo());
        dataConsulta.put("ver", consulta.isVer());

        whereConsulta.put("id", consulta.getId());

        return driver.update("consulta", dataConsulta, whereConsulta);
    }

    /**
     * Elimina un registro de conversación de la base de datos.
     *
     * @param consulta El objeto Consulta a eliminar.
     * @return true si la eliminación es exitosa, false en caso contrario.
     */
    public boolean eliminaConsulta(Consulta consulta) {
        Map<String, Object> whereConsulta = new HashMap<>();
        whereConsulta.put("id", consulta.getId());
        return driver.delete("consulta", whereConsulta);
    }
}
