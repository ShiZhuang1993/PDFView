原著：https://github.com/voghDev/PdfViewPager#usage

本人练习写 做了一下AndroidX适配

安装：

在您的project / build.gradle中添加这一行

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
  在您的app / build.gradle中添加这一行
  
  dependencies {
	        implementation 'com.github.ShiZhuang1993:PDFView:1.0'
	}
  
用法:  

本地资源的PDF

1.-如果您的PDF位于资产目录中，则将资产复制到缓存目录

CopyAsset copyAsset = new CopyAssetThreadImpl(context, new Handler());
copyAsset.copy(asset, new File(getCacheDir(), "sample.pdf").getAbsolutePath());


2a。- 通过资产中的 PDF文件创建PDFViewPager（请参阅示例）

pdfViewPager = new PDFViewPager(this, "sample.pdf");


2b。-直接在XML布局上声明
它将自动具有缩放和平移功能
<es.voghdev.pdfviewpager.library.PDFViewPager
    android:id="@+id/pdfViewPager"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:assetFileName="sample.pdf"/>
    
    
    
    
 SD卡上的PDF
 
    1.-创建一个PDFViewPager对象，传递SD卡中的文件位置
    
PDFViewPager pdfViewPager = new PDFViewPager(context, getPdfPathOnSDCard());

protected String getPdfPathOnSDCard() {
    File f = new File(getExternalFilesDir("pdf"), "adobe.pdf");
    return f.getAbsolutePath();
}

2.-别忘了在onDestroy中释放适配器

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ((PDFPagerAdapter) pdfViewPager.getAdapter()).close();
    }

URl上的PDF

1 .-添加INTERNET，READ_EXTERNAL_STORAGE以及WRITE_EXTERNAL_STORAGE权限上的AndroidManifest.xml

<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

2.-使您的Activity或Fragment实现DownloadFile.Listener

public class RemotePDFActivity extends AppCompatActivity implements DownloadFile.Listener {


3.-创建一个RemotePDFViewPager对象

String url = "https://web.stanford.edu/~xgzhou/zhou_book2017.pdf";
RemotePDFViewPager remotePDFViewPager =
      new RemotePDFViewPager(context, url, this);
      
      
4.-配置相应的回调，并将在每种情况下调用它们。

@Override
public void onSuccess(String url, String destinationPath) {
    // That's the positive case. PDF Download went fine

    adapter = new PDFPagerAdapter(this, "AdobeXMLFormsSamples.pdf");
    remotePDFViewPager.setAdapter(adapter);
    setContentView(remotePDFViewPager);
}

@Override
public void onFailure(Exception e) {
    // This will be called if download fails
}

@Override
public void onProgressUpdate(int progress, int total) {
    // You will get download progress here
    // Always on UI Thread so feel free to update your views here
}


5.-不要忘记在onDestroy中关闭适配器以释放所有资源

@Override
protected void onDestroy() {
    super.onDestroy();

    adapter.close();
}


在Kotlin中的用法

您可能会发现，该库在Kotlin编程语言中完全可用。您可以在此处找到示例代码。

只需像在Java中那样将库作为gradle依赖项导入即可。


