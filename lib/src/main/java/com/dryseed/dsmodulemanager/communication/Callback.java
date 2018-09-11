package com.dryseed.dsmodulemanager.communication;

/**
 * Author:yuanzeyao<br/>
 * Date:16/6/30 上午9:47
 * Email:yuanzeyao@qiyi.com
 */
public abstract class Callback<T> {

    public abstract void onSuccess(T result);

    public void onFail(Object obj){

    }
}
