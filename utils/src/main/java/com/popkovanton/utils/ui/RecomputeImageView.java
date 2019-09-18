package com.popkovanton.utils.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.popkovanton.utils.R;

@SuppressLint("AppCompatCustomView")
public class RecomputeImageView extends ImageView {

    private Position position = Position.TOP;
    private float translateY ;
    private float translateX;
    private float scaleX;
    private float scaleY;

    public enum Position {
        TOP, BOTTOM, MIDDLE, CENTER_INSIDE, CENTER_CROP, FIT_CENTER, FIT_START, FIT_END, FIT_XY
    }

    public RecomputeImageView(Context context) {
        super(context);
        setScaleType(ScaleType.MATRIX);
    }

    public RecomputeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RecomputeImageView, 0, 0);
        try {
            position = Position.values()[a.getInt(R.styleable.RecomputeImageView_riv_scaleT, 0)];
            translateY = a.getFloat(R.styleable.RecomputeImageView_riv_translateY, 0.0f);
            translateX = a.getFloat(R.styleable.RecomputeImageView_riv_translateX, 0.0f);
            scaleX = a.getFloat(R.styleable.RecomputeImageView_riv_scale, 1.0f);
            scaleY = a.getFloat(R.styleable.RecomputeImageView_riv_scale, 1.0f);
        } finally {
            a.recycle();
        }
        invalidate();
    }

    /**
     * If you choose MIDDLE, you need to change translateX or translateY
     */
    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        switch (getPosition()) {
            case TOP:
                recomputeImgMatrix();
                break;
            case BOTTOM:
                recomputeImgMatrix(l, t, r, b);
                break;
            case MIDDLE:
                //translateScale();
                recomputeImgMatrixMiddle();
                break;
            case CENTER_INSIDE:
                setScaleType(ScaleType.CENTER_INSIDE);
                break;
            case CENTER_CROP:
                setScaleType(ScaleType.CENTER_CROP);
                break;
            case FIT_CENTER:
                setScaleType(ScaleType.FIT_CENTER);
                break;
            case FIT_START:
                setScaleType(ScaleType.FIT_START);
                break;
            case FIT_END:
                setScaleType(ScaleType.FIT_END);
                break;
            case FIT_XY:
                setScaleType(ScaleType.FIT_XY);
                break;
        }
        return super.setFrame(l, t, r, b);
    }

    private void recomputeImgMatrix() {
        if (getDrawable() != null) {
            final Matrix matrix = getImageMatrix();

            float scale;
            final int viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            final int viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();
            final int drawableWidth = getDrawable().getIntrinsicWidth();
            final int drawableHeight = getDrawable().getIntrinsicHeight();

            if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
                scale = (float) viewHeight / (float) drawableHeight;
            } else {
                scale = (float) viewWidth / (float) drawableWidth;
            }

            matrix.setScale(scale, scale);
            setImageMatrix(matrix);
        }
    }

    private void recomputeImgMatrixMiddle() {
        if (getDrawable() != null) {
            final Matrix matrix = getImageMatrix();

            final int viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            final int viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();

            matrix.setTranslate(viewWidth * getTranslateX(), viewHeight * getTranslateY());
            setImageMatrix(matrix);
        }
    }

    private void translateScale(){
        if (getDrawable() != null) {
            final Matrix matrix = getImageMatrix();

            final int viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            final int viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();

            matrix.setTranslate(viewWidth * getTranslateX(), viewHeight * getTranslateY());
            matrix.postScale(getScaleXCustom(), getScaleYCustom());
            setImageMatrix(matrix);
        }
    }

    private void setScale(){
        setScaleX(scaleX);
        setScaleY(scaleX);
    }

    private void recomputeImgMatrix(int frameLeft, int frameTop, int frameRight, int frameBottom) {
        if (getDrawable() != null) {
            float frameWidth = frameRight - frameLeft;
            float frameHeight = frameBottom - frameTop;

            float originalImageWidth = (float) getDrawable().getIntrinsicWidth();
            float originalImageHeight = (float) getDrawable().getIntrinsicHeight();

            float usedScaleFactor = 1;

            if ((frameWidth > originalImageWidth) || (frameHeight > originalImageHeight)) {
                // If frame is bigger than image
                // => Crop it, keep aspect ratio and position it at the bottom and center horizontally

                float fitHorizontallyScaleFactor = frameWidth / originalImageWidth;
                float fitVerticallyScaleFactor = frameHeight / originalImageHeight;

                usedScaleFactor = Math.max(fitHorizontallyScaleFactor, fitVerticallyScaleFactor);
            }

            float newImageWidth = originalImageWidth * usedScaleFactor;
            float newImageHeight = originalImageHeight * usedScaleFactor;

            Matrix matrix = getImageMatrix();
            matrix.setScale(usedScaleFactor, usedScaleFactor, 0,
                    0); // Replaces the old matrix completly
            matrix.postTranslate((frameWidth - newImageWidth) / 2, frameHeight - newImageHeight);

            setImageMatrix(matrix);
        }
    }

    /**
     * Override on extend class
     * @return matrix position
     */
    protected Position getPosition() {
        return position;
    }

    protected float getTranslateY() {
        return translateY;
    }

    protected float getTranslateX() {
        return translateX;
    }

    protected float getScaleYCustom() {
        return scaleY;
    }

    protected float getScaleXCustom() {
        return scaleX;
    }
}
