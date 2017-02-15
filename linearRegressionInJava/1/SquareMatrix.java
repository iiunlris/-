
public class SquareMatrix extends MyMatrix {
	int rank;

    /// <summary>Construct an identity matrix</summary>
    public SquareMatrix(int rank)
    {
    	super(rank,rank);
        this.rank = rank;
        for (int i = 0; i < rank; i++) this.data[i][i] = 1;
    }
    public SquareMatrix(double[][] data) throws Exception
    {
    	super(data);
        if (data.length != data[0].length)
        {
            throw new Exception("Only square matrix supported");
        }
        this.rank = data.length;
        for(int i=0;i<data.length;i++){
        	for(int j=0;j<data.length;j++){
        		this.data[i][j]=data[i][j];
        	}
        }
    }

	public double Determinant() throws Exception
    {
        if (this.rank == 1)
        {
            return data[0][0];
        }
        if (this.rank == 2)
        {
            return data[0][0] * data[1][1] - data[1][0] * data[0][1];
        }

        double det = 0;
        for (int i = 0; i < this.rank; i++)
        {
            det += this.data[i][0] * this.Cofactor(i, 0).Determinant() * (i % 2 == 0 ? 1 : -1);
        }
        return det;
    }
    
    public SquareMatrix Cofactor(int i, int j) throws Exception
    {
        if (this.rank < 2) throw new Exception("Rank is less than two");

        SquareMatrix result = new SquareMatrix(this.rank - 1);
        double[][] buf = result.data;
        for (int y = 0; y < this.rank; y++)
        {
            for (int x = 0; x < this.rank; x++)
            {
                if (y != i && x != j)
                {
                    buf[y - (y > i ? 1 : 0)][x - (x > j ? 1 : 0)] = this.data[y][x];
                }
            }
        }
        return result;
    }
    
    public SquareMatrix Inverse() throws Exception
    {
        double det = this.Determinant();
        if (Math.abs(det) < 1e-8) throw new Exception("Cannot inverse a singular matrix");

        SquareMatrix result = new SquareMatrix(this.rank);
        for (int i = 0; i < this.rank; i++)
        {
            for (int j = 0; j < this.rank; j++)
            {
                result.data[j][i] = this.Cofactor(i, j).Determinant() * ((i + j) % 2 == 0 ? 1 : -1) / det;
            }
        }
        return result;
    }
    
	public static SquareMatrix FromMatrix(MyMatrix m) throws Exception {
		if (m == null || m.getCols() != m.getRows()) throw new Exception("Not a valid square matrix");
        return new SquareMatrix(m.getRawData());
	}
}
