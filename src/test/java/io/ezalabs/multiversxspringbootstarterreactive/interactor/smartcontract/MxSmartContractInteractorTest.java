package io.ezalabs.multiversxspringbootstarterreactive.interactor.smartcontract;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static io.ezalabs.multiversxspringbootstarterreactive.interactor.Helper.verifyInteractionOk;

import io.ezalabs.multiversxspringbootstarterreactive.api.ApiResourceURI;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResult;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResultHex;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResultInt;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResultString;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.TransactionHash;
import io.ezalabs.multiversxspringbootstarterreactive.client.MxProxyClient;
import io.ezalabs.multiversxspringbootstarterreactive.config.constants.TransactionConstants;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.ContractFunction;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.ContractQuery;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.FunctionArg;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.FunctionName;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.WrappedResponses;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.MxTransactionInteractor;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

@ExtendWith(MockitoExtension.class)
class MxSmartContractInteractorTest {

  MxSmartContractInteractor interactor;

  @Mock
  MxProxyClient client;
  @Mock
  MxTransactionInteractor tInteractor;

  private static final BigInteger GAS_LIMIT = BigInteger.valueOf(10_000_000_000_000L);

  private static final Address callerAddress = Address.fromBech32(
      "erd1h7r2m9c250yncguz3zwq5na6gu5ttwz3vdx40nxkthxaak5v3wcqtpkvkj");
  private static final Address scAddress = Address.fromBech32(
      "erd1gklqdv77my5y8n75hszv737gq54q9xk0tmzdh8v5vkfstd64aw7ser9nfr");
  private static final Wallet wallet = Wallet.fromPrivateKeyHex(
      "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");

  ContractQuery query = ContractQuery.builder()
      .callerAddress(callerAddress)
      .smartContractAddress(scAddress)
      .functionName(FunctionName.fromString("getValue"))
      .value(Balance.zero())
      .args(List.of(FunctionArg.fromString("first")))
      .build();

  @BeforeEach
  void setUp() {
    interactor = new MxSmartContractInteractorImpl(client, tInteractor);
  }

  @ParameterizedTest
  @MethodSource("functionRequestDataProvider")
  void callFunction(final ContractFunction functionRequest) {
    var hash = "699ae03e6f9a18cb8b1f131b061a46a8b7dd96dfa3fe24861f03aa824a462920";

    var requestCaptor = ArgumentCaptor.forClass(TransactionRequest.class);

    when(tInteractor.sendTransaction(eq(wallet), requestCaptor.capture()))
        .thenReturn(Mono.just(TransactionHash.builder().hash(hash).build()));

    StepVerifier.create(interactor.callFunction(wallet, functionRequest))
        .assertNext(r -> {
          var request = requestCaptor.getValue();

          assertEquals(functionRequest.getSmartContractAddress().getBech32(), request.getReceiverAddress().getBech32());
          assertEquals(getExpectedBalance(functionRequest), request.getValue().getValue());
          assertEquals(getExpectedGasLimit(functionRequest), request.getGasLimit().getValue());
          assertEquals(functionRequest.getPayloadData(), request.getData());
          assertEquals(functionRequest.getFunctionName().getValue(), request.getData().toString().split("@")[0]);

          assertEquals(r.getHash(), hash);
        })
        .verifyComplete();
  }

  @Test
  void query() {
    var apiResponse = WrappedResponses.ScQueryResponse.builder()
        .result(ContractQueryResult.builder()
            .code("code")
            .message("retrieved")
            .data(List.of("value"))
            .build())
        .build();

    verifyInteractionOk(
        client,
        apiResponse,
        () -> interactor.query(query),
        (r) -> {
          assertEquals("code", r.getCode());
          assertEquals("retrieved", r.getMessage());

          verify(client).post(ArgumentMatchers.eq(ApiResourceURI.QUERY_SMART_CONTRACT.getURI()),
              any(), eq(WrappedResponses.ScQueryResponse.class));
        }, HttpMethod.POST);
  }

  @Test
  void queryHex() {
    var apiResponse = ContractQueryResultHex.builder().value("hexValue").build();

    verifyInteractionOk(
        client,
        apiResponse,
        () -> interactor.queryHex(query),
        (r) -> {
          assertEquals(r.getValue(), "hexValue");

          verify(client).post(eq(ApiResourceURI.QUERY_SMART_CONTRACT_HEX.getURI()),
              any(), eq(ContractQueryResultHex.class));
        }, HttpMethod.POST);
  }

  @Test
  void queryString() {
    var apiResponse = ContractQueryResultString.builder().value("stringValue").build();

    verifyInteractionOk(
        client,
        apiResponse,
        () -> interactor.queryString(query),
        (r) -> {
          assertEquals(r.getValue(), "stringValue");

          verify(client).post(eq(ApiResourceURI.QUERY_SMART_CONTRACT_STRING.getURI()),
              any(), eq(ContractQueryResultString.class));
        }, HttpMethod.POST);
  }

  @Test
  void queryInt() {
    var apiResponse = ContractQueryResultInt.builder().value(BigInteger.TEN).build();

    verifyInteractionOk(
        client,
        apiResponse,
        () -> interactor.queryInt(query),
        (r) -> {
          assertEquals(r.getValue(), BigInteger.TEN);

          verify(client).post(eq(ApiResourceURI.QUERY_SMART_CONTRACT_INT.getURI()),
              any(), eq(ContractQueryResultInt.class));
        }, HttpMethod.POST);
  }

  private BigInteger getExpectedGasLimit(final ContractFunction contractFunction) {
    final var gasLimit = contractFunction.getGasLimit();
    return nonNull(gasLimit) ? gasLimit.getValue() : TransactionConstants.SC_CALL_GAS_LIMIT;
  }

  private BigInteger getExpectedBalance(final ContractFunction contractFunction) {
    final var balance = contractFunction.getValue();
    return nonNull(balance) ? balance.getValue() : BigInteger.ZERO;
  }

  private static Stream<Arguments> functionRequestDataProvider() {
    final var scFunction = ContractFunction.builder()
        .smartContractAddress(scAddress)
        .functionName(FunctionName.fromString("delegate"))
        .args(List.of(FunctionArg.fromString(scAddress.getBech32())))
        .build();
    final var gasLimit = GasLimit.fromNumber(GAS_LIMIT);
    return Stream.of(
        Arguments.of(scFunction),
        Arguments.of(scFunction.toBuilder()
            .gasLimit(gasLimit)
            .value(Balance.fromEgld(1.00))
            .build())
    );
  }
}