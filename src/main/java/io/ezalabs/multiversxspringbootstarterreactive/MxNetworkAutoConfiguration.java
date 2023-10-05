package io.ezalabs.multiversxspringbootstarterreactive;

import io.ezalabs.multiversxspringbootstarterreactive.config.MxNetworkConfigSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.network.MxNetworkInteractor;

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
