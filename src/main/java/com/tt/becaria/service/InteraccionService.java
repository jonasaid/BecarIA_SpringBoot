package com.tt.becaria.service;

import com.tt.becaria.model.Interaccion;
import com.tt.becaria.model.ResponseData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface InteraccionService {
    public ResponseData getAllInteracciones();
    public ResponseData getInteraccionById(@PathVariable int id);
    public ResponseData createInteraccion(@RequestBody Interaccion interaccion);
    public ResponseData updateInteraccion(@RequestBody Interaccion interaccion, @PathVariable int id);
    public ResponseData deleteInteraccion(@PathVariable int id);

}
