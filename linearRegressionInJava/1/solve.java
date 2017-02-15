import java.util.Random;

/**
 * @ClassName: solve
 * @Description: ʹ�øýṹ��T2��T3��վ¥��ʵ������������⡣
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
	
	//��Ϲ�ʽ�Ķ���
	public double function(double[] coefficients, double x){
		double f_ret = 0;
        for (int i = coefficients.length - 1; i >= 0; i--)
        {
            f_ret *= x;
            f_ret += coefficients[i];
        }
        return f_ret;
	}
	
	/*��û���˽�����������Ŷӵ���Ӧ���ȶ��ߵ�*/
    private final int GOWITHOUTRESISTANCE = 3;

    private data_type[] re = new data_type[1500];
    private int numInRe = 0;

    //����������ϵ�ʱ�򣬲�Ҫѡȡ̫��ĵ㣬�Ἣ��Ӱ���㷨���ٶ�
    private final int limitIn = 50;//�������ֵ��������������ѡȡ�ĵ������
    private final int powerOfFormula = 4;//������Ϻ����Ľ���
    
    /**
     * data_store[][0]��¼����ͨ����ԭʼ���ݽ��м���õ���ֵ
     * data_store[][1]��¼����ͨ����ԭʼ���ݽ��в����õ���ֵ
     * data_store[][2]��¼����ͨ�������߽�����ϵõ��Ĺ���ֵ
     */
    public int[][] data_store = new int[1500][3];
    
    /**
     * ��T2��T3��վ¥ʵ���������й���Ĺؼ�����
     * @param realinq ͨ����ԭʼ���ݽ��м���õ���ֵ
     * @param inq ��ԭʼ���ݽ��в����õ���ֵ
     * @param peoplein ��������Ƿ��г˿;���
     * @param I T2��T3��վ¥��ڴ��÷���ͨ�������ļ�¼
     * @param O T2��T3��վ�ڳ��ڴ��÷���ͨ�������ļ�¼
     * @param hour ��ǰʱ�� Сʱ
     * @param minute ��ǰʱ�� ����
     * @return ͨ������õ��Ĺ�������
     * @throws Exception ������������������쳣
     */
    public ReturnVal calp(int realinq, int inq, boolean peoplein, allDataIn I, allDataOut O, int hour, int minute) throws Exception
    {
        //�������
        //��ͳ��������4����һ�����ʵĹ�0��
        if (4 == hour && 0 == minute)
        {
            inq = 0;
            numInRe = 0;//��յȴ���ϵĵ�
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

        //��û���ÿͽ���������£�����Ӧ������Ӧ�̶ȵļ���
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
