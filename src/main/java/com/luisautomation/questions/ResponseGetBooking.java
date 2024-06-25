package com.luisautomation.questions;

import com.luisautomation.models.BookingFinal;
import com.luisautomation.models.BookingModel;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ResponseGetBooking implements Question {

    public static ResponseGetBooking ofClient (){
        return new ResponseGetBooking();
    }
    @Override
    public BookingModel answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(BookingModel.class);
    }
}
