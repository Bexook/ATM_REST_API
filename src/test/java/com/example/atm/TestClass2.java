package com.example.atm;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;


public class TestClass2 extends AtmApplicationTests {

    @Test
    public void co() {
        RestAssured.post("http://localhost:8080/login?username=denys@gmail.com&password=admin");
        String str = RestAssured.get("http://localhost:8080/account/operation/get").asString();
        System.out.println(str);
    }


}
