package es.inditex.tariff.e2e;

import es.inditex.tariff.domain.Tariff;
import es.inditex.tariff.infrastructure.adapter.in.web.PickTariffDto;
import es.inditex.tariff.utils.TariffTestSampleData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class E2EPickingTariffTest {

    @LocalServerPort
    private int port;

    static Stream<Arguments> tariffTestCases() {
        return Stream.of(
                Arguments.of("2020-06-14-10.00.00", 35455, 1, TariffTestSampleData.TARIFF_01),
                Arguments.of("2020-06-14-16.00.00", 35455, 1, TariffTestSampleData.TARIFF_02),
                Arguments.of("2020-06-14-21.00.00", 35455, 1, TariffTestSampleData.TARIFF_01),
                Arguments.of("2020-06-15-10.00.00", 35455, 1, TariffTestSampleData.TARIFF_03),
                Arguments.of("2020-06-16-21.00.00", 35455, 1, TariffTestSampleData.TARIFF_04)
        );
    }

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @ParameterizedTest
    @MethodSource("tariffTestCases")
    void testTariffSelection(String date, int productId, int brandId, Tariff expected) {
        PickTariffDto result = given().contentType(ContentType.JSON)
                .when()
                .queryParam("date", date)
                .queryParam("productId", productId)
                .queryParam("brandId", brandId)
                .get("/tariffs")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response().as(PickTariffDto.class);

        assertEquals(expected.getId().id(), result.tariffList());
        assertEquals(expected.getBrandId(), result.brandId());
        assertEquals(expected.getProductId(), result.productId());
        assertEquals(expected.getSchedule().startDate(), result.schedule().startDate());
        assertEquals(expected.getSchedule().endDate(), result.schedule().endDate());
        assertEquals(expected.getPrice().currency(), result.price().currency());
        assertEquals(expected.getPrice().amount(), result.price().amount());
    }


}
