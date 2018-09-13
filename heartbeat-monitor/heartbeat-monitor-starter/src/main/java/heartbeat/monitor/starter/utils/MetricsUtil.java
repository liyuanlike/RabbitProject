package heartbeat.monitor.starter.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;

import com.sun.management.OperatingSystemMXBean;
/**
 * @apiNote
 */
public class MetricsUtil {

    /**
     * @apiNote 获取当前系统的内存占用率
     * @return
     */
    public static double getMemoryPercent(){
        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println("Total RAM：" + mem.getTotalPhysicalMemorySize() / 1024 / 1024 + "MB");
        System.out.println("Available　RAM：" + mem.getFreePhysicalMemorySize() / 1024 / 1024 + "MB");
        return (mem.getTotalPhysicalMemorySize() - mem.getFreePhysicalMemorySize())/(mem.getTotalPhysicalMemorySize()*1.0);
    }

    /**
     * @apiNote 获取本地IP
     * @return
     */
    public static String getLocalIp(){
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress().toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ip;
    }


    public static void main(String[] args){
        System.out.println(getMemoryPercent());
        System.out.println(getLocalIp());
    }
}





