package ru.simplelib.library.service.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;
import java.util.Optional;

public class PageRequestBuilder {
    private Integer rowsOnPage;
    private Integer pageNum;
    private String sortField;
    private String sortDirection;

    interface RangeMapper {
        Integer onPage();

        Integer pageNum();
    }

    interface SortMapper {
        String getSortField();

        String getSortDirection();
    }

    public PageRequestBuilder setDefaultRowOnPage(Integer rows) {
        this.rowsOnPage = rows;
        return this;
    }

    public PageRequestBuilder setRange(RangeMapper rangeMapper) {
        this.rowsOnPage = rangeMapper.onPage();
        this.pageNum = rangeMapper.pageNum();
        return this;
    }

    public PageRequestBuilder setSort(SortMapper sortMapper) {
        this.sortField = sortMapper.getSortField();
        this.sortDirection = sortMapper.getSortDirection();
        return this;
    }

    public Pageable build() {
        int pageNum = Objects.nonNull(this.pageNum) ? this.pageNum : 0;
        int pageSize = this.rowsOnPage;
        if (Objects.nonNull(sortField)) {
            Optional<Sort.Direction> directionOptional = Sort.Direction.fromOptionalString(sortDirection);
            return PageRequest.of(pageNum, pageSize, Sort.by(
                    directionOptional.orElse(Sort.Direction.ASC),
                    sortField));
        }
        return PageRequest.of(pageNum, pageSize);
    }

    public static class ArrayRangeMapper implements RangeMapper {
        private Integer onPage;
        private Integer pageNum;

        public ArrayRangeMapper(String[] range) {
            if (range.length < 2)
                throw new IllegalArgumentException("Wrong array consistency");
            this.onPage = Integer.valueOf(range[0]);
            this.pageNum = Integer.valueOf(range[1]);
        }

        @Override
        public Integer onPage() {
            return this.onPage;
        }

        @Override
        public Integer pageNum() {
            return this.pageNum;
        }
    }

    public static class ArraySortMapper implements SortMapper {
        private String sortField;
        private String sortDirection;

        public ArraySortMapper(String[] sort) {
            if (sort.length < 2)
                throw new IllegalArgumentException("Wrong array consistency");
            this.sortField = sort[0];
            this.sortDirection = sort[1];
        }

        @Override
        public String getSortField() {
            return sortField;
        }

        @Override
        public String getSortDirection() {
            return sortDirection;
        }
    }

    public static class BasicRangeMapper implements RangeMapper {
        private Integer onPage;
        private Integer pageNum;

        public BasicRangeMapper(Integer onPage, Integer pageNum) {
            this.onPage = onPage;
            this.pageNum = pageNum;
        }

        @Override
        public Integer onPage() {
            return this.onPage;
        }

        @Override
        public Integer pageNum() {
            return this.pageNum;
        }
    }

    public static class BasicSortMapper implements SortMapper {
        private String sortField;
        private String sortDirection;

        public BasicSortMapper(String sortField, String sortDirection) {
            this.sortField = sortField;
            this.sortDirection = sortDirection;
        }

        @Override
        public String getSortField() {
            return sortField;
        }

        @Override
        public String getSortDirection() {
            return sortDirection;
        }
    }
}
