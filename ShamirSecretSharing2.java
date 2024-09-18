import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ShamirSecretSharing2 {

    public static BigInteger decodeValue(int base, String value) {
        return new BigInteger(value, base);
    }

    public static BigInteger lagrangeInterpolation(List<BigInteger[]> points, BigInteger prime) {
        BigInteger c = BigInteger.ZERO;

        for (int j = 0; j < points.size(); j++) {
            BigInteger x_j = points.get(j)[0];
            BigInteger y_j = points.get(j)[1];

            BigInteger basisPolynomial = BigInteger.ONE;
            for (int i = 0; i < points.size(); i++) {
                if (i != j) {
                    BigInteger x_i = points.get(i)[0];
                    // (x - x_i) / (x_j - x_i)
                    BigInteger numerator = x_i.negate().mod(prime);
                    BigInteger denominator = x_j.subtract(x_i).mod(prime);
                    basisPolynomial = basisPolynomial.multiply(numerator).multiply(denominator.modInverse(prime)).mod(prime);
                }
            }

            c = c.add(y_j.multiply(basisPolynomial)).mod(prime);
        }

        return c;
    }

    public static BigInteger findSecretConstant() {
        int n = 9;
        int k = 6;
        BigInteger prime = new BigInteger("999999999999989"); // A large prime number

        List<BigInteger[]> points = new ArrayList<>();

        points.add(new BigInteger[]{BigInteger.ONE, decodeValue(10, "28735619723837")});
        points.add(new BigInteger[]{BigInteger.valueOf(2), decodeValue(16, "1A228867F0CA")});
        points.add(new BigInteger[]{BigInteger.valueOf(3), decodeValue(12, "32811A4AA0B7B")});
        points.add(new BigInteger[]{BigInteger.valueOf(4), decodeValue(11, "917978721331A")});
        points.add(new BigInteger[]{BigInteger.valueOf(5), decodeValue(16, "1A22886782E1")});
        points.add(new BigInteger[]{BigInteger.valueOf(6), decodeValue(10, "28735619654702")});
        points.add(new BigInteger[]{BigInteger.valueOf(7), decodeValue(14, "71AB5070CC4B")});
        points.add(new BigInteger[]{BigInteger.valueOf(8), decodeValue(9, "122662581541670")});
        points.add(new BigInteger[]{BigInteger.valueOf(9), decodeValue(8, "642121030037605")});

        points.sort((a, b) -> a[0].compareTo(b[0]));
        List<BigInteger[]> selectedPoints = points.subList(0, k);

        return lagrangeInterpolation(selectedPoints, prime);
    }

    public static void main(String[] args) {
        BigInteger c = findSecretConstant();
        System.out.println("The constant term (c) is: " + c);
    }
}
