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

## 目前存在的插件

## 开源库

## 测试名单

## 鸣谢
