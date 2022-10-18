package com.kjcrane.glofox.rest.Models.RequestInputs;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class UpdateClassInput {

    @NotNull(message = "Class name cannot be empty")
    private String className;
    @NotNull(message = "Class date cannot be empty")
    private String classDate;
    @NotNull(message = "Class capacity cannot be empty")
    @Min(value = 1, message = "Class capacity must be at least 1")
    private int capacity;
}
