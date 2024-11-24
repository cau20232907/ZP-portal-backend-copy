package org.zeropage.portal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.zeropage.portal.domain.ForumContent;
import org.zeropage.portal.dto.GetForum;
import org.zeropage.portal.repository.ForumContentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumService {
    private final ForumContentRepository forumContentRepository;
    private final FileService fileService;

    public long create(String authorId, String authorNickname,
                       String board, String title, String content,
                       long[] fileIdxes) {
        ForumContent savedContent = forumContentRepository.save(ForumContent.builder()
                .board(board)
                .authorIdx(authorId)
                .authorNickname(authorNickname)
                .createDate(LocalDateTime.now())
                .lastChangedDate(LocalDateTime.now())
                .title(title)
                .content(content)
                .views(0L).build());
        for (long fileIdx : fileIdxes) {
            fileService.updateForumContent(fileIdx, savedContent);
        }
        return savedContent.getId();
    }

    public ForumContent find(long id) {
        Optional<ForumContent> result = forumContentRepository.findById(id);
        if (result.isPresent()) {
            ForumContent forumContent = result.get();
            forumContent.addViews();
            forumContentRepository.save(forumContent);
            return forumContent;
        }
        else throw new NoSuchElementException("데이터를 찾을 수 없습니다.");
    }

    public List<GetForum> findForumList(long page){
        return forumContentRepository
                .findForumList(PageRequest.of((int) (page-1),20));
    }

    public long forumCount(){
        long forumCount = forumContentRepository.count();
        return forumPageCount(forumCount);
    }

    private long forumPageCount(long forumCount){
        if(forumCount % 20 == 0)
            return forumCount / 20;
        else
            return forumCount / 20 + 1;
    }

    public List<GetForum> searchForumByTitleAndContent(long page, String title, String content){
        return forumContentRepository
                .searchForumByTitleAndContent(PageRequest.of((int) (page-1), 20),'%'+title+'%', '%'+content+'%')
                .getContent();

    }

    public List<GetForum> searchForumByTitle(long page, String title){
        return forumContentRepository
                .searchForumByTitle(PageRequest.of((int) (page-1), 20),'%'+title+'%')
                .getContent();
    }

    public List<GetForum> searchForumByContent(long page, String content){
        return forumContentRepository
                .searchForumByContent(PageRequest.of((int) (page-1), 20), '%'+content+'%')
                .getContent();
    }

    public List<GetForum> searchForumByNickname(long page, String authorNickname){
        return forumContentRepository
                .searchForumByNickname(PageRequest.of((int) (page-1), 20), '%'+authorNickname+'%')
                .getContent();
    }

    public long countByTitleAndContent(String title, String content){
        long forumCount = forumContentRepository.countByTitleAndContent('%'+title+'%', '%'+content+'%');
        return forumPageCount(forumCount);
    }

    long countByTitle(String title){
        long forumCount = forumContentRepository.countByTitleContaining(title);
        return forumPageCount(forumCount);
    }

    long countByContent(String content){
        long forumCount = forumContentRepository.countByContentContaining(content);
        return forumPageCount(forumCount);
    }

    long countByAuthorNickname(String authorNickname){
        long forumCount = forumContentRepository.countByAuthorNicknameContaining(authorNickname);
        return forumPageCount(forumCount);
    }

    public void delete(GetForum getForum){
        forumContentRepository.deleteById(getForum.getId());
    }
}