package com.soogoori.boardserver.service.impl;

import com.soogoori.boardserver.dto.PostDto;
import com.soogoori.boardserver.dto.UserDto;
import com.soogoori.boardserver.mapper.PostMapper;
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
    private UserProfileMapper userProfileMapper;
    @Override
    public void register(String id, PostDto postDto) {
        UserDto memberInfo = userProfileMapper.getUserProfile(id);
        postDto.setUserId(memberInfo.getId());
        postDto.setCreateTime(new Date());

        if (memberInfo != null) {
            postMapper.register(postDto);
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
}
