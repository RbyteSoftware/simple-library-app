package ru.simplelib.library.controller.converters;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Bidirectional object converter interface
 *
 * @param <Domain> generic for Domain objects
 * @param <Dto>    generic for Data transfer objects
 * @author Mikhail Yuzbashev
 */
public interface Converter<Domain, Dto> {
    /**
     * Method implementation should be convert Domain to Dto object
     *
     * @param domain Domain object
     * @return Result of convert Domain to Dto
     */
    Dto from(Domain domain);

    /**
     * Method implementation should be convert Dto to Domain object
     *
     * @param dto Dto object
     * @return Result of convert Dto to Domain
     */
    Domain to(Dto dto);

    default List<Dto> fromList(List<Domain> domainList) {
        if (Objects.nonNull(domainList))
            return domainList.stream().map(this::from).collect(Collectors.toList());
        return Collections.emptyList();
    }

    default List<Domain> toList(List<Dto> domainList) {
        if (Objects.nonNull(domainList))
            return domainList.stream().map(this::to).collect(Collectors.toList());
        return Collections.emptyList();
    }
}
