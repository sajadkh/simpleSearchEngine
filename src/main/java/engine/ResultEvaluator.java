package main.java.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ResultEvaluator {
    String path = "result.txt";


    public ResultEvaluator(String path) {
        this.path = path;
    }

    public double MAP() throws IOException {
        double mapResult = 0;
        double sum = 0;
        int counter = 0;
        File file = new File(this.path);
        String line = "";
        Scanner sc = new Scanner(file);

        while (sc.hasNext()) {
            counter++;
            line = sc.nextLine();
            String scores[] = line.split(" ");
            ArrayList<Integer> scoresNumber = new ArrayList<Integer>();
            for (String score : scores) {

                scoresNumber.add(Integer.parseInt(score));
            }
            sum += averagePrecisionCalculator(scoresNumber);
        }
        double d = counter;
        mapResult = sum / d;

        return mapResult;
    }

    public double averagePrecisionCalculator(ArrayList<Integer> resultList) {

        double result = 0;
        int related = 0;
        for (int i = 0; i < resultList.size(); i++) {
            int numerator = 0;
            for (int j = 0; j < i; j++) {
                if (resultList.get(j).equals(1) || resultList.get(j).equals(2)) {
                    numerator++;
                }
            }
            if (i > 0) {
                double d = i;
                result += numerator / d;
                related++;
            } else if (numerator > 0) {
                result += 1;
                related++;
            }
        }
        result = result / related;

        return result;
    }

    public double ndcg() throws IOException {
        double ndcgResult = 0;
        double sum = 0;
        int counter = 0;
        File file = new File(this.path);
        String line = "";
        Scanner sc = new Scanner(file);

        while (sc.hasNext()) {
            counter++;
            line = sc.nextLine();
            String scores[] = line.split(" ");
            ArrayList<Integer> scoresNumber = new ArrayList<Integer>();
            for (String score : scores) {

                scoresNumber.add(Integer.parseInt(score));
            }
            double idealDcg = idealDcg(scoresNumber);
            if(idealDcg == 0){
                idealDcg = 1;
            }
            sum += dcg(scoresNumber) / idealDcg;

        }
        double d = counter;
        ndcgResult = sum / d;

        return ndcgResult;
    }


    public double idealDcg(ArrayList<Integer> resultList) {
        Collections.sort(resultList);
        Collections.reverse(resultList);
        return dcg(resultList);
    }

    public double dcg(ArrayList<Integer> resultList) {
        double result = resultList.get(0);
        for (int i = 1; i < resultList.size(); i++) {
            result += resultList.get(i) / log2(i + 1);
        }
        return result;
    }

    public static double log2(int x) {
        return (double) (Math.log(x) / Math.log(2) + 1e-10);
    }

}
