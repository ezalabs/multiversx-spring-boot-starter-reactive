package software.crldev.multiversxspringbootstarterreactive.interactor.block;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import software.crldev.multiversxspringbootstarterreactive.api.model.Hyperblock;
import software.crldev.multiversxspringbootstarterreactive.api.model.ShardBlock;
import software.crldev.multiversxspringbootstarterreactive.client.MxProxyClient;
import software.crldev.multiversxspringbootstarterreactive.interactor.Helper;
import software.crldev.multiversxspringbootstarterreactive.interactor.WrappedResponses;

@ExtendWith(MockitoExtension.class)
class MxBlockInteractorTest {

  MxBlockInteractor blockInteractor;

  @Mock
  MxProxyClient client;

  Long nonce = 10L;
  String hash = "af32f23fijo209j903f";

  @BeforeEach
  void setUp() {
    blockInteractor = new MxBlockInteractorImpl(client);
  }

  @Test
  void queryHyperblock_byNonce_and_byHash() {
    var apiResponse = WrappedResponses.QueryHyperblock
        .builder()
        .hyperblock(Hyperblock
            .builder()
            .nonce(nonce)
            .hash(hash)
            .build())
        .build();

    Helper.verifyInteractionOk(
        client,
        apiResponse,
        () -> blockInteractor.queryHyperblockByNonce(nonce),
        (r) -> {
          assertEquals(nonce, r.getNonce());
          assertEquals(hash, r.getHash());
        }, HttpMethod.GET);

    Helper.verifyInteractionOk(
        client,
        apiResponse,
        () -> blockInteractor.queryHyperblockByHash(hash),
        (r) -> {
          assertEquals(nonce, r.getNonce());
          assertEquals(hash, r.getHash());
        }, HttpMethod.GET);
  }

  @Test
  void queryShardBlock_byNonce_and_byHash() {
    var apiResponse = WrappedResponses.QueryBlock
        .builder()
        .block(ShardBlock
            .builder()
            .nonce(nonce)
            .hash(hash)
            .build())
        .build();

    Helper.verifyInteractionOk(
        client,
        apiResponse,
        () -> blockInteractor.queryShardBlockByNonceFromShard(1, nonce),
        (r) -> {
          assertEquals(nonce, r.getNonce());
          assertEquals(hash, r.getHash());
        }, HttpMethod.GET);

    Helper.verifyInteractionOk(
        client,
        apiResponse,
        () -> blockInteractor.queryShardBlockByHashFromShard(1, hash),
        (r) -> {
          assertEquals(nonce, r.getNonce());
          assertEquals(hash, r.getHash());
        }, HttpMethod.GET);
  }

}