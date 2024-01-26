package com.soogoori.boardserver.service.impl;

import com.soogoori.boardserver.dto.CategoryDto;
import com.soogoori.boardserver.mapper.CategoryMapper;
import com.soogoori.boardserver.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void register(String accountId, CategoryDto categoryDto) {
        if (accountId != null) {
            categoryMapper.register(categoryDto);
        } else {
            log.error("register ERROR! {}", categoryDto);
            throw new RuntimeException("register ERROR! 상품 카테고리 등록 메서드를 확인해주세요\n" + "Params : " + categoryDto);
        }
    }

    @Override
    public void update(CategoryDto categoryDto) {
        if (categoryDto != null) {
            categoryMapper.updateCategory(categoryDto);
        } else {
            log.error("update ERROR! {}", categoryDto);
            throw new RuntimeException("update ERROR! 물품 카테고리 변경 메서드를 확인해주세요\n" + "Params : " + categoryDto);
        }
    }

    @Override
    public void delete(int categoryId) {
        if (categoryId != 0) {
            categoryMapper.deleteCategory(categoryId);
        } else {
            log.error("deleteCategory ERROR! {}", categoryId);
            throw new RuntimeException("deleteCategory ERROR! 물품 카테고리 삭제 메서드를 확인해주세요\n" + "Params : " + categoryId);
        }
    }
}
