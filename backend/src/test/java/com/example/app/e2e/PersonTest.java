package com.example.app.e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PersonTest {

    private static final String OPEN_DIALOG_ADD_USER_BTN_ID = "btn_add_user";
    private static final String INPUT_EMAIL_ID = "input_user_email";
    private final String EMAIL = "email_not_in_database@email.com";

    private static final String ADD_USER_BTN_ID = "add_user_btn";
    private static final String EMAIL_CELL_ID = "person_email";
    private static final String DELETE_PERSON_BTN_ID = "btn_delete_person";

    private static final String ROW_ID = "row_user";

    private WebDriver webDriver;

    public PersonTest() {
        System.setProperty("webdriver.chrome.driver","/home/krystof/TJV/chromeDriver/chromedriver");
        webDriver = new ChromeDriver();
        webDriver.get("http://localhost:4200/passangers");
    }


    /**
     * USERSTORY:
     *  -user wants to add a new user.
     *  -clicks button add uder
     *  -dialog opens
     *  -non-exiting email is used
     *  -user clicks button add in the dialog
     *  -dialog closes
     *  result: and new user is added and displayed
     *  ! ADDS TESTUSER TO REAL DATABASE, RUN shouldDeleteUser AFTER THIS TEST
     *  AND CHECK THAT BOTH PASSED.
     */
    @Test
    @Order(1)
    void shouldAddNewUser() throws InterruptedException {

        //set timeout
        webDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        // open dialog
        WebElement btnOpenDialogAddFlight = webDriver.findElement(By.id(OPEN_DIALOG_ADD_USER_BTN_ID));
        btnOpenDialogAddFlight.click();

        WebElement inputAirportFrom = webDriver.findElement(By.id(INPUT_EMAIL_ID));
        WebElement btnAddUser = webDriver.findElement(By.id(ADD_USER_BTN_ID));

        // fill email
        inputAirportFrom.sendKeys(EMAIL);
        // send request
        btnAddUser.click();

        Thread.sleep(5000);

        List<WebElement> rows = webDriver
                .findElements(By.id(ROW_ID));

        //check if user was displayed
        boolean found = false;
        for(var row : rows){
            WebElement email = row.findElement(By.id(EMAIL_CELL_ID));
            String userEmail= email.getText();
            if(userEmail.equals(EMAIL)){
                found = true;
                break;
            }
        }
        webDriver.close();
        Assertions.assertTrue(found);
    }

    /**
     * USERSTORY: user wants to delete person
     *  -clicks button delete
     *  result: user should be deleted.
     * RUN THIS TEST AFTER shouldAddNewUser()
     */
    @Test
    @Order(2)
    void shouldDeleteUser() throws InterruptedException {
        //set timeout
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> rows = webDriver
                .findElements(By.id(ROW_ID));


        for(var row : rows){
            WebElement email = row.findElement(By.id(EMAIL_CELL_ID));
            String userEmail = email.getText();
            if( ! userEmail.equals(EMAIL)){
                continue;
            }
            WebElement btnDelete = row.findElement(By.id(DELETE_PERSON_BTN_ID));
            btnDelete.click();
        }

        Thread.sleep(5000);
        // reload data from table
        // try to find the deleted user
        rows = webDriver
                .findElements(By.id(EMAIL_CELL_ID));
        if(rows.isEmpty()){
            // no user in the table
            Assertions.assertTrue(true);
        }
        boolean found = false;
        for(var row : rows){
            String userEmail= row.getText();
            if(userEmail.equals(EMAIL)){
                found = true;
                break;
            }
        }
        webDriver.close();
        Assertions.assertFalse(found);
    }
}
