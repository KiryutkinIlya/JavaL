import java.io.Serializable;

public class DataNumber implements Serializable {
    private double Min;
    private double Max;
    private double Step;
    private double Result;
    private String Method;
    private static String Simpson="метод Cимпcона";
    private static String Trap="метод Трапеции";
    public DataNumber(double min, double max, double step, double result) {
        Min = min;
        Max = max;
        Step = step;
        Result = result;
        Method="";
    }
    public DataNumber() {
        Min = 0;
        Max = 0;
        Step = 0;
        Result = 0;
        Method="";
    }
    public DataNumber(DataNumber dataNumber) {
        this.Min = dataNumber.Min;
        this.Max = dataNumber.Max;
        this.Step = dataNumber.Step;
        this.Result =dataNumber.Result;
        this.Method= dataNumber.Method;
    }
    public DataNumber(double[] temp) {

        Min = temp[0];
        Max = temp[1];
        Step = temp[2];
        Result = Trap(temp[0],temp[1],temp[2]);
        Method=Trap;

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
   public void setAllFieldTrap(double a, double b,double step)
   {
       Min = a;
       Max = b;
       Step = step;
       if(Min>Max)
       {
           setResultNull();
       }else {
           setResultTrap();
       }
   }
    public void setAllField(double a, double b,double step,int method)
    {
        Min = a;
        Max = b;
        Step = step;
        if(Min>Max)
        {
            setResultNull();
        };

        if(method==0)
        {
            setResultTrap();
            Method=Trap;
        } else{ if(method==1) {
            setResultSimpson();
            Method=Simpson;
        }else{
            setResultNull();
            Method="";
        }}

    }
    public static String getSimpson() {
        return Simpson;
    }

    public static void setSimpson(String simpson) {
        Simpson = simpson;
    }

    public static String getTrap() {
        return Trap;
    }

    public static void setTrap(String trap) {
        Trap = trap;
    }

    public void setAllFieldSimpson(double a, double b, double step)
    {
        Min = a;
        Max = b;
        Step = step;
        if(Min>Max)
        {
            setResultNull();
        }else {
            setResultSimpson();
        }
    }

    public void setResultTrap() {

        Result =  Trap(Min,Max,Step);
    }

    public void setResultSimpson()
    {
        Result=Simpson(Min,Max,Step);
    }
    public void setResultNull() {
        Result =  0;
    }

    public Object[] addModSimpson() {
        Object[] temp=new Object[5];
        temp[0]=Min;
        temp[1]=Max;
        temp[2]=Step;
        temp[3]=Result;
        temp[4]=Simpson;
        return temp;
    }
    public Object[] addMod() {
        Object[] temp=new Object[5];
        temp[1]=Min;
        temp[0]=Max;
        temp[2]=Step;
        temp[3]=Result;
        temp[4]=Method;
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

    public void setResult(double result) {
        Result = result;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public double Simpson(double a, double b, double n){
        int i,z;
        double h,s;

        n=n+n;
        s = InFunction(a)*InFunction(b);
        h = (b-a)/n;
        z = 4;

        for(i = 1; i<n; i++){
            s = s + z * InFunction(a+i*h);
            z = 6 - z;
        }
        return (s * h)/3;
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
                ", Method='" + Method + '\'' +
                '}';
    }
    public String toStringApi() {
        return Min+","+Max+","+Step+","+Result+","+Method;
    }
}
