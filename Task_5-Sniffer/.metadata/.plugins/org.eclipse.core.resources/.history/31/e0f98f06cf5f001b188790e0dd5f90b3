package edu.kis.vh.nursery;

public class DefaultCountingOutRhymer {

    private static final int TOTALSTATUS = -1;
    private static final int SIZE = 12;
    private static final int RETURN_VALUE = -1;
    private static final int MAX = 11;

    public static int getTOTALSTATUS() {
        return TOTALSTATUS;
    }

    public static int getSIZE() {
        return SIZE;
    }

    public static int getReturnValue() {
        return RETURN_VALUE;
    }

    public static int getMAX() {
        return MAX;
    }

    public int getTotal() {
        return total;
    }

    private int total = getTOTALSTATUS();
    private final int[] numbers = new int[getSIZE()];

    public void countIn(int in) {
        if (!isFull())
            numbers[++total] = in;
    }

    public boolean callCheck() {
        return total == getReturnValue();
    }

    public boolean isFull() {
        return total == getMAX();
    }

    protected int peekaboo() {
        if (callCheck())
            return getReturnValue();
        return numbers[total];
    }

    public int countOut() {
        if (callCheck())
            return getReturnValue();
        return numbers[total--];
    }

}
