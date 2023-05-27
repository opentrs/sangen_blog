package com.bilibili.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName LinkVO
 * @Description TODO
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVO {
    private Long id;
    private String name;
    private String logo;
    private String description;
    private String address;
}
