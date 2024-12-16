package com.bptn.feedAppAutomation.stepPageDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;

import com.bptn.feedAppAutomation.jwt.JwtService;
import com.bptn.feedAppAutomation.pageObjects.NewProfilePage;
import com.bptn.feedAppAutomation.provider.ResourceProvider;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class NewProfilePageSteps {

		@Autowired
		ResourceProvider resourceProvider;

		@Autowired
		NewProfilePage newProfilePage;

		@Autowired
		JwtService jwtService;

		String jwtToken;

		@Given("User {string} has a valid JWT token")
		public void user_has_a_valid_JWT_token(String username) {

			this.jwtToken = this.jwtService.generateJwtToken(username, this.resourceProvider.getJwtExpiration());
		}

		@When("User opens the basic profile page {string}")
		public void user_opens_the_basic_profile_page(String basicProfilePageRoute) {

			String basicProfilePageRouteWithToken = basicProfilePageRoute + "?token=" + this.jwtToken;
			this.newProfilePage.openNewProfilePage(basicProfilePageRouteWithToken);
		}

		@Then("User should see the following values:")
		public void user_should_see_the_following_values(DataTable data) {
			assertEquals(data.asMap().get("firstName"), this.newProfilePage.getFirstName());
			assertEquals(data.asMap().get("lastName"), this.newProfilePage.getLastName());
			assertEquals("", this.newProfilePage.getPassword());
			assertEquals(data.asMap().get("phone"), this.newProfilePage.getPhone());
			assertEquals(data.asMap().get("email"), this.newProfilePage.getEmailId());
		}

		@When("User fills in the basic profile with the following values:")
		public void user_fills_in_the_basic_profile_with_the_following_values(DataTable data) {

			this.newProfilePage.setFirstName(data.asMap().get("firstName"));
			this.newProfilePage.setLastName(data.asMap().get("lastName"));
			this.newProfilePage.setPassword(data.asMap().get("password"));
			this.newProfilePage.setPhone(data.asMap().get("phone"));
			this.newProfilePage.setEmailId(data.asMap().get("email"));
		}

		@When("User clicks 'Save' on the basic profile page")
		public void user_clicks_on_the_basic_profile_page() {
			this.newProfilePage.clickSave();
		}

		@Then("User should see the message {string} on the basic profile page")
		public void user_should_see_the_message_on_the_basic_profile_page(String message) {
			assertEquals(message, this.newProfilePage.getMessage());
		}
	}