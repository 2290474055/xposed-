package com.example.xposeddemo;



import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dalvik.system.DexClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author Zhenxi on 2020-07-03
 */
public class LHook implements IXposedHookLoadPackage {
    static String Tag = "zeyu";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(lpparam.packageName.startsWith("com.dragonli.projectsnow.lhm")) {
            Log.e(Tag,"load so");
            System.load("/data/app/~~paQ79ewYUV1kqi0p5dR4WA==/com.dragonli.projectsnow.lhm-k9nmAJzmAXyVXcfzvhmMLQ==/lib/arm64/libpatch_arm.so");
            //System.load("/data/app/~~YzzECQs8ecVu05xTVdF-6w==/com.square_enix.android_googleplay.EngageKill_j-0TGPm6r8WOjaF_Rg0tV54Q==/lib/arm64/libpatch_svc.so");
            //System.load("/system/lib/arm/libpatch_arm64.so");
        }

        if (lpparam.processName.equals("com.dragonli.projectsnow.lhm")) {
            Log.e("zeyu", "发现app");
            starthook(lpparam);
        }
    }

    static ClassLoader classLoad = null;
    public boolean ones = true;
    private void starthook(final XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException, InterruptedException {
//        Thread.sleep(1000*30);
//        XposedHelpers.findAndHookMethod("java.lang.Class",lpparam.classLoader,"forName",String.class,boolean.class,ClassLoader.class,new XC_MethodHook() {
//            protected void beforeHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
//                String ClassName = (String)param.args[0];
//                ClassLoader me = (ClassLoader)param.args[2];
//                if(((ClassName.indexOf("com.google.firebase.analytics.connector.internal")) != -1) && (ones == true)){
//                    Log.e(Tag, "yes eee");
//                    XposedHelpers.findAndHookMethod("com.google.android.gms.internal.consent_sdk.zzbz",me,"zza",new XC_MethodHook() {
//                    protected void beforeHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
//                            Log.e(Tag, "zzbz zza is Emulator");
//                        }
//                    });
//                    XposedHelpers.findAndHookMethod("com.tencent.hawk.bridge.HawkNative",me,"checkEmulator",String.class,String.class,new XC_MethodHook() {
//                    protected void beforeHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
//                            Log.e(Tag, "HawkNative checkEmulator");
//                        }
//                    });
//                    XposedHelpers.findAndHookMethod("com.ihoc.mgpa.toolkit.util.DeviceUtil",me,"isRunningOnEmulator",new XC_MethodHook() {
//                    protected void beforeHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
//                            Log.e(Tag, "DeviceUtil isRunningOnEmulator");
//                        }
//                    });
//                    XposedHelpers.findAndHookMethod("com.facebook.appevents.internal.AppEventUtility",me,"isEmulator",new XC_MethodHook() {
//                    protected void beforeHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
//                            Log.e(Tag, "AppEventUtility isEmulator");
//                        }
//                    });
//                    XposedHelpers.findAndHookMethod("com.tencent.imsdk.android.tools.log.FileLogger",me,"isEmulator",new XC_MethodHook() {
//                    protected void beforeHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
//                            Log.e(Tag, "FileLogger isEmulator");
//                        }
//                    });
//                    XposedHelpers.findAndHookMethod("com.tencent.hawk.bridge.QccConfig",me,"isEmulator",String.class,String.class,new XC_MethodHook() {
//                    protected void beforeHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
//                            Log.e(Tag, "QccConfig isEmulator");
//                        }
//                    });
//                    XposedHelpers.findAndHookMethod("com.tencent.hawk.bridge.QccHandlerSync",me,"isEmulator",String.class,String.class,new XC_MethodHook() {
//                    protected void beforeHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
//                            Log.e(Tag, "QccHandlerSync isEmulator");
//                        }
//                    });
//                }
//                Log.e(Tag, "Class forName ClassName:"+ClassName);
//            }
//        });
//        XposedHelpers.findAndHookConstructor("java.net.URL", lpparam.classLoader,String.class,new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                // 在构造函数执行之前执行的代码
//                String str = (String)param.args[0];
//                Log.e(Tag, "URL init URL:"+str);
//                //PrintStack();
//            }
//        });

        //Log.e(Tag,"Runtime beforeHookedMethod loadLibrary0 LibName:");
        XposedHelpers.findAndHookMethod("java.lang.Runtime",lpparam.classLoader,"loadLibrary0", Class.class,String.class,new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // 在构造函数执行之前执行的代码
                String LibName = (String)param.args[1];
                Log.e(Tag,"Runtime beforeHookedMethod loadLibrary0 LibName:"+LibName);
                //PrintStack();
                if((LibName.indexOf("tprt")) != -1){
                    Log.e(Tag,"Runtime beforeHookedMethod loadLibrary0 tersafe2");
                    XposedHelpers.findAndHookMethod("com.tencent.tp.l",lpparam.classLoader,"a",String.class,new XC_MethodHook() {
                        protected void afterHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
                            String ret = (String)param.getResult();
                            String arg1 = (String)param.args[0];
                            Log.e(Tag, "l a arg1:"+arg1+"   jeimi:"+ret);
                            PrintStack();
                        }
                    });

                    XposedHelpers.findAndHookMethod("com.tencent.tp.MainThreadDispatcher2",lpparam.classLoader,"doOnCmd",String.class,new XC_MethodHook() {
                        protected void beforeHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
                            String arg1 = (String)param.args[0];
                            Log.e(Tag, "MainThreadDispatcher2 doOnCmd:"+arg1);
                        }
                    });

                    XposedHelpers.findAndHookMethod("com.tencent.tp.TssJavaMethod",lpparam.classLoader,"sendCmdEx",String.class,new XC_MethodHook() {
                        protected void beforeHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
                            String arg1 = (String)param.args[0];
                            Log.e(Tag, "TssJavaMethod sendCmdEx arg1:"+arg1);
                            //PrintStack();
                        }
                    });

                    XposedHelpers.findAndHookMethod("com.tencent.tp.TssJavaMethod",lpparam.classLoader,"sendCmdEx",String.class,new XC_MethodHook() {
                        protected void beforeHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
                            String arg1 = (String)param.args[0];
                            Log.e(Tag, "TssJavaMethod sendCmdEx arg1:"+arg1);
                            //PrintStack();
                        }
                    });
                }
            }
        });

//        XposedHelpers.findAndHookMethod("org.json.JSONObject", lpparam.classLoader,"put",String.class,Object.class,new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                // 在构造函数执行之前执行的代码
//                String str = (String)param.args[0];
//                Object key = param.args[1];
//
//                Log.e(Tag, "JSONObject put:"+str+"   key:"+key +"  type:"+key.getClass().getName());
//                String device = "device_ext";
//                if((str.equals(device))) {
//                    param.args[1] = null;
//                }
//            }
//        });



        Log.e("zeyu", "hook end");
    }
    static void PrintStack(){
        Log.i("Dump Stack: ", "---------------start----------------");
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {

                Log.e("zeyu"+"Dump Stack"+i+": ", stackElements[i].getClassName()
                        +"----"+stackElements[i].getFileName()
                        +"----" + stackElements[i].getLineNumber()
                        +"----" +stackElements[i].getMethodName());
            }
        }
        Log.i("Dump Stack: ", "---------------over----------------");
    }
}
