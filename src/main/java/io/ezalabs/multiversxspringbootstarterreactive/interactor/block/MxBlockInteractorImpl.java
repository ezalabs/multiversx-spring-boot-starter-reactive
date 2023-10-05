package io.ezalabs.multiversxspringbootstarterreactive.interactor.block;

import static java.lang.String.format;

import io.ezalabs.multiversxspringbootstarterreactive.api.ApiResourceURI;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.Hyperblock;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ShardBlock;
import io.ezalabs.multiversxspringbootstarterreactive.client.MxProxyClient;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.WrappedResponses;

@AllArgsConstructor
public class MxBlockInteractorImpl implements MxBlockInteractor {

  private final MxProxyClient client;

  @Override
  public Mono<Hyperblock> queryHyperblockByNonce(Long nonce) {
    return client
        .get(String.format(ApiResourceURI.HYPERBLOCK_BY_NONCE.getURI(), nonce),
            WrappedResponses.QueryHyperblock.class)
        .map(WrappedResponses.QueryHyperblock::getHyperblock);
  }

  @Override
  public Mono<Hyperblock> queryHyperblockByHash(String hash) {
    return client
        .get(format(ApiResourceURI.HYPERBLOCK_BY_HASH.getURI(), hash),
            WrappedResponses.QueryHyperblock.class)
        .map(WrappedResponses.QueryHyperblock::getHyperblock);
  }

  @Override
  public Mono<ShardBlock> queryShardBlockByNonceFromShard(Integer shard, Long nonce) {
    return client
        .get(format(ApiResourceURI.BLOCK_BY_NONCE_FROM_SHARD.getURI(), shard, nonce),
            WrappedResponses.QueryBlock.class)
        .map(WrappedResponses.QueryBlock::getBlock);
  }

  @Override
  public Mono<ShardBlock> queryShardBlockByHashFromShard(Integer shard, String hash) {
    return client
        .get(format(ApiResourceURI.BLOCK_BY_HASH_FROM_SHARD.getURI(), shard, hash),
            WrappedResponses.QueryBlock.class)
        .map(WrappedResponses.QueryBlock::getBlock);
  }

}
