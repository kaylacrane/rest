package com.kjcrane.glofox.rest.Models.RequestInputs;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateBookingInput {

    @NotNull(message = "A valid first name must be provided in order to fetch related bookings")
    private String firstName;
    @NotNull(message = "A valid last name must be provided in order to fetch related bookings")
    private String lastName;
    @NotNull(message = "Class to drop date cannot be empty")
    private String classToDropDate;
    @NotNull(message = "Class to book date cannot be empty")
    private String classToBookDate;
}
