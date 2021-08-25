package software.crldev.elrondspringbootstarterreactive.domain;

/**
 * An Interface describing the behaviour
 * of a API object model converting into a domain object
 *
 * @author carlo_stanciu
 *
 * @param <T> - domain object type
 */
public interface ApiModelToDomainConvertible<T> {

    /**
     * Method used to map API object model to a domain object
     *
     * @return - T domain object
     */
    T toDomainObject();

}
