package com.v.springCache.Controller;

import com.v.springCache.Service.IbgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ibge")
public class ibgeController {

    @Autowired
    private IbgeService service;

    @GetMapping
    public ResponseEntity<List<IbgeResponse>> findAllCidade(@RequestParam String estado){
        return ResponseEntity.ok(service.findAllCidades(estado));
    }

}
