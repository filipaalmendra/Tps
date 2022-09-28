public class Tp1 {
    public static void main(String[] args){
        float w = integrate(0f, 1f, (int)Math.pow(10, Double.valueOf(-7.0)));
        System.out.println(w);
    }

    private static Float f(Float x){
        return x;
    }

    static float integrate(float a, float b, int N) {
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
}
