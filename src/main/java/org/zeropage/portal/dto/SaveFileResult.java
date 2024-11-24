package org.zeropage.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SaveFileResult {
    private String link;
    private long index;
}