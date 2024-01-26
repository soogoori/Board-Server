package com.soogoori.boardserver.mapper;

import com.soogoori.boardserver.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    public int register(CategoryDto productDto);

    public void updateCategory(CategoryDto categoryDto);

    public void deleteCategory(int categoryId);
}
