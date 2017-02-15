import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class testall {//测试整个系统的运行
	public static class idxAndTerminal {
		public int idx, Terminal;
		public idxAndTerminal(int idx, int Terminal) {
			this.idx = idx;
			this.Terminal = Terminal;
		}
	}
	
	public static int strToi(String s) {
	    int ret = 0;
	    for(int i = 0; i < s.length(); i++) {
	    	char ch = s.charAt(i);
	        ret *= 10;
	        ret += ch - '0';
	    }
	    return ret;
	}
	
	public static allDataIn recordin[] = new allDataIn[2000];
	public static allDataOut recordout[] = new allDataOut[2000];
	static int totInQue[] = new int[1500];
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		//文件存放的目录
		String path="G:\\update\\data4";
		
		File file=new File(path);
		File[] tempList = file.listFiles();
		//System.out.println("该目录下对象个数："+tempList.length);
		
		Map<String, idxAndTerminal> tran = new HashMap<String, idxAndTerminal>();
		
		tran.put("00012412", new idxAndTerminal(1, 3));
		tran.put("00005978", new idxAndTerminal(1, 3));
		tran.put("00013281", new idxAndTerminal(0, 3));
		tran.put("00012411", new idxAndTerminal(0, 3));
		tran.put("00012413", new idxAndTerminal(0, 3));
		tran.put("00012423", new idxAndTerminal(0, 3));
		tran.put("00013282", new idxAndTerminal(0, 3));
		
		tran.put("00012160", new idxAndTerminal(1, 2));
		tran.put("00012414", new idxAndTerminal(0, 2));
		tran.put("00012394", new idxAndTerminal(1, 2));
		tran.put("00012405", new idxAndTerminal(1, 2));
		tran.put("00012404", new idxAndTerminal(0, 2));
		tran.put("00012415", new idxAndTerminal(0, 2));
		
		for (int i = 0; i < 1440; i++) {
			recordin[i] = new allDataIn();
			recordout[i] = new allDataOut();
		}
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				//System.out.println("文     件："+tempList[i]);
				Scanner cin = new Scanner(tempList[i]);
				while(cin.hasNext()) {
					String s = cin.nextLine();
		            String deviceNumber = s.substring(0, 8);
		            idxAndTerminal now = tran.get(deviceNumber);
		            int in = strToi(s.substring(22, 26)), out = strToi(s.substring(26, 30));
		            int hour = strToi(s.substring(16, 18)), minute = strToi(s.substring(18, 20));
		            if(0 == now.idx) {
		                /*出口*/
		            	int idx = 0;
		            	if(deviceNumber.equals(new String("00013281")))
		            		idx = 1;
		            	else if(deviceNumber.equals(new String("00012423")))
		            		idx = 2;
		            	else if(deviceNumber.equals(new String("00012411")))
		            		idx = 3;
		            	else if(deviceNumber.equals(new String("00013282")))
		            		idx = 4;
		            	//System.out.println(deviceNumber);
		            	//System.out.println(new String("00012411") == deviceNumber);
		                recordout[hour * 60 + minute].recordOfAllExit[idx << 1] = in;
		                recordout[hour * 60 + minute].recordOfAllExit[idx << 1 | 1] = out;
		            }
		            else {
		                /*入口*/
		            	int idx=0;
		            	if(deviceNumber.equals(new String("00005978")))
		            		idx = 1;
		                recordin[hour * 60 + minute].recordOfAllEntry[idx << 1] = in;
		                recordin[hour * 60 + minute].recordOfAllEntry[idx << 1 | 1] = out;
		            }
				}
				cin.close();
			}
		}
		
		int inq = 0;
		boolean peoplein = false;
		solve ss = new solve();
		int realinq=0;
		for(int minute = 0; minute < 24 * 60; minute++) {
			//debug lsr
			//if(minute==3){
			//	System.out.println("minute:"+minute);
			//}
			ReturnVal ret = ss.calp(realinq, inq, peoplein, recordin[minute], recordout[minute], minute / 60, minute % 60);
			ret.inq=Math.max(0,ret.inq);
			peoplein = ret.peoplein;
			
			realinq = ss.data_store[minute][0];
			inq = ss.data_store[minute][1];
			
	        totInQue[minute] = ret.inq;
		}
		
		for(int minute = 0; minute < 24 * 60; minute++) {
	        System.out.println(totInQue[minute]);
	    }
	}

}
