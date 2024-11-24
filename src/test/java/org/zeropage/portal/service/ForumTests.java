package org.zeropage.portal.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zeropage.portal.dto.GetForum;
import org.zeropage.portal.repository.ForumContentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ForumTests {
    @Autowired
    ForumService forumService;
    @Autowired
    ForumContentRepository forumContentRepository;

    @Test
    void titleSearchTest1(){
        List<GetForum> result = forumService.searchForumByTitle(1, "test");

        assertEquals(1,result.size());
        assertEquals("free",result.get(0).getBoard());
    }

    @Test
    void titleSearchTest2(){
        List<GetForum> result = forumService.searchForumByTitle(1, "test");

        assertEquals(1,result.size());
        assertEquals("free",result.get(0).getBoard());
    }

    @Test
    void contentCountTest(){
        long result = forumService.countByContent("unk");
        long directResult = forumContentRepository.countByContentContaining("unk");

        assertEquals(2, result);
        assertEquals(22,directResult);
    }

    @Test
    void TotalForumTest(){
        //No given (already in DB)
        long count = forumService.forumCount();
        List<GetForum> forumList1 = forumService.findForumList(1);
        List<GetForum> forumList2 = forumService.findForumList(2);

        assertEquals(2, count);
        assertEquals(20, forumList1.size());
        assertEquals(2, forumList2.size());

        byte[] list1Idx=new byte[]{4,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70};
        for(int i=0;i<list1Idx.length;i++){
            assertEquals(list1Idx[i],forumList1.get(i).getId());
        }
        byte[] list2Idx=new byte[]{71,102};
        for(int i=0;i<list2Idx.length;i++){
            assertEquals(list2Idx[i],forumList2.get(i).getId());
        }
    }

    @Test
    void searchForumByTitleAndContentTest(){
        //No given (already in DB)

        String title = "0";
        String content = "2";

        long count = forumService.countByTitleAndContent(title, content);
        List<GetForum> forumList1 = forumService.searchForumByTitleAndContent(1, title, content);

        assertEquals(1, count);
        assertEquals(5, forumList1.size());

        byte[] list1Idx=new byte[]{52,54,62,64,102};
        for(int i=0;i<list1Idx.length;i++){
            assertEquals(list1Idx[i],forumList1.get(i).getId());
        }
    }

    @Test
    void searchForumByTitleTest(){
        //No given (already in DB)

        String title = "1";

        long count = forumService.countByTitle(title);
        List<GetForum> forumList1 = forumService.searchForumByTitle(1, title);

        assertEquals(1, count);
        assertEquals(11, forumList1.size());

        byte[] list1Idx=new byte[]{53,62,63,64,65,66,67,68,69,70,71};
        for(int i=0;i<list1Idx.length;i++){
            assertEquals(list1Idx[i],forumList1.get(i).getId());
        }
    }

    @Test
    void searchForumByContentTest(){
        //No given (already in DB)

        String content = "n";

        long count = forumService.countByContent(content);
        List<GetForum> forumList1 = forumService.searchForumByContent(1, content);
        List<GetForum> forumList2 = forumService.searchForumByContent(2, content);

        assertEquals(2, count);
        assertEquals(20, forumList1.size());
        assertEquals(2, forumList2.size());

        byte[] list1Idx=new byte[]{4,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70};
        for(int i=0;i<list1Idx.length;i++){
            assertEquals(list1Idx[i],forumList1.get(i).getId());
        }
        byte[] list2Idx=new byte[]{71,102};
        for(int i=0;i<list2Idx.length;i++){
            assertEquals(list2Idx[i],forumList2.get(i).getId());
        }
    }

    @Test
    void searchForumByNickname(){
        //No given (already in DB)

        String nickname = "2";

        long count = forumService.countByAuthorNickname(nickname);
        List<GetForum> forumList1 = forumService.searchForumByNickname(1, nickname);

        assertEquals(1, count);
        assertEquals(1, forumList1.size());

        byte[] list1Idx=new byte[]{4};
        for(int i=0;i<list1Idx.length;i++){
            assertEquals(list1Idx[i],forumList1.get(i).getId());
        }
    }

//    @Test
//    @Rollback(value = false)
    void dataInput(){
        //for(int i=0;i<20;i++){
            forumService.create("12345","auth",
                    "free","free"+20,"unknown"+20,new long[0]);
        //}
    }
}
