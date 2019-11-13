package main.java.engine;

import main.java.Searcher.Searcher;
import main.java.Searcher.SearcherWithStopWord;
import main.java.Searcher.SearcherWithoutStopWord;
import main.java.indexer.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Operator {
    private String operator;
    private String operationParam;
    private String indexPath = "index";
    private String docsPath = "doc";
    private String field = "contents";
    private Integer perPageHit = 10;
    private Indexer indexer;
    private Searcher searcher;
    private String queries;
    private String destResult;

    public Operator(String[] operators) throws Exception {
        if(this.valid(operators)) {
            this.operator = operators[0];
            this.operationParam = operators.length > 1 ? operators[1] : "show";
            this.indexPath = operators.length > 2? operators[2] : "index";
            if (this.operator.equals("-index")) {
                this.docsPath = operators[3] != null ? operators[3] : "doc";
                this.chooseIndexer();
            } else if (this.operator.equals("-query")) {
                this.queries = operators[3]!=null ? operators[3] : "queries.txt";
                this.field = operators.length > 4? operators[4] : "contents";
                this.perPageHit = operators.length>5 ? Integer.parseInt(operators[5]) : 10;
                this.destResult = operators.length > 6 ? operators[6] : "result";
                this.chooseSearcher();
            }else if (operator.equals("-evaluate")){
                this.operationParam = operators[1];
            }
        }
        else {
            throw new Exception("Wrong Param");
        }
    }

    private void chooseIndexer() {
        switch (this.operationParam) {
            case "invertedWithStopWords":
                this.indexer = new InvertedWithStopWordsIndexer(this.indexPath, this.docsPath);
                break;
            case "invertedWithoutStopWords":
                this.indexer = new InvertedWithoutStopWordsIndexer(this.indexPath, this.docsPath);
                break;
            case "positionalWithoutStopWords":
                this.indexer = new PositionalWithoutStopWordsIndexer(this.indexPath, this.docsPath);
                break;
            case "positionalWithStopWords":
                this.indexer = new PositionalWithStopWordsIndexer(this.indexPath, this.docsPath);
                break;
        }
    }

    private void chooseSearcher() {
        switch (operationParam) {
            case "withStopWords":
                this.searcher = new SearcherWithStopWord(this.indexPath, this.field);
                break;
            case "withoutStopWords":
                this.searcher = new SearcherWithoutStopWord(this.indexPath, this.field);
                break;
        }
    }

    public boolean valid(String[] args) {
        String[] validOperand = new String[]{"-index", "-query", "-evaluate", "-help"};
        List<String> validOperandList = Arrays.asList(validOperand);
        String[] validIndexParam = new String[]{"invertedWithStopWords", "invertedWithoutStopWords"
                , "positionalWithoutStopWords", "positionalWithStopWords"};
        List<String> validIndexParamList = Arrays.asList(validIndexParam);
        String[] validSearchParam = new String[]{"withStopWords", "withoutStopWords"};
        List<String> validSearchParamList = Arrays.asList(validSearchParam);
        String[] validEvaluateParam = new String[]{"*"};
        List<String> validEvaluateParamList = Arrays.asList(validEvaluateParam);

        if (!validOperandList.contains(args[0])) {
            return false;
        } else {
            if (args[0].equals("-index")) {
                return validIndexParamList.contains(args[1]) || validIndexParamList.contains("*");
            } else if (args[0].equals("-query")) {
                return validSearchParamList.contains(args[1]) || validSearchParamList.contains("*");
            } else if (args[0].equals("-evaluate")){
                return validEvaluateParamList.contains(args[1]) || validEvaluateParamList.contains("*");
            }
            else if(args[0].equals("-help")){
                return true;
            }
            return false;
        }
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperationParam() {
        return operationParam;
    }

    public void setOperationParam(String operationParam) {
        this.operationParam = operationParam;
    }

    public String getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }

    public String getDocPath() {
        return docsPath;
    }

    public void setDocPath(String docPath) {
        this.docsPath = docPath;
    }

    public Indexer getIndexer() {
        return indexer;
    }

    public void setIndexer(Indexer indexer) {
        this.indexer = indexer;
    }

    public Searcher getSearcher() {
        return searcher;
    }

    public void setSearcher(Searcher searcher) {
        this.searcher = searcher;
    }

    public Integer getPerPageHit() {
        return perPageHit;
    }

    public void setPerPageHit(Integer perPageHit) {
        this.perPageHit = perPageHit;
    }

    public String getQueries() {
        return queries;
    }

    public void setQueries(String queries) {
        this.queries = queries;
    }

    public String getDestResult() {
        return destResult;
    }

    public void setDestResult(String destResult) {
        destResult = destResult;
    }
}