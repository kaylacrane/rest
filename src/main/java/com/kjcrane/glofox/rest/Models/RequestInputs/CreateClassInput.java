package com.kjcrane.glofox.rest.Models.RequestInputs;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateClassInput {

    @NotNull(message = "Class name cannot be empty")
    private String className;
    @NotNull(message = "Class start date cannot be empty")
    private String startDate;
    @NotNull(message = "Class end date cannot be empty")
    private String endDate;
    @NotNull(message = "Class capacity cannot be empty")
    @Min(value = 1, message = "Class capacity must be at least 1")
    private int capacity;

}
