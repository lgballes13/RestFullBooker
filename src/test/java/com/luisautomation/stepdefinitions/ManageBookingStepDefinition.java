package com.luisautomation.stepdefinitions;

import com.luisautomation.models.BookingFinal;
import com.luisautomation.models.BookingModel;
import com.luisautomation.models.UserModel;
import com.luisautomation.questions.ResponseBooking;
import com.luisautomation.questions.ResponseCode;
import com.luisautomation.questions.ResponseGetBooking;
import com.luisautomation.tasks.CreateBooking;
import com.luisautomation.tasks.CreateToken;
import com.luisautomation.tasks.GetBooking;
import com.luisautomation.tasks.UpdateBooking;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.hamcrest.CoreMatchers;
import org.json.simple.JSONObject;

import java.util.List;

public class ManageBookingStepDefinition {

    private final String baseUrl = "https://restful-booker.herokuapp.com";


    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private String checkin;
    private String checkout;
    private String additionalneeds;

    private void loadSessionVariables() {
        this.firstname = Serenity.sessionVariableCalled("firstname");
        this.lastname = Serenity.sessionVariableCalled("lastname");
        this.totalprice = Serenity.sessionVariableCalled("totalprice");
        this.depositpaid = Serenity.sessionVariableCalled("depositpaid");
        this.checkin = Serenity.sessionVariableCalled("checkin");
        this.checkout = Serenity.sessionVariableCalled("checkout");
        this.additionalneeds = Serenity.sessionVariableCalled("additionalneeds");
    }


    @Before
    public void before(){
        OnStage.setTheStage(new OnlineCast());
    }


    @Given("^the user wants to use the base URL$")
    public void theUserWantsToUseTheBaseURL() {
        OnStage.theActorCalled("user").whoCan(CallAnApi.at(baseUrl));
    }


    @When("^create booking with information of client$")
    public void createBookingWithInformationOfClient(List<UserModel> user) {
        OnStage.theActorInTheSpotlight().attemptsTo(CreateBooking.forUser(user));
    }

    @When("^consult the information of booking$")
    public void consultTheInformationOfBooking() {
        OnStage.theActorInTheSpotlight().attemptsTo(GetBooking.ofClient());
    }

    @When("^create token for update$")
    public void createTokenForUpdate() {
        OnStage.theActorInTheSpotlight().attemptsTo(CreateToken.forUpdate());
    }

    @When("^update booking$")
    public void updateBooking(List<UserModel> user) {
        OnStage.theActorInTheSpotlight().attemptsTo(UpdateBooking.ofClient(user));
    }


    @Then("^validate status code (\\d+)$")
    public void validateStatusCode(int statusCode) {
            OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat(ResponseCode.was(), CoreMatchers.equalTo(statusCode)));
    }

    @Then("^validate response with information the user$")
    public void validateResponseWithInformationTheUser() {
        BookingFinal model = new ResponseBooking().answeredBy(Actor.named("user"));
        loadSessionVariables();
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the usename", actor -> model.getBooking().getFirstname(), CoreMatchers.equalTo(firstname)));
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the lastname", actor -> model.getBooking().getLastname(), CoreMatchers.equalTo(lastname)));
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the totalprice", actor -> model.getBooking().getTotalprice(), CoreMatchers.equalTo(totalprice)));
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the depositpaid", actor -> model.getBooking().getDepositpaid(), CoreMatchers.equalTo(depositpaid)));
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the checkin", actor -> model.getBooking().getBookingdates().getCheckin(), CoreMatchers.equalTo(checkin)));
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the checkout", actor -> model.getBooking().getBookingdates().getCheckout(), CoreMatchers.equalTo(checkout)));
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the additionalneeds", actor -> model.getBooking().getAdditionalneeds(), CoreMatchers.equalTo(additionalneeds)));

    }


    @Then("^validate response with information saved of user$")
    public void validateResponseWithInformationSavedOfUser() {
        BookingModel model = new ResponseGetBooking().answeredBy(Actor.named("user"));
        loadSessionVariables();
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the usename", actor -> model.getFirstname(), CoreMatchers.equalTo(firstname)));
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the lastname", actor -> model.getLastname(), CoreMatchers.equalTo(lastname)));
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the totalprice", actor -> model.getTotalprice(), CoreMatchers.equalTo(totalprice)));
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the depositpaid", actor -> model.getDepositpaid(), CoreMatchers.equalTo(depositpaid)));
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the checkin", actor -> model.getBookingdates().getCheckin(), CoreMatchers.equalTo(checkin)));
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the checkout", actor -> model.getBookingdates().getCheckout(), CoreMatchers.equalTo(checkout)));
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat("the additionalneeds", actor -> model.getAdditionalneeds(), CoreMatchers.equalTo(additionalneeds)));

    }

}
