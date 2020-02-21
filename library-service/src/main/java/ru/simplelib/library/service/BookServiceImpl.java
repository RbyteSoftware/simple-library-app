package ru.simplelib.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.simplelib.library.domain.Book;
import ru.simplelib.library.repositories.BooksRepository;
import ru.simplelib.library.repositories.PageSort;
import ru.simplelib.library.service.common.PageRequestBuilder;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Value("${simplelib.default.onPage}")
    private Integer onPage;

    private final
    BooksRepository repository;

    public BookServiceImpl(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long getBookCount() {
        return repository.getCount();
    }

    @Override
    public List<Book> getList(Integer pageNum, Integer perPage, String sortFieldName, String sortDirection) {
        PageRequestBuilder pageRequestBuilder = new PageRequestBuilder()
                .setDefaultRowOnPage(onPage)
                .setRange(new PageRequestBuilder.BasicRangeMapper(perPage, pageNum))
                .setSort(new PageRequestBuilder.BasicSortMapper(sortFieldName, sortDirection));

        return PageSort.by((List<Book>) repository.findAll(), pageRequestBuilder.build());
    }
}
