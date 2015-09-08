package cn.androidy.lyric.data;

/**
 * Created by mwp on 2015/9/8.
 */
public class LyricRow {
    String text;
    public float start;
    public float end;
    private float totalLength;

    public LyricRow(String text, float start, float end, float totalLength) {
        this.text = text;
        this.start = start;
        this.end = end;
        this.totalLength = totalLength;
    }

    public String getText() {
        return text;
    }

    public float getStart() {
        return start;
    }

    public float getLyricRowLength() {
        return end - start;
    }
}
