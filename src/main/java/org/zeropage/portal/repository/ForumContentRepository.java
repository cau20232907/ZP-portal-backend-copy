package org.zeropage.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zeropage.portal.domain.ForumContent;
import org.zeropage.portal.dto.GetForum;

import java.util.List;

@Repository
public interface ForumContentRepository extends JpaRepository<ForumContent, Long> {
    String defaultQuery = "select new org.zeropage.portal.dto.GetForum(f.id, f.authorNickname, f.createDate, f.lastChangedDate, f.board, f.title, f.views) from ForumContent f";
    String countQuery = "select count(f) from ForumContent f";

    @Query(defaultQuery)
    List<GetForum> findForumList(Pageable pageable);

    @Query(defaultQuery + " where f.content like :content or f.title like :title")
    Page<GetForum> searchForumByTitleAndContent(Pageable pageable, String title, String content);

    @Query(countQuery + " where f.content like :content or f.title like :title")
    long countByTitleAndContent(String title, String content);

    @Query(defaultQuery + " where f.title like :title")
    Page<GetForum> searchForumByTitle(Pageable pageable, String title);

    long countByTitleContaining(String title);

    @Query(defaultQuery + " where f.content like :content")
    Page<GetForum> searchForumByContent(Pageable pageable, String content);

    long countByContentContaining(String content);

    @Query(defaultQuery + " where f.authorNickname like :authorNickname")
    Page<GetForum> searchForumByNickname(Pageable pageable, String authorNickname);

    long countByAuthorNicknameContaining(String authorNickname);
}