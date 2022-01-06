package software.crldev.elrondspringbootstarterreactive.interactor.smartcontract;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.crldev.elrondspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.config.TransactionConstants;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.FunctionArgs;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.FunctionName;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ScFunction;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ScQuery;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.WrappedResponses;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.ErdTransactionInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static software.crldev.elrondspringbootstarterreactive.interactor.Helper.verifyInteractionOk;

@ExtendWith(MockitoExtension.class)
class ErdSmartContractInteractorTest {

    ErdSmartContractInteractor interactor;

    @Mock
    ErdProxyClient client;
    @Mock
    ErdTransactionInteractor tInteractor;

    Address callerAddress = Address.fromBech32("erd1h7r2m9c250yncguz3zwq5na6gu5ttwz3vdx40nxkthxaak5v3wcqtpkvkj");
    Address scAddress = Address.fromBech32("erd1gklqdv77my5y8n75hszv737gq54q9xk0tmzdh8v5vkfstd64aw7ser9nfr");
    Wallet wallet = Wallet.fromPrivateKeyHex("8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");

    ScQuery query = ScQuery.builder()
            .callerAddress(callerAddress)
            .smartContractAddress(scAddress)
            .functionName(FunctionName.fromString("getValue"))
            .value(Balance.zero())
            .args(FunctionArgs.fromString("first"))
            .build();

    @BeforeEach
    void setUp() {
        interactor = new ErdSmartContractInteractorImpl(client, tInteractor);
    }

    @Test
    void callFunction() {
        var hash = "699ae03e6f9a18cb8b1f131b061a46a8b7dd96dfa3fe24861f03aa824a462920";

        var requestCaptor = ArgumentCaptor.forClass(TransactionRequest.class);

        when(tInteractor.sendTransaction(eq(wallet), requestCaptor.capture()))
                .thenReturn(Mono.just(TransactionHash.builder().hash(hash).build()));

        var functionRequest = ScFunction.builder()
                .smartContractAddress(scAddress)
                .functionName(FunctionName.fromString("delegate"))
                .args(FunctionArgs.fromString(scAddress.getBech32()))
                .value(Balance.zero())
                .build();

        StepVerifier.create(interactor.callFunction(wallet, functionRequest))
                .assertNext(r -> {
                    var request = requestCaptor.getValue();

                    assertEquals(functionRequest.getSmartContractAddress().getBech32(), request.getReceiverAddress().getBech32());
                    assertEquals(functionRequest.getValue(), request.getValue());
                    assertEquals(TransactionConstants.SC_CALL_GAS_LIMIT, request.getGasLimit().getValue());
                    assertEquals(functionRequest.getPayloadData(), request.getData());
                    assertEquals(functionRequest.getFunctionName().getValue(), request.getData().toString().split("@")[0]);

                    assertEquals(r.getHash(), hash);
                })
                .verifyComplete();
    }

    @Test
    void query() {
        var apiResponse = WrappedResponses.ScQueryResponse.builder()
                .result(ScQueryResult.builder()
                        .code("code")
                        .message("retrieved")
                        .data(List.of("value"))
                        .build())
                .build();

        verifyInteractionOk(
                client,
                apiResponse,
                () -> interactor.query(wallet, query),
                (r) -> {
                    assertEquals("code", r.getCode());
                    assertEquals("retrieved", r.getMessage());

                    verify(client).post(eq(ApiResourceURI.QUERY_SMART_CONTRACT.getURI()),
                            any(), eq(WrappedResponses.ScQueryResponse.class));
                }, HttpMethod.POST);
    }

    @Test
    void queryHex() {
        var apiResponse = ScQueryResultHex.builder().value("hexValue").build();

        verifyInteractionOk(
                client,
                apiResponse,
                () -> interactor.queryHex(wallet, query),
                (r) -> {
                    assertEquals(r.getValue(), "hexValue");

                    verify(client).post(eq(ApiResourceURI.QUERY_SMART_CONTRACT_HEX.getURI()),
                            any(), eq(ScQueryResultHex.class));
                }, HttpMethod.POST);
    }

    @Test
    void queryString() {
        var apiResponse = ScQueryResultString.builder().value("stringValue").build();

        verifyInteractionOk(
                client,
                apiResponse,
                () -> interactor.queryString(wallet, query),
                (r) -> {
                    assertEquals(r.getValue(), "stringValue");

                    verify(client).post(eq(ApiResourceURI.QUERY_SMART_CONTRACT_STRING.getURI()),
                            any(), eq(ScQueryResultString.class));
                }, HttpMethod.POST);
    }

    @Test
    void queryInt() {
        var apiResponse = ScQueryResultInt.builder().value(BigInteger.TEN).build();

        verifyInteractionOk(
                client,
                apiResponse,
                () -> interactor.queryInt(wallet, query),
                (r) -> {
                    assertEquals(r.getValue(), BigInteger.TEN);

                    verify(client).post(eq(ApiResourceURI.QUERY_SMART_CONTRACT_INT.getURI()),
                            any(), eq(ScQueryResultInt.class));
                }, HttpMethod.POST);
    }

}