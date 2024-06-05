package org.qqz.common.vo;

import lombok.Data;
import java.util.List;

@Data
public class ResourceInfo {
    private String userId;
    private List<String> endpoint;
}
