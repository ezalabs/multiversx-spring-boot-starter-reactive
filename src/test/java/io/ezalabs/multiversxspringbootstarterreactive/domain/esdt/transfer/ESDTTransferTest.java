package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.Test;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.ESDTTransfer;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.FunctionArg;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.FunctionName;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;

class ESDTTransferTest {

  private static final Wallet wallet = Wallet.fromPrivateKeyHex(
      "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");

  @Test
  void transferWithoutScCall() {
    var transferWithoutScCall = transferBuilder().build();
    var req1 = transferWithoutScCall.toTransactionRequest(wallet);

    assertEquals("ESDTTransfer@4d4f4a4f2d6a3239393030@0a", req1.getData().toString());
  }

  @Test
  void transferWithFunction() {
    var transferWithFunction = transferBuilder()
        .functionName(FunctionName.fromString("call"))
        .build();
    var req2 = transferWithFunction.toTransactionRequest(wallet);

    assertEquals("ESDTTransfer@4d4f4a4f2d6a3239393030@0a@63616c6c", req2.getData().toString());
  }

  @Test
  void transferWithFunctionAndArgs() {
    var transferWithFunctionAndArgs = transferBuilder()
        .functionName(FunctionName.fromString("call"))
        .args(List.of(FunctionArg.fromString("baby")))
        .build();
    var req3 = transferWithFunctionAndArgs.toTransactionRequest(wallet);

    assertEquals("ESDTTransfer@4d4f4a4f2d6a3239393030@0a@63616c6c@62616279", req3.getData().toString());
  }

  private ESDTTransfer.ESDTTransferBuilder transferBuilder() {
    return ESDTTransfer.builder()
        .receiverAddress(Address.fromBech32("erd1gklqdv77my5y8n75hszv737gq54q9xk0tmzdh8v5vkfstd64aw7ser9nfr"))
        .tokenIdentifier(TokenIdentifier.fromString("MOJO-J29900"))
        .amount(Balance.fromNumber(BigInteger.TEN));
  }
}