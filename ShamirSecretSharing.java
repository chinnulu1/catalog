import java.util.ArrayList;
import java.util.List;

public class ShamirSecretSharing {

    public static int decodeValue(int base, String value) {
        return Integer.parseInt(value, base);
    }

    public static double lagrangeInterpolation(List<int[]> points) {
        double c = 0.0;

        for (int j = 0; j < points.size(); j++) {
            int x_j = points.get(j)[0];
            int y_j = points.get(j)[1];

            double basisPolynomial = 1.0;
            for (int i = 0; i < points.size(); i++) {
                if (i != j) {
                    int x_i = points.get(i)[0];
                    basisPolynomial *= (0 - x_i) / (double) (x_j - x_i);
                }
            }

            c += y_j * basisPolynomial;
        }

        return c;
    }

    public static double findSecretConstant() {
        int n = 4;
        int k = 3;

        List<int[]> points = new ArrayList<>();

        points.add(new int[]{1, 4}); // base 10, value 4
        points.add(new int[]{2, 7}); // base 2, value 111
        points.add(new int[]{3, 12}); // base 10, value 12
        points.add(new int[]{6, 27}); // base 4, value 213

        List<int[]> selectedPoints = points.subList(0, k);

        return lagrangeInterpolation(selectedPoints);
    }

    public static void main(String[] args) {
        double c = findSecretConstant();
        System.out.println("The constant term (c) is: " + c);
    }
}