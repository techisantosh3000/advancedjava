package com.rest.endpoint.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Toy {
    private Integer toyId;
    private String toyType;
    private Boolean isAvailable;

}
