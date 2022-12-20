import java.util.concurrent.CountDownLatch;

public class PracticeTest {
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(5);

        countDownLatch.countDown();

        countDownLatch.await();

        System.out.println("여기 실행 안될 듯");
    }
}
