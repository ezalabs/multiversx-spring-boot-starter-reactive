package software.crldev.multiversxspringbootstarterreactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.crldev.multiversxspringbootstarterreactive.config.MxNetworkConfigSupplier;
import software.crldev.multiversxspringbootstarterreactive.interactor.network.MxNetworkInteractor;

@Configuration
@AutoConfigureAfter(MxInteractorAutoConfiguration.class)
public class MxNetworkAutoConfiguration {

  @Autowired
  private MxNetworkInteractor networkInteractor;

  @Bean
  public MxNetworkConfigSupplier proxyNetworkConfig() {
    return new MxNetworkConfigSupplier(networkInteractor);
  }

}
