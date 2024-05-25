package com.tt.becaria.controller;

public interface EmailController {
    boolean enviarCorreoDestinatario(String destinatario, String asunto, String contenido);
    boolean enviarCorreo(String remitente, String contenido);
}
