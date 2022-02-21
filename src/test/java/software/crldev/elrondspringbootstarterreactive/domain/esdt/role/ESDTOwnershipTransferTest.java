package software.crldev.elrondspringbootstarterreactive.domain.esdt.role;

import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTOwnershipTransfer;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ESDTOwnershipTransferTest {

    private static final Wallet wallet = Wallet.fromPrivateKeyHex("8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");
    private static final Address targetAddress = Address.fromBech32("erd1gklqdv77my5y8n75hszv737gq54q9xk0tmzdh8v5vkfstd64aw7ser9nfr");
    private final TokenIdentifier identifier = TokenIdentifier.fromString("YMY-8888aa");

    @Test
    void transferOwnership() {
        var transfer = ESDTOwnershipTransfer.builder()
                .tokenIdentifier(identifier)
                .targetAddress(targetAddress)
                .build();

        var req = transfer.toTransactionRequest(wallet);

        assertEquals(Address.fromBech32(ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS).getBech32(), req.getReceiverAddress().getBech32());
        assertEquals(GasLimit.defaultEsdtIssuance().getValue(), req.getGasLimit().getValue());
        assertTrue(req.getValue().isZero());

        var dataArgs = req.getData().toString().split("@");
        assertEquals(ESDTConstants.ESDT_TRANSFER_OWNERSHIP_CALL, dataArgs[0]);
        assertEquals(identifier.getHex(), dataArgs[1]);
        assertEquals(targetAddress.getHex(), dataArgs[2]);
        assertEquals(3, dataArgs.length);
    }

}