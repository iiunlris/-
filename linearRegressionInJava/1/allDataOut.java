/**
 * @ClassName: allDataOut
 * @Description: ʹ�øýṹ��T2��T3��վ¥���ڵ��������м�¼��
 * @author: Sirui Lin
 * @date: 20160808
 */
public class allDataOut {
	public final int limitOfR = 5;
    public final int limitOfInq = 50;
    
    /**
     * ����T3��վ¥��ÿ��ֵ������������£�
     * recordOfAllExit[0]��recordOfAllExit[1]�ֱ��ʾ��00012413��������ڵ��ϱ�����ͨ������
     * recordOfAllExit[2]��recordOfAllExit[3]�ֱ��ʾ��00013281��������ڵ��ϱ�����ͨ������
     * recordOfAllExit[4]��recordOfAllExit[5]�ֱ��ʾ��00012423��������ڵ��ϱ�����ͨ������
     * recordOfAllExit[6]��recordOfAllExit[7]�ֱ��ʾ��00012411��������ڵ��ϱ�����ͨ������
     * recordOfAllExit[8]��recordOfAllExit[9]�ֱ��ʾ��00013282��������ڵ��ϱ�����ͨ������
     */
    public int[] recordOfAllExit;
    
    public allDataOut()
    {
        recordOfAllExit = new int[14];
    }
    
    /**
     * @param inq �Ŷ�����
     * ����4�ͳ���5�ǱȽ�����ĵ�λ
     * һ�����Ŷ��������������²������׿���
     * ����Ŷ��������࣬���������������������䶯
     * ��Ӧ���ǹ����������뵼��
     */
    public void update1(int inq)
    {
        if (inq >= limitOfInq) return;
        for (int idxOfExit = 6; idxOfExit < 10; idxOfExit += 2)
        {
            recordOfAllExit[idxOfExit] = 0;
        }
    }
    
    /**
     * ���ڵ�λһ���ǵȴ����ÿ��뿪�Ⱥ���
     * ���ٳ����ÿͷ������Ⱥ���������
     * ��Ҫ�ǹ�����Ա��ָ��������Ҫ���ڴ�������Χ�ǻ�������С�
     * �ڽ��������������ٵ�ʱ�򣬴ӳ��ڽ����ŶӵȺ����ĸ������ݿ����޳���
     */
    public void update2()
    {
        for (int idxOfExit = 0; idxOfExit < 6; idxOfExit += 2)
        {
            recordOfAllExit[idxOfExit] = 0;
        }
    }
    
    /**
     * ����÷�����ͨ�����ڵ�������
     * @return ������
     */
    public int calPeopleOut(){
    	int ret=0;
    	for(int idxOfExit = 0; idxOfExit < 10; idxOfExit += 2)
    	{
    		ret += recordOfAllExit[idxOfExit^1] - recordOfAllExit[idxOfExit];
    	}
    	return ret;
    }
}
