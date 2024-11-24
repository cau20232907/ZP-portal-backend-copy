package org.zeropage.portal.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ForumFile {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private ForumContent content;
    @Column(nullable = false)
    private String originalName;
    @Column(unique = true, length = 48)
    private String savedName;
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @Column(nullable = false)
    private LocalDateTime lastChangedDate;
    @Column(nullable = false)
    private long fileSize;

    @SuppressWarnings("null")
    public ForumFile(MultipartFile file) throws FileNotFoundException {
        this.originalName = file.getOriginalFilename();
        try {
            String fileType = '.' + originalName.substring
                    (originalName.lastIndexOf('.'));
            this.savedName = UUID.randomUUID().toString() + fileType;
        } catch (IndexOutOfBoundsException e) {
            throw new FileNotFoundException("확장자를 확인할 수 없는 파일입니다.");
        } catch (NullPointerException e) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }
        this.fileSize = file.getSize();
        this.createdDate = LocalDateTime.now();
        this.lastChangedDate = LocalDateTime.now();
    }

    public void setContent(ForumContent content) {
        this.content = content;
        this.lastChangedDate = LocalDateTime.now();
    }
}
