package com.bilibili.constants;

public class SystemConstants {
    public static final String BLOG_LOGIN = "bloglogin:";
    public static final String ADMIN_LOGIN = "adminlogin:";

    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     *  分类是启用
     */
    public static final Integer CATEGORY_STATUS_NORMAL = 0;

    /**
     *  分类是禁用
     */
    public static final int CATEGORY_STATUS_DISABLE = 1;

    /**
     * 友链是审核通过状态
     */
    public static final String LINK_STATUS_NORMAL  = "0";

    /**
     * 友链是未审核通过得不可用状态
     */
    public static final String LINK_STATUS_FAILING  = "2";


    /**
     * 友链是还未审核
     */
    public static final String LINK_STATUS_UNKNOWN  = "1";


    /**
     * 根评论
     */

    public static final int ROOT_COMMENT  = -1;

    public static final String TYPE_ARTICLE = "0";
    public static final String TYPE_LINK = "1";
}