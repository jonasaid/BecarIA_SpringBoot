package com.tt.becaria.service;

import com.tt.becaria.model.Email;
import com.tt.becaria.model.EmailRemitente;
import com.tt.becaria.model.ResponseData;

public interface EmailService {
    ResponseData enviarCorreoDestinatario(Email email);
    ResponseData enviarCorreo(EmailRemitente email);
}
