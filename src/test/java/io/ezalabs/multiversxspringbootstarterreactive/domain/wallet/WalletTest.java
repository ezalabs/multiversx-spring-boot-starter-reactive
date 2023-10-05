package io.ezalabs.multiversxspringbootstarterreactive.domain.wallet;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import io.ezalabs.multiversxspringbootstarterreactive.error.exception.InvalidPemFileException;
import io.ezalabs.multiversxspringbootstarterreactive.error.exception.PrivateKeyHexSizeException;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.Transaction;

class WalletTest {

  String publicKeyHex = "bf86ad970aa3c93c2382889c0a4fba4728b5b851634d57ccd65dcddeda8c8bb0";
  String privateKeyHex = "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62";
  byte[] privateKeyBuffer = Hex.decode(privateKeyHex);

  @Test
  void fromPrivateKeyHex() {
    var wallet = Wallet.fromPrivateKeyHex(privateKeyHex);

    assertArrayEquals(privateKeyBuffer, wallet.getPrivateKey());
    assertEquals(publicKeyHex, wallet.getPublicKeyHex());
  }

  @Test
  void fromPrivateKeyHex_sizeException() {
    assertThrows(PrivateKeyHexSizeException.class, () -> Wallet.fromPrivateKeyHex(privateKeyHex.concat("1")));
  }

  @Test
  void fromPrivateKeyBuffer() {
    var wallet = Wallet.fromPrivateKeyBuffer(privateKeyBuffer);

    assertEquals(privateKeyBuffer, wallet.getPrivateKey());
    assertEquals(publicKeyHex, wallet.getPublicKeyHex());
  }

  @Test
  void testPem_success_fromFile() throws Exception {
    var pemFile = ResourceUtils.getFile("classpath:devnetAccount1.pem");
    var wallet = Wallet.fromPemFile(pemFile);

    assertEquals(publicKeyHex, wallet.getPublicKeyHex());
    assertEquals(privateKeyHex, Hex.toHexString(wallet.getPrivateKey()));
  }

  @Test
  void testPem_success_fromFilePart() throws Exception {
    var pemFile = ResourceUtils.getFile("classpath:devnetAccount1.pem");

    var pemFilePart = Mockito.mock(FilePart.class);
    var dataBuffer = Mockito.mock(DataBuffer.class);

    when(dataBuffer.asInputStream()).thenReturn(new FileInputStream(pemFile));
    when(pemFilePart.content()).thenReturn(Flux.just(dataBuffer));

    StepVerifier.create(Wallet.fromPemFile(pemFilePart))
        .assertNext(wallet -> {
          assertEquals(publicKeyHex, wallet.getPublicKeyHex());
          assertEquals(privateKeyHex, Hex.toHexString(wallet.getPrivateKey()));
        })
        .verifyComplete();
  }

  @Test
  void testPem_fail() throws Exception {
    var pemFileInvalidPem = ResourceUtils.getFile("classpath:invalid.pem");

    assertThrows(InvalidPemFileException.class,
        () -> Wallet.fromPemFile(pemFileInvalidPem));

    var pemFileInvalid = Mockito.mock(File.class);
    when(pemFileInvalid.getName()).thenReturn(null);

    assertThrows(InvalidPemFileException.class,
        () -> Wallet.fromPemFile(pemFileInvalid));

    var pemFilePart = Mockito.mock(FilePart.class);
    var dataBuffer = Mockito.mock(DataBuffer.class);

    when(dataBuffer.asInputStream()).thenReturn(new FileInputStream(pemFileInvalidPem));
    when(pemFilePart.content()).thenReturn(Flux.just(dataBuffer));

    StepVerifier.create(Wallet.fromPemFile(pemFilePart))
        .expectError(InvalidPemFileException.class);
  }

  @Test
  void test_fromMnemonic() {
    var mnemonicPhrase = List.of("helmet", "kingdom", "fire", "surprise", "asthma",
        "tourist", "junk", "talent", "delay", "bleak", "scene", "crazy", "squirrel", "credit",
        "lamp", "raise", "peanut", "ensure", "inhale", "object", "endorse", "sound", "return",
        "fish");

    assertEquals(publicKeyHex, Wallet.fromMnemonic(mnemonicPhrase, 0).getPublicKeyHex());
  }

  @Test
  void sign_success() {
    var signable = new Transaction();

    var wallet = Wallet.fromPrivateKeyHex(privateKeyHex);
    wallet.sign(signable);
  }

}