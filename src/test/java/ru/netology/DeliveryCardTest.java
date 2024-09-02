package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    private String formatDate(int days, String pattern) {
        LocalDate date = LocalDate.now().plusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void souldBeSuccsess() throws InterruptedException {
        String planing = formatDate(4, "dd.MM.yyyy");
        SelenideElement form = $(".form");

        form.$("[data-test-id='city'] .input__control").setValue("Москва");
        form.$("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "a");
        form.$("[data-test-id='date'] .input__control").sendKeys(Keys.DELETE);
        form.$("[data-test-id='date'] .input__control").setValue(planing);
        form.$("[data-test-id='name'] .input__control").setValue("Иванов Иван");
        form.$("[data-test-id='phone'] .input__control").setValue("+79105004060");
        form.$("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + planing));
    }

}