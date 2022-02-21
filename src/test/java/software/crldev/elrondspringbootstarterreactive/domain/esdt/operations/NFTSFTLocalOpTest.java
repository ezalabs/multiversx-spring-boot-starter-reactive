package software.crldev.elrondspringbootstarterreactive.domain.esdt.operations;

import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.NFTSFTLocalOp;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NFTSFTLocalOpTest {

    private static final Wallet wallet = Wallet.fromPrivateKeyHex("8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");
    private final TokenIdentifier identifier = TokenIdentifier.fromString("YMY-8888aa");
    private final Balance amount = Balance.fromNumber(BigInteger.valueOf(500));
    private final Nonce nonce = Nonce.fromLong(2L);

    @Test
    void mint() {
        var minting = builder()
                .type(NFTSFTLocalOp.Type.ADD)
                .build();

        var request = minting.toTransactionRequest(wallet);

        assertEquals(wallet.getAddress().getBech32(), request.getReceiverAddress().getBech32());
        assertEquals(GasLimit.defaultNftCreate().getValue(), request.getGasLimit().getValue());
        assertTrue(request.getValue().isZero());

        var dataArgs = request.getData().toString().split("@");
        assertEquals(ESDTConstants.ESDT_NFTSFT_ADD_CALL, dataArgs[0]);
        assertEquals(identifier.getHex(), dataArgs[1]);
        assertEquals(nonce.getHex(), dataArgs[2]);
        assertEquals(amount.getHex(), dataArgs[3]);
    }

    @Test
    void burn() {
        var burning = builder()
                .type(NFTSFTLocalOp.Type.BURN)
                .build();

        var request = burning.toTransactionRequest(wallet);

        var dataArgs = request.getData().toString().split("@");
        assertEquals(ESDTConstants.ESDT_NFTSFT_BURN_CALL, dataArgs[0]);
    }

    private NFTSFTLocalOp.NFTSFTLocalOpBuilder builder() {
        return NFTSFTLocalOp.builder()
                .amount(amount)
                .nonce(nonce)
                .tokenIdentifier(identifier);
    }

}