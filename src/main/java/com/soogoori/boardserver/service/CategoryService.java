package com.soogoori.boardserver.service;

import com.soogoori.boardserver.dto.CategoryDto;

public interface CategoryService {

    void register(String accountId, CategoryDto categoryDto);

    void update(CategoryDto categoryDto);

    void delete(int categoryId);
}
