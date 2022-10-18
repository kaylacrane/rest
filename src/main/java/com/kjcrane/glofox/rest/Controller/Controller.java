package com.kjcrane.glofox.rest.Controller;

import com.kjcrane.glofox.rest.Models.*;
import com.kjcrane.glofox.rest.Models.RequestInputs.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class Controller {
    public Collection<StudioClass> listOfClasses = new ArrayList<>();
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // CONTROLLERS FOR CLASS REQUESTS
    @GetMapping(value = "/classes")
    public Collection<StudioClass> listClasses() {
        return listOfClasses;
    }

    @PostMapping(value = "/classes")
    public void createClass(@Valid @RequestBody CreateClassInput inputStudioClass) throws Exception {

        LocalDate startDate = LocalDate.parse(inputStudioClass.getStartDate(), DATE_FORMATTER);
        LocalDate endDate = LocalDate.parse(inputStudioClass.getEndDate(), DATE_FORMATTER);
        long daysBetween = startDate.until(endDate, ChronoUnit.DAYS);

        ArrayList<LocalDate> datesNotAdded = new ArrayList<>();
        if (daysBetween >= 0) {
            for (int i = 0; i <= daysBetween; i++) {
                LocalDate dateOfClass = endDate.minusDays(i);
                StudioClass newClass = new StudioClass(inputStudioClass.getClassName(), dateOfClass, inputStudioClass.getCapacity(), new ArrayList<ClientProfile>());

                if (!listOfClasses.stream().anyMatch(singleClass -> singleClass.getClassDate().equals(dateOfClass))) {
                    listOfClasses.add(newClass);
                } else {
                    datesNotAdded.add(dateOfClass);
                }
            }
            if (!datesNotAdded.isEmpty()) {
                System.out.println("Class could not be added on following dates: " + datesNotAdded);
            }
        } else {
          throw new Exception("Class dates are not valid");
        }


    }

    @PutMapping(value = "/classes")
    public void updateClass(@RequestBody UpdateClassInput updatedClass) {

        try {
            LocalDate classDate = LocalDate.parse(updatedClass.getClassDate(), DATE_FORMATTER);
            StudioClass classToUpdate = listOfClasses.stream().filter(singleClass -> singleClass.getClassName().equals(updatedClass.getClassName())
            && singleClass.getClassDate().equals(classDate)).findFirst().orElse(null);

            classToUpdate.setCapacity(updatedClass.getCapacity());
        } catch (Exception e) {
            throw e;
        }

    }

    @DeleteMapping(value="/classes")
    public void deleteClass(@RequestBody DeleteClassInput classToDelete) {

        try {
            LocalDate classDate = LocalDate.parse(classToDelete.getClassDate(), DATE_FORMATTER);
            StudioClass classMatch = listOfClasses.stream().filter(singleClass -> singleClass.getClassName().equals(classToDelete.getClassName())
                    && singleClass.getClassDate().equals(classDate)).findFirst().orElse(null);

            listOfClasses.remove(classMatch);
        } catch (Exception e) {
            throw e;
        }
    }


    // CONTROLLERS FOR BOOKING REQUESTS
    @GetMapping(value = "/bookings")
    public Collection<StudioClass> listbookings(@RequestBody ListBookingsInput listBookingsInput) {

        try {
            ClientProfile clientProfile = new ClientProfile(listBookingsInput.getFirstName(), listBookingsInput.getLastName());
            Collection<StudioClass> clientBookingsList = listOfClasses.stream().filter(singleClass -> singleClass.getSignUpList().contains(clientProfile)).collect(Collectors.toList());
            if (!clientBookingsList.isEmpty()) {
                return clientBookingsList;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping(value = "/bookings")
    public void createBooking(@Valid @RequestBody CreateBookingInput createBookingInput) {

        try {
            ClientProfile clientProfile = new ClientProfile(createBookingInput.getFirstName(), createBookingInput.getLastName());
            LocalDate bookingDate = LocalDate.parse(createBookingInput.getClassDate(), DATE_FORMATTER);

            StudioClass classToBook = listOfClasses.stream().filter(singleClass -> singleClass.getClassDate().equals(bookingDate)).findFirst().orElse(null);
            classToBook.getSignUpList().add(clientProfile);

            /* If we want to limit booking numbers to capacity:
            if (!(classToBook.getSignUpList().size() == classToBook.getCapacity()) && !classToBook.getSignUpList().contains(clientProfile)) {
                classToBook.getSignUpList().add(clientProfile);
            }*/
        } catch (Exception e) {
            throw e;
        }

    }

    @PutMapping(value = "/bookings")
    public void updateBooking(@RequestBody UpdateBookingInput updateBookingInput) {
        // Update client booking: cancel booking on one day and book class for another day
        try {
            ClientProfile clientProfile = new ClientProfile(updateBookingInput.getFirstName(), updateBookingInput.getLastName());
            LocalDate classToDropDate = LocalDate.parse(updateBookingInput.getClassToDropDate(), DATE_FORMATTER);
            LocalDate classToBookDate = LocalDate.parse(updateBookingInput.getClassToBookDate(), DATE_FORMATTER);

            // Delete booking
            StudioClass classMatch = listOfClasses.stream().filter(singleClass -> singleClass.getSignUpList().contains(clientProfile)
                    && singleClass.getClassDate().equals(classToDropDate)).findFirst().orElse(null);

            classMatch.getSignUpList().remove(clientProfile);

            // Book new class
            StudioClass classToBook = listOfClasses.stream().filter(singleClass -> singleClass.getClassDate().equals(classToBookDate)).findFirst().orElse(null);
            classToBook.getSignUpList().add(clientProfile);

            /* If we want to limit booking numbers to capacity:
             if (!(classToBook.getSignUpList().size() == classToBook.getCapacity()) && !classToBook.getSignUpList().contains(clientProfile)) {
                classToBook.getSignUpList().add(clientProfile);
            }*/

        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping(value="/bookings")
    public void deleteBooking(@RequestBody DeleteBookingInput deleteBookingInput) {

        try {
            ClientProfile clientProfile = new ClientProfile(deleteBookingInput.getFirstName(), deleteBookingInput.getLastName());
            LocalDate classDate = LocalDate.parse(deleteBookingInput.getClassDate(), DATE_FORMATTER);

            StudioClass classMatch = listOfClasses.stream().filter(singleClass -> singleClass.getSignUpList().contains(clientProfile)
                    && singleClass.getClassDate().equals(classDate)).findFirst().orElse(null);

            classMatch.getSignUpList().remove(clientProfile);
        } catch (Exception e) {
            throw e;
        }
    }
}
