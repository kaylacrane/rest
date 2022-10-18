package com.kjcrane.glofox.rest.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ClientProfile {

    @NotNull(message = "A valid first name must be provided in order to fetch related bookings")
    private String firstName;
    @NotNull(message = "A valid last name must be provided in order to fetch related bookings")
    private String lastName;
}
