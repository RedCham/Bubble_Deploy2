package com.example.bubble_deploy.mainActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.bubble_deploy.R;
import com.example.bubble_deploy.referenceClass.EditMediaStore;
import com.example.bubble_deploy.referenceClass.GalleryActivity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.File;


public class BubblingFolderActivity extends GalleryActivity {
    AlertDialog.Builder builder;
    int folderState=0;
    String strPicFolder;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;    //this
        setContentView(R.layout.activity_bubblingfolder);
        super.onCreate(savedInstanceState);
        folderSelectState = getIntent().getIntExtra("folderState",0);

        BottomBar bottomBar = (BottomBar)findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_photo);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_Home:
                        finish();
                        break;
                    case R.id.tab_photo:

                        break;
                    case R.id.tab_next:
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        break;
                }
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            //가운데 아이템이 활성화 되어있는 상태고 다시 눌렀을 때 선택한거 다사라짐
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if(tabId==R.id.tab_photo){
                    gridAdapter.clearSelectedItem();
                }else if(tabId==R.id.tab_next){
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        // alertDialog 생성
        createAndSetAdapter();
        builder = new AlertDialog.Builder(BubblingFolderActivity.this);
        final String[] items = publicfolderNames.toArray(new String[publicfolderNames.size()]);

        builder.setTitle("Let's go Bubbling!")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        strPicFolder=items[which];
                        Toast.makeText(getApplicationContext(), items[which]+ " is clicked", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
               AlertDialog.Builder innBuilder = new AlertDialog.Builder( BubblingFolderActivity.this);
               innBuilder.setMessage("이 폴더로 모든 사진을 이동합니다");
               innBuilder.setTitle(strPicFolder);
               innBuilder .setPositiveButton( "확인", new DialogInterface.OnClickListener(){
                   public void onClick( DialogInterface dialog, int which) {
                       String folderPath;
                       // 폴더의 위치를 찾아야 함.
                       // DCIM
                       File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),strPicFolder);
                       if (folder.exists()) {
                            folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/"+strPicFolder;
                       }
                       else{
                           // Pictures
                           folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),strPicFolder);
                           if (folder.exists()) {
                               folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+strPicFolder;
                           }
                           else{
                               // Download
                               folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),strPicFolder);
                               if (folder.exists()) {
                                   folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+strPicFolder;
                               }
                               else{
                                   folderPath="fail";
                               }
                           }
                       }
                       //folder의 위치를 못찾거나(fail) sd카드이거나 폰의 버전이 9이하이면 폴더 이동 막힘
                       if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                           Toast.makeText(mContext.getApplicationContext(), "안드로이드 버전 10 미만에서는 폴더 이동이 되지 않습니다.", Toast.LENGTH_SHORT).show();
                       }
                       else if(folderPath.equals("fail")){
                           Toast.makeText(mContext.getApplicationContext(), "권한 밖에 있는 폴더 입니다!", Toast.LENGTH_SHORT).show();
                       }
                       else{
                           for(int i=0;i<gridAdapter.mSelectedItems.size();i++){
                               // folder 이동시킬 때는 mediaStore만 사용해서 이동시킴
                               new EditMediaStore(
                                       mContext,
                                       imageBitmapList.get(gridAdapter.mSelectedItems.keyAt(i)).getImagePath(),
                                       imageBitmapList.get(gridAdapter.mSelectedItems.keyAt(i)).getImageAbPate(),
                                       folderPath,
                                       imageBitmapList.get(gridAdapter.mSelectedItems.keyAt(i)).getImageName(),
                                       imageBitmapList.get(gridAdapter.mSelectedItems.keyAt(i)).getImageDate()
                               );
                           }
                       }
                       dialog.dismiss();
                       finish();
                   }
               }); innBuilder.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume(){
        super.onResume();
        BottomBar bottomBar = (BottomBar)findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_photo);
    }
}