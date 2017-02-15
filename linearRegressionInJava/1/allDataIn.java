import java.util.Random;

/**
 * @ClassName: allDataIn
 * @Description: 使用该结构对T2，T3航站楼入口的流量进行记录。
 * @author Sirui Lin
 * @date 20160808
 */
public class allDataIn {
	/**
	 * 对于T3航站楼，每个值代表的意义如下：
	 * recordOfAllEntry[0]和recordOfAllEntry[1]分别表示”00012412”这个入口的南北向方向人数
	 * recordOfAllEntry[2]和recordOfAllEntry[3]分别表示”00005978”这个入口的南北向方向人数
	 */
	public int[] recordOfAllEntry;
	
    public allDataIn()
    {
        recordOfAllEntry = new int[8];
    }
    public final int limitOfC = 10;
    
    /**
     * 对于入口点位
     * 外出方向突然增大的数值可能是工作人员推动行李车列反向通过入口时误判。
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
     * 计算该分钟内通过入口的总人数
     * @return 计算结果
     */
    public int calPeopleIn() {
    	int ret = 0;
    	for(int idxOfEntry = 1; idxOfEntry < 4; idxOfEntry += 2){
    		ret+=recordOfAllEntry[idxOfEntry^1]-recordOfAllEntry[idxOfEntry];
    	}
    	return ret;
    }
}
