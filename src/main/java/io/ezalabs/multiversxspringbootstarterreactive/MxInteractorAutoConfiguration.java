package io.ezalabs.multiversxspringbootstarterreactive;

import io.ezalabs.multiversxspringbootstarterreactive.client.MxProxyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.account.MxAccountInteractor;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.account.MxAccountInteractorImpl;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.block.MxBlockInteractor;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.block.MxBlockInteractorImpl;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.esdt.MxESDTInteractor;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.esdt.MxESDTInteractorImpl;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.network.MxNetworkInteractor;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.network.MxNetworkInteractorImpl;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.smartcontract.MxSmartContractInteractor;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.smartcontract.MxSmartContractInteractorImpl;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.MxTransactionInteractor;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.MxTransactionInteractorImpl;

@Configuration
@ConditionalOnClass({MxNetworkInteractor.class, MxAccountInteractor.class, MxTransactionInteractor.class})
@AutoConfigureAfter(MxClientAutoConfiguration.class)
public class MxInteractorAutoConfiguration {

  @Autowired
  private MxProxyClient mxProxyClient;

  @Bean
  public MxNetworkInteractor networkInteractor() {
    return new MxNetworkInteractorImpl(mxProxyClient);
  }

  @Bean
  public MxAccountInteractor accountInteractor() {
    return new MxAccountInteractorImpl(mxProxyClient);
  }

  @Bean
  public MxTransactionInteractor transactionInteractor() {
    return new MxTransactionInteractorImpl(mxProxyClient, accountInteractor());
  }

  @Bean
  public MxSmartContractInteractor smartContractInteractor() {
    return new MxSmartContractInteractorImpl(mxProxyClient, transactionInteractor());
  }

  @Bean
  public MxBlockInteractor blockInteractor() {
    return new MxBlockInteractorImpl(mxProxyClient);
  }

  @Bean
  public MxESDTInteractor esdtInteractor() {
    return new MxESDTInteractorImpl(mxProxyClient, transactionInteractor(), smartContractInteractor());
  }

}
