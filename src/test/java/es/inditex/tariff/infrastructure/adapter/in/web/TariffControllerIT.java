package es.inditex.tariff.infrastructure.adapter.in.web;

import es.inditex.tariff.application.PickingTariffQueryHandler;
import es.inditex.tariff.application.port.in.PickingTariffQuery;
import es.inditex.tariff.application.port.in.PickingTariffQueryValidationException;
import es.inditex.tariff.utils.TariffTestSampleData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDateTime;
import java.util.Optional;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class TariffControllerIT {
    @LocalServerPort
    private int port;

    @MockBean
    private PickingTariffQueryHandler pickingTariffQueryHandler;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }


    @Test
    void shouldThrow404WhenTariffNotFound() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/tariffs?date=2022-06-14-10.00.00&productId=35455&brandId=1")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldReturnTariffWhenFound() throws PickingTariffQueryValidationException {
        PickingTariffQuery pickingTariffQuery = new PickingTariffQuery(LocalDateTime.of(2020, 6, 14, 10, 0), 35455, 1);
        Mockito.when(pickingTariffQueryHandler.process(pickingTariffQuery)).thenReturn(Optional.of(TariffTestSampleData.TARIFF_01));
        given().contentType(ContentType.JSON)
                .when()
                .get("/tariffs?date=2020-06-14-10.00.00&productId=35455&brandId=1")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturn400WhenDateIsNotValid() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/tariffs?date=2020.06-14-10.00.00&productId=35455&brandId=1")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturn400WhenProductIdIsMissing() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/tariffs?date=2020-06-14-10.00.00&brandId=1")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturn400WhenBrandIdIsMissing() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/tariffs?date=2020-06-14-10.00.00&productId=35455")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturn400WhenDateIsMissing() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/tariffs?productId=35455&brandId=1")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturn400WhenProductIdIsNegative() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/tariffs?date=2020-06-14-10.00.00&productId=-35455&brandId=1")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturn400WhenBrandIdIsNegative() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/tariffs?date=2020-06-14-10.00.00&productId=35455&brandId=-1")
                .then()
                .statusCode(400);
    }
}
