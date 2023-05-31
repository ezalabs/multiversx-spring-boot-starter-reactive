package software.crldev.multiversxspringbootstarterreactive.domain.esdt.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.ESDTGlobalOp;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.ESDTGlobalOp.Type;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;

class ESDTGlobalOpTest {

  private static final Wallet wallet = Wallet.fromPrivateKeyHex(
      "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");
  private static final Address targetAddress = Address.fromBech32(
      "erd1gklqdv77my5y8n75hszv737gq54q9xk0tmzdh8v5vkfstd64aw7ser9nfr");
  private final TokenIdentifier identifier = TokenIdentifier.fromString("YMY-8888aa");

  @Test
  void pause() {
    var pausing = builder()
        .type(Type.PAUSE)
        .build();

    var req1 = pausing.toTransactionRequest(wallet);

    assertEquals(Address.fromBech32(ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS).getBech32(),
        req1.getReceiverAddress().getBech32());
    assertEquals(GasLimit.defaultEsdtGlobalOp().getValue(), req1.getGasLimit().getValue());
    assertTrue(req1.getValue().isZero());

    var dataArgs1 = req1.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_PAUSE_CALL, dataArgs1[0]);
    assertEquals(identifier.getHex(), dataArgs1[1]);
    assertEquals(2, dataArgs1.length);

    var unpausing = builder()
        .type(Type.UNPAUSE)
        .targetAddress(targetAddress)
        .build();

    var req2 = unpausing.toTransactionRequest(wallet);
    var dataArgs2 = req2.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_UNPAUSE_CALL, dataArgs2[0]);
  }

  @Test
  void freeze() {
    var freezing = builder()
        .type(Type.FREEZE)
        .targetAddress(targetAddress)
        .build();

    var req1 = freezing.toTransactionRequest(wallet);

    var dataArgs1 = req1.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_FREEZE_CALL, dataArgs1[0]);
    assertEquals(identifier.getHex(), dataArgs1[1]);
    assertEquals(targetAddress.getHex(), dataArgs1[2]);
    assertEquals(3, dataArgs1.length);

    var unfreezing = builder()
        .type(Type.UNFREEZE)
        .targetAddress(targetAddress)
        .build();

    var req2 = unfreezing.toTransactionRequest(wallet);
    var dataArgs2 = req2.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_UNFREEZE_CALL, dataArgs2[0]);
  }

  @Test
  void wipe() {
    var wiping = builder()
        .type(Type.WIPE)
        .targetAddress(targetAddress)
        .build();

    var req1 = wiping.toTransactionRequest(wallet);

    var dataArgs1 = req1.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_WIPE_CALL, dataArgs1[0]);
    assertEquals(identifier.getHex(), dataArgs1[1]);
    assertEquals(targetAddress.getHex(), dataArgs1[2]);
    assertEquals(3, dataArgs1.length);
  }

  @Test
  void errorWithoutAddress() {
    var freeze = builder().type(Type.FREEZE).build();
    var unfreeze = builder().type(Type.UNFREEZE).build();
    var wipe = builder().type(Type.WIPE).build();

    assertThrows(IllegalArgumentException.class, () -> freeze.toTransactionRequest(wallet));
    assertThrows(IllegalArgumentException.class, () -> unfreeze.toTransactionRequest(wallet));
    assertThrows(IllegalArgumentException.class, () -> wipe.toTransactionRequest(wallet));
  }

  private ESDTGlobalOp.ESDTGlobalOpBuilder builder() {
    return ESDTGlobalOp.builder()
        .tokenIdentifier(identifier);

  }

}