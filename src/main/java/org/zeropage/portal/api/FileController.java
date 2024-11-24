package org.zeropage.portal.api;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zeropage.portal.dto.SaveFileResult;
import org.zeropage.portal.service.FileService;
import org.zeropage.portal.dto.GetFileResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/ping")
    public Map<String, String> ping() {
        HashMap<String, String> result = new HashMap<>();
        result.put("success", "true");
        return result;
    }

    @GetMapping("/forum/file/download")
    public ResponseEntity<Resource> download(@RequestParam("seq") long id)
            throws MalformedURLException, FileNotFoundException {
        GetFileResult file = fileService.getFile(id);
        String contentDisposition = URLEncoder.encode(
                "attachment; filename=\"" + file.getSavedInfo().getOriginalName() + '\"',
                StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        contentDisposition)
                .body(file.getResource());
    }

    @PostMapping(value = "/forum/file/upload", consumes = "multipart/form-data")
    public SaveFileResult upload(@RequestParam("file") MultipartFile file) throws IOException {
        long savedFileIndex = fileService.createFile(file);
        return new SaveFileResult("/forum/file/download?seq=" + savedFileIndex, savedFileIndex);
    }
}

