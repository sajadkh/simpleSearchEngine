package main.java.engine;

public class Main {
    public static void main(String[] args) {
        Operator op = argsProcessor(args);

        switch (op.getOperator()) {
            case "-index":
                op.getIndexer().index();
                break;
            case "-query":
                System.out.println("Not Supported");
            default:
                help();
        }

    }

    private static Operator argsProcessor(String[] args) {
        Operator op = new Operator();
        switch (args.length) {
            case 2:
                op = new Operator(args[0], args[1]);
                break;
            case 4:
                op = new Operator(args[0], args[1], args[2], args[3]);
                break;
        }
        return op;
    }

    private static void help(){
        System.out.println("Wrong Argument!!!!\n\n");
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Help:");
        System.out.println("-index <invertedWithStopWords>|<invertedWithoutStopWords>|" +
                "<positionalWithoutStopWords>|<positionalWithStopWords>  [indexPath] [docPath]");
    }

}


