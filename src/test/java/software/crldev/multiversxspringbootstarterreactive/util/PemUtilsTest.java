package software.crldev.multiversxspringbootstarterreactive.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import software.crldev.multiversxspringbootstarterreactive.error.exception.InvalidPemFileException;

class PemUtilsTest {

  @Test
  void extractKeyHex_fromFile() throws IOException {
    var pemFile = ResourceUtils.getFile("classpath:devnetAccount1.pem");
    var expected = "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62";

    assertEquals(expected, PemUtils.extractKeyHex(pemFile));
  }

  @Test
  void extractKeyHex_invalidFile() throws IOException {
    var pemFile = ResourceUtils.getFile("classpath:invalid.pem");

    assertThrows(InvalidPemFileException.class, () -> PemUtils.extractKeyHex(pemFile));
  }

  @Test
  void extractKeyHex_fromFilePart() throws IOException {
    var pemFile = ResourceUtils.getFile("classpath:devnetAccount1.pem");

    var pemFilePart = Mockito.mock(FilePart.class);
    var dataBuffer = Mockito.mock(DataBuffer.class);

    when(dataBuffer.asInputStream()).thenReturn(new FileInputStream(pemFile));
    when(pemFilePart.content()).thenReturn(Flux.just(dataBuffer));

    var expected = "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62";

    StepVerifier.create(PemUtils.extractKeyHex(pemFilePart))
        .assertNext(key -> assertEquals(expected, key))
        .verifyComplete();
  }

}