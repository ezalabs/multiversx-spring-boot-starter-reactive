package io.ezalabs.multiversxspringbootstarterreactive.properties;

import io.ezalabs.multiversxspringbootstarterreactive.error.exception.GatewayException;
import java.util.Locale;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration class for spring properties mapping
 *
 * @author carlo_stanciu
 */
@Data
@ConfigurationProperties(prefix = "spring.multiversx.client")
public class MxClientProperties {

  private String proxyUrl;
  private long readTimeoutMillis = 10_000L;
  private long writeTimeoutMillis = 10_000L;

  {
    setGateway("devnet");
  }

  public void setGateway(String gateway) {
    var gatewayValue = switch (gateway.toLowerCase(Locale.ROOT).strip()) {
      case "devnet" -> "devnet-gateway";
      case "testnet" -> "testnet-gateway";
      case "mainnet" -> "gateway";
      default -> throw new GatewayException();
    };

    this.proxyUrl = "https://%s.multiversx.com".formatted(gatewayValue);
  }

  public void setCustomProxyUrl(String customProxyUrl) {
    this.proxyUrl = customProxyUrl;
  }
}
