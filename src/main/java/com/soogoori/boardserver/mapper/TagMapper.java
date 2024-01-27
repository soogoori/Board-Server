package com.soogoori.boardserver.mapper;

import com.soogoori.boardserver.dto.CommentDto;
import com.soogoori.boardserver.dto.TagDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {
    public int register(TagDto tagDto);

    public void updateTags(TagDto tagDto);

    public void deletePostTag(int tagId);

    public void createPostTag(Integer tagId, Integer postId);
}
