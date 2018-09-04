package rabbit.sender.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private TopicSender topicSender;

    static class Runner implements Runnable {
        private CyclicBarrier barrier;
        private String name;
        private TopicSender topicSender;
        private static volatile AtomicLong beginTime = new AtomicLong(0L);

        public Runner(CyclicBarrier barrier, String name,TopicSender topicSender) {
            this.barrier = barrier;
            this.name = name;
            this.topicSender = topicSender;
        }
        @Override
        public void run() {
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            if (beginTime.get() == 0){
                beginTime.getAndSet(System.currentTimeMillis());
            }
            logger.info(name + " 准备OK 开始执行....{}");
            for (int i = 0; i < 1000; i++) {
                topicSender.send();
            }
        }
    }

    @GetMapping("/test1")
    public Object test1() throws Exception{
        CyclicBarrier barrier = new CyclicBarrier(10000);
        ExecutorService executor = Executors.newFixedThreadPool(10000);
        for (int i = 0; i < 10000; i++) {
            executor.submit(new Thread(new Runner(barrier,"线程"+i,topicSender)));
        }

        executor.shutdown();
        long  end;
        while(true){
            if(executor.isTerminated()){
                end = System.currentTimeMillis();
                logger.info("------------所有的子线程都结束了！begin={} end={}",Runner.beginTime,end);
                break;
            }
            Thread.sleep(1000);
        }
        logger.info("+++++++++ 执行的时间差 = {}",(end-Runner.beginTime.get())/1000);

        executor.shutdown();
        return "success";
    }
}
