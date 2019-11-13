package main.java.engine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            Operator op = argsProcessor(args);

            switch (op.getOperator()) {
                case "-index":
                    op.getIndexer().index();
                    break;
                case "-query":
                    try {
                        Map<String, ArrayList<String>> results = op.getSearcher().search(op.getQueries(), op.getPerPageHit());
                        printResult(results);
                        printToFile(results, op.getOperationParam(), op.getDestResult());
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case "-evaluate":
                    ResultEvaluator ev = new ResultEvaluator(op.getOperationParam());
                    System.out.println("MAP: " + ev.MAP());
                    System.out.println("NDCG: " + ev.ndcg());
                    break;
                case "-help":
                    help();
                    break;
                default:
                    wrongArg();
            }
        }catch (Exception e){
            wrongArg();
        }

    }

    private static Operator argsProcessor(String[] args) throws Exception {
        return new Operator(args);
    }

    private static void help() {
        System.out.println("Help:");
        System.out.println("-index <invertedWithStopWords>|<invertedWithoutStopWords>|" +
                "<positionalWithoutStopWords>|<positionalWithStopWords>  [indexPath] [docPath]");
        System.out.println("-query <withStopWords>|<withoutStopWords> [indexPath] [queriesPath] [field] [perPageHit]" +
                " [destPath]");
        System.out.println("-evaluate [resultPath]");
    }

    private static void wrongArg(){
        System.out.println("Wrong Argument!!!!");
        System.out.println("Enter -help for find appropriate command");
    }

    private static void printResult(Map<String, ArrayList<String>> results) {
        results.forEach((query, strings) -> {
            System.out.println("Results for: " + query);
            System.out.println();
            strings.forEach(System.out::println);
            System.out.println("***********************************************");
        });
    }

    public static void printToFile(Map<String, ArrayList<String>> results, String queryType, String desResult)
    {
        results.forEach((query, strings) -> {
            try {
                FileWriter fileWriter = new FileWriter(desResult + '/' + query + ".txt");
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println("Results for: " + query);
                printWriter.println();
                strings.forEach(printWriter::println);
                printWriter.close();
            }catch (Exception e){
                System.out.println(e);
            }
        });
    }

}


