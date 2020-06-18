package com.liqian.myrichedittext;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.liqian.myrichedittext.bean.FontStyle;
import com.liqian.myrichedittext.handle.Utils;
import com.liqian.myrichedittext.view.FontStylePanel;
import com.liqian.myrichedittext.view.RichEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create by liqian yang
 */
public class MainActivity extends AppCompatActivity implements FontStylePanel.OnFontPanelListener
        , RichEditText.OnSelectChangeListener {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @BindView(R.id.richEditText)
    RichEditText richEditText;
    @BindView(R.id.fontStylePanel)
    FontStylePanel fontStylePanel;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        textView.setVisibility(View.VISIBLE);
        fontStylePanel.setOnFontPanelListener(this);
        richEditText.setOnSelectChangeListener(this);

    }


    @Override
    public void setBold(boolean isBold) {
        richEditText.setBold(isBold);
    }

    @Override
    public void setItalic(boolean isItalic) {
        richEditText.setItalic(isItalic);
    }

    @Override
    public void setUnderline(boolean isUnderline) {
        richEditText.setUnderline(isUnderline);
    }

    @Override
    public void setStreak(boolean isStreak) {
        richEditText.setStreak(isStreak);
    }

    @Override
    public void insertImg() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                pickPicture();
            }
        } else {
            pickPicture();
        }
    }

    private void pickPicture() {
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, 0);
    }

    //字体大小
    @Override
    public void setFontSize(int size) {
        richEditText.setFontSize(size);
    }

    //颜色设置
    @Override
    public void setFontColor(int color) {
        richEditText.setFontColor(color);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            Uri originalUri = data.getData(); // 获得图片的uri
            String path = Utils.getRealPathFromUri(this, originalUri);
            richEditText.setImg(path);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 样式改变
     *
     * @param fontStyle
     */
    @Override
    public void onFontStyleChang(FontStyle fontStyle) {
        fontStylePanel.initFontStyle(fontStyle);
    }

    /**
     * 光标选中监听
     *
     * @param start
     * @param end
     */
    @Override
    public void onSelect(int start, int end) {
    }

    private void checkPermission() {

    }
}
