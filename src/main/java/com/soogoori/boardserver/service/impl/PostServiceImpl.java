package com.soogoori.boardserver.service.impl;

import com.soogoori.boardserver.dto.CommentDto;
import com.soogoori.boardserver.dto.PostDto;
import com.soogoori.boardserver.dto.TagDto;
import com.soogoori.boardserver.dto.UserDto;
import com.soogoori.boardserver.mapper.CommentMapper;
import com.soogoori.boardserver.mapper.PostMapper;
import com.soogoori.boardserver.mapper.TagMapper;
import com.soogoori.boardserver.mapper.UserProfileMapper;
import com.soogoori.boardserver.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;
    @Override
    public void register(String id, PostDto postDto) {
        UserDto memberInfo = userProfileMapper.getUserProfile(id);
        postDto.setUserId(memberInfo.getId());
        postDto.setCreateTime(new Date());

        if (memberInfo != null) {
            postMapper.register(postDto);
            Integer postId = postDto.getId();
            // 생성된 post 객체에서 태그 리스트 생성
            for(int i=0; i<postDto.getTagDtoList().size(); i++){
                TagDto tagDto = postDto.getTagDtoList().get(i);
                tagMapper.register(tagDto);
                Integer tagId = tagDto.getId();
                // M:N 관계 테이블 생성
                tagMapper.createPostTag(tagId, postId);
            }
        } else {
            log.error("register ERROR! {}", postDto);
            throw new RuntimeException("register ERROR! 상품 등록 메서드를 확인해주세요\n" + "Params : " + postDto);
        }
    }

    @Override
    public List<PostDto> getMyProducts(int accountId) {
        List<PostDto> postDtoList = postMapper.selectMyProducts(accountId);
        return postDtoList;
    }

    @Override
    public void updateProducts(PostDto postDto) {
        if (postDto != null && postDto.getId() != 0 && postDto.getUserId() != 0) {
            postMapper.updateProducts(postDto);
        } else {
            log.error("updateProducts ERROR! {}", postDto);
            throw new RuntimeException("updateProducts ERROR! 물품 변경 메서드를 확인해주세요\n" + "Params : " + postDto);
        }
    }

    @Override
    public void deleteProduct(int userId, int productId) {
        if (userId != 0 && productId != 0) {
            postMapper.deleteProduct(productId);
        } else {
            log.error("deleteProduct ERROR! {}", productId);
            throw new RuntimeException("updateProducts ERROR! 물품 삭제 메서드를 확인해주세요\n" + "Params : " + productId);
        }
    }

    @Override
    public void registerComment(CommentDto commentDto) {
        if (commentDto.getPostId() != 0) {
            commentMapper.register(commentDto);
        } else {
            log.error("registerComment ERROR! {}", commentDto);
            throw new RuntimeException("registerComment ERROR! 댓글 추가 메서드를 확인해주세요\n" + "Params : " + commentDto);
        }
    }

    @Override
    public void updateComment(CommentDto commentDto) {
        if (commentDto != null) {
            commentMapper.updateComments(commentDto);
        } else {
            log.error("updateComment ERROR!");
            throw new RuntimeException("updateComment ERROR! 댓글 변경 메서드를 확인해주세요\n");
        }
    }

    @Override
    public void deletePostComment(int userId, int commentId) {
        if (userId != 0 && commentId != 0) {
            commentMapper.deletePostComment(commentId);
        } else {
            log.error("deletePostComment ERROR! {}", commentId);
            throw new RuntimeException("deletePostComment ERROR! 댓글 삭제 메서드를 확인해주세요\n" + "Params : " + commentId);
        }
    }

    @Override
    public void registerTag(TagDto tagDto) {
        if (tagDto.getPostId() != 0) {
            tagMapper.register(tagDto);
        } else {
            log.error("registerTag ERROR! {}", tagDto);
            throw new RuntimeException("registerTag ERROR! 태그 추가 메서드를 확인해주세요\n" + "Params : " + tagDto);
        }
    }

    @Override
    public void updateTag(TagDto tagDto) {
        if (tagDto != null) {
            tagMapper.updateTags(tagDto);
        } else {
            log.error("updateTag ERROR!");
            throw new RuntimeException("updateTag ERROR! 태그 변경 메서드를 확인해주세요\n");
        }
    }

    @Override
    public void deletePostTag(int userId, int tagId) {
        if (userId != 0 && tagId != 0) {
            tagMapper.deletePostTag(tagId);
        } else {
            log.error("deletePostTag ERROR! {}", tagId);
            throw new RuntimeException("deletePostTag ERROR! 태그 삭제 메서드를 확인해주세요\n" + "Params : " + tagId);
        }
    }
}
