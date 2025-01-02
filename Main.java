import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {

    public static BigInteger decode(String val, int b) {
        return new BigInteger(val, b);
    }

    public static BigInteger lInter(List<Integer> x, List<BigInteger> y, int k) {
        BigInteger res = BigInteger.ZERO;

        for (int i = 0; i < k; i++) {
            BigInteger term = y.get(i);
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    BigInteger num = BigInteger.valueOf(-x.get(j));
                    BigInteger den = BigInteger.valueOf(x.get(i) - x.get(j));
                    term = term.multiply(num).divide(den);
                }
            }
            res = res.add(term);
        }
        return res;
    }

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.json"));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line.trim());
            }

            String json = jsonBuilder.toString();

            int n = Integer.parseInt(json.split("\"n\":")[1].split(",")[0].trim());
            int k = Integer.parseInt(json.split("\"k\":")[1].split("}")[0].trim());

            List<Integer> x = new ArrayList<>();
            List<BigInteger> y = new ArrayList<>();

            for (int i = 1; i < n; i++) {
                String index = "\"" + i + "\":";
                int sIdx = json.indexOf(index) + index.length();
                int eIdx = json.indexOf("}", sIdx);
                String point = json.substring(sIdx, eIdx + 1);
                String baseStr = point.split("\"base\":")[1].split(",")[0].trim();
                baseStr = baseStr.replaceAll("\"", "").trim();
                int base = Integer.parseInt(baseStr);
                String valueStr = point.split("\"value\":")[1].split("\"")[1].trim();
                valueStr = valueStr.replaceAll("\"", "").trim();
                x.add(i);
                y.add(decode(valueStr, base));
            }

            BigInteger c = lInter(x.subList(0, k), y.subList(0, k), k);
            System.out.println(c);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
