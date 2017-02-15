import java.util.Random;

/**
 * @ClassName: solve
 * @Description: 使用该结构对T2，T3航站楼的实际人数进行求解。
 * @author Sirui Lin
 * @date 20160808
 */
public class solve {
	class data_type
    {
        public int tOfData;
        public int numberOfQue;
        public data_type(int tOfData,int numberOfQue)
        {
            this.tOfData = tOfData;
            this.numberOfQue = numberOfQue;
        }
    }
	
	//拟合公式的定义
	public double function(double[] coefficients, double x){
		double f_ret = 0;
        for (int i = coefficients.length - 1; i >= 0; i--)
        {
            f_ret *= x;
            f_ret += coefficients[i];
        }
        return f_ret;
	}
	
	/*在没有人进来的情况下排队的人应该稳定走掉*/
    private final int GOWITHOUTRESISTANCE = 3;

    private data_type[] re = new data_type[1500];
    private int numInRe = 0;

    //尝试曲线拟合的时候，不要选取太多的点，会极大影响算法的速度
    private final int limitIn = 50;//设置这个值方便更改曲线拟合选取的点的数量
    private final int powerOfFormula = 4;//设置拟合函数的阶数
    
    /**
     * data_store[][0]记录的是通过对原始数据进行计算得到的值
     * data_store[][1]记录的是通过对原始数据进行补偿得到的值
     * data_store[][2]记录的是通过对曲线进行拟合得到的估计值
     */
    public int[][] data_store = new int[1500][3];
    
    /**
     * 对T2、T3航站楼实际人数进行估算的关键函数
     * @param realinq 通过对原始数据进行计算得到的值
     * @param inq 对原始数据进行补偿得到的值
     * @param peoplein 这分钟内是否有乘客经过
     * @param I T2、T3航站楼入口处该分钟通过人数的记录
     * @param O T2、T3航站口出口处该分钟通过人数的记录
     * @param hour 当前时间 小时
     * @param minute 当前时间 分钟
     * @return 通过计算得到的估计人数
     * @throws Exception 矩阵或者拟合运算出现异常
     */
    public ReturnVal calp(int realinq, int inq, boolean peoplein, allDataIn I, allDataOut O, int hour, int minute) throws Exception
    {
        //归零调整
        //在统计意义上4点是一个合适的归0点
        if (4 == hour && 0 == minute)
        {
            inq = 0;
            numInRe = 0;//清空等待拟合的点
        }
        I.update();
        O.update1(inq);
        O.update2();

        int inp = 0, outp = 0;
        for (int idxOfEntry = 0; idxOfEntry < 4; idxOfEntry += 2)
        {
            inp += I.recordOfAllEntry[idxOfEntry] - I.recordOfAllEntry[idxOfEntry ^ 1];
        }
        for (int idxOfExit = 1; idxOfExit < 10; idxOfExit += 2)
        {
            //if(inp >= 20)tmpo = Math.min(tmpo, REASONP);
            outp += O.recordOfAllExit[idxOfExit] - O.recordOfAllExit[idxOfExit ^ 1];
        }
        //debug lsr;
        //if(0==hour&&minute<10){
        //	System.out.println("out:"+inp+" "+outp);
       // }

        //在没有旅客进来的情况下，人数应该有相应程度的减少
        Random rand = new Random();
        if(!peoplein && inq != 0) {
        	outp = Math.max(outp, rand.nextInt(GOWITHOUTRESISTANCE));
        }

        inq += inp;
        inq = Math.max(inq, 0);

        //if(inq >= 20)outp = Math.min(outp, REASONP);
        inq -= outp;
        inq = Math.max(inq, 0);

        double[] answer = new double[powerOfFormula];

        realinq += inp;
        realinq -= outp;
        re[numInRe++] = new data_type(hour*60+minute, realinq - inq);

        data_store[hour * 60 + minute][0] = realinq;
        data_store[hour * 60 + minute][1] = inq;
        if (numInRe < 6)
        {
            data_store[hour * 60 + minute][2] = inq;
        }
        else
        {
            int len = Math.min(limitIn, numInRe);
            double[] X = new double[len];
            double[] Y = new double[len];
            for (int jj = numInRe - len, kk = 0; jj < numInRe; jj++, kk++)
            {
                X[kk] = jj - (numInRe - len);
                Y[kk] = re[jj].numberOfQue;
            }
            GaussNewton gaussNewton = new GaussNewton(powerOfFormula);
            gaussNewton.Initialize(Y, X);
            answer = gaussNewton.getCoefficients();
            data_store[hour * 60 + minute][2] = data_store[hour * 60 + minute][0] - (int)(function(answer, X.length - 1));
            data_store[hour * 60 + minute][2] = Math.max(0, data_store[hour * 60 + minute][2]);
        }

        ReturnVal ret = new ReturnVal();

        if (inp != 0)
            ret.peoplein = true;
        else
            ret.peoplein = false;
        ret.inq = data_store[hour * 60 + minute][2];

        return ret;
    }
}
