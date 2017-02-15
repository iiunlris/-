import java.util.Arrays;

public class GaussNewton {
	private boolean solved;
    private int coefficientCount;
    private double[] coefficients;
    private double[] xs;
    private double[] ys;
    private double sse;
 
    public static int MaxIteration = 16;
    public static double Epsilon = 1e-10;
    public static double ConvergeThreshold = 1e-8;
    
    //拟合公式的定义
	public double function(double[] coefficients, double x){
		double f_ret = 0;
        for (int i = coefficients.length - 1; i >= 0; i--)
        {
            f_ret *= x;
            f_ret += coefficients[i];
        }
        return f_ret;
	}
	
	public GaussNewton(int coefficientCount)
    {
		this.solved=false;
        this.coefficientCount = coefficientCount;
        this.coefficients = new double[coefficientCount];
    }
	
	/// <summary> Initialize the solver without a guess. Y = f(X) </summary>
    public void Initialize(double[] Y, double[] X) throws Exception
    {
        Initialize(Y, X, null);
    }

    /// <summary> Initialize the solver: Y = f(X) with a guessed approximation</summary>
    public void Initialize(double[] Y, double[] X, double[] coefficientGuess) throws Exception
    {
        if (X == null || Y == null) {
        	throw new Exception("ArgumentNull");
        }
        if (X.length != Y.length) {
        	throw new Exception("Y and X not in pairs");
        }

        this.xs = X;
        this.ys = Y;
        this.solved = false;

        if (coefficientGuess != null && coefficientGuess.length == this.coefficientCount)
        {
        	this.coefficients=Arrays.copyOf(coefficientGuess, this.coefficientCount);
        }
    }
    
  /// <summary> Iteratively solve the coefficients using Gauss-Newton method </summary>
    /// <remarks> http://en.wikipedia.org/wiki/Gauss%E2%80%93Newton_algorithm </remarks>
    public double[] Solve() throws Exception
    {
        if (this.ys == null) throw new Exception("Not yet initialized");

        double lastSSE = Double.MAX_VALUE;
        double[] errors = new double[this.ys.length];

        // let C0 be the current coefficient guess, C1 be the better answer we are after
        // let E0 be the error using current guess
        // we have:
        // JacT * Jac * (C1 - C0) = JacT * E0
        //
        MyMatrix jacobian = Jacobian();
        MyMatrix jacobianT = jacobian.Transpose();
        MyMatrix product = jacobianT.Multiply(jacobian);
        SquareMatrix inverse = SquareMatrix.FromMatrix(product).Inverse();

        for (int iteration = 0; iteration < GaussNewton.MaxIteration; iteration++)
        {
            this.sse = 0;

            for (int i = 0; i < this.ys.length; i++)
            {
                double y = function(this.coefficients, this.xs[i]);
                errors[i] = this.ys[i] - y;
                sse += errors[i] * errors[i];
            }
            
            if (lastSSE - sse < GaussNewton.ConvergeThreshold)
            {
                this.solved = true;
                return this.coefficients;
            }

            double[] shift = inverse.Multiply(jacobianT.Multiply(errors));

            for (int i = 0; i < this.coefficientCount; i++)
            {
                this.coefficients[i] += shift[i];
            }

            lastSSE = sse;
        }
        throw new Exception("No answer can be found");
    }
    
    private MyMatrix Jacobian()
    {
        double[][] p = new double[this.coefficientCount][];
        for (int i = 0; i < p.length; i++)
        {
            p[i] = new double[this.coefficientCount];
            p[i][i] = 1;
        }

        MyMatrix jacobian = new MyMatrix(this.ys.length, this.coefficientCount);
        for (int i = 0; i < this.ys.length; i++)
        {
            for (int j = 0; j < this.coefficientCount; j++)
            {
                jacobian.data[i][j] = function(p[j], this.xs[i]);
            }
        }
        return jacobian;
    }
    
    public double[] getCoefficients() throws Exception{
    	return this.coefficients=Solve();
    }
}
