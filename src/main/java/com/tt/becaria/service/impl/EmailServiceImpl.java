package com.tt.becaria.service.impl;

import com.tt.becaria.controller.impl.EmailControllerImpl;
import com.tt.becaria.service.EmailService;
import com.tt.becaria.model.Email;
import com.tt.becaria.model.EmailRemitente;
import com.tt.becaria.model.ResponseData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Esta clase es un controlador de endpoints relacionados con el envío de
 * correos electrónicos.
 * Proporciona un endpoint para enviar correos electrónicos utilizando el
 * controlador EmailControllerImpl.
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class EmailServiceImpl implements EmailService {

    private final EmailControllerImpl emailControllerImpl;

    /**
     * Crea una instancia del controlador EmailServiceImpl con el
     * EmailControllerImpl especificado.
     *
     * @param emailControllerImpl El EmailControllerImpl utilizado para enviar correos
     *                         electrónicos.
     */
    @Autowired
    public EmailServiceImpl(EmailControllerImpl emailControllerImpl) {
        this.emailControllerImpl = emailControllerImpl;
    }

    /**
     * Endpoint para enviar un correo electrónico.
     * Recibe un objeto Email en el cuerpo de la solicitud y utiliza el
     * EmailControllerImpl para enviar el correo electrónico.
     *
     * @param email El objeto Email que contiene los datos del correo electrónico a
     *              enviar.
     *
     * @return Un objeto ResponseData que contiene la respuesta de la solicitud.
     *         Si se logra enviar el correo, se devuelve una respuesta con
     *         estado "success", el código HTTP correspondiente y el objeto Email.
     *         Si no se encuentran registros, se devuelve una respuesta con estado
     *         "error", el código HTTP correspondiente y un mensaje de error.
     *
     */
    @PostMapping("/enviar_correo_destinatario")
    public ResponseData enviarCorreoDestinatario(@RequestBody Email email) {
        // Obtener los datos del correo electrónico desde el cuerpo de la solicitud
        // Enviar el correo electrónico
        boolean success = emailControllerImpl.enviarCorreoDestinatario(email.getDestinatario(), email.getAsunto(), email.getContenido());
        if (success) {
            return new ResponseData("success", HttpStatus.CREATED.value(), email);
        } else {
            return new ResponseData("error", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "No se pudo enviar el correo.");
        }
    }

    /**
     * Endpoint para enviar un correo electrónico.
     * Recibe un objeto EmailRemitente en el cuerpo de la solicitud y utiliza el
     * EmailControllerImpl para enviar el correo electrónico.
     *
     * @param email El objeto EmailRemitente que contiene los datos del correo electrónico a
     *              enviar.
     *
     * @return Un objeto ResponseData que contiene la respuesta de la solicitud.
     *         Si se logra enviar el correo, se devuelve una respuesta con
     *         estado "success", el código HTTP correspondiente y el objeto EmailRemitente.
     *         Si no se encuentran registros, se devuelve una respuesta con estado
     *         "error", el código HTTP correspondiente y un mensaje de error.
     *
     */
    @PostMapping("/enviar_correo")
    public ResponseData enviarCorreo(@RequestBody EmailRemitente email) {
        // Obtener los datos del correo electrónico desde el cuerpo de la solicitud
        // Enviar el correo electrónico
        boolean success = emailControllerImpl.enviarCorreo(email.getRemitente(), email.getContenido());
        if (success) {
            return new ResponseData("success", HttpStatus.CREATED.value(), email);
        } else {
            return new ResponseData("error", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "No se pudo enviar el correo.");
        }
    }
}
