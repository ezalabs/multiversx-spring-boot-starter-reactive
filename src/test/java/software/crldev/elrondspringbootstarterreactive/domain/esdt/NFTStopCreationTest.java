package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NFTStopCreationTest {

    private static final Wallet wallet = Wallet.fromPrivateKeyHex("8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");

    @Test
    void transferRole() {
        final var identifier = TokenIdentifier.fromString("YMY-8888aa");

        var stopCreation = NFTStopCreation.builder()
                .tokenIdentifier(identifier)
                .build();

        var req = stopCreation.toTransactionRequest(wallet);

        assertEquals(Address.fromBech32(ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS).getBech32(), req.getReceiverAddress().getBech32());
        assertEquals(GasLimit.defaultEsdtIssuance().getValue(), req.getGasLimit().getValue());
        assertTrue(req.getValue().isZero());

        var dataArgs = req.getData().toString().split("@");
        assertEquals(ESDTConstants.ESDT_NFT_STOP_CREATION_CALL, dataArgs[0]);
        assertEquals(identifier.getHex(), dataArgs[1]);
    }

}