package org.zeropage.portal.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class GetForum {

    private Long id;
    private String authorNickname;
    private LocalDateTime createDate;
    private LocalDateTime lastChangedDate;
    private String board;
    private String title;
    private Long views;

    public GetForum(Long id, String authorNickname, LocalDateTime createDate, LocalDateTime lastChangedDate, String board, String title, Long views) {
        this.id = id;
        this.authorNickname = authorNickname;
        this.createDate = createDate;
        this.lastChangedDate = lastChangedDate;
        this.board = board;
        this.title = title;
        this.views = views;
    }
}
