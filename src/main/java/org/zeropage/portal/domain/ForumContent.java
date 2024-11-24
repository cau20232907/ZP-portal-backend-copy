package org.zeropage.portal.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ForumContent {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;
    @Column(nullable = false, length = 48)
    private String authorIdx;
    @Column(nullable = false)
    private String authorNickname;
    @Column(nullable = false)
    private LocalDateTime createDate;
    @Column(nullable = false)
    private LocalDateTime lastChangedDate;
    @Column(nullable = false)
    private String board;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;
    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED DEFAULT 0")
    private Long views;

    public void addViews(){
        views++;
    }
}