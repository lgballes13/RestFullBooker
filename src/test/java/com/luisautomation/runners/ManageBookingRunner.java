package com.luisautomation.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/manage_bookings.feature", glue = "com.luisautomation", snippets = SnippetType.CAMELCASE)
public class ManageBookingRunner {
}
