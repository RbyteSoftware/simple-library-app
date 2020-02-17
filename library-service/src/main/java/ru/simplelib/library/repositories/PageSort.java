package ru.simplelib.library.repositories;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * Utility for extracting pages and apply sorting implements by basic Java comparators
 *
 * @author Mikhail Yuzbashev
 */
public class PageSort {

    public static <T> List<T> by(List<T> sourceList, Pageable pageable) {
        PagedListHolder<T> pagedListHolder = new PagedListHolder<>(sourceList);
        Optional<Sort.Order> orderOpt = pageable.getSort().get().findFirst();
        if (orderOpt.isPresent()) {
            pagedListHolder.setSort(new MutableSortDefinition(orderOpt.get().getProperty(), false, orderOpt.get().isAscending()));
            pagedListHolder.resort();
        }
        pagedListHolder.setPageSize(pageable.getPageSize());
        pagedListHolder.setPage(pageable.getPageNumber());
        return pagedListHolder.getPageList();
    }

}
