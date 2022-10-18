package com.kjcrane.glofox.rest.Models.RequestInputs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateBookingInput {

    @NotNull(message = "A valid first name must be provided in order to fetch related bookings")
    private String firstName;
    @NotNull(message = "A valid last name must be provided in order to fetch related bookings")
    private String lastName;
    @NotNull(message = "Class date cannot be empty")
    private String classDate;
}
