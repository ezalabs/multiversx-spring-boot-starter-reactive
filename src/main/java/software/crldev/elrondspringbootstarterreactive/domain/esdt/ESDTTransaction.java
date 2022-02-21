package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

public interface ESDTTransaction {

    TransactionRequest toTransactionRequest(Wallet wallet);

}
