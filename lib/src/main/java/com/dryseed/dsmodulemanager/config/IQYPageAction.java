package com.dryseed.dsmodulemanager.config;

/**
 * Created by shisong on 2018/3/5.
 * QYPage事件定义
 */
public final class IQYPageAction {

    /**
     * 是否在服务页展示红点
     */
    public static final int ACTION_SHOW_REDDOT_SERVICE = 102;

    /**
     * 获取InitLogin中底导航皮肤包时间戳
     */
    public static final int ACTION_GET_BOTTOM_THEME_TIME = 103;

    /**
     * 当前是否有新的泡泡消息
     */
    public static final int ACTION_HAS_NEW_MESSAGE = 104;

    /**
     * 清空当前消息红点展示
     */
    public static final int ACTION_CLEAR_MESSAGE_DOT = 105;

    /**
     * 清除皮肤
     */
    public static final int ACTION_CLEAR_SKIN = 106;

    /**
     * 是否需要请求vip tab红点接口
     */
    public static final int ACTION_IS_VIP_NEED_REQUEST_REDDOT = 107;

    /**
     * 跳转VIP精选页面
     */
    public static final int ACTION_TO_VIP_RECOMMEND_PAGE = 108;

    /**
     * 跳转VIP会员俱乐部页面
     */
    public static final int ACTION_TO_VIP_CLUB_PAGE = 109;

    /**
     * 影吧消息推送通知邀请观影
     */
    public static final int ACTION_NOTIFY_INVITATION = 110;

    /**
     * 设置是否热启动展示开机屏广告
     */
    public static final int ACTION_SET_HOT_LAUNCH = 111;

    /**
     * 获取是否热启动展示开机屏广告
     */
    public static final int ACTION_IS_HOT_LAUNCH = 112;

    /*
     * 是否关注了对应的爱奇艺号
     * */
    public static final int ACTION_HAS_FOLLOWED = 113;

    /*
     * 添加关注的爱奇艺号
     * */
    public static final int ACTION_ADD_USER = 114;

    /*
     * 移除关注的爱奇艺号
     * */
    public static final int ACTION_REMOVE_USER = 115;

    /*
     * 获取pageId信息
     * */
    public static final int ACTION_GET_PAGEID = 116;

    /**
     * 设置搜索页来源标记
     */
    public static final int ACTION_SET_SEARCH_FROM_TYPE = 117;

    /**
     * 获取搜索页来源标记
     */
    public static final int ACTION_GET_SEARCH_FROM_TYPE = 118;

    public static final int ACTION_REPORT_CRASH_BIZ_ERROR = 119;

    /**
     * 获取本地缓存预约状态
     */
    public static final int ACTION_IS_SUBSCRIBE_MOVIE = 120;

    /**
     * 添加预约
     */
    public static final int ACTION_SUBSCRIBE_MOVIE = 121;

    /**
     * 取消预约
     */
    public static final int ACTION_CANCEL_SUBSCRIBE_MOVIE = 122;

    /**
     * push通知更新首刷广告id
     */
    public static final int ACTION_NOTIFY_UPDATE_AD_ID = 123;

}
