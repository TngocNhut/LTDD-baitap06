package com.tngocnhat.baitap06;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {

    private final Paint inactivePaint;
    private final Paint activePaint;
    private final float indicatorHeight;
    private final float indicatorStrokeWidth;
    private final float indicatorItemLength;
    private final float indicatorItemPadding;
    private final float indicatorRadius;
    private final Context context;

    public LinePagerIndicatorDecoration(Context context) {
        this.context = context;
        inactivePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        inactivePaint.setStyle(Paint.Style.FILL);
        inactivePaint.setColor(0x66FFFFFF);

        activePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        activePaint.setStyle(Paint.Style.FILL);
        activePaint.setColor(0xFFFFFFFF);

        indicatorHeight = dpToPx(16);
        indicatorStrokeWidth = dpToPx(4);
        indicatorItemLength = dpToPx(30);
        indicatorItemPadding = dpToPx(8);
        indicatorRadius = dpToPx(2);
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, RecyclerView.State state) {
        int itemCount = parent.getAdapter() != null ? parent.getAdapter().getItemCount() : 0;
        if (itemCount == 0) return;

        float totalLength = indicatorItemLength * itemCount;
        float totalPadding = Math.max(0, itemCount - 1) * indicatorItemPadding;
        float totalIndicatorWidth = totalLength + totalPadding;
        float startX = (parent.getWidth() - totalIndicatorWidth) / 2f;
        float posY = parent.getHeight() - indicatorHeight;

        // draw inactive lines
        for (int i = 0; i < itemCount; i++) {
            float x1 = startX + (indicatorItemLength + indicatorItemPadding) * i;
            RectF r = new RectF(x1, posY, x1 + indicatorItemLength, posY + indicatorStrokeWidth);
            c.drawRoundRect(r, indicatorRadius, indicatorRadius, inactivePaint);
        }

        int childCount = parent.getChildCount();
        if (childCount == 0) return;

        // find child whose center is nearest to parent center
        int parentCenterX = (parent.getLeft() + parent.getRight()) / 2;
        int minDistance = Integer.MAX_VALUE;
        View nearestChild = null;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int childCenterX = (child.getLeft() + child.getRight()) / 2;
            int distance = Math.abs(childCenterX - parentCenterX);
            if (distance < minDistance) {
                minDistance = distance;
                nearestChild = child;
            }
        }
        if (nearestChild == null) return;
        int activePosition = parent.getChildAdapterPosition(nearestChild);
        if (activePosition == RecyclerView.NO_POSITION) return;

        // draw active line
        float left = startX + (indicatorItemLength + indicatorItemPadding) * activePosition;
        RectF ar = new RectF(left, posY, left + indicatorItemLength, posY + indicatorStrokeWidth);
        c.drawRoundRect(ar, indicatorRadius, indicatorRadius, activePaint);
    }
}
