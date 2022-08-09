# RocoGuide
Compose实现的洛克王国攻略

## 数据
本项目数据来自:
- [百度百科](https://baike.baidu.com/item/%E6%B4%9B%E5%85%8B%E7%8E%8B%E5%9B%BD%E5%AE%A0%E7%89%A9%E5%A4%A7%E5%85%A8/4962564)
- [4399技能查询](http://news.4399.com/luoke/jinengsearch/)
- 洛克王国官方公众号

## 插件开发
项目支持插件化开发，您应该:
1. 本地Compose项目调试至基本无异常
2. 克隆该项目
3. 在本地项目中引入克隆下来的项目内的`lib_plugin_base`模块
4. 将本地项目的`MainAcitity: ComponentActivity()`改为`MainActivity: IPluginActivityInterface()`
5. 将本地项目的`AndroidManifest.xml`的`MainActivity`相关节点注释
6. 项目完成后打包(apk格式),发送至xxx

**注1: 建议使用Compose开发，使用非Compose开发可能产生未经检查的异常**  
**注2: 您的compose相关依赖版本请与`lib_plugin_base`一致**

## 目前开发完成的插件

## 开源库
- [Compose-WebView: 适用于Compose的浏览器控件](https://google.github.io/accompanist/webview/)
- [Compose-refresh: 适用于Compose的下拉刷新框架](https://google.github.io/accompanist/swiperefresh/)
- [Compose-paging3: 适用于Compose的数据分页框架](https://developer.android.google.cn/topic/libraries/architecture/paging/v3-overview)
- [Compose-navigation: 适用于Compose的路由框架](https://developer.android.google.cn/guide/navigation/navigation-getting-started)
- [Coil: 基于kt的图片加载库](https://github.com/coil-kt/coil/blob/main/README-zh.md)
- Compose-constraintlayout: 适用于Compose的约束布局

## 测试名单
- [bbyyxx2](https://github.com/bbyyxx2)
- [XiaoAnXA](https://github.com/XiaoAnXA)

## 鸣谢
- [XiaoAnXA](https://github.com/XiaoAnXA),为本项目提供服务器数据及接口支持

## 推荐阅读
- compose开发助手
  - [文档](https://google.github.io/accompanist/)
  - [版本](https://search.maven.org/search?q=g:com.google.accompanist)

