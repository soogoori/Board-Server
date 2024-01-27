package com.soogoori.boardserver.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    private int id;
    private String name;
    private String url;
    private int postId;
}
