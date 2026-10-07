package com.v.springCache.cloud;

import java.io.Serializable;
// é preciso implementar o serializable para utilizar o redis

public record IbgeResponse(int id, String nome) implements Serializable {
}
