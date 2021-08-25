package com.crldev.elrondspringbootstarterreactive.util;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import software.crldev.elrondspringbootstarterreactive.util.PemUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PemUtilsTest {

    @Test
    void extractKeyHex() throws IOException {
        var pemFile = ResourceUtils.getFile("classpath:devnetAccount1.pem");
        var expected = "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62";

        assertEquals(expected, PemUtils.extractKeyHex(pemFile));
    }

}