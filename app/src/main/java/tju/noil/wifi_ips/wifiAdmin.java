package tju.noil.wifi_ips;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

import java.util.List;

/**
 * Created by noil on 2016/5/7.
 */
public class wifiAdmin {

    private PreWifiInfoList preWifiInfoList;
    //定义WifiManager对象
    private WifiManager mWifiManager;
    //定义WifiInfo对象
    private WifiInfo mWifiInfo;
    //扫描出的网络连接列表
    private List<ScanResult> mWifiList;

    //构造函数
    public  wifiAdmin(Context context)
    {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        preWifiInfoList = new PreWifiInfoList();
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
        mWifiManager.startScan();
//得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        for(ScanResult scanResult : mWifiList){
            if(scanResult.BSSID.equals(preWifiInfoList.list.get(0).BSSID)){
                preWifiInfoList.setIRSSI(0,scanResult.level);
            }
            else if(scanResult.BSSID.equals(preWifiInfoList.list.get(1).BSSID)){
                preWifiInfoList.setIRSSI(1,scanResult.level);
            }
            else if(scanResult.BSSID.equals(preWifiInfoList.list.get(2).BSSID)){
                preWifiInfoList.setIRSSI(2,scanResult.level);
            }
        }

    }
    public void ReScan()
    {
        mWifiManager.startScan();
//得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        for(ScanResult scanResult : mWifiList){
            if(scanResult.BSSID.equals(preWifiInfoList.list.get(0).BSSID)){
                preWifiInfoList.setIRSSI(0,scanResult.level);
            }
            else if(scanResult.BSSID.equals(preWifiInfoList.list.get(1).BSSID)){
                preWifiInfoList.setIRSSI(1,scanResult.level);
            }
            else if(scanResult.BSSID.equals(preWifiInfoList.list.get(2).BSSID)){
                preWifiInfoList.setIRSSI(2,scanResult.level);
            }
        }
    }
    //得到网络列表
    public List<ScanResult> GetWifiList()
    {
        return mWifiList;
    }
    //查看扫描结果
    public StringBuilder LookUptarget()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(preWifiInfoList.list.get(0).RSSI+" "+preWifiInfoList.list.get(1).RSSI+" "+preWifiInfoList.list.get(2).RSSI);
        stringBuilder.append("\n");
        return stringBuilder;
    }
    //查看扫描结果
    public StringBuilder LookUpScan()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (ScanResult scanResult : mWifiList) {
            stringBuilder.append("\nSSID: "+scanResult.SSID+"\nBSSID: "+scanResult.BSSID
                    +"\nRSSI: "+scanResult.level+"\nLEVEL: "+mWifiManager.calculateSignalLevel(scanResult.level,4));
            stringBuilder.append("\n");
        }
        return stringBuilder;
    }
}
