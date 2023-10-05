package io.ezalabs.multiversxspringbootstarterreactive.interactor.esdt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import io.ezalabs.multiversxspringbootstarterreactive.api.model.AccountESDTRoles;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResult;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ESDTToken;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.NFTData;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.TokenList;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.TransactionHash;
import io.ezalabs.multiversxspringbootstarterreactive.client.MxProxyClient;
import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Nonce;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.ESDTQueryType;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.ESDTTransaction;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.ContractQuery;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.Helper;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.WrappedResponses;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.smartcontract.MxSmartContractInteractor;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.MxTransactionInteractor;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

@ExtendWith(MockitoExtension.class)
class MxESDTInteractorTest {

  MxESDTInteractor interactor;

  @Mock
  MxTransactionInteractor tInteractor;
  @Mock
  MxSmartContractInteractor scInteractor;
  @Mock
  MxProxyClient client;

  private static final Address address = Address.fromBech32(
      "erd1h7r2m9c250yncguz3zwq5na6gu5ttwz3vdx40nxkthxaak5v3wcqtpkvkj");
  private static final Wallet wallet = Wallet.fromPrivateKeyHex(
      "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");

  @BeforeEach
  void setUp() {
    interactor = new MxESDTInteractorImpl(client, tInteractor, scInteractor);
  }

  @Test
  void processEsdtTransaction() {
    var transaction = Mockito.mock(ESDTTransaction.class);
    var request = Mockito.mock(TransactionRequest.class);
    when(transaction.toTransactionRequest(wallet)).thenReturn(request);

    var hash = "699ae03e6f9a18cb8b1f131b061a46a8b7dd96dfa3fe24861f03aa824a462920";

    when(tInteractor.sendTransaction(eq(wallet), eq(request)))
        .thenReturn(Mono.just(TransactionHash.builder().hash(hash).build()));

    StepVerifier.create(interactor.processEsdtTransaction(wallet, transaction))
        .assertNext(r -> assertEquals(r.getHash(), hash))
        .verifyComplete();
  }

  @Test
  void getEsdtForAddress() {
    var token1 = ESDTToken.builder()
        .identifier("TKN1-93083a")
        .balance("1000").build();
    var token2 = ESDTToken.builder()
        .identifier("MKN2-53083a")
        .balance("2000").build();

    var esdts = new HashMap<String, ESDTToken>();
    esdts.put("1", token1);
    esdts.put("2", token2);

    var apiResponse = WrappedResponses.AccountESDTsResponse
        .builder().result(esdts).build();

    Helper.verifyInteractionOk(
        client,
        apiResponse,
        () -> interactor.getTokensForAccount(address),
        (r) -> {
          assertTrue(r.contains(token1));
          assertTrue(r.contains(token2));
        }, HttpMethod.GET);
  }

  @Test
  void getEsdtRolesForAddress() {
    var token1 = "TKN1-93083a";
    var token2 = "MKN2-53083a";
    var role1 = "ESDTRoleLocalBurn";
    var role2 = "ESDTRoleNFTAddQuantity";

    var result = new HashMap<String, Set<String>>();
    result.put(token1, Set.of(role1));
    result.put(token2, Set.of(role2));

    var apiResponse = AccountESDTRoles.builder().roles(result).build();

    Helper.verifyInteractionOk(
        client,
        apiResponse,
        () -> interactor.getTokenRolesForAccount(address),
        (r) -> {
          assertEquals(Set.of(role1), r.getRoles().get(token1));
          assertEquals(Set.of(role2), r.getRoles().get(token2));
        }, HttpMethod.GET);
  }

