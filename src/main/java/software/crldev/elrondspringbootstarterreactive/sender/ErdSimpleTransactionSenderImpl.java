package software.crldev.elrondspringbootstarterreactive.sender;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.error.exception.MissingTransactionRequestException;
import software.crldev.elrondspringbootstarterreactive.interactor.account.ErdAccountInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.ErdTransactionInteractor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ErdSimpleTransactionSenderImpl implements ErdSimpleTransactionSender {

    private final ErdAccountInteractor accountInteractor;
    private final ErdTransactionInteractor transactionInteractor;

    @Override
    public Mono<TransactionHash> send(Wallet wallet, TransactionRequest request) {
        return assignNonce(wallet)
                .map(nonce -> buildSendableFromRequest(wallet, request, nonce, false))
                .flatMap(transactionInteractor::sendTransaction);
    }

    @Override
    public Mono<TransactionsSentResult> sendBatch(Wallet wallet, List<TransactionRequest> request) {
        request.stream().findFirst().orElseThrow(MissingTransactionRequestException::new);
        return assignNonce(wallet)
                .map(n -> {
                    var listOfSendables = new ArrayList<Transaction.Sendable>();

                    for (long i = 0, nonce = n; i < request.size() && nonce <= nonce + request.size(); i++, nonce++)
                        listOfSendables.add(buildSendableFromRequest(wallet, request.get((int) i), nonce, false));

                    return listOfSendables;
                })
                .flatMap(transactionInteractor::sendBatchOfTransactions);
    }

    @Override
    public Mono<SimulationResults> simulate(Wallet wallet, TransactionRequest request) {
        return assignNonce(wallet)
                .map(nonce -> buildSendableFromRequest(wallet, request, nonce, false))
                .flatMap(transactionInteractor::simulateTransaction);
    }

    @Override
    public Mono<TransactionCostEstimation> estimate(Wallet wallet, TransactionRequest request) {
        return assignNonce(wallet)
                .map(nonce -> buildSendableFromRequest(wallet, request, nonce, true))
                .flatMap(transactionInteractor::estimateTransactionCost);
    }

    private Transaction.Sendable buildSendableFromRequest(Wallet wallet, TransactionRequest request, Long nonceValue, boolean isEstimationRequest) {
        var senderAddress = Address.fromHex(wallet.getPublicKeyHex());
        var receiverAddress = request.getReceiverAddress();
        var nonce = Nonce.fromLong(nonceValue);
        var value = request.getValue();
        var data = request.getData();

        var transaction = new Transaction();
        transaction.setValue(value);
        transaction.setNonce(nonce);
        transaction.setReceiver(receiverAddress);
        transaction.setSender(senderAddress);
        transaction.setPayloadData(data);

        if (isEstimationRequest)
            return transaction.toSendableForEstimation();

        wallet.sign(transaction);
        return transaction.toSendable();
    }

    private Mono<Long> assignNonce(Wallet wallet) {
        return accountInteractor.getNonce(Address
                        .fromHex(wallet.getPublicKeyHex()))
                .map(AddressNonce::getNonce);
    }

}

