package com.rest.endpoint.dto;

import com.rest.endpoint.model.Error;
import com.rest.endpoint.model.Toy;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class APIResponse<T> {
    @NonNull
    private List<Toy> toys;

    @NonNull
    private List<Error> errors;

}
