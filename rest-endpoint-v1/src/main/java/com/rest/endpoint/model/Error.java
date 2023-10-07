package com.rest.endpoint.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Error {
    private Integer errorCode;
    private String errorReason;


}
