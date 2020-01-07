package com.popkovanton.utilssample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.popkovanton.utils.ui.recomptureimageview.RecomputeImageView;
import com.popkovanton.utils.ui.recomptureimageview.ScalableType;

public class RecomputeImageActivity extends AppCompatActivity {

    private RecomputeImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recompute_image);

        mImageView = findViewById(R.id.image_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recompute_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ScalableType scalableType;
        switch (item.getItemId()) {
            case R.id.scale_none:
                scalableType = ScalableType.NONE;
                break;

            case R.id.scale_fit_xy:
                scalableType = ScalableType.FIT_XY;
                break;
            case R.id.scale_fit_start:
                scalableType = ScalableType.FIT_START;
                break;
            case R.id.scale_fit_center:
                scalableType = ScalableType.FIT_CENTER;
                break;
            case R.id.scale_fit_end:
                scalableType = ScalableType.FIT_END;
                break;

            case R.id.scale_left_top:
                scalableType = ScalableType.LEFT_TOP;
                break;
            case R.id.scale_left_center:
                scalableType = ScalableType.LEFT_CENTER;
                break;
            case R.id.scale_left_bottom:
                scalableType = ScalableType.LEFT_BOTTOM;
                break;
            case R.id.scale_center_top:
                scalableType = ScalableType.CENTER_TOP;
                break;
            case R.id.scale_center:
                scalableType = ScalableType.CENTER;
                break;
            case R.id.scale_center_bottom:
                scalableType = ScalableType.CENTER_BOTTOM;
                break;
            case R.id.scale_right_top:
                scalableType = ScalableType.RIGHT_TOP;
                break;
            case R.id.scale_right_center:
                scalableType = ScalableType.RIGHT_CENTER;
                break;
            case R.id.scale_right_bottom:
                scalableType = ScalableType.RIGHT_BOTTOM;
                break;

            case R.id.scale_left_top_crop:
                scalableType = ScalableType.LEFT_TOP_CROP;
                break;
            case R.id.scale_left_center_crop:
                scalableType = ScalableType.LEFT_CENTER_CROP;
                break;
            case R.id.scale_left_bottom_crop:
                scalableType = ScalableType.LEFT_BOTTOM_CROP;
                break;
            case R.id.scale_center_top_crop:
                scalableType = ScalableType.CENTER_TOP_CROP;
                break;
            case R.id.scale_center_crop:
                scalableType = ScalableType.CENTER_CROP;
                break;
            case R.id.scale_center_bottom_crop:
                scalableType = ScalableType.CENTER_BOTTOM_CROP;
                break;
            case R.id.scale_right_top_crop:
                scalableType = ScalableType.RIGHT_TOP_CROP;
                break;
            case R.id.scale_right_center_crop:
                scalableType = ScalableType.RIGHT_CENTER_CROP;
                break;
            case R.id.scale_right_bottom_crop:
                scalableType = ScalableType.RIGHT_BOTTOM_CROP;
                break;

            case R.id.scale_start_inside:
                scalableType = ScalableType.START_INSIDE;
                break;
            case R.id.scale_center_inside:
                scalableType = ScalableType.CENTER_INSIDE;
                break;
            case R.id.scale_end_inside:
                scalableType = ScalableType.END_INSIDE;
                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        mImageView.setScalableType(scalableType);
        mImageView.requestLayout();
        return true;
    }
}
