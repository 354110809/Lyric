package cn.androidy.lyric.data;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by mwp on 2015/9/8.
 */
public class LyricDrawInfo {

    private LyricRow mLyricRow;
    private LyricParams settings;
    private int startX;
    private int textWidth;
    public float startY;
    private Paint paint;

    public LyricDrawInfo(LyricRow lyricRow, LyricParams settings) {
        this.mLyricRow = lyricRow;
        this.settings = settings;
        paint = new Paint();
        paint.setTextSize(settings.getTextSize());
    }

    public void updateLyricState(float tempStartY) {
        textWidth = (int) Math.min(settings.getMaxTextWidth(), paint.measureText(mLyricRow.getText()));
        startX = settings.getCanvasWidth() / 2 - textWidth / 2;
        startY = tempStartY;
    }

    public void setSettings(LyricParams settings) {
        this.settings = settings;
    }

    public LyricRow getLyricRow() {
        return mLyricRow;
    }

    /**
     * @param canvas
     * @param paint
     * @param progress
     * @return 如果是正在渲染的歌词，返回true。
     */
    public boolean onDraw(Canvas canvas, Paint paint, float progress) {
        float start = getLyricRow().getStart() / settings.getMax();
        float length = getLyricRow().getLyricRowLength() / settings.getMax();
        float divProgress = 0;
        if (progress > start + length) {
            divProgress = 1.0f;
        } else if (progress < start) {
            divProgress = 0.0f;
        } else {
            divProgress = (progress - start) / length;
        }
        int dividorPosition = (int) (divProgress * textWidth + startX);
        if (divProgress < 1.0f && divProgress > 0) {
            drawText(canvas, paint, settings.getColorChange(), startX, dividorPosition);
            drawText(canvas, paint, settings.getColor(), dividorPosition, startX + textWidth);
            return true;
        } else {
            drawText(canvas, paint, settings.getColor(), startX, startX + textWidth);
        }
        return false;
    }

    private void drawText(Canvas canvas, Paint paint, int color, int dividorPosition, int endX) {
        paint.setColor(color);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(dividorPosition, 0, endX, settings.getCanvasHight());
        canvas.drawText(getLyricRow().getText(), startX, startY, paint);
        canvas.restore();
    }
}
