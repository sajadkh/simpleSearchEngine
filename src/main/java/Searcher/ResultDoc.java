package main.java.Searcher;
import java.io.File;

public class ResultDoc {
    private int id;
    private float score;
    private String path;
    private String title;
    public ResultDoc(int id, float score, String path, String title) {
        this.id = id;
        this.score = score;
        this.path = path;
        this.title = this.getFileName(path);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName(String path){
        File f = new File(path);
        return f.getName();
    }
}

