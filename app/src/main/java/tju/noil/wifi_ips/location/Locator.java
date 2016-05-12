package tju.noil.wifi_ips.location;

import java.util.List;

import Jama.Matrix;
import tju.noil.wifi_ips.PreWifiInfo;

/**
 * Created by noil on 2016/5/10.
 */

public class Locator {
    private Matrix A;
    private Matrix B;
    private Matrix J;
    private Matrix J_inv;
    private Matrix J_tran;
    private Matrix Js;
    public double[][] arrayA;
    public double[] arrayB;
    public double[] tmpB;
    private double[] dis;
    public List<PreWifiInfo> list;
    public double[][] coordinates;
    private int m;
    private double height=1.1;
    private double[] fbeta;
    public int inter;
    private double minResidual=1.0E308;
    public double sumresidual;
    public Locator(List<PreWifiInfo> list,int m){
        this.list=list;
        this.m=m;
        coordinates=new double[m][2];
        for (int i=0;i<m;i++){
            coordinates[i][0]=list.get(i).pos.x;
            coordinates[i][1]=list.get(i).pos.y;
        }
        buildMatrixA();
        buildTmpB();
    }
    public double [][] Gauss_Newton(double[] beta,int iterations){
        Matrix tmp;
        Matrix mbeta;
        double[][] betas=new double[iterations][2];
        bulidMatrixJ(beta);
        betas[0]=beta;
        inter=iterations;
        for (int i = 1; i < iterations; i++) {
            mbeta=new Matrix(beta,2);
            tmp=Js.times(residual(beta));
            mbeta=mbeta.minus(tmp);
            beta=mbeta.getColumnPackedCopy();
            betas[i]=beta;
            //System.out.println(sumresidual+" "+minResidual);
            if(sumresidual<minResidual){
                minResidual=sumresidual;
                inter=i;
            }
            else if(sumresidual>minResidual*1.2){
                break;
            }
        }
        fbeta=betas[inter-1];
        return betas;
    }
    public void setListRSSI(double [] rssi){
        this.dis=new double[rssi.length];
        for (int i=0;i<rssi.length;i++){
            dis[i]=rssiToDistance(rssi[i]);
            list.get(i).RSSI=rssi[i];
            list.get(i).distance=dis[i];
        }
    }
    public double rssiToDistance(double rssi){
        double distance=0;
        if (rssi>-100){
            double mi=(-rssi-29.1925)/23.9258;
            distance=Math.pow(10,mi);
        }
        if(distance>height)
            distance=Math.sqrt(sq(distance)-sq(height));
        else
            distance=0;
        return distance;
    }
    public double[] leastSquares(){
        Matrix X;
        buildMatrixB();
        X=A.solve(B);
        return X.getColumnPackedCopy();
    }
    public void buildMatrixA(){
        arrayA=new double[m-1][2];
        for (int i=0;i<m-1;i++){
            arrayA[i][0]=2*(coordinates[i][0]-coordinates[m-1][0]);
            arrayA[i][1]=2*(coordinates[i][1]-coordinates[m-1][1]);
        }
        A=new Matrix(arrayA);
    }
    public void buildTmpB(){
        tmpB=new double[m-1];
        for (int i=0;i<m-1;i++){
            tmpB[i]=sq(coordinates[i][0])+sq(coordinates[i][1])-sq(coordinates[m-1][0])-sq(coordinates[m-1][1]);
        }
    }
    public void buildMatrixB(){
        arrayB=new double[m-1];
        for (int i=0;i<m-1;i++){
            arrayB[i]=tmpB[i]+sq(dis[m-1])-sq(dis[i]);
        }
        B=new Matrix(arrayB,m-1);
    }
    public void bulidMatrixJ(double[] beta){
        double[][] arrayJ=new double[m][2];
        for (int i = 0; i < m; i++) {
            arrayJ[i][0]=2*(beta[0]-coordinates[i][0]);
            arrayJ[i][1]=2*(beta[1]-coordinates[i][1]);
        }
        J=new Matrix(arrayJ);
        J_inv=J.inverse();
        J_tran=J.transpose();
        Js=J_tran.times(J).inverse().times(J_inv);
    }
    public double sq(double x){
        return x*x;
    }
    public Matrix residual(double[] beta){
        double[] r=new double[m];
        sumresidual=0;
        for (int i = 0; i < m; i++) {
            r[i]=residualFunction(beta,i);
            sumresidual+=r[i]*r[i];
        }
        return new Matrix(r,m);
    }
    public double residualFunction(double[] beta,int i){
        double result;
        result=sq(beta[0] - coordinates[i][0])+sq(beta[1]-coordinates[i][1])-sq(dis[i]);
        return result;
    }
    public double[] getfbeta(){
        return fbeta;
    }
    public double getResidualSum(double[] beta){
        double result=0;
        double[] r=new double[m];
        for (int i = 0; i < m; i++) {
            r[i]=residualFunction(beta,i);
            result+=r[i]*r[i];
        }
        return result;
    }
}





