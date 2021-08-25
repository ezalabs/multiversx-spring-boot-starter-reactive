package software.crldev.elrondspringbootstarterreactive.sender;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.elrondspringbootstarterreactive.error.exception.MissingTransactionRequestException;
import software.crldev.elrondspringbootstarterreactive.interactor.account.ErdAccountInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.ErdTransactionInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.SendableTransaction;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ErdSimpleTransactionSenderImpl implements ErdSimpleTransactionSender {

    private final ErdAccountInteractor accountInteractor;
    private final ErdTransactionInteractor transactionInteractor;

    @Override
    public Mono<TransactionHash> send(TransactionRequest request) {
        return assignNonce(request)
                .map(nonce -> buildSendableFromRequest(request, nonce, false))
                .flatMap(transactionInteractor::sendTransaction);
    }

    @Override
    public Mono<TransactionsSentResult> sendBatchOfTransactions(List<TransactionRequest> request) {
        return assignNonce(request.stream().findFirst().orElseThrow(MissingTransactionRequestException::new))
                .map(n -> {
                    var listOfSendables = new ArrayList<SendableTransaction>();

                    for (long i = 0, nonce = n; i < request.size() && nonce <= nonce + request.size(); i++, nonce++)
                        listOfSendables.add(buildSendableFromRequest(request.get((int) i), nonce, false));

                    return listOfSendables;
                })
                .flatMap(transactionInteractor::sendBatchOfTransactions);
    }

    @Override
    public Mono<SimulationResults> simulate(TransactionRequest request) {
        return assignNonce(request)
                .map(nonce -> buildSendableFromRequest(request, nonce, false))
                .flatMap(transactionInteractor::simulateTransaction);
    }

    @Override
    public Mono<TransactionCostEstimation> estimate(TransactionRequest request) {
        return assignNonce(request)
                .map(nonce -> buildSendableFromRequest(request, nonce, true))
                .flatMap(transactionInteractor::estimateTransactionCost);
    }

    private SendableTransaction buildSendableFromRequest(TransactionRequest request, Long nonceValue, boolean isEstimationRequest) {
        var wallet = request.getWallet();
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

    private Mono<Long> assignNonce(TransactionRequest request) {
        return accountInteractor.getNonce(Address
                .fromHex(request.getWallet().getPublicKeyHex()))
                .map(AddressNonce::getNonce);
    }

}

