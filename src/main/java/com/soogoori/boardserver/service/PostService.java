package com.soogoori.boardserver.service;

import com.soogoori.boardserver.dto.PostDto;

import java.util.List;

public interface PostService {

    void register(String id, PostDto postDto);

    List<PostDto> getMyProducts(int accountId);

    void updateProducts(PostDto postDto);

    void deleteProduct(int userId, int productId);
}
