package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

public interface ESDTTransaction {

  TransactionRequest toTransactionRequest(Wallet wallet);

}
