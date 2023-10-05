package io.ezalabs.multiversxspringbootstarterreactive;

import io.ezalabs.multiversxspringbootstarterreactive.client.MxProxyClient;
import io.ezalabs.multiversxspringbootstarterreactive.client.MxProxyClientImpl;
import io.ezalabs.multiversxspringbootstarterreactive.config.MxClientConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.ezalabs.multiversxspringbootstarterreactive.properties.MxClientProperties;

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
