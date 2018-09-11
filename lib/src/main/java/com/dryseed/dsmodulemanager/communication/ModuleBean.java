package com.dryseed.dsmodulemanager.communication;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pools;

/**
 * Author:yuanzeyao<br/>
 * Date:16/6/27 下午4:16
 * Email:yuanzeyao@qiyi.com
 */
public class ModuleBean implements Parcelable {

    private static final Pools.SynchronizedPool<ModuleBean> POOL = new Pools.SynchronizedPool<>(10);
    /**
     * 行为action,主要由两部分构成(Module id | Action id)
     */
    protected int mAction;
    /**
     * 传递的参数数据
     */
    protected ArrayMap<String, Object> mExtra = new ArrayMap<>();

    protected String mModuleName;
    /**
     * 用于传递Event 数据
     *
     * @deprecated (since v9.5.5, MM 2.0 no longer use this field)
     */
    @Deprecated
    private Parcelable mEventData;

    public ModuleBean() {
    }

    protected ModuleBean(int moduleId, String name, int actionId) {
        mAction = moduleId | actionId;
        mModuleName = name;
    }

    public static ModuleBean obtain(int moduleId, int actionId) {
        return obtain(moduleId, null, actionId);
    }

    public static ModuleBean obtain(int moduleId, String name, int actionId) {
        ModuleBean bean = POOL.acquire();
        if (bean != null) {
            bean.mAction = moduleId | actionId;
            bean.mModuleName = name;
            bean.mEventData = null;
            bean.mExtra.clear();
        } else {
            bean = new ModuleBean(moduleId, name, actionId);
        }
        return bean;
    }

    public static void release(ModuleBean bean) {
        bean.mAction = 0;
        bean.mModuleName = null;
        bean.mEventData = null;
        bean.mExtra.clear();
        POOL.release(bean);
    }

    public void putArg(String key, Object value) {
        mExtra.put(key, value);
    }

    public Object getArg(String key) {
        return mExtra.get(key);
    }

    /**
     * 获取Action id
     */
    public int getAction() {
        return mAction & IModuleConstants.ACTION_MASK;
    }

    /**
     * 获取Module id
     */
    public int getModule() {
        return mAction & IModuleConstants.MODULE_MASK;
    }

    public String getModuleName() {
        return mModuleName;
    }

    /**
     * @deprecated (since v9.5.5, MM 2.0 no longer use this method)
     */
    @Deprecated
    public void setEventData(Parcelable mEventData) {
        this.mEventData = mEventData;
    }

    /**
     * @deprecated (since v9.5.5, MM 2.0 no longer use this method)
     */
    @Deprecated
    public Parcelable getEventData() {
        return mEventData;
    }

    public static final Creator<ModuleBean> CREATOR = new Creator<ModuleBean>() {
        @Override
        public ModuleBean createFromParcel(Parcel source) {
            return new ModuleBean(source);
        }

        @Override
        public ModuleBean[] newArray(int size) {
            return new ModuleBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mAction);
        dest.writeString(mModuleName);
        dest.writeParcelable(mEventData, flags);
        dest.writeMap(mExtra);
    }

    public ModuleBean(Parcel in) {
        mAction = in.readInt();
        mModuleName = in.readString();
        mEventData = in.readParcelable(ModuleBean.class.getClassLoader());
        in.readMap(mExtra, ModuleBean.class.getClassLoader());
    }

    @Override
    public String toString() {
        return "ModuleBean{" +
                "mAction=" + mAction +
                ", mExtra=" + mExtra +
                ", mModuleName='" + mModuleName + '\'' +
                ", mEventData=" + mEventData +
                '}';
    }

}
