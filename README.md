# EnjoyTravelBike

我享出行(管理端)
项目使用jetpack组件实现MVVM架构，
Navigation + Databinding + Viewmodel+ Hilt(项目使用到地方还很少) + Retrofit，
目前还没有用到Room,后期有需要的话可以加上。

## 导航
使用Navigation了，全局只有一个MainActivity,其他均为fragment,蓝湖里面的UI设计页面基本都写好了，自己在检查下哪里有缺漏或还需要哪些适配。
可查看`D:\androidstudiospace\EnjoyTravelBike\app\src\main\res\navigation\nav_travel_bike.xml`
页面导航都在里面，label里写了是哪个页面相关的。

Navigation的坑可以看下
https://blog.csdn.net/ByteDanceTech/article/details/120052166


## ViewModel作用域

如有需要，应该给每个Fragment都设置一个独立Fragment作用域的ViewModel存储属于自身的数据，用于全局共享的数据应放在Actvity作用域的ViewModel,首页绑定了NavigationView的menu的Fragment的ViewModel作用域要设置为Activity,因为点击菜单的跳转导航时NavController.navigate()的NavOptions设置了setLaunchSingleTop(true)，会导致在menu绑定的Fragment之间进行导航时，当前菜单的Fragment会走onDestroy销毁掉，下次再导航回来时候，需要Activity域的ViewModel来恢复数据。

还有关于LiveData粘性的问题，在Activity域的ViewModel中，需要使用到LiveData粘性时可以使用MutableLiveData，不需要粘性时可使用UnPeekLiveData，防止出现LiveData订阅接受多次的情况。

注意，只需要收到唯一一次的LiveData还是要用SingleLiveEvent,比如BlueToothViewModel中的数据，UnPeekLiveData好像只是有没有了LiveData的粘性，实践上多个页面注册了同一个BlueToothViewModel后，但其中只有一个页面observe了其中一项数据，期间
此项数据改变值后，回到observe数据的页面，将会收到同等页面数的通知（如连续打开四个页面，四个页面都注册了BlueToothViewModel，其中只有第一个页面observe了isConnect，当从第四个页面返回第一个页面时，isConnect收到了四次同一个通知，不知道我有没有搞错），这里可能会引起错误。

## 蓝牙相关

蓝牙相关的东西都放在`D:\androidstudiospace\EnjoyTravelBike\app\src\main\java\com\huishan\enjoytravel\bluetooth`包下，使用了FastBle，小安蓝牙协议的接口基本都实现了，除了少数感觉使用不到接口的响应实体没有解析具体数据，在需要使用的页面注册Activity域的BlueToothViewModel，通过调用BlueToothUtil选择具体接口的获取相应的字节数组，BlueToothViewModel.notifyBlueTooth()统一处理得到的结果并更新到LiveData中，在需要使用的页面中observe相应LiveData进行处理就行了。代码里都有注释，有问题的话自己看看蓝牙协议，看看有没有哪里写错了。

连接蓝牙设备所需mac地址应该只能用车辆编号请求接口获得，电动车那些到现在也没有实机测试样本(小安的是通过解析他们自己的设定IMEI码为MAC地址进行连接)。

## 网络请求

使用Retrofit + 协程，通过BikeRepositoryImpl的统一数据源来获取数据，后期有加入Room的话，也要在BikeRepositoryImpl中加入判断，看是什么时候该返回本地数据，什么时候该使用网络请求数据。还要注意下协程线程池相关会不会内存泄漏的问题。

## 其他
注意layout文件里有直接使用ViewModel数据对赋值和监听事件的

感觉后面每个页面的业务逻辑基本都是要重写的，因为接口都没调好，而且需求是很不明确定的，没有需求说明书，也没有产品经理，所以原型上写的东西也不是很清晰的。

界面相关的东西都放在了`D:\androidstudiospace\EnjoyTravelBike\app\src\main\java\com\huishan\enjoytravel\ui`包下

高德地图相关的操作放在了`D:\androidstudiospace\EnjoyTravelBike\app\src\main\java\com\huishan\enjoytravel\map`包下

数据相关的实体和来源放在了`D:\androidstudiospace\EnjoyTravelBike\app\src\main\java\com\huishan\enjoytravel\data`包下

还有一件事，蓝湖上的UI设计没有分类，看起来会很头痛

还有一件事，Navigation导航切换时，Databinding绑定的布局会出现内存泄漏，无法完全回收内存的情况，还没找到具体原因，有探究精神的话可以查一下，还有string和dimen资源那些，我懒得命名了，没有归入xml资源中

还有一件事，个人中心页面的UI要实现几个模糊效果，我还没搞
