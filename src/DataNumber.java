public class DataNumber {
    private double Min;
    private double Max;
    private double Step;
    private double Result;

    public DataNumber(double min, double max, double step, double result) {
        Min = min;
        Max = max;
        Step = step;
        Result = result;
    }
    public DataNumber() {
        Min = 0;
        Max = 0;
        Step = 0;
        Result = 0;
    }
    public DataNumber(double[] temp) {

        Min = temp[0];
        Max = temp[1];
        Step = temp[2];
        Result = Trap(temp[0],temp[1],temp[2]);
    }
    public double getMin() {
        return Min;
    }

    public double getMax() {
        return Max;
    }

    public double getStep() {
        return Step;
    }

    public double getResult() {
        return Result;
    }

    public void setMin(double min) {
        Min = min;
    }

    public void setMax(double max) {
        Max = max;
    }

    public void setStep(double step) {
        Step = step;
    }
   public void setAllField(double a, double b,double step)
   {
       Min = a;
       Max = b;
       Step = step;
       if(Min>Max)
       {
           setResultNull();
       }else {
           setResult();
       }
   }
    public void setResult() {

        Result =  Trap(Min,Max,Step);
    }
    public void setResultNull() {

        Result =  0;
    }

    public Object[] addMod() {
        Object[] temp=new Object[4];
        temp[0]=Min;
        temp[1]=Max;
        temp[2]=Step;
        temp[3]=Result;
        return temp;
    }
    public double Trap(double a,double b, double h){
        double result=0;
        double num=0;
        for(double i=a;i<=b-(h*2);i+=h)
        {num=i+h;
            if(i>b)
            {
                num=b;
            }
            result+=(InFunction(i)+InFunction(num))*(b-i)/2;
        }
        //double result=0;
        // int n = (int)((a-b)/h);
        //result += (InFunction(a)+InFunction(b))/2;
        //for(int i = 1; i < n; i++) {
        //    result += InFunction(b + h * i);
        //}
        //}
        return result;
    }
    public static double InFunction(double x) //Подынтегральная функция
    {
        return Math.sin(Math.pow(x,2));
    }
    @Override
    public String toString() {
        return "DataNumber{" +
                "Min=" + Min +
                ", Max=" + Max +
                ", Step=" + Step +
                ", Result=" + Result +
                '}';
    }
}
