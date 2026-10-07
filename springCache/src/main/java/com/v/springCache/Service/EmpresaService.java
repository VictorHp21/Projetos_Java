package com.v.springCache.Service;

import com.v.springCache.Entity.Empresa;
import com.v.springCache.Repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repository;



    @Cacheable("empresas") // cache em memoria ao parar a aplicação ele perde tbm(usar redis entre outros)
    public List<Empresa> findAllComCache(){
        return findAll();  // para evitar conflito de anotações
    }

    public List<Empresa> findAll(){
        return (List<Empresa>) repository.findAll();
    }
}
