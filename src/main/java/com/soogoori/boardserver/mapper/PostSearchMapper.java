package com.soogoori.boardserver.mapper;

import com.soogoori.boardserver.dto.PostDto;
import com.soogoori.boardserver.dto.request.PostSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostSearchMapper {
    public List<PostDto> selectPosts(PostSearchRequest postSearchRequest);
    public List<PostDto> getPostByTag(String tagName);
}
