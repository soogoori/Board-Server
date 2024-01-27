package com.soogoori.boardserver.mapper;

import com.soogoori.boardserver.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    public int register(CommentDto commentDto);

    public void updateComments(CommentDto commentDto);

    public void deletePostComment(int commentId);
}
