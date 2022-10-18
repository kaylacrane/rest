package com.kjcrane.glofox.rest.Models.RequestInputs;

import lombok.Getter;
import javax.validation.constraints.NotNull;

@Getter
public class DeleteClassInput {

    @NotNull(message = "Class name cannot be empty")
    private String className;
    @NotNull(message = "Class date cannot be empty")
    private String classDate;
}
