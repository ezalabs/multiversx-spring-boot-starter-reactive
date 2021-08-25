package software.crldev.elrondspringbootstarterreactive.config;

import software.crldev.elrondspringbootstarterreactive.properties.ErdClientProperties;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Getter;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Class used for the configuration of ErdClient
 *
 * @author carlo_stanciu
 */
@Getter
public class ErdClientConfig {

    private final String gatewayUrl;
    private final WebClient.Builder erdClientBuilder;

    /**
     * Constructor for creating an ErdClientConfig instance using properties
     *
     * @param clientProperties - injected properties required for webclient configuration
     */
    public ErdClientConfig(ErdClientProperties clientProperties) {
        this.gatewayUrl = clientProperties.getGateway();
        var httpClient = HttpClient.create()
                .doOnConnected(conn -> conn
                        .addHandler(new ReadTimeoutHandler(clientProperties.getReadTimeoutMillis(), TimeUnit.MILLISECONDS))
                        .addHandler(new WriteTimeoutHandler(clientProperties.getWriteTimeoutMillis(), TimeUnit.MILLISECONDS)));

        this.erdClientBuilder = WebClient
                .builder()
                .baseUrl(gatewayUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
