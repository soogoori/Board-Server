package com.soogoori.boardserver.service;

import com.soogoori.boardserver.dto.PostDto;
import com.soogoori.boardserver.dto.request.PostSearchRequest;

import java.util.List;

public interface PostSearchService {
    List<PostDto> getPosts(PostSearchRequest postSearchRequest);

    List<PostDto> getPostByTag(String tagName);
}
