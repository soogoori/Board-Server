package com.soogoori.boardserver.controller;

import com.soogoori.boardserver.dto.PostDto;
import com.soogoori.boardserver.dto.request.PostSearchRequest;
import com.soogoori.boardserver.service.impl.PostSearchServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchServiceImpl postSearchService;

    @PostMapping
    public PostSearchResponse search(@RequestBody PostSearchRequest postSearchRequest) {
        List<PostDto> postDtoList = postSearchService.getPosts(postSearchRequest);
        return new PostSearchResponse(postDtoList);
    }

    @GetMapping
    public PostSearchResponse searchByTagName(String tagName) {
        List<PostDto> postDtoList = postSearchService.getPostByTag(tagName);
        return new PostSearchResponse(postDtoList);
    }

    // -------------- response 객체 --------------
    @Getter
    @AllArgsConstructor
    private static class PostSearchResponse {
        private List<PostDto> postDtoList;
    }
}
