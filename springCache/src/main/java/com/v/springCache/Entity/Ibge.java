package com.v.springCache.Entity;

import com.v.springCache.cloud.IbgeResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "ibge-ms", url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados")
public interface Ibge {

    @RequestMapping(method = RequestMethod.GET, value = "{estado}/municipios")
    List<IbgeResponse> findAllCidades(@PathVariable String estado);

    List<IbgeResponse> findlAllCidades(String estado);
}
