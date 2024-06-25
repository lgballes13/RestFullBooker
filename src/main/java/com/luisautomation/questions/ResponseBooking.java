package com.luisautomation.questions;

import com.luisautomation.models.BookingFinal;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ResponseBooking implements Question {

    public ResponseBooking forCreate(){
        return new ResponseBooking();
    }


    @Override
    public BookingFinal answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(BookingFinal.class);
    }

}
