package com.adp.communicationdisplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

public class BatteryView extends View {
    private float radius = 0f;
    //private boolean isCharging = false;

    // Top
    private ColorDrawable topPaint = new ColorDrawable(Color.WHITE);
    private Rect topRect = new Rect();
    private int topPaintWidthPercent = 50;
    private int topPaintHeightPercent = 8;

    // Border
    private Paint borderPaint = new Paint();
    private RectF borderRect = new RectF();
    private int borderStrokeWidthPercent = 8;
    private float borderStroke = 0f;

    // Percent
    private Paint percentPaint = new Paint();
    private RectF percentRect = new RectF();
    private float percentRectTopMin = 0f;
    private int percent = 0;

    // Charging
    private RectF chargingRect = new RectF();
    //private Bitmap chargingBitmap = null;

    public BatteryView(Context context) {
        super(context);
        init(null);
        //chargingBitmap = getBitmap(R.drawable.ic_charging);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        //chargingBitmap = getBitmap(R.drawable.ic_charging);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        //chargingBitmap = getBitmap(R.drawable.ic_charging);
    }

    private void init(@Nullable AttributeSet attrs) {
        // Read attributes if needed
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = (int) (View.getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec)/1.7);
        int measureHeight = (int) (measureWidth * 1.8f);
        setMeasuredDimension(measureWidth, measureHeight);

        radius = borderStroke / 2;
        borderStroke = (borderStrokeWidthPercent * measureWidth) / 100f;

        // Top
        int topLeft = (int) (measureWidth * ((100 - topPaintWidthPercent) / 2) / 100);
        int topRight = measureWidth - topLeft;
        int topBottom = (topPaintHeightPercent * measureHeight) / 100;
        topRect.set(topLeft, 0, topRight, topBottom);

        // Border
        float borderLeft = borderStroke / 2;
        float borderTop = topBottom + borderStroke / 2;
        float borderRight = measureWidth - borderStroke / 2;
        float borderBottom = measureHeight - borderStroke / 2;
        borderRect.set(borderLeft, borderTop, borderRight, borderBottom);

        // Progress
        float progressLeft = borderStroke;
        percentRectTopMin = topBottom + borderStroke;
        float progressRight = measureWidth - borderStroke;
        float progressBottom = measureHeight - borderStroke;
        percentRect.set(progressLeft, percentRectTopMin, progressRight, progressBottom);

        // Charging Image
        float chargingLeft = borderStroke;
        float chargingTop = topBottom + borderStroke;
        float chargingRight = measureWidth - borderStroke;
        float chargingBottom = measureHeight - borderStroke;
        float diff = ((chargingBottom - chargingTop) - (chargingRight - chargingLeft));
        chargingTop += diff / 2;
        chargingBottom -= diff / 2;
        chargingRect.set(chargingLeft, chargingTop, chargingRight, chargingBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawTop(canvas);
        drawBody(canvas);
        drawProgress(canvas, percent);
    }

    private void drawTop(Canvas canvas) {
        topPaint.setBounds(topRect);
        //topPaint.setCornerRadii(new float[]{radius, radius, radius, radius, 0f, 0f, 0f, 0f});
        topPaint.draw(canvas);
    }

    private void drawBody(Canvas canvas) {
        borderPaint.setStrokeWidth(borderStroke);
        borderPaint.setColor(Color.BLACK);
        canvas.drawRoundRect(borderRect, radius, radius, borderPaint);
    }

    private void drawProgress(Canvas canvas, int percent) {
        percentPaint.setColor(getPercentColor(percent));
        percentRect.top = percentRectTopMin + (percentRect.bottom - percentRectTopMin) * (100 - percent) / 100;
        canvas.drawRect(percentRect, percentPaint);
        final float testTextSize = 50f;
        // Get the bounds of the text, using our testTextSize.
        Paint writepaint = new Paint();
        writepaint.setTextSize(testTextSize);
        writepaint.setColor(Color.WHITE);
        canvas.drawText(String.valueOf(percent), (float) ((canvas.getWidth() - testTextSize)*0.45),(canvas.getHeight()+testTextSize)/2,writepaint);
    }

    private int getPercentColor(int percent) {
        if (percent > 60) {
            return Color.GREEN;
        }
        if (percent > 40) {
            return Color.rgb(255,168,51);
        }
        return Color.RED;
    }

    //private void drawCharging(Canvas canvas) {
    //    if (chargingBitmap != null) {
   //         canvas.drawBitmap(chargingBitmap, null, chargingRect, null);
    //    }
   // }

    private Bitmap getBitmap(int drawableId) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), drawableId, null);
        if (drawable == null) {
            return null;
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    //public void charge() {
        //isCharging = true;
        //invalidate();
    //}

   //public void unCharge() {
    //    isCharging = false;
     //   invalidate();
    //}

    public void setPercent(int percent) {
        Log.e("PERCENT",percent+"");
        if (percent > 100 || percent < 0) {
            return;
        }
        this.percent = percent;
        invalidate();
    }

    public int getPercent() {
        return percent;
    }
}
