package tju.noil.wifi_ips;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

import java.text.DecimalFormat;
import java.util.List;
import tju.noil.wifi_ips.location.Locator;

/**
 * Created by noil on 2016/5/7.
 */
public class wifiAdmin {
    //先验WIFI信息列表
    private PreWifiInfoList preWifiInfoList;
    //定位器
    private Locator locator;
    //定义WifiManager对象
    private WifiManager mWifiManager;
    //定义WifiInfo对象
    private List<ScanResult> mWifiList;
    //RSSI数组
    private double[] rssi;
    private double [] solution;
    private double [][] result;
    //构造函数
    public  wifiAdmin(Context context)
    {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }
    //打开WIFI
    public void OpenWifi()
    {
        if (!mWifiManager.isWifiEnabled())
        {
            mWifiManager.setWifiEnabled(true);
        }
    }
    //关闭WIFI
    public void CloseWifi()
    {
        if (!mWifiManager.isWifiEnabled())
        {
            mWifiManager.setWifiEnabled(false);
        }
    }
    //开始扫描
    public void StartScan()
    {
        //本段程序在通用环境下应该选择最强信号源作为DME
        preWifiInfoList = new PreWifiInfoList();
        preWifiInfoList.defaultInit();

        ReScan();
    }
    public void ReScan()
    {
        locator = new Locator(preWifiInfoList.list,preWifiInfoList.max_pre);
        mWifiManager.startScan();
        //得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        rssi=new double[3];
        for(ScanResult scanResult : mWifiList){
            if(scanResult.BSSID.equals(preWifiInfoList.list.get(0).BSSID)){
                //preWifiInfoList.setIRSSI(0,scanResult.level);
                rssi[0]=scanResult.level;
            }
            else if(scanResult.BSSID.equals(preWifiInfoList.list.get(1).BSSID)){
                //preWifiInfoList.setIRSSI(1,scanResult.level);
                rssi[1]=scanResult.level;
            }
            else if(scanResult.BSSID.equals(preWifiInfoList.list.get(2).BSSID)){
                //preWifiInfoList.setIRSSI(2,scanResult.level);
                rssi[2]=scanResult.level;
            }
        }
        locator.setListRSSI(rssi);
        solution=locator.leastSquares();
        result=locator.Gauss_Newton(solution,200000);
        solution=locator.getfbeta();
    }
    //得到网络列表
    public List<ScanResult> GetWifiList()
    {
        return mWifiList;
    }
    //查看扫描结果
    public StringBuilder LookUptarget()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < solution.length; i++) {
            stringBuilder.append(df.format(solution[i])+" ");
        }
        stringBuilder.append("\n");
        stringBuilder.append(locator.list.get(0).RSSI+" "+locator.list.get(1).RSSI+" "+locator.list.get(2).RSSI+"\n");
        stringBuilder.append(df.format(locator.list.get(0).distance)+" "+df.format(locator.list.get(1).distance)+" "+df.format(locator.list.get(2).distance));
        stringBuilder.append("\n");
        return stringBuilder;
    }
    //查看扫描结果
    public StringBuilder LookUpScan()
    {

        DecimalFormat df = new DecimalFormat("#.00");
        StringBuilder stringBuilder = new StringBuilder();
        double sum=0;
        for (int i = 0; i < locator.inter; i+=1000) {
            sum=0;
            for (int j = 0; j < result[0].length; j++) {
                stringBuilder.append(df.format(result[i][j]) + " ");
            }
            double[] r=locator.residual(result[i]).getColumnPackedCopy();
            stringBuilder.append(df.format(locator.getResidualSum(r))+" \n");
        }

//        for (int i = 0; i < locator.arrayA.length; i++) {
//            for (int j = 0; j < locator.arrayA[0].length; j++) {
//                stringBuilder.append(df.format(locator.arrayA[i][j])+" ");
//            }
//            stringBuilder.append("\n\n");
//        }
//        for (int i = 0; i < locator.tmpB.length; i++) {
//            stringBuilder.append(df.format(locator.tmpB[i])+" ");
//        }
//        stringBuilder.append("\n\n");
//        for (int i = 0; i < locator.arrayB.length; i++) {
//            stringBuilder.append(df.format(locator.arrayB[i])+" ");
//        }
//        for (ScanResult scanResult : mWifiList) {
//            stringBuilder.append("\nSSID: "+scanResult.SSID+"\nBSSID: "+scanResult.BSSID
//                    +"\nRSSI: "+scanResult.level+"\nLEVEL: "+mWifiManager.calculateSignalLevel(scanResult.level,4));
//            stringBuilder.append("\n");
//        }
        return stringBuilder;
    }
}
