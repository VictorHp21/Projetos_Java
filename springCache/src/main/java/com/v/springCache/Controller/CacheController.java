package com.v.springCache.Controller;

import com.v.springCache.Service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheService service;

    @PostMapping
    public void clear(@RequestParam("cacheName") String cacheName){
        service.evictAllCacheValues(cacheName);
    }


    @PutMapping
    public void atualizar(){
        service.atualizarCacheEmpresa();
    }

}
