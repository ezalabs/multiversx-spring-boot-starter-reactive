package software.crldev.multiversxspringbootstarterreactive;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.crldev.multiversxspringbootstarterreactive.client.MxProxyClient;
import software.crldev.multiversxspringbootstarterreactive.client.MxProxyClientImpl;
import software.crldev.multiversxspringbootstarterreactive.config.MxClientConfig;
import software.crldev.multiversxspringbootstarterreactive.properties.MxClientProperties;

@Configuration
@EnableConfigurationProperties(MxClientProperties.class)
public class MxClientAutoConfiguration {

  @Bean
  public MxClientConfig clientConfig(MxClientProperties props) {
    return new MxClientConfig(props);
  }

  @Bean
  @ConditionalOnBean(MxClientConfig.class)
  public MxProxyClient proxyClient(MxClientConfig config) {
    return new MxProxyClientImpl(config);
  }


}
