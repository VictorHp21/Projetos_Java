package com.v.springCache.Service;

import com.v.springCache.Entity.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

// classe responsável por limpar o cache


@Service
public class CacheService {

    //CacheManager é uma interface do Spring responsável por gerenciar os caches da aplicação.
    @Autowired
    private CacheManager cacheManager;


    public void evictAllCacheValues(String cacheName){
              //verifica se determinado objeto não é null
        Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
    }

    @Autowired
    private EmpresaService empresaService;

    @CachePut("empresas") // para atualizar cache
    public List<Empresa> atualizarCacheEmpresa(){
        return empresaService.findAll();
    }



}
