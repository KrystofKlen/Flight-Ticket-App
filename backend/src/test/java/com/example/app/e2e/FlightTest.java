package com.example.app.e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class FlightTest {

    private static final String AIRPORT_INPUT_ID = "input_airport";
    private static final String AIRPORT_FROM_CLASS_ID = "airport_from_input";
    private static final String AIRPORT_TO_CLASS_ID = "airport_to_input";
    private static final String BTN_OPEN_DIALOG_ID = "add_flight";
    private static final String FREE_SEATS_INPUT_ID="free_seats_input";
    private static final String BTN_SAVE_FLIGHT_ID="btn_save_flight";
    private static final String INVALID_FROM = "bla bla";
    private static final String TO = "Barcelona";

    private WebDriver webDriver;
    public FlightTest() {
        System.setProperty("webdriver.chrome.driver","/home/krystof/TJV/chromeDriver/chromedriver");
        webDriver = new ChromeDriver();
        webDriver.get("http://localhost:4200/flight");
    }

    /**
     * USERSTORY:
     * -user wants to add a new flight
     * -inputs an unknown airport
     * result: alert should be trigerred.
     */
    @Test
    public void shouldTriggerAlertWhenInvalidAirportGiven() {
        //set timeout
        webDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        // open dialog
        WebElement btnOpenDialogAddFlight = webDriver.findElement(By.id(BTN_OPEN_DIALOG_ID));
        btnOpenDialogAddFlight.click();
        // find input fields
        WebElement inputAirportFrom = webDriver.findElement(By.id(AIRPORT_FROM_CLASS_ID)).findElement(By.id(AIRPORT_INPUT_ID));
        WebElement inputAirportTo= webDriver.findElement(By.id(AIRPORT_TO_CLASS_ID)).findElement(By.id(AIRPORT_INPUT_ID));
        WebElement inputFreeSeats = webDriver.findElement(By.id(FREE_SEATS_INPUT_ID));
        WebElement btnSaveFlight = webDriver.findElement(By.id(BTN_SAVE_FLIGHT_ID));
        // put valid airports from database
        inputAirportFrom.sendKeys(INVALID_FROM);
        inputAirportTo.sendKeys(TO);
        // add free seats
        String freeSeatsStr = "70";
        inputAirportFrom.clear();
        inputFreeSeats.sendKeys(freeSeatsStr);
        //save flight
        try{
            btnSaveFlight.click();
            Thread.sleep(3000);
            Alert alert = webDriver.switchTo().alert();
            String text = alert.getText();
            boolean eq = text.equals("Flight information invalid.");
            alert.dismiss();
            Assertions.assertTrue(eq);
        }catch (Exception Ex) {
            Assertions.assertTrue(false);
        }
        webDriver.close();
    }

}
