package Demo;  
  
import java.io.IOException;  
  
import org.apache.zookeeper.CreateMode;  
import org.apache.zookeeper.KeeperException;  
import org.apache.zookeeper.WatchedEvent;  
import org.apache.zookeeper.Watcher;  
import org.apache.zookeeper.ZooDefs.Ids;  
import org.apache.zookeeper.ZooKeeper;  
  
public class MyZookeeperTest {  
     
  
     //回话超时时间，设置为与系统默认时间一致  
    private static final int SESSION_TIMEOUT = 30000;  
    //创建ZooKeeper实例  
    ZooKeeper zk;  
    //创建Watcher实例  
    Watcher wh = new Watcher() {  
      
       @Override  
       public void process(WatchedEvent event) {  
           System.out.println(event.toString());  
       }  
    };  
      
    //初始化ZooKeeper实例  
    private void createZKInstance() throws IOException {
    	 zk = new ZooKeeper("119.29.54.47:2181",MyZookeeperTest.SESSION_TIMEOUT,this.wh);
       //zk = new ZooKeeper("192.168.88.129:2181,192.168.88.129:2182,192.168.88.129:2183",MyZookeeperTest.SESSION_TIMEOUT,this.wh);  
    }  
      
    private void ZKOperations() throws KeeperException, InterruptedException {  
       System.out.println("\n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");  
       zk.create("/zoo2", "myData2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);  
       System.out.println("\n2.查看是否创建成功：");  
       System.out.println(new String(zk.getData("/zoo2", false, null)));  
       System.out.println("\n3.修改节点数据");  
       zk.setData("/zoo2", "toto".getBytes(), -1);  
       System.out.println("\n4.查看是否修改成功:");  
       System.out.println(new String(zk.getData("/zoo2", false, null)));  
       System.out.println("\n5.删除节点");  
       zk.delete("/zoo2", -1);  
       System.out.println("\n6.查看节点是否被删除：");  
       System.out.println("节点状态:[" + zk.exists("/zoo2", false) + "]");  
    }  
      
    private void ZKClose() throws InterruptedException {  
       zk.close();  
    }  
      
    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {  
    	MyZookeeperTest dm = new MyZookeeperTest();  
       dm.createZKInstance();  
       dm.ZKOperations();  
       dm.ZKClose();  
    }  
}  