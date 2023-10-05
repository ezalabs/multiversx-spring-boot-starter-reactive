package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

public interface ESDTTransaction {

  TransactionRequest toTransactionRequest(Wallet wallet);

}
