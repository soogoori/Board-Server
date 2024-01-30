package com.soogoori.boardserver.service.impl;

import com.soogoori.boardserver.dto.CategoryDto;
import com.soogoori.boardserver.exception.BoardServerException;
import com.soogoori.boardserver.mapper.CategoryMapper;
import com.soogoori.boardserver.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void register(String accountId, CategoryDto categoryDto) {
        if (accountId != null) {
            try {
                categoryMapper.register(categoryDto);
            } catch (RuntimeException e) {
                log.error("register 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("register ERROR! {}", categoryDto);
            throw new RuntimeException("register ERROR! 상품 카테고리 등록 메서드를 확인해주세요\n" + "Params : " + categoryDto);
        }
    }

    @Override
    public void update(CategoryDto categoryDto) {
        if (categoryDto != null) {
            try {
                categoryMapper.updateCategory(categoryDto);
            } catch (RuntimeException e) {
                log.error("update 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("update ERROR! {}", categoryDto);
            throw new RuntimeException("update ERROR! 물품 카테고리 변경 메서드를 확인해주세요\n" + "Params : " + categoryDto);
        }
    }

    @Override
    public void delete(int categoryId) {
        if (categoryId != 0) {
            try {
                categoryMapper.deleteCategory(categoryId);
            } catch (RuntimeException e) {
                log.error("delete 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("deleteCategory ERROR! {}", categoryId);
            throw new RuntimeException("deleteCategory ERROR! 물품 카테고리 삭제 메서드를 확인해주세요\n" + "Params : " + categoryId);
        }
    }
}
