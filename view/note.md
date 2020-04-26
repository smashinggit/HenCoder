# 笔记
****
## 基础
1. getX()，getY()返回的是触摸点A相对于view的位置
2. getRaw（）,getRawY()返回的是触摸点B相对于phone(屏幕）的位置。
3. scrollTo(x,y) 是将View中的内容移动到指定的坐标x,y处，此x,y是相对于View的左上角来说，而不上屏幕的左上角。
 当 x 大于0 时，代表view向左移动
 例如 当ScrollTo(-30,50)时，它从正中心移动到了右上方

4.  getScrollX() 和 getScrollY()，返回的是  mScrollX，mScrollY，
mScrollX 大于0时，代表view向左移动
mScrollX 小于0时，代表view向右移动
```
 postSendViewScrolledAccessibilityEventCallback(l - oldl, t - oldt);//传入坐标参数 -原有的偏移量
 假设 当前的 mScrollX = 100 ，调用 ScrollTo(-30,50) 后，
 当前的  mScrollX = -30 ，view的实际移动距离是 -30 - 100 = -130,即view从原有位置向右移动了130
 所以我们看到的实际效果是 view 向右移动了
```

5. scrollBy(x,y)  是在当前的 mScrollX，mScrollY基础上，再移动 x,y 
其源码为   scrollTo(mScrollX + x, mScrollY + y);

## 一、自定义View

### ScalableImageView
  双击放大、缩小，双指缩放的ImageView

1. GestureDetectorCompat 手势监测(双击、拖动、惯性滑动)  
2. ScaleGestureDetector  手指缩放
2. OverScroller  处理惯性滑动

### TwoPager
1. onInterceptTouchEvent 做判断，是否拦截事件
2. onTouchEvent 中做移动， 在ACTION_MOVE事件中根据移动距离 使用 scrollTo(dx.toInt(), 0) 移动
3. onTouchEvent 中做移动， 在ACTION_UP中计算速度，根据速度决定滑动到哪一屏，然后使用OverScroller的
  overScroller.startScroll(scrollX, 0, scrollDistance, 0) 开始滑动
4. 重写 View的computeScroll 方法，在其中使用  crollTo(dx.toInt(), 0) 方法平滑移动

## 二、拖拽(有两种实现方式)

当一个View被拖拽时，页面中所有可见的view都会收到相关的拖拽方法回调

### ViewDragHelper ()
场景：横向、纵向滑动
关注点：
特点


### OnDragListener (api 11 加入的)
场景：用户拖起来，放下
关注点：1.drag  2.drop
特点：可以携带数据， 可以跨进程

## 三、嵌套滑动
### 不同向滑动

### 同向滑动

场景1 ： ScrollView 嵌套 ScrollView 
需求 ：  子view能滑动时滑子view ，子view滑不动的时候(滑到底)滑父view
解决 ：  使用 NestedScrollView 替代 ScrollView

场景2 ： ScrollView 嵌套 ScrollView 
需求 ：  

- 父 view 展开时，
 - 上滑，优先父view，
 - 下滑，滑不动(所以可以说优先父view )
 
- 父 view 半展开时
 - 上滑，优先父view，滑到父view完全收起后滑动子view
 - 下滑，优先父view，滑到父view完全展开后滑不动
 
 - 父 view 收起时
  - 上滑，滑子view，
  - 下滑，优先滑子view，滑到子view顶部后滑动父view
            
解决 ：  使用 NestedScrollView 替代 ScrollView





