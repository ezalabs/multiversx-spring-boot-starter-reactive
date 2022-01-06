package software.crldev.elrondspringbootstarterreactive.properties;

import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.error.exception.GatewayException;

import static org.junit.jupiter.api.Assertions.*;

class ErdClientPropertiesTest {

    @Test
    void setGateway() {
        var props = new ErdClientProperties();

        assertEquals("https://devnet-gateway.elrond.com", props.getGateway());

        props.setGateway("devnet");
        assertEquals("https://devnet-gateway.elrond.com", props.getGateway());

        props.setGateway("testnet");
        assertEquals("https://testnet-gateway.elrond.com", props.getGateway());

        props.setGateway("mainnet");
        assertEquals("https://gateway.elrond.com", props.getGateway());

        assertThrows(GatewayException.class, () -> props.setGateway("invalid"));
    }
}