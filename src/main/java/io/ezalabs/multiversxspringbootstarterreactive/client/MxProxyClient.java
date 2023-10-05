package io.ezalabs.multiversxspringbootstarterreactive.client;

import reactor.core.publisher.Mono;

/**
 * Proxy client used for interaction with MultiversX Gateway
 *
 * @author carlo_stanciu
 */
public interface MxProxyClient {

  /**
   * Used for GET HTTP method
   *
   * @param uri          - resource URI
   * @param responseType - response class type object type for deserialization
   * @param <T>          - response object
   * @return - instance of response object T
   */
  <T> Mono<T> get(String uri, Class<T> responseType);

  /**
   * Used for POST HTTP method
   *
   * @param uri          - resource URI
   * @param payload      - request body
   * @param responseType - response class type object type for deserialization
   * @param <T>          - response object
   * @param <P>          - payload object
   * @return - instance of response object T
   */
  <T, P> Mono<T> post(String uri, P payload, Class<T> responseType);

}
