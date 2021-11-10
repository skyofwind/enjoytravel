-dontoptimize

# Some methods are only called from tests, so make sure the shrinker keeps them.
-keep class com.example.android.architecture.blueprints.** { *; }

-keep class androidx.drawerlayout.widget.DrawerLayout { *; }
-keep class androidx.test.espresso.**
# keep the class and specified members from being removed or renamed
-keep class androidx.test.espresso.IdlingRegistry { *; }
-keep class androidx.test.espresso.IdlingResource { *; }

-keep class com.google.common.base.Preconditions { *; }

-keep class androidx.room.RoomDataBase { *; }
-keep class androidx.room.Room { *; }
-keep class android.arch.** { *; }

# Proguard rules that are applied to your test apk/code.
-ignorewarnings

-keepattributes *Annotation*

-dontnote junit.framework.**
-dontnote junit.runner.**

-dontwarn androidx.test.**
-dontwarn org.junit.**
-dontwarn org.hamcrest.**
-dontwarn com.squareup.javawriter.JavaWriter
# Uncomment this if you use Mockito
-dontwarn org.mockito.**

#3D地图 V5.0.0之后：
-keep class com.amap.api.maps.**{*;}
-keep class com.autonavi.**{*;}
-keep class com.amap.api.trace.**{*;}

#定位：
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

#导航 V7.3.0及以后：
-keep class com.amap.api.navi.**{*;}
-keep class com.alibaba.mit.alitts.*{*;}
-keep class com.google.**{*;}

#搜索：
-keep class com.amap.api.services.**{*;}