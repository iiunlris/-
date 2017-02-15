import java.util.Arrays;

public class GaussNewton {
	private boolean solved;
    private int coefficientCount;
    private double[] coefficients;
    private Instance[] instance;
    private double sse;
 
    public static int MaxIteration = 400;
    public static double Epsilon = 1e-10;
    public static double ConvergeThreshold = 1e-12;
    
    //拟合公式的定义
	public double function(double[] coefficients, Instance nowx){
		double f_ret = 0;
        for (int i = coefficients.length - 1; i >= 0; i--)
        {
            f_ret += coefficients[i] * nowx.x[i];
        }
        return 1 / (1 + Math.exp(-f_ret));
	}
	
	public GaussNewton(int coefficientCount)
    {
		this.solved=false;
        this.coefficientCount = coefficientCount;
        this.coefficients = new double[coefficientCount];
    }
	
	/// <summary> Initialize the solver without a guess. Y = f(X) </summary>
    public void Initialize(Instance[] nowinstance) throws Exception
    {
        Initialize(nowinstance, null);
    }

    /// <summary> Initialize the solver: Y = f(X) with a guessed approximation</summary>
    public void Initialize(Instance[] nowinstance, double[] coefficientGuess) throws Exception
    {
        if (nowinstance == null) {
        	throw new Exception("ArgumentNull");
        }

        this.instance = nowinstance;
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
        if (this.instance == null) throw new Exception("Not yet initialized");

        double lastSSE = Double.MAX_VALUE;
        double[] errors = new double[this.instance.length];

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

            for (int i = 0; i < this.instance.length; i++)
            {
                double y = function(this.coefficients, this.instance[i]);
                errors[i] = this.instance[i].label - y;
                sse += -this.instance[i].label * Math.log(y) - (1-this.instance[i].label) * Math.log(1 - y);
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
            //System.out.println("!!!!!!!:" + " " + lastSSE + " " + sse);
            lastSSE = sse;
        }
        throw new Exception("No answer can be found");
    }
    
    private MyMatrix Jacobian()
    {
        MyMatrix jacobian = new MyMatrix(this.instance.length, this.coefficientCount);
        for (int i = 0; i < this.instance.length; i++)
        {
        	double yy = function(this.coefficients, this.instance[i]);
            for (int j = 0; j < this.coefficientCount; j++)
            {
                jacobian.data[i][j] = yy * (1 - yy) * this.instance[i].x[j];
            }
        }
        return jacobian;
    }
    
    public double[] getCoefficients() throws Exception{
    	return this.coefficients=Solve();
    }
}
