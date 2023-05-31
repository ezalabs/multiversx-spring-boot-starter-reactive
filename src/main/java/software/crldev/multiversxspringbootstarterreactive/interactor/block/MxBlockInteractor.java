package software.crldev.multiversxspringbootstarterreactive.interactor.block;

import reactor.core.publisher.Mono;
import software.crldev.multiversxspringbootstarterreactive.api.model.Hyperblock;
import software.crldev.multiversxspringbootstarterreactive.api.model.ShardBlock;

/**
 * Interface used for interaction with hyperblocks / shard blocks on the network
 *
 * @author carlo_stanciu
 */
public interface MxBlockInteractor {

  /**
   * Method used to query Hyperblock information by nonce
   *
   * @param nonce - value used as query parameter
   * @return - Hyperblock API response
   */
  Mono<Hyperblock> queryHyperblockByNonce(Long nonce);

  /**
   * Method used to query Hyperblock information by hash
   *
   * @param hash - value used as query parameter
   * @return - Hyperblock API response
   */
  Mono<Hyperblock> queryHyperblockByHash(String hash);

  /**
   * Method used to query ShardBlock information by shard and nonce
   *
   * @param shard - value used as query parameter
   * @param nonce - value used as query parameter
   * @return - ShardBlock API response
   */
  Mono<ShardBlock> queryShardBlockByNonceFromShard(Integer shard, Long nonce);

  /**
   * Method used to query ShardBlock information by shard and hash
   *
   * @param shard - value used as query parameter
   * @param hash  - value used as query parameter
   * @return - ShardBlock API response
   */
  Mono<ShardBlock> queryShardBlockByHashFromShard(Integer shard, String hash);

}
