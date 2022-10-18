package com.kjcrane.glofox.rest.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class StudioClass {

    @NotNull
    private String className;
    @NotNull
    private LocalDate classDate;
    @NotNull
    private int capacity;
    private ArrayList<ClientProfile> signUpList;

}
