package org.zeropage.portal.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.zeropage.portal.domain.ForumFile;

@Data
@Builder
public class GetFileResult {
    private Resource resource;
    private ForumFile savedInfo;
}