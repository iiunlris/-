
public class MyMatrix {
	private int cols, rows;
    protected double[][] data;
    
  /// <summary>Construct a matrix</summary>
    
    public MyMatrix(){
    	
    }
    
    public MyMatrix(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }
    
  /// <summary>Construct a matrix by copying the data</summary>
    public MyMatrix(double[][] data)
    {
    	this.rows=data.length;
    	this.cols=data[0].length;
    	//java复制二维数组
    	this.data = new double[rows][cols];
    	for(int i=0;i<this.rows;i++){
    		this.data[i]=data[i].clone();
    	}
    }
    
    public double[] Multiply(double[] x) throws Exception
    {
        if (x == null || x.length != this.cols) throw new Exception("Invalid vector");
        double[] result = new double[this.rows];

        for (int i = 0; i < result.length; i++)
        {
            double sum = 0;
            for (int j = 0; j < this.cols; j++)
            {
                sum += this.data[i][j] * x[j];
            }
            result[i] = sum;
        }
        return result;
    }
    
    public MyMatrix Multiply(MyMatrix m) throws Exception
    {
        if (m == null || m.rows != this.cols) throw new Exception("Invalid matrix to multiply");
        MyMatrix result = new MyMatrix(this.rows, m.cols);
        int inner = this.cols;

        for (int row = 0; row < result.rows; row++)
        {
            for (int col = 0; col < result.cols; col++)
            {
                double sum = 0;
                for (int i = 0; i < inner; i++) sum += this.data[row][i] * m.data[i][col];
                result.data[row][col] = sum;
            }
        }
        return result;
    }
    
    public MyMatrix Transpose()
    {
        MyMatrix result = new MyMatrix(this.cols, this.rows);
        for (int row = 0; row < this.rows; row++)
        {
            for (int col = 0; col < this.cols; col++)
            {
                result.data[col][row] = this.data[row][col];
            }
        }
        return result;
    }
    
    public int getCols(){
    	return cols;
    }
    
    public int getRows(){
    	return rows;
    }
    
    public double[][] getRawData(){
    	return data;
    }
}
