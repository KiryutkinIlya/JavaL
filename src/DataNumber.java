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
        Result = temp[3];
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

    public void setResult(double result) {
        Result = result;
    }

    public Object[] addMod() {
        Object[] temp=new Object[4];
        temp[0]=Min;
        temp[1]=Max;
        temp[2]=Step;
        temp[3]=Result;
        return temp;
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
