package producerconsumer;

import java.util.Random;
import java.lang.StringBuilder;

public class OperationBuilder {
    private int lowerBound;
    private int upperBound;
    private Random randomGenerator = new Random(System.currentTimeMillis());

    public OperationBuilder(int min, int max) {
        lowerBound = min;
        upperBound = max;
    }

    /* The random generator is too slow, so it somehow sometimes recycle the operations
     *  instead of generating them, particularly because of the huge demand in small gap of time. */

    public String generate() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("( ");
        stringBuilder.append(Operator(randomGenerator.nextInt(4))).append(" ");
        stringBuilder.append(GetRandom()).append(" ");
        stringBuilder.append(GetRandom());
        stringBuilder.append(" )");

        return stringBuilder.toString();
    }

    private int GetRandom(){
        return (randomGenerator.nextInt(upperBound - lowerBound) + lowerBound);
    }

    private String Operator(int option) {
        switch (option) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "/";
            case 3:
                return "*";
            default:
                return "";
        }
    }
}