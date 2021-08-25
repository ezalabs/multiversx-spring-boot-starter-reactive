package software.crldev.elrondspringbootstarterreactive.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration class for spring properties mapping
 *
 * @author carlo_stanciu
 */
@Data
@ConfigurationProperties(prefix = "spring.elrond.client")
public class ErdClientProperties {

    private String gateway = "https://devnet-gateway.elrond.com";
    private long readTimeoutMillis = 10_000L;
    private long writeTimeoutMillis = 10_000L;

}
