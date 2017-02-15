import java.util.Date;


public class test2 {
	static int totInQue[] = new int[1500];
	public static allDataIn recordin[] = new allDataIn[2000];
	public static allDataOut recordout[] = new allDataOut[2000];
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int inq = 0;
		boolean peoplein = false;
		solve ss = new solve();
		int realinq=0;
		Date date=new Date();
		System.out.println(date.getHours()+" "+date.getMinutes());
		/*for(int minute = 0; minute < 24 * 60; minute++) {
			ReturnVal ret = ss.calp(realinq, inq, peoplein, recordin[minute], recordout[minute], minute / 60, minute % 60);
			ret.inq=Math.max(0,ret.inq);
			peoplein = ret.peoplein;
			
			realinq = ss.data_store[minute][0];
			inq = ss.data_store[minute][1];
			
	        totInQue[minute] = ret.inq;
		}*/
	}

}
