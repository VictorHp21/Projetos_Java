package com.v.springCache.Service;

import com.v.springCache.Entity.Ibge;
import jakarta.persistence.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IbgeService {

    @Autowired
    private Ibge ibge;

    @Cacheable(value = "estados", condition = "#estado.equalsIgnoreCase('MG')")
    public List<IbgeResponse> findlAllCidades(String estado){
        return ibge.findlAllCidades(estado);
    }
}
