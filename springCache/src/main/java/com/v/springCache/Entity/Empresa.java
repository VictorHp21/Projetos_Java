package com.v.springCache.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Empresa {

    @Id
    private Long Id;


    private String Nome;

}
