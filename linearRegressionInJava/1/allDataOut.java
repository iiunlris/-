/**
 * @ClassName: allDataOut
 * @Description: 使用该结构对T2，T3航站楼出口的流量进行记录。
 * @author: Sirui Lin
 * @date: 20160808
 */
public class allDataOut {
	public final int limitOfR = 5;
    public final int limitOfInq = 50;
    
    /**
     * 对于T3航站楼，每个值代表的意义如下：
     * recordOfAllExit[0]和recordOfAllExit[1]分别表示“00012413”这个出口的南北向方向通过人数
     * recordOfAllExit[2]和recordOfAllExit[3]分别表示“00013281”这个出口的南北向方向通过人数
     * recordOfAllExit[4]和recordOfAllExit[5]分别表示“00012423”这个出口的南北向方向通过人数
     * recordOfAllExit[6]和recordOfAllExit[7]分别表示“00012411”这个出口的南北向方向通过人数
     * recordOfAllExit[8]和recordOfAllExit[9]分别表示“00013282”这个出口的南北向方向通过人数
     */
    public int[] recordOfAllExit;
    
    public allDataOut()
    {
        recordOfAllExit = new int[14];
    }
    
    /**
     * @param inq 排队人数
     * 出口4和出口5是比较特殊的点位
     * 一般在排队人数不多的情况下不会轻易开启
     * 如果排队人数不多，但那两个口依旧有人数变动
     * 那应该是工作人数出入导致
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
     * 出口点位一般是等待的旅客离开等候区
     * 很少出现旅客反向进入等候区的现象
     * 主要是工作人员因指挥人流需要，在传感器周围徘徊造成误判。
     * 在进出口人数都很少的时候，从出口进入排队等候区的干扰数据可以剔除。
     */
    public void update2()
    {
        for (int idxOfExit = 0; idxOfExit < 6; idxOfExit += 2)
        {
            recordOfAllExit[idxOfExit] = 0;
        }
    }
    
    /**
     * 计算该分钟内通过出口的总人数
     * @return 计算结果
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
