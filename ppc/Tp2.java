
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

public class Tp2 extends RecursiveTask<Float>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float a;
	private float b;
	private float p;
	private Function<Float, Float> f;
	
	public Tp2(float a, float b, float p, Function<Float,Float> f) {
		this.a = a;
		this.b = b;
		this.p = p;
		this.f = f;
	}
	
    public static void main(String[] args) {
    	int NUMBER_THREADS = Runtime.getRuntime().availableProcessors();
        //float w = integrate(0f, 1f, (int)Math.pow(10, Double.valueOf(-7.0)));
        //System.out.println(w);
    	
    	Function<Float,Float> ft = (x->(x*(x-1)));
    	
        Tp2 bg = new Tp2(0f,1f,0.000001f,ft);
        ForkJoinPool fp = new ForkJoinPool(NUMBER_THREADS);
        fp.invoke(bg);
        
    }

    /**
    private static Float f(Float x){
        return (x * (x-1));
    }
	*/

    static float integrate(float a, float b, int N, ) {
        float h = (b - a) / N;              // step size
        float sum = (float)(0.5) * (f(a) + f(b));    // area
        for (int i = 1; i < N; i++) {
            float x = a + h * i;
           sum = sum + f(x);
        }
        return sum * h;
     }

    private float trapezoidRule(float a, float b, int p){
        return integrate(a, b, p);
    }

    @Override
	protected Float compute() {
		
		
		Tp2 f1 = new Tp2(a,(a+b)/2,p,f);
		f1.fork();

		Tp2 f2 = new Tp2(a+(a+b)/2,b,p,f);
		f2.fork();
		
		Float a1 = f1.join();
		Float a2 = f2.join();
		
		return a + b;
	}

}
