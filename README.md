# RocoGuide
Compose实现的洛克王国攻略

## 数据
本项目数据来自:
- [百度百科](https://baike.baidu.com/item/%E6%B4%9B%E5%85%8B%E7%8E%8B%E5%9B%BD%E5%AE%A0%E7%89%A9%E5%A4%A7%E5%85%A8/4962564)
- [4399技能查询](http://news.4399.com/luoke/jinengsearch/)
- [洛克王国论坛](https://17roco.gamebbs.qq.com/)
- 洛克王国官方公众号
- 精灵图鉴资源链接1: http://res.17roco.qq.com/res/combat/previews/编号-idle.swf
- 精灵图鉴资源链接2: https://res.17roco.qq.com/res/combat/icons/编号-.png
- BGM资源链接: http://res.17roco.qq.com/res/music/new_编号(01-103).swf
   - 可能有新增,本条链接来自论坛

### 我想自己抓取资源
#### 前提
分为两步: 提取和解析,您需要准备:  
1. JPEXS Free Flash Decompiler
2. 建议下载360浏览器,下述步骤均为360浏览器调试
#### 提取
1. 进入洛克王国官网
2. 键盘按下F12
3. 选择[网络]
4. 进入游戏后进行操作(查看精灵等)
5. 在控制台会有相应`swf`或`xml`文件输出
6. 将`swf`文件下载(链接复制后在浏览器打开一个新页面粘贴后回车)
7. 将下载好的swf文件放入上述前提1的程序中即可
#### 解析
以精灵图鉴资源链接为例,将`编号`改为`100`,[下载](http://res.17roco.qq.com/res/combat/previews/100-idle.swf)对应的精灵swf后,在  
1. 打开前提1的程序,选择打开下载的swf;  
![1](https://github.com/taxeric/RocoGuide/blob/master/screenshot/analyze_1.png)
2. 点击左边的`精灵`子项后可以看到精灵画面,鼠标右键,选择导出已选;  
![2](https://github.com/taxeric/RocoGuide/blob/master/screenshot/analyze_2.png)
3. 保存为png;  
![3](https://github.com/taxeric/RocoGuide/blob/master/screenshot/analyze_3.png)

其他资源与之类似,您可以提取您想要的任何资源.例如,您想要提取背景音乐,保存swf后,找到对应的`子项`(可能是音频),选择保存为`.mp3`即可

## 开源库
- [Compose-WebView: 适用于Compose的浏览器控件](https://google.github.io/accompanist/webview/)
- [Compose-refresh: 适用于Compose的下拉刷新框架](https://google.github.io/accompanist/swiperefresh/)
- [Compose-paging3: 适用于Compose的数据分页框架](https://developer.android.google.cn/topic/libraries/architecture/paging/v3-overview)
- [Compose-navigation: 适用于Compose的路由框架](https://developer.android.google.cn/guide/navigation/navigation-getting-started)
- [Coil: 基于kt的图片加载库](https://github.com/coil-kt/coil/blob/main/README-zh.md)
- Compose-constraintlayout: 适用于Compose的约束布局
- [Compose-glance: 适用于Compose的小部件库](https://developer.android.com/reference/kotlin/androidx/glance/package-summary)
- [WorkManager: 适用用于持久性工作的推荐解决方案](https://developer.android.com/topic/libraries/architecture/workmanager)
- [Jsoup: 适用于Java的html解析器](https://jsoup.org/)

## 测试名单
- [bbyyxx2](https://github.com/bbyyxx2)
- [XiaoAnXA](https://github.com/XiaoAnXA)
- love****fe61(惊鸿照影)
- shen****i111(琳)

## 鸣谢
- [XiaoAnXA](https://github.com/XiaoAnXA),为本项目提供接口支持

## 推荐阅读
- compose开发助手
  - [文档](https://google.github.io/accompanist/)
  - [版本](https://search.maven.org/search?q=g:com.google.accompanist)

