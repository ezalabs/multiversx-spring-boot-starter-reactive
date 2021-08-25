package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import software.crldev.elrondspringbootstarterreactive.config.ErdNetworkConfigSupplier;
import software.crldev.elrondspringbootstarterreactive.config.JsonMapper;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.SendableTransaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;

import static software.crldev.elrondspringbootstarterreactive.util.GasUtils.computeGasCost;

/**
 * Domain object for Transaction representation, which is Signable
 * Contains all the required fields to validate & construct a transaction
 *
 * @author carlo_stanciu
 */
@Data
public class Transaction implements Signable {

    private Nonce nonce = Nonce.fromLong(0L);
    private ChainID chainID = ChainID.fromString(ErdNetworkConfigSupplier.config.getChainId());
    private Balance value = Balance.zero();
    private Address sender = Address.zero();
    private Address receiver = Address.zero();
    private GasPrice gasPrice = GasPrice.fromNumber(ErdNetworkConfigSupplier.config.getMinGasPrice());
    private GasLimit gasLimit = GasLimit.fromNumber(ErdNetworkConfigSupplier.config.getMinGasLimit());
    private PayloadData payloadData = PayloadData.empty();
    private TransactionVersion version = TransactionVersion.withDefaultVersion();
    private Signature signature = Signature.empty();
    private Hash hash = Hash.empty();
    private TransactionStatus status = TransactionStatus.UNKNOWN;

    @Override
    public byte[] serializeForSigning() throws JsonProcessingException {
        return JsonMapper.serializeToJsonBuffer(toSendable());
    }

    @Override
    public void applySignature(Signature signature) {
        setSignature(signature);
    }

    /**
     * Method use to create a Sendable representation of the transaction
     * used for submitting send/sendMultiple/simulate requests to the TransactionInteractor
     *
     * @return - instance of SendableTransaction
     */
    public SendableTransaction toSendable() {
        return generateSender(false);
    }

    /**
     * Method use to create a Sendable representation of the transaction
     * used for submitting estimate request to the TransactionInteractor
     *
     * @return - instance of SendableTransaction
     */
    public SendableTransaction toSendableForEstimation() {
        return generateSender(true);
    }

    /**
     * Setter
     * Used for setting PayloadData
     * and also computing GasCost for the data
     *
     * @param payloadData - data input value
     */
    public void setPayloadData(PayloadData payloadData) {
        this.payloadData = payloadData;
        this.gasLimit = GasLimit.fromNumber(computeGasCost(payloadData));
    }

    private SendableTransaction generateSender(boolean isEstimation) {
        var sendableBuilder = SendableTransaction.builder()
                .chainId(chainID.getValue())
                .nonce(nonce.getValue())
                .value(value.toString())
                .receiver(receiver.getBech32())
                .sender(sender.getBech32())
                .data(payloadData.isEmpty() ? null : payloadData.encoded())
                .signature(signature.isEmpty() ? null : signature.getHex())
                .version(version.getValue());
        if (!isEstimation) {
            sendableBuilder.gasLimit(gasLimit.getValue());
            sendableBuilder.gasPrice(gasPrice.getValue());
        }

        return sendableBuilder.build();
    }

}
