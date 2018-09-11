package com.dryseed.dsmodulemanager.communication;

/**
 * 定义了模块的名称和模块id
 * 对于一个action(int类型),高10为表示模块id,低22位表示action id
 * Author:yuanzeyao<br/>
 * Date:16/6/20 下午5:24
 * Email:yuanzeyao@qiyi.com
 */
public final class IModuleConstants {

    private IModuleConstants() {
    }

    //-----------------------------模块名称 start-------------------------------
    /**
     * 下载模块名称
     */
    public static final String MODULE_NAME_DOWNLOAD = "download";

    /**
     * 下载服务模块名称
     */
    public static final String MODULE_NAME_DOWNLOAD_SERVICE = "download_service";

    /**
     * 账号模块名称
     */
    public static final String MODULE_NAME_PASSPORT = "passport";

    /**
     * 账号插件模块名称
     */
    public static final String MODULE_NAME_PASSPORT_PLUGIN = "passport_plugin";

    /**
     * 投递模块名称
     */
    public static final String MODULE_NAME_DELIVER = "deliver";

    /**
     * 播放器模块名称
     */
    public static final String MODULE_NAME_PLAYER = "player";

    /**
     * videoview模块名称
     */
    public static final String MODULE_NAME_VIDEOVIEW = "videoview";

    /**
     * 分享模块名称
     */
    public static final String MODULE_NAME_SHARE = "share";

    /**
     * 支付模块名称
     */

    public static final String MODULE_NAME_PAY = "pay";

    /**
     * 播放记录模块名称
     */
    public static final String MODULE_NAME_PLAYRECORD = "playrecord";

    /**
     * client模块名称
     */
    public static final String MODULE_NAME_CLIENT = "qiyi_client";

    /**
     * 收藏模块名称
     */
    public static final String MODULE_NAME_COLLECTION = "collection";

    /**
     * FingerPrint模块名称
     */
    public static final String MODULE_NAME_FINGERPRINT = "fingerprint";

    /**
     * 泡泡模块名称
     */
    public static final String MODULE_NAME_PAOPAO = "paopao";

    /**
     * InitLogin模块名称
     */
    public static final String MODULE_NAME_INITLOGIN = "initlogin";

    /**
     * 插件中心模块名称
     */
    public static final String MODULE_NAME_PLUGINCENTER = "plugincenter";

    /**
     * 红点消息分发模块
     */
    public static final String MODULE_NAME_MESSAGE_DISPATCH = "message_dispatch";

    /**
     * 定向免流量模块名称
     */
    public static final String MODULE_NAME_TRAFFIC = "traffic";

    /**
     * 定向流量模块，供插件使用
     */
    public static final String MODULE_NAME_TRAFFIC_FOR_PLUGIN = "traffic_for_plugin";


    /**
     * 客户的播放器分离的投屏模块
     */
    public static final String MODULE_NAME_QYDLAN_MODULE = "qy_dlan_module";

    /**
     * 弹幕模块
     */
    public static final String MODULE_NAME_DANMAKU_MODULE = "danmaku";

    /**
     * 基线所有插件抽象成插件模块
     */
    public static final String MODULE_NAME_PLUGIN = "plugins";

    /**
     * RN模块
     */
    public static final String MODULE_NAME_REACT = "react";

    /**
     * 我的模块
     */
    public static final String MODULE_NAME_MYMAIN = "mymain";

    /**
     * QYPage模块
     */
    public static final String MODULE_NAME_QYPAGE = "qypage";

    /**
     * QYPage模块
     */
    public static final String MODULE_NAME_NAVIGATION = "navigation";

    /**
     * DebugCenter模块
     */
    public static final String MODULE_NAME_DEBUG_CENTER = "debug_center";

    /**
     * AIVOICE模块
     */
    public static final String MODULE_NAME_AIVOICE = "aivoice";
    /**
     * 基线竖屏播放器模块
     */
    public static final String MODULE_NAME_VERTICAL_PLAYER = "vertical_player";

