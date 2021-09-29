package software.crldev.elrondspringbootstarterreactive.interactor.account;

import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.interactor.Helper;
import software.crldev.elrondspringbootstarterreactive.interactor.WrappedResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import static software.crldev.elrondspringbootstarterreactive.config.CurrencyConstants.ONE_EGLD_STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ErdAccountInteractorTest {

    ErdAccountInteractor accountInteractor;

    @Mock
    ErdProxyClient client;
    Address address;

    @BeforeEach
    void setUp() {
        address = Address.fromBech32("erd1h7r2m9c250yncguz3zwq5na6gu5ttwz3vdx40nxkthxaak5v3wcqtpkvkj");
        accountInteractor = new ErdAccountInteractorImpl(client);
    }

    @Test
    void getAccount() {
        var addressBech32 = address.getBech32();
        var balance = new BigInteger(ONE_EGLD_STRING);
        var nonce = 10L;

        var apiResponse = WrappedResponses.GetAccountWrapper
                .builder()
                .account(AccountOnNetwork
                        .builder()
                        .address(addressBech32)
                        .balance(balance)
                        .nonce(nonce)
                        .build())
                .build();

        Helper.verifyInteractionOk(
                client,
                apiResponse,
                () -> accountInteractor.getAccountInfo(address),
                (r) -> {
                    assertEquals(addressBech32, r.getAddress());
                    assertEquals(balance, r.getBalance());
                    assertEquals(nonce, r.getNonce());
                }, HttpMethod.GET);
    }

    @Test
    void getBalance() {
        var balance = new BigInteger(ONE_EGLD_STRING);

        var apiResponse = AddressBalance
                .builder().balance(balance).build();

        Helper.verifyInteractionOk(
                client,
                apiResponse,
                () -> accountInteractor.getBalance(address),
                (r) -> assertEquals(balance, r.getBalance()),
                HttpMethod.GET);
    }

    @Test
    void getNonce() {
        var nonce = 10L;

        var apiResponse = AddressNonce
                .builder().nonce(nonce).build();

        Helper.verifyInteractionOk(
                client,
                apiResponse,
                () -> accountInteractor.getNonce(address),
                (r) -> assertEquals(nonce, r.getNonce()), HttpMethod.GET);
    }

    @Test
    void getTransactions() {
        var data1 = "crldev";
        var data2 = "elrond";

        var apiResponse = WrappedResponses.GetAccountTransactionsWrapper
                .builder()
                .list(List.of(
                        TransactionForAddress
                                .builder()
                                .data(data1)
                                .build(),
                        TransactionForAddress
                                .builder()
                                .data(data2)
                                .build()
                ))
                .build();

        Helper.verifyInteractionOk(
                client,
                apiResponse,
                () -> accountInteractor.getTransactions(address),
                (r) -> {
                    assertEquals(data1, r.get(0).getData());
                    assertEquals(data2, r.get(1).getData());
                }, HttpMethod.GET);
    }

    @Test
    void getStorageValue() {
        var storageValue = "btc9032032";

        var apiResponse = AddressStorageValue
                .builder().value(storageValue).build();

        Helper.verifyInteractionOk(
                client,
                apiResponse,
                () -> accountInteractor.getStorageValue(address, "bitcoinAddress"),
                (r) -> assertEquals(storageValue, r.getValue()), HttpMethod.GET);
    }

    @Test
    void getAllStorage() {
        var key1 = "ethAddress";
        var key2 = "btcAddress";
        var value1 = "eth230923";
        var value2 = "btc092332";

        var storage = new HashMap<String, String>();
        storage.put(key1, value1);
        storage.put(key2, value2);

        var apiResponse = WrappedResponses.GetAllAccountStorageWrapper
                .builder().pairs(storage).build();

        Helper.verifyInteractionOk(
                client,
                apiResponse,
                () -> accountInteractor.getStorage(address),
                (r) -> {
                    assertEquals(value1, r.get(key1));
                    assertEquals(value2, r.get(key2));
                }, HttpMethod.GET);
    }

}