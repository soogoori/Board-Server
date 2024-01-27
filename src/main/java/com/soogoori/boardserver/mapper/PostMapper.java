package com.soogoori.boardserver.mapper;

import com.soogoori.boardserver.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    public int register(PostDto postDto);

    public List<PostDto> selectMyProducts(int accountId);

    public void updateProducts(PostDto postDto);

    public void deleteProduct(int productId);
}
