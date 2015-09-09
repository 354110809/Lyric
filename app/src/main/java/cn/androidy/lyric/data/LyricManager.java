package cn.androidy.lyric.data;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rick Meng on 2015/6/18.
 */
public class LyricManager {
    private List<LyricDrawInfo> mLyricList = new ArrayList<>();
    private LyricDrawInfo mCurrentLyric;
    private float currentStartY;
    private LyricParams params;
    private float stepHeight;
    private Paint paint;
    private Rect mTextBound = new Rect();

    public List<LyricDrawInfo> getLyricList() {
        return mLyricList;
    }

    public LyricManager(LyricParams params) {
        this.params = params;
        paint = new Paint();
        paint.setTextSize(params.getTextSize());
    }

    public void playLyric(List<LyricRow> list) {
        mLyricList.clear();
        if (list != null && !list.isEmpty()) {
            for (LyricRow row : list) {
                mLyricList.add(new LyricDrawInfo(row, params));
            }
            mTextBound = measureText(mLyricList.get(0).getLyricRow().getText(), paint);
            setTextPlaySpeed();
        }
    }

    private void setTextPlaySpeed() {
        double result = 0;
        for (LyricDrawInfo lyricDrawInfo : mLyricList) {
            double w = paint.measureText(lyricDrawInfo.getLyricRow().getText());
            double speed = w / lyricDrawInfo.getLyricRow().getLyricRowLength();
            if (speed > result) {
                result = speed;
            }
        }
        params.setTextSpeed(result);
    }

    public void updateLyricInfo() {
        if (mLyricList != null && !mLyricList.isEmpty()) {
            currentStartY = params.getCanvasHight() / 2 - mTextBound.height() / 2;
            stepHeight = mTextBound.height() * 1.5f;
            updateLyricInfo(currentStartY);
        }
    }

    public void updateLyricInfo(float startY) {
        Paint paint = new Paint();
        paint.setTextSize(params.getTextSize());
        currentStartY = startY;
        if (mLyricList != null && !mLyricList.isEmpty()) {
            //将正在渲染的歌词居中处理。
            float tempStartY = currentStartY;
            for (LyricDrawInfo info : mLyricList) {
                info.updateLyricState(tempStartY += stepHeight);
            }
        }
    }

    private Rect measureText(String text, Paint paint) {
        int w = (int) paint.measureText(text);
        Rect mTextBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), mTextBound);
        return mTextBound;
    }


    public void dispatchDraw(Canvas canvas, Paint paint, float progress) {
        if (mLyricList.isEmpty()) {
            return;
        }

        for (LyricDrawInfo info : mLyricList) {
            boolean isCurrent = info.onDraw(canvas, paint, progress);
            if (isCurrent) {
                //如果换了一句歌词
                if (mCurrentLyric != info) {
                    if (mCurrentLyric != null) {
                        nextLyric();
                    }
                    mCurrentLyric = info;
                }
            }
        }
    }

    //调整歌词整体上移一句
    private void nextLyric() {
        float targetStartY = currentStartY - stepHeight;
        ValueAnimator animator = ValueAnimator.ofFloat(currentStartY, targetStartY).setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateLyricInfo(currentStartY);
                currentStartY = (float) ((ValueAnimator) animation).getAnimatedValue();
            }
        });
        animator.start();
    }
}
