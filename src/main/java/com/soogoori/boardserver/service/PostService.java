package com.soogoori.boardserver.service;

import com.soogoori.boardserver.dto.CommentDto;
import com.soogoori.boardserver.dto.PostDto;
import com.soogoori.boardserver.dto.TagDto;

import java.util.List;

public interface PostService {

    void register(String id, PostDto postDto);

    List<PostDto> getMyProducts(int accountId);

    void updateProducts(PostDto postDto);

    void deleteProduct(int userId, int productId);

    void registerComment(CommentDto commentDto);

    void updateComment(CommentDto commentDto);

    void deletePostComment(int userId, int commentId);

    void registerTag(TagDto tagDto);

    void updateTag(TagDto tagDto);

    void deletePostTag(int userId, int tagId);
}
