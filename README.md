# FragmentBackHandler

[![](https://jitpack.io/v/mcxinyu/fragment-back-handler.svg)](https://jitpack.io/#mcxinyu/fragment-back-handler)

FragmentBackHandler 是一个 Fragment拦截Back键的一个库，仅需两步即可搞定，同时支持ViewPager、多Fragment以及Fragment嵌套。

origin from [FragmentBackHandler](https://github.com/ikidou/FragmentBackHandler)

## Download

[JitPack](https://jitpack.io/#mcxinyu/fragment-back-handler)

```gradle
allprojects {
    repositories {
    	...
    	maven { url 'https://jitpack.io' }
    }
}
dependencies {
    implementation 'com.github.mcxinyu:fragment-back-handler:tag'
}
```

## Usage

1、 在Activity中覆盖`onBackPressed()`方法

```kotlin
class MyActivity : FragmentActivity {
    override fun onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed()
        }
    }
}
```

2、实现 `FragmentBackHandler`

```kotlin
class MyFragment : Fragment, BackHandlerCallbacks {
    override fun onBackPressed() = handleBackPressed() || BackHandlerHelper.handleBackPress(this)

    private fun handleBackPressed(): Boolean {
        TODO("something you want to do")
    }
}
```

或让需要拦截 BackPress 的 Fragment 及父 Fragment 继承`BackHandledFragment`
