package org.zeropage.portal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zeropage.portal.domain.ForumContent;
import org.zeropage.portal.domain.ForumFile;
import org.zeropage.portal.dto.GetFileResult;
import org.zeropage.portal.exception.FileNotSavableException;
import org.zeropage.portal.repository.ForumFileRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final ForumFileRepository forumFileRepository;
    @Value("${files.location}")
    private String filePath;

    @Transactional
    public long createFile(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            ForumFile savedFile = new ForumFile(file);
            try {
                file.transferTo(new File(filePath + savedFile.getSavedName()));
            } catch (IOException e) {
                throw new FileNotSavableException("파일 저장에 실패했습니다. 다시 시도해 주세요.");
            }
            return forumFileRepository.save(savedFile).getId();
        } else {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public GetFileResult getFile(long id) throws FileNotFoundException, MalformedURLException {
        Optional<ForumFile> savedInfoOptional = forumFileRepository.findById(id);
        if (savedInfoOptional.isPresent()) {
            ForumFile savedInfo = savedInfoOptional.get();
            Resource resource =
                    new UrlResource("file:" + filePath + savedInfo.getSavedName());
            return GetFileResult.builder()
                    .resource(resource)
                    .savedInfo(savedInfo)
                    .build();
        } else {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }
    }

    //No transaction
    void updateForumContent(long fileId, ForumContent forumContent) {
        Optional<ForumFile> target = forumFileRepository.findById(fileId);
        target.ifPresent(f -> {
            f.setContent(forumContent);
            forumFileRepository.save(f);
        });
    }
}