package com.soogoori.boardserver.controller;

import com.soogoori.boardserver.aop.LoginCheck;
import com.soogoori.boardserver.dto.CommentDto;
import com.soogoori.boardserver.dto.PostDto;
import com.soogoori.boardserver.dto.TagDto;
import com.soogoori.boardserver.dto.UserDto;
import com.soogoori.boardserver.dto.response.CommonResponse;
import com.soogoori.boardserver.service.impl.PostServiceImpl;
import com.soogoori.boardserver.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Log4j2
public class PostController{

    private final PostServiceImpl postService;
    private final UserServiceImpl userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDto>> registerPost(String accountId, @RequestBody PostDto postDto) {
        postService.register(accountId, postDto);
        CommonResponse commonResponse
                = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPost", postDto);
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("my-posts")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDto>>> myPostInfo(String accountId) {
        UserDto memberInfo = userService.getUserInfo(accountId);
        List<PostDto> postDTOList = postService.getMyProducts(memberInfo.getId());
        CommonResponse commonResponse
                = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "myPostInfo", postDTOList);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostRequest>> updatePosts(String accountId,
                                                                   @PathVariable(name = "postId") int postId,
                                                                   @RequestBody PostRequest postRequest) {
        UserDto memberInfo = userService.getUserInfo(accountId);
        PostDto postDto = PostDto.builder()
                .id(postId)
                .name(postRequest.getName())
                .contents(postRequest.getContents())
                .views(postRequest.getViews())
                .categoryId(postRequest.getCategoryId())
                .userId(memberInfo.getId())
                .fileId(postRequest.getFileId())
                .updateTime(new Date())
                .build();
        postService.updateProducts(postDto);
        CommonResponse commonResponse
                = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePosts", postDto);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deleteposts(String accountId,
                                                                         @PathVariable(name = "postId") int postId,
                                                                         @RequestBody PostDeleteRequest postDeleteRequest) {
        UserDto memberInfo = userService.getUserInfo(accountId);
        postService.deleteProduct(memberInfo.getId(), postId);
        CommonResponse commonResponse
                = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deleteposts", postDeleteRequest);
        return ResponseEntity.ok(commonResponse);
    }

    // -------------- comments --------------
    @PostMapping("comments")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDto>> registerPostComment(String accountId,
                                                                          @RequestBody CommentDto commentDto) {
        postService.registerComment(commentDto);
        CommonResponse commonResponse
                = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPostComment", commentDto);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDto>> updatePostComment(String accountId,
                                                                        @PathVariable(name = "commentId") int commentId,
                                                                        @RequestBody CommentDto commentDto) {
        UserDto memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null)
            postService.updateComment(commentDto);
        CommonResponse commonResponse
                = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePostComment", commentDto);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDto>> deletePostComment(String accountId,
                                                                        @PathVariable(name = "commentId") int commentId,
                                                                        @RequestBody CommentDto commentDto) {
        UserDto memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null)
            postService.deletePostComment(memberInfo.getId(), commentId);
        CommonResponse commonResponse
                = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deletePostComment", commentDto);
        return ResponseEntity.ok(commonResponse);
    }

    // -------------- tags --------------
    @PostMapping("tags")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDto>> registerPostTag(String accountId, @RequestBody TagDto tagDto) {
        postService.registerTag(tagDto);
        CommonResponse commonResponse
                = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPostTag", tagDto);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDto>> updatePostTag(String accountId,
                                                                @PathVariable(name = "tagId") int tagId,
                                                                @RequestBody TagDto tagDto) {
        UserDto memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null)
            postService.updateTag(tagDto);
        CommonResponse commonResponse
                = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePostTag", tagDto);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDto>> deletePostTag(String accountId,
                                                                @PathVariable(name = "tagId") int tagId,
                                                                @RequestBody TagDto tagDto) {
        UserDto memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null)
            postService.deletePostTag(memberInfo.getId(), tagId);
        CommonResponse commonResponse
                = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deletePostTag", tagDto);
        return ResponseEntity.ok(commonResponse);
    }

    // -------------- response 객체 --------------
    @Getter
    @AllArgsConstructor
    private static class PostResponse {
        private List<PostDto> postDto;
    }

    // -------------- request 객체 --------------
    @Setter
    @Getter
    private static class PostRequest {
        private String name;
        private String contents;
        private int views;
        private int categoryId;
        private int userId;
        private int fileId;
        private Date updateTime;
    }

    @Setter
    @Getter
    private static class PostDeleteRequest {
        private int id;
        private int accountId;
    }
}
