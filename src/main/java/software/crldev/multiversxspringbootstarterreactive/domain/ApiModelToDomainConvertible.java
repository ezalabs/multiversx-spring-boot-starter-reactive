package software.crldev.multiversxspringbootstarterreactive.domain;

/**
 * An Interface describing the behaviour of a API object model converting into a domain object
 *
 * @param <T> - domain object type
 * @author carlo_stanciu
 */
public interface ApiModelToDomainConvertible<T> {

  /**
   * Method used to map API object model to a domain object
   *
   * @return - T domain object
   */
  T toDomainObject();

}
