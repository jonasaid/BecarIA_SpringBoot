package com.tt.becaria.controller.impl;


import com.tt.becaria.model.PeticionHF;
import com.tt.becaria.service.impl.HuggingFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Component
public class HuggingFaceController {
    @Autowired
    private HuggingFaceService huggingFaceService;

    @PostMapping("/query")
    public String sendQuery(@RequestBody PeticionHF peticion) {
        return huggingFaceService.query(peticion);
    }
}
