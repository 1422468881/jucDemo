import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//题目：一个初始值为零的变量，两个线程对其交替操作，一个加1一个减1，来5轮
class ShareData{
    private int number = 0;
    private Lock lock =new ReentrantLock();
    private Condition condition = lock.newCondition();

    public  void  increment() throws Exception{
        lock.lock();
          try{
              while (number!=0){
                  condition.await();
              }
              number++;
              System.out.println(Thread.currentThread().getName()+"\t"+number);
              condition.signalAll();
          }catch (Exception e){

          }finally {
              lock.unlock();
          }


    }

    public  void  decrement() throws Exception{
        lock.lock();
        try{
            while (number==0){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            condition.signalAll();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }


    }
}
//传统的生成者和消费者
public class ProdConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(()->{
            for (int i = 1; i <=5 ; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 1; i <=5 ; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();
    }
}
