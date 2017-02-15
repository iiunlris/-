import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class test {
	private static double sigmoid(double z) {
		return 1.0 / (1.0 + Math.exp(-z));
	}
	
	private static double classify(double[] weights, int[] x) {
		double logit = .0;
		for (int i=0; i<weights.length;i++)  {
			logit += weights[i] * x[i];
		}
		return sigmoid(logit);
	}
	
	public static List<Instance> readDataSet(String file) throws FileNotFoundException {
		List<Instance> dataset = new ArrayList<Instance>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(file));
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("#")) {
					continue;
				}
				String[] columns = line.split("\\s+");

				// skip first column and last column is the label
				int i = 1;
				int[] data = new int[columns.length-2];
				for (i=1; i<columns.length-1; i++) {
					data[i-1] = Integer.parseInt(columns[i]);
				}
				int label = Integer.parseInt(columns[i]);
				Instance instance = new Instance(label, data);
				dataset.add(instance);
			}
		} finally {
			if (scanner != null)
				scanner.close();
		}
		return dataset;
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		List<Instance> instances = readDataSet("dataset.txt");
		System.out.println("instance.size()" + " " + instances.size());
		Instance nowinstance[] = new Instance[instances.size()];
		instances.toArray(nowinstance);
		System.out.println("nowinstance.size()" + " " + nowinstance.length);
		
		GaussNewton gaussNewton = new GaussNewton(5);
        gaussNewton.Initialize(nowinstance);
        double[] answer = gaussNewton.getCoefficients();
        int[] x = {2, 1, 1, 0, 1};
        for(int i = 0; i < 5; i++) {
        	System.out.println(answer[i]);
        }
        System.out.println(classify(answer, x));
        
        int[] x2 = {1, 0, 1, 0, 0};
        System.out.println(classify(answer, x2));
	}

}
