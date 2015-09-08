package cn.androidy.lyric.data;

/**
 * Created by mwp on 2015/9/8.
 */
public class LyricRow {
    String text;
    public double start;
    public double end;
    private double max;

    public LyricRow(String text, double start, double end, double max) {
        this.text = text;
        this.start = start;
        this.end = end;
        this.max = max;
    }

    public double getMax() {
        return max;
    }

    public String getText() {
        return text;
    }

    public double getStart() {
        return start;
    }

    public double getLyricRowLength() {
        return end - start;
    }
}
