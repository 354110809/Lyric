package cn.androidy.lyric.data;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by mwp on 2015/9/8.
 */
public class LyricDrawInfo {

    private LyricRow mLyricRow;
    private LyricParams params;
    private int startX;
    private int textWidth;
    public float startY;
    private Paint paint;

    public LyricDrawInfo(LyricRow lyricRow, LyricParams params) {
        this.mLyricRow = lyricRow;
        this.params = params;
        paint = new Paint();
        paint.setTextSize(params.getTextSize());
    }

    public void updateLyricState(float tempStartY) {
        textWidth = (int) Math.min(params.getMaxTextWidth(), paint.measureText(mLyricRow.getText()));
        startX = params.getCanvasWidth() / 2 - textWidth / 2;
        startY = tempStartY;
    }

    public void setSettings(LyricParams params) {
        this.params = params;
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
        double startPercent = getLyricRow().getStart() / mLyricRow.getMax();//本句歌词的开始时间点处理整首歌曲中的百分比位置
        double lengthPercent = getLyricRow().getLyricRowLength() / mLyricRow.getMax();//本句歌词的时长在整首歌曲中的百分比
        double divProgress;//本句歌词的总体进度，用来判断是否正在播放。
        if (progress > startPercent + lengthPercent) {
            divProgress = 1.0f;
        } else if (progress < startPercent) {
            divProgress = 0.0f;
        } else {
            divProgress = (progress - startPercent) / lengthPercent;
        }

        if (divProgress < 1.0f && divProgress > 0) {
            double speed = params.getTextSpeed();
            double lyricCurrentTime = divProgress * mLyricRow.getLyricRowLength();
            double dividorPosition = lyricCurrentTime * speed + startX;
            drawText(canvas, paint, params.getColorChange(), startX, (int) dividorPosition);
            drawText(canvas, paint, params.getColor(), (int) dividorPosition, startX + textWidth);
            return true;
        } else {
            drawText(canvas, paint, params.getColor(), startX, startX + textWidth);
        }
        return false;
    }

    private void drawText(Canvas canvas, Paint paint, int color, int dividorPosition, int endX) {
        paint.setColor(color);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(dividorPosition, 0, endX, params.getCanvasHight());
        canvas.drawText(getLyricRow().getText(), startX, startY, paint);
        canvas.restore();
    }
}
