package com.soogoori.boardserver.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private int id;
    private int postId;
    private String contents;
    private int subCommentId;
}