  @Test
  void getTokens() {
    var esdt = "TKN1-93083a";
    var nft = "NFT2-52083b";
    var sft = "SFT2-53063a";

    var esdtList = Set.of(esdt);
    var nftList = Set.of(nft);
    var sftList = Set.of(sft);
    var allList = new HashSet<String>();
    allList.addAll(esdtList);
    allList.addAll(nftList);
    allList.addAll(sftList);

    Helper.verifyInteractionOk(
        client,
        TokenList.builder().tokens(esdtList).build(),
        () -> interactor.getAllTokens(ESDTQueryType.FUNGIBLE),
        (r) -> assertTrue(r.getTokens().contains(esdt)),
        HttpMethod.GET);

    Helper.verifyInteractionOk(
        client,
        TokenList.builder().tokens(nftList).build(),
        () -> interactor.getAllTokens(ESDTQueryType.NON_FUNGIBLE),
        (r) -> assertTrue(r.getTokens().contains(nft)),
        HttpMethod.GET);

    Helper.verifyInteractionOk(
        client,
        TokenList.builder().tokens(sftList).build(),
        () -> interactor.getAllTokens(ESDTQueryType.SEMI_FUNGIBLE),
        (r) -> assertTrue(r.getTokens().contains(sft)),
        HttpMethod.GET);

    Helper.verifyInteractionOk(
        client,
        TokenList.builder().tokens(allList).build(),
        () -> interactor.getAllTokens(ESDTQueryType.ALL),
        (r) -> {
          assertTrue(r.getTokens().contains(esdt));
          assertTrue(r.getTokens().contains(nft));
          assertTrue(r.getTokens().contains(sft));
        },
        HttpMethod.GET);
  }

  @Test
  void getProperties() {
    testGetProps(CallType.PROPS);
  }

  @Test
  void getRoles() {
    testGetProps(CallType.ROLES);
  }

  @Test
  void getNftData() {
    var nftData = NFTData.builder()
        .creatorAddress(address.getBech32())
        .nonce(3L)
        .build();

    var apiResponse = WrappedResponses.NFTDataResponse
        .builder().result(nftData).build();

    Helper.verifyInteractionOk(
        client,
        apiResponse,
        () -> interactor.getNftDataForAccount(address,
            TokenIdentifier.fromString("KN1-93083a"), Nonce.fromLong(3L)),
        (r) -> {
          assertEquals(nftData.getCreatorAddress(), r.getCreatorAddress());
          assertEquals(nftData.getNonce(), r.getNonce());
        }, HttpMethod.GET);
  }

  @Test
  void getNfts() {
    var nft = "NFT2-52083b";
    var sft = "SFT2-53063a";

    Helper.verifyInteractionOk(
        client,
        TokenList.builder().tokens(Set.of(nft, sft)).build(),
        () -> interactor.getNftSftForAccount(wallet.getAddress()),
        (r) -> {
          assertTrue(r.getTokens().contains(nft));
          assertTrue(r.getTokens().contains(sft));
        },
        HttpMethod.GET);
  }

  @Test
  void getTokensWithRole() {
    var nft = "NFT2-52083b";
    var sft = "SFT2-53063a";

    Helper.verifyInteractionOk(
        client,
        TokenList.builder().tokens(Set.of(nft, sft)).build(),
        () -> interactor.getTokensWithRole(wallet.getAddress(), ESDTSpecialRole.ESDT_LOCAL_BURN),
        (r) -> {
          assertTrue(r.getTokens().contains(nft));
          assertTrue(r.getTokens().contains(sft));
        },
        HttpMethod.GET);
  }

  private void testGetProps(CallType type) {
    var queryResponse = ContractQueryResult.builder()
        .data(List.of("prop1", "prop2"))
        .code("ok")
        .build();

    var queryCaptor = ArgumentCaptor.forClass(ContractQuery.class);

    when(scInteractor.query(queryCaptor.capture())).thenReturn(Mono.just(queryResponse));

    var call = type.equals(CallType.PROPS) ?
        interactor.getTokenProperties(TokenIdentifier.fromString("TKN1-93083a"))
        :
            interactor.getTokenSpecialRoles(TokenIdentifier.fromString("TKN1-93083a"));

    var method = type.equals(CallType.PROPS) ?
        ESDTConstants.ESDT_GET_PROPERTIES_CALL
        :
            ESDTConstants.ESDT_GET_SPECIAL_ROLES_CALL;

    StepVerifier.create(call, 1)
        .assertNext(r -> {
          var query = queryCaptor.getValue();

          assertEquals(ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS,
              query.getSmartContractAddress().getBech32());
          assertEquals(method, query.getFunctionName().getValue());

          assertEquals(2, r.getData().size());
        })
        .verifyComplete();
  }

  enum CallType {PROPS, ROLES}

}