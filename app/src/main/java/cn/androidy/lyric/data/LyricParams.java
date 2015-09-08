package cn.androidy.lyric.data;

/**
 * 绘制歌词的整体配置信息
 * Created by mwp on 2015/9/8.
 */
public class LyricParams {
    private int color;//默认字体颜色
    private int colorChange;//正在着色的字体颜色
    private int canvasHight;//画布整体高度
    private int canvasWidth;//画布整体宽度
    private int maxTextWidth;//允许的最大字的宽度
    private int textSize;
    private int max;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getColor() {
        return color;
    }

    public int getColorChange() {
        return colorChange;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setColorChange(int colorChange) {
        this.colorChange = colorChange;
    }

    public int getCanvasHight() {
        return canvasHight;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public void setCanvasHight(int canvasHight) {
        this.canvasHight = canvasHight;
    }

    public void setCanvasWidth(int canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    public void setMaxTextWidth(int maxTextWidth) {
        this.maxTextWidth = maxTextWidth;
    }

    public int getMaxTextWidth() {
        return maxTextWidth;
    }
}
