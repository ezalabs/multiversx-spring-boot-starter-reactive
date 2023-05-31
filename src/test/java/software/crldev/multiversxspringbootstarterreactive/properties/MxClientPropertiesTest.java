package software.crldev.multiversxspringbootstarterreactive.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.error.exception.GatewayException;

class MxClientPropertiesTest {

  @Test
  void setGateway() {
    var props = new MxClientProperties();

    assertEquals("https://devnet-gateway.multiversx.com", props.getProxyUrl());

    props.setGateway("devnet");
    assertEquals("https://devnet-gateway.multiversx.com", props.getProxyUrl());

    props.setGateway("testnet");
    assertEquals("https://testnet-gateway.multiversx.com", props.getProxyUrl());

    props.setGateway("mainnet");
    assertEquals("https://gateway.multiversx.com", props.getProxyUrl());

    props.setCustomProxyUrl("https://custom-api.com");
    assertEquals("https://custom-api.com", props.getProxyUrl());

    assertThrows(GatewayException.class, () -> props.setGateway("invalid"));
  }
}