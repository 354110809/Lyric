package cn.androidy.lyric.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.androidy.lyric.R;
import cn.androidy.lyric.data.LyricManager;
import cn.androidy.lyric.data.LyricRow;
import cn.androidy.lyric.data.LyricParams;
import cn.androidy.lyric.data.LyricUtil;

/**
 * Created by Rick Meng on 2015/6/18.
 */
public class LyricView extends View implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    private Paint mPaint;
    private int mTextSize = sp2px(30);
    private int mTextOriginColor = 0xff000000;
    private int mTextChangeColor = 0xffff0000;
    private int mMaxTextWidth;
    private float mProgress;
    private LyricManager mLyricManager;
    private ValueAnimator animator;
    private boolean isPlaying = false;
    private LyricParams params;

    public LyricView(Context context) {
        super(context, null);
        init(context, null);
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs,
                    R.styleable.Lyric);
            mTextSize = ta.getDimensionPixelSize(
                    R.styleable.Lyric_text_size, mTextSize);
            mTextOriginColor = ta.getColor(
                    R.styleable.Lyric_text_origin_color,
                    mTextOriginColor);
            mTextChangeColor = ta.getColor(
                    R.styleable.Lyric_text_change_color,
                    mTextChangeColor);
            mProgress = ta.getFloat(R.styleable.Lyric_progress, 0);
            ta.recycle();
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);

        params = new LyricParams();
        params.setColor(mTextOriginColor);
        params.setColorChange(mTextChangeColor);
        params.setTextSize(mTextSize);
        mLyricManager = new LyricManager(params);
    }

    public void startOrPause() {
        if (isPlaying) {
            animator.cancel();
            isPlaying = false;
        } else {
            if (mLyricManager.getLyricList() != null && !mLyricManager.getLyricList().isEmpty()) {
                animator = ValueAnimator.ofFloat(mProgress, 1).setDuration((long) (mLyricManager.getLyricList().get(0).getLyricRow().getMax() * (1 - mProgress)));
                animator.setInterpolator(new LinearInterpolator());
                animator.addUpdateListener(this);
                animator.addListener(this);
                animator.start();
            }
        }
    }

    public void playLyric(List<LyricRow> list) {
        mLyricManager.playLyric(list);
    }

    public void playLyric(InputStream inputStream) {
        List<LyricRow> list = new ArrayList<>();
        try {
            LyricUtil lyricUtil = new LyricUtil(inputStream);
            List<LyricUtil.Statement> statementList = lyricUtil.getLrcList();
            for (int i = 0, count = statementList.size(); i < count; i++) {
                if (i < count - 1) {
                    LyricUtil.Statement current = statementList.get(i);
                    LyricUtil.Statement next = statementList.get(i + 1);
                    LyricRow lyricRow = new LyricRow(current.getLyric(), current.getTimeMillis(), next.getTimeMillis(), statementList.get(count - 1).getTimeMillis());
                    list.add(lyricRow);
                }
            }
            playLyric(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        mMaxTextWidth = width - getPaddingLeft() - getPaddingRight();
        params.setCanvasHight(getMeasuredHeight());
        params.setCanvasWidth(getMeasuredWidth());
        params.setMaxTextWidth(mMaxTextWidth);
        mLyricManager.updateLyricInfo();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLyricManager.dispatchDraw(canvas, mPaint, mProgress);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
    }

    private int sp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, getResources().getDisplayMetrics());
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        LyricView.this.mProgress = (float) animation.getAnimatedValue();
        invalidate();
    }

    @Override
    public void onAnimationStart(Animator animation) {
        isPlaying = true;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        isPlaying = false;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        isPlaying = false;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        isPlaying = true;
    }
}
