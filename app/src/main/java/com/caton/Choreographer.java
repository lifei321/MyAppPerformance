package com.caton;


/**********
 *
 *
 * Android消息机制的重要性：
 * 1.在卡顿监测会用到消息机制；主要是发送一个延时消息来监测是否，在执行时间内没有remove该消息就代码APP发生卡顿；
 * 2.ANR监测也是通过发送一个延时消息来监测是否发生ANR；ANR是APP卡顿的极端情况；
 * 3.View监测事件是否长按也用到消息机制，在发生Down的时候会发送一个延时消息，在Up的时候会将该消息Remove掉，如果指定的时间没有发生UP就会触发长按事件；
 * 4.Choreographer在渲染每一帧的时候也是通过发送一个消息，然后在Looper.loop中处理下一个消息时才会去渲染下一帧；
 * 5.Activity生命周期的控制也是在ActivityThread发送不同的消息来切换Activity生命周期；
 * 6.消息机制可以将一个任务切换到其它指定的线程，如AsyncTask；
 * 以上这些场景都用到Android消息机制，还有很多其他未知的场景可能也会用到Android消息机制，所以消息机制在Android中具有很重要的地位；
 *
 * 作者：htkeepmoving
 * 链接：https://www.jianshu.com/p/9e8f88eac490
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */

public class Choreographer {
}
