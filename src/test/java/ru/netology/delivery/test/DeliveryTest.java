package ru.netology.delivery.test;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan meeting")
    void shouldPlanMeeting () {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysToAddForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        int daysToAddForSecondMeeting = 7;
        String secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate))
                .shouldBe(visible);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $(".button").click();
        $("[data-test-id=replan-notification] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(visible);
        $("[data-test-id=replan-notification] button").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate))
                .shouldBe(visible);

    }

//    @Test
//    public void firstCityTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue("Samara");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(5, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(5, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("Иван Иванов");
//        $("[data-test-id='phone'] input").setValue("+79001488228");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='city']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Доставка в выбранный город недоступна"));
//    }
//
//    @Test
//    public void secondCityTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue(" ");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(5, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(5, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("Иван Иванов");
//        $("[data-test-id='phone'] input").setValue("+79001488228");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='city']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Поле обязательно для заполнения"));
//    }
//
//    @Test
//    public void thirdCityTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue("123");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(5, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(5, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("Иван Иванов");
//        $("[data-test-id='phone'] input").setValue("+79001488228");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='city']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Доставка в выбранный город недоступна"));
//    }
//
//    @Test
//    public void firstDateTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue("Самара");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(0, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(0, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("Иван Иванов");
//        $("[data-test-id='phone'] input").setValue("+79001488228");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='date']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Заказ на выбранную дату невозможен"));
//    }
//
//    @Test
//    public void secondDateTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue("Самара");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(-3, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(-3, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("Иван Иванов");
//        $("[data-test-id='phone'] input").setValue("+79001488228");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='date']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Заказ на выбранную дату невозможен"));
//    }
//
//    @Test
//    public void thirdDateTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue("Самара");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(2, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(2, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("Иван Иванов");
//        $("[data-test-id='phone'] input").setValue("+79001488228");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='date']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Заказ на выбранную дату невозможен"));
//    }
//
//    @Test
//    public void firstNameTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue("Самара");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(3, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("Ivan Ivanov");
//        $("[data-test-id='phone'] input").setValue("+79001488228");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='name']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
//    }
//
//    @Test
//    public void secondNameTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue("Самара");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(3, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("");
//        $("[data-test-id='phone'] input").setValue("+79001488228");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='name']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Поле обязательно для заполнения"));
//    }
//
//    @Test
//    public void thirdNameTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue("Самара");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(3, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("+-1()&*321");
//        $("[data-test-id='phone'] input").setValue("+79001488228");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='name']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
//    }
//
//    @Test
//    public void firstPhoneTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue("Самара");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(5, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(5, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("Иван Иванов");
//        $("[data-test-id='phone'] input").setValue("+");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='phone']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Мобильный телефон Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
//    }
//    //BUG
//
//    @Test
//    public void secondPhoneTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue("Самара");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(5, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(5, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("Иван Иванов");
//        $("[data-test-id='phone'] input").setValue("+1");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='phone']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Мобильный телефон Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
//    }
//
//    //BUG
//
//    @Test
//    public void thirdPhoneTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue("Самара");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(5, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(5, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("Иван Иванов");
//        $("[data-test-id='phone'] input").setValue("");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='phone']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Поле обязательно для заполнения"));
//    }
//
//    @Test
//    public void firstAgreementTest () {
//        open("http://localhost:9999");
//        $("[data-test-id='city'] input").setValue("Самара");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        String planningDate = generateDate(5, "dd.MM.yyyy");
//        $("[data-test-id='date'] input").setValue(generateDate(5, "dd.MM.yyyy"));
//        $("[data-test-id='name'] input").setValue("Иван Иванов");
//        $("[data-test-id='phone'] input").setValue("+79001488228");
//        $(".button").click();
//        $("[data-test-id='agreement']")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldBe(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
//    }
//
}