    //----------------------------模块名称 end------------------------------------

    public static final int MODULE_MASK = 2047 << 22;
    public static final int ACTION_MASK = (1 << 22) - 1;

    //------------------------------模块id start---------------------------------


    /**
     * 下载模块id
     */
    public static final int MODULE_ID_DOWNLOAD = 1 << 22;


    /**
     * 账号模块id
     */
    public static final int MODULE_ID_PASSPORT = 2 << 22;

    /**
     * EVENT 当做特殊模块处理,将其id设置为3
     */
    public static final int EVENT = 3 << 22;

    /**
     * 投递模块id
     */
    public static final int MODULE_ID_DELIVER = 4 << 22;

    /**
     * 播放器模块id
     */
    public static final int MODULE_ID_PLAYER = 5 << 22;

    /**
     * 分享模块id
     */
    public static final int MODULE_ID_SHARE = 6 << 22;

    /**
     * 支付模块
     */
    public static final int MODULE_ID_PAY = 7 << 22;

    /**
     * 播放记录模块id
     */
    public static final int MODULE_ID_PLAYRECORD = 8 << 22;


    /**
     * 首页模块
     */
    public static final int MODULE_ID_CLIENT = 9 << 22;

    /**
     * 收藏模块id
     */
    public static final int MODULE_ID_COLLECT = 10 << 22;

    /**
     * FingerPrint模块id
     */
    public static final int MODULE_ID_FINGERPRINT = 11 << 22;

    /**
     * 泡泡模块id
     */
    public static final int MODULE_ID_PAOPAO = 12 << 22;

    /**
     * InitLogin
     */
    public static final int MODULE_ID_INIT_LOGIN = 13 << 22;

    /**
     * 插件中心模块id
     */
    public static final int MODULE_ID_PLUGINCENTER = 14 << 22;

    /**
     * 所有的插件抽象成一个模块的Id
     */
    public static final int MODULE_ID_PLUGIN = 15 << 22;

    /**
     * 定向免流量id
     */
    public static final int MODULE_ID_TRAFFIC = 16 << 22;

    /**
     * 消息中心模块id
     */
    public static final int MODULE_ID_MESSAGE_DISPATCH = 17 << 22;

    /**
     * 客户从播放器业务分离的投屏模块id
     */
    public static final int MODULE_ID_QY_DLAN_MODULE = 18 << 22;

    /**
     * 弹幕模块id
     */
    public static final int MODULE_ID_DANMAKU_MODULE = 19 << 22;

    /**
     * RN模块id
     */
    public static final int MODULE_ID_REACT = 20 << 22;

    /**
     * 我的模块id
     */
    public static final int MODULE_ID_MYMAIN = 21 << 22;

    /**
     * QYPage模块id
     */
    public static final int MODULE_ID_QYPAGE = 22 << 22;

    /**
     * videovie模块id
     */
    public static final int MODULE_ID_VIDEOVIEW = 23 << 22;


    /**
     * debug center模块id
     */
    public static final int MODULE_ID_DEBUG_CENTER = 24 << 22;

    /**
     * aivoice模块id
     */
    public static final int MODULE_ID_AIVOICE_MODULE = 25 << 22;

    /**
     * 下载服务模块id
     */
    public static final int MODULE_ID_DOWNLOAD_SERVICE = 26 << 22;

    /**
     * 导航模块id
     */
    public static final int MODULE_ID_NAVIGATION_SERVICE = 27 << 22;
    /**
     * 基线竖屏播放器id
     */
    public static final int MODULE_ID_VERTICAL_PLAYER = 28 << 22;

    /**
     * 账号插件模块 id
     */
    public static final int MODULE_ID_PASSPORT_PLUGIN = 29 << 22;

    //------------------------------模块id end------------------------------------


}
