package org.zeropage.portal.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zeropage.portal.domain.ForumContent;
import org.zeropage.portal.service.ForumService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ForumController {
    private final ForumService forumService;

    @GetMapping("forum/boards")
    public ForumContent getContent(long id) {
        return forumService.find(id);
    }

    @PostMapping("forum/boards")
    public long create(@AuthenticationPrincipal Jwt jwt,
                       String board, String title, String content,
                       long[] fileIdxes) {
        Map<String, Object> claims = jwt.getClaims();
        String authorId = (String) claims.get("sid");
        String authorNickname = (String) claims.get("nickname");
        return forumService.create(authorId, authorNickname,
                board, title, content, fileIdxes);
    }
}