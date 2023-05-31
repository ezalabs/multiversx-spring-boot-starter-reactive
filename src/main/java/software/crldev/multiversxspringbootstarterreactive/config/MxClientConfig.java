package software.crldev.multiversxspringbootstarterreactive.config;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import software.crldev.multiversxspringbootstarterreactive.error.exception.InvalidProxyUrlException;
import software.crldev.multiversxspringbootstarterreactive.properties.MxClientProperties;

/**
 * Class used for the configuration of MxClient
 *
 * @author carlo_stanciu
 */
@Slf4j
@Getter
public class MxClientConfig {

  private final String proxyUrl;
  private final WebClient.Builder mxClientBuilder;

  /**
   * Constructor for creating an MxClientConfig instance using properties
   *
   * @param clientProperties - injected properties required for webclient configuration
   */
  public MxClientConfig(MxClientProperties clientProperties) {
    this.proxyUrl = clientProperties.getProxyUrl();
    var httpClient = HttpClient.create()
        .doOnConnected(conn -> conn
            .addHandlerFirst(new ReadTimeoutHandler(clientProperties.getReadTimeoutMillis(), TimeUnit.MILLISECONDS))
            .addHandlerFirst(new WriteTimeoutHandler(clientProperties.getWriteTimeoutMillis(), TimeUnit.MILLISECONDS)));

    validateUrl(proxyUrl);

    log.info("Configuring client with proxy URl %s.".formatted(proxyUrl));
    this.mxClientBuilder = WebClient
        .builder()
        .baseUrl(proxyUrl)
        .clientConnector(new ReactorClientHttpConnector(httpClient));
  }

  private void validateUrl(String url) {
    try {
      new URL(url);
    } catch (MalformedURLException e) {
      throw new InvalidProxyUrlException(url);
    }
  }
}
