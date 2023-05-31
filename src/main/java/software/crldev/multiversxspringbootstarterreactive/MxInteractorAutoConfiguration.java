package software.crldev.multiversxspringbootstarterreactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.crldev.multiversxspringbootstarterreactive.client.MxProxyClient;
import software.crldev.multiversxspringbootstarterreactive.interactor.account.MxAccountInteractor;
import software.crldev.multiversxspringbootstarterreactive.interactor.account.MxAccountInteractorImpl;
import software.crldev.multiversxspringbootstarterreactive.interactor.block.MxBlockInteractor;
import software.crldev.multiversxspringbootstarterreactive.interactor.block.MxBlockInteractorImpl;
import software.crldev.multiversxspringbootstarterreactive.interactor.esdt.MxESDTInteractor;
import software.crldev.multiversxspringbootstarterreactive.interactor.esdt.MxESDTInteractorImpl;
import software.crldev.multiversxspringbootstarterreactive.interactor.network.MxNetworkInteractor;
import software.crldev.multiversxspringbootstarterreactive.interactor.network.MxNetworkInteractorImpl;
import software.crldev.multiversxspringbootstarterreactive.interactor.smartcontract.MxSmartContractInteractor;
import software.crldev.multiversxspringbootstarterreactive.interactor.smartcontract.MxSmartContractInteractorImpl;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.MxTransactionInteractor;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.MxTransactionInteractorImpl;

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
