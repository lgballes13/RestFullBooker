package com.luisautomation.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class GetBooking implements Task {

    private String url = "/booking";

    private String bookingid = "/"+Serenity.sessionVariableCalled("bookingid");

    public static GetBooking ofClient(){
        return Tasks.instrumented(GetBooking.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        System.out.println(bookingid);
        actor.attemptsTo(Get.resource(url+bookingid).with(requestSpecification -> requestSpecification.contentType(ContentType.JSON)));
        lastResponse().prettyPrint();

    }
}
