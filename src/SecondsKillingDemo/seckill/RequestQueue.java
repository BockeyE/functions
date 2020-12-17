package SecondsKillingDemo.seckill;

import org.apache.http.HttpRequest;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestQueue {
    public static ConcurrentLinkedQueue<HttpRequest> queue = new ConcurrentLinkedQueue<HttpRequest>();
}