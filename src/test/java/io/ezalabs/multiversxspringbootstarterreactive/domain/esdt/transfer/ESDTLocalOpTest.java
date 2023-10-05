package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.ESDTLocalOp;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;

class ESDTLocalOpTest {

  private static final Wallet wallet = Wallet.fromPrivateKeyHex(
      "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");
  private final TokenIdentifier identifier = TokenIdentifier.fromString("YMY-8888aa");
  private final Balance amount = Balance.fromNumber(BigInteger.valueOf(500));

  @Test
  void mint() {
    var minting = builder()
        .type(ESDTLocalOp.Type.MINT)
        .build();

    var request = minting.toTransactionRequest(wallet);

    assertEquals(wallet.getAddress().getBech32(), request.getReceiverAddress().getBech32());
    assertEquals(GasLimit.defaultEsdtLocalOp().getValue(), request.getGasLimit().getValue());
    assertTrue(request.getValue().isZero());

    var dataArgs = request.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_MINT_CALL, dataArgs[0]);
    assertEquals(identifier.getHex(), dataArgs[1]);
    assertEquals(amount.getHex(), dataArgs[2]);
  }

  @Test
  void burn() {
    var burning = builder()
        .type(ESDTLocalOp.Type.BURN)
        .build();

    var request = burning.toTransactionRequest(wallet);

    var dataArgs = request.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_BURN_CALL, dataArgs[0]);
  }

  private ESDTLocalOp.ESDTLocalOpBuilder builder() {
    return ESDTLocalOp.builder()
        .amount(amount)
        .tokenIdentifier(identifier);
  }

}