package com.jiushig.markdown.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.jiushig.markdown.BaseActivity;
import com.jiushig.markdown.R;
import com.jiushig.markdown.ui.adapter.ViewPageAdapter;
import com.jiushig.markdown.ui.fragment.EditorFragment;
import com.jiushig.markdown.ui.fragment.PreviewFragment;
import com.jiushig.markdown.utils.Permission;

import java.util.ArrayList;
import java.util.List;


public class EditorActivity extends BaseActivity {

    protected ViewPager viewPager;

    private EditorFragment editorFragment = new EditorFragment();
    private PreviewFragment previewFragment = new PreviewFragment();

    public final static int REQUEST_CODE_IMG = 4000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        setToolBar();
        initViews();

        toolbar.setTitle(R.string.action_ot_edit);
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(editorFragment);
        fragments.add(previewFragment);
        viewPager.setAdapter(new ViewPageAdapter(getSupportFragmentManager(), fragments));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    toolbar.setTitle(R.string.action_ot_edit);
                }
                if (position == 1) {
                    previewFragment.load(editorFragment.editText.getText().toString());
                    toolbar.setTitle(R.string.action_ot_preview);
                    closeKeyboard(editorFragment.editText, EditorActivity.this);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 获得启动此Activity的Intent
     *
     * @param title
     * @param text
     */
    public static Intent getStartIntent(String title, String text) {
        Intent intent = new Intent();
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        return intent;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_reference) {
            clickReference();
        } else if (item.getItemId() == R.id.action_save) {
            clickSave(editorFragment.editTitle.getText().toString(), editorFragment.editText.getText().toString());
        } else if (item.getItemId() == R.id.action_more) {
            editorFragment.expandableLayout.toggle();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_IMG) {
                editorFragment.richHandler.addImg(data.getData());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permission.REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                editorFragment.richHandler.onClick(findViewById(R.id.edit_img));
            } else {
                Snackbar.make(findViewById(R.id.root), R.string.sd_fail, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 语法参考
     */
    protected void clickReference() {

    }

    /**
     * 保存
     *
     * @param title
     * @param text
     */
    protected void clickSave(String title, String text) {

    }

}
