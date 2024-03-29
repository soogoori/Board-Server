package com.soogoori.boardserver.service.impl;

import com.soogoori.boardserver.dto.PostDto;
import com.soogoori.boardserver.dto.request.PostSearchRequest;
import com.soogoori.boardserver.exception.BoardServerException;
import com.soogoori.boardserver.mapper.PostSearchMapper;
import com.soogoori.boardserver.service.PostSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    @Autowired
    private PostSearchMapper postSearchMapper;

    /*@Autowired
    private SlackService slackService;*/

    @Async
    @Cacheable(value = "getPosts", key = "'getPosts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDto> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDto> postDtoList = null;
        try {
            postDtoList = postSearchMapper.selectPosts(postSearchRequest);
        } catch (RuntimeException e) {
            //slackService.sendSlackMessage("selectPosts 실패 " +e.getMessage(),"error");
            log.error("selectPosts 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return postDtoList;
    }

    @Override
    public List<PostDto> getPostByTag(String tagName) {
        List<PostDto> postDTOList = null;
        try {
            postDTOList = postSearchMapper.getPostByTag(tagName);
        } catch (RuntimeException e) {
            log.error("getPostByTag 실패", e.getMessage());
        }
        return postDTOList;
    }
}
