package SecondsKillingDemo.seckill;

import org.apache.http.HttpRequest;
/**
 * 预处理阶段，把不必要的请求直接驳回，必要的请求添加到队列中进入下一阶段.
 *
 *
 用户请求预处理模块

 经过HTTP服务器的分发后，单个服务器的负载相对低了一些，但总量依然可能很大，
 如果后台商品已经被秒杀完毕，那么直接给后来的请求返回秒杀失败即可，不必再进一步发送事务了，示例代码可以如下所示：
 */
public class PreProcessor {
    // 商品是否还有剩余
    private static boolean reminds = true;
    private static void forbidden() {
        // Do something.
    }
    public static boolean checkReminds() {
        if (reminds) {
            // 远程检测是否还有剩余，该RPC接口应由数据库服务器提供，不必完全严格检查.
            if (!RPC.checkReminds()) {
                reminds = false;
            }
        }
        return reminds;
    }
    /**
     * 每一个HTTP请求都要经过该预处理.
     */
    public static void preProcess(HttpRequest request) {
        if (checkReminds()) {
            // 一个并发的队列
            RequestQueue.queue.add(request);
        } else {
            // 如果已经没有商品了，则直接驳回请求即可.
            forbidden();
        }
    }
}