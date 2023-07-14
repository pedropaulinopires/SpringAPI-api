package com.pedro.spring.request;

import com.pedro.spring.domain.Person;
import com.pedro.spring.enums.Sexo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonPutRequest {

    @NotNull(message = "Campo id é obrigatório")
    private UUID id;

    @NotEmpty(message = "Campo nome é obrigatório")
    private String name;
    @NotNull(message = "Campo sexo é obrigatório!")
    private Sexo sexo;


    public Person build() {
        return new Person().builder().id(this.id).name(this.name).sexo(this.sexo).build();
    }


    public Person build(UUID id) {
        return new Person().builder().id(id).name(this.name).sexo(this.sexo).build();
    }


}
