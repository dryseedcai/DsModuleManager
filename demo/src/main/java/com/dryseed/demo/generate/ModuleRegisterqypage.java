package com.dryseed.demo.generate;

import android.content.Context;


import com.dryseed.demo.config.QYPageModule;
import com.dryseed.dsmodulemanager.ModuleManager;

import java.util.Arrays;
import java.util.List;

public class ModuleRegisterqypage {
  public static void registerModules(Context context, String hostProcessName) {
    String[] process = new String[]{hostProcessName};
    List<String> processList = Arrays.asList(process);
    if (processList.contains(ModuleManager.getCurrentProcessName())) {
      ModuleManager.registerModule("qypage", QYPageModule.getInstance());
    }
  }

  public static void registerModules(Context context) {
    registerModules(context, ModuleManager.getCurrentProcessName());
  }
}
