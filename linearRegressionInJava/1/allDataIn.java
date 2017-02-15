import java.util.Random;

/**
 * @ClassName: allDataIn
 * @Description: ʹ�øýṹ��T2��T3��վ¥��ڵ��������м�¼��
 * @author Sirui Lin
 * @date 20160808
 */
public class allDataIn {
	/**
	 * ����T3��վ¥��ÿ��ֵ������������£�
	 * recordOfAllEntry[0]��recordOfAllEntry[1]�ֱ��ʾ��00012412�������ڵ��ϱ���������
	 * recordOfAllEntry[2]��recordOfAllEntry[3]�ֱ��ʾ��00005978�������ڵ��ϱ���������
	 */
	public int[] recordOfAllEntry;
	
    public allDataIn()
    {
        recordOfAllEntry = new int[8];
    }
    public final int limitOfC = 10;
    
    /**
     * ������ڵ�λ
     * �������ͻȻ�������ֵ�����ǹ�����Ա�ƶ�����з���ͨ�����ʱ���С�
     */
    public void update()
    {
        Random rand = new Random();
        for (int idxOfEntry = 1; idxOfEntry < 4; idxOfEntry += 2)
        {
            if (recordOfAllEntry[idxOfEntry] > limitOfC)
            {
                recordOfAllEntry[idxOfEntry] -= rand.nextInt(recordOfAllEntry[idxOfEntry]);
            }
        }
    }
    
    /**
     * ����÷�����ͨ����ڵ�������
     * @return ������
     */
    public int calPeopleIn() {
    	int ret = 0;
    	for(int idxOfEntry = 1; idxOfEntry < 4; idxOfEntry += 2){
    		ret+=recordOfAllEntry[idxOfEntry^1]-recordOfAllEntry[idxOfEntry];
    	}
    	return ret;
    }
}
