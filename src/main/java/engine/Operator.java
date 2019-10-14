package main.java.engine;

import main.java.indexer.*;

public class Operator {
    private String operator;
    private String operationParam;
    private String indexPath;
    private String docsPath;
    private Indexer indexer;

    public Operator() {
        this.operator = "";
        this.operationParam = "";
        this.indexPath = "";
        this.docsPath = "";
    }

    public Operator(String operator, String operationParam) {
        this.operator = operator;
        this.operationParam = operationParam;
        this.indexPath = "index";
        this.docsPath = "doc";
        if (operator.equals("-index")) {
            switch (operationParam) {
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

    }

    public Operator(String operator, String operationParam, String indexPath, String docPath) {
        this.operator = operator;
        this.operationParam = operationParam;
        this.indexPath = indexPath;
        this.docsPath = docPath;
        if (operator.equals("-index")) {
            switch (operationParam) {
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
}