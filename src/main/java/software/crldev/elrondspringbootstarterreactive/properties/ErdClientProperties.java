package software.crldev.elrondspringbootstarterreactive.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import software.crldev.elrondspringbootstarterreactive.error.exception.GatewayException;

import java.util.Locale;

/**
 * Configuration class for spring properties mapping
 *
 * @author carlo_stanciu
 */
@Data
@ConfigurationProperties(prefix = "spring.elrond.client")
public class ErdClientProperties {

    private String gateway;
    private long readTimeoutMillis = 10_000L;
    private long writeTimeoutMillis = 10_000L;

    {
        setGateway("devnet");
    }

    public void setGateway(String gateway) {
        switch (gateway.toLowerCase(Locale.ROOT).strip()) {
            case "devnet":
                this.gateway = processUrl("devnet-gateway");
                break;
            case "testnet":
                this.gateway = processUrl("testnet-gateway");
                break;
            case "mainnet":
                this.gateway = processUrl("gateway");
                break;
            default:
                throw new GatewayException();
        }
    }

    private String processUrl(String gatewayValue) {
        return String.format("https://%s.elrond.com", gatewayValue);
    }
}
