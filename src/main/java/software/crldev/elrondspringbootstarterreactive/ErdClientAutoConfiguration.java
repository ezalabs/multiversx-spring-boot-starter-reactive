package software.crldev.elrondspringbootstarterreactive;

import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClientImpl;
import software.crldev.elrondspringbootstarterreactive.config.ErdClientConfig;
import software.crldev.elrondspringbootstarterreactive.properties.ErdClientProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ErdClientProperties.class)
public class ErdClientAutoConfiguration {

    @Bean
    public ErdClientConfig clientConfig(ErdClientProperties props) {
        return new ErdClientConfig(props);
    }

    @Bean
    @ConditionalOnBean(ErdClientConfig.class)
    public ErdProxyClient proxyClient(ErdClientConfig config) {
        return new ErdProxyClientImpl(config);
    }


}
