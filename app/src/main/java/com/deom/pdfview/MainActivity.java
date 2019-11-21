package com.deom.pdfview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.deom.pdfviewlibs.RemotePDFViewPager;
import com.deom.pdfviewlibs.adapter.PDFPagerAdapter;
import com.deom.pdfviewlibs.remote.DownloadFile;
import com.deom.pdfviewlibs.util.FileUtil;

public class MainActivity extends AppCompatActivity implements DownloadFile.Listener{
    private RelativeLayout pdf_root;
    private RemotePDFViewPager remotePDFViewPager;
    private PDFPagerAdapter adapter;


    private String url = "https://web.stanford.edu/~xgzhou/zhou_book2017.pdf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pdf_root = (RelativeLayout) findViewById(R.id.remote_pdf_root);
        //设置监听v
        final DownloadFile.Listener listener = this;
        remotePDFViewPager = new RemotePDFViewPager(this, url, listener);
        remotePDFViewPager.setId(R.id.pdfViewPager);

    }
    //加载成功
    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        pdf_root.removeAllViewsInLayout();
        pdf_root.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

    }
    //加载失败
    @Override
    public void onFailure(Exception e) {

    }
    /*
    * progress：当前进度
    * total：总进度
    * */
    @Override
    public void onProgressUpdate(int progress, int total) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.close();
    }
}
