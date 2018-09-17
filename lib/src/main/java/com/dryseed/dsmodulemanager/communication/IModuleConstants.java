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
     * QYPage模块
     */
    public static final String MODULE_NAME_QYPAGE = "qypage";

    //----------------------------模块名称 end------------------------------------

    //------------------------------模块id start---------------------------------

    public static final int MODULE_MASK = 2047 << 22;
    public static final int ACTION_MASK = (1 << 22) - 1;

    /**
     * QYPage模块id
     */
    public static final int MODULE_ID_QYPAGE = 1 << 22;

    //------------------------------模块id end------------------------------------


}
