package com.murphy.community.model;

import lombok.Data;

/**
 * @author 233murphy
 */

@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long createdAt;
    private Long updatedAt;
    private String avatarUrl;
}
