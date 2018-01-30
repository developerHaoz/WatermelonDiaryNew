### 前言
> 本文的内容主要是解析日记 APP 的制作流程，以及代码的具体实现，若有什么不足之处，还请提出建议，附上这个 APP 的 Github 地址 [WatermelonDiaryNew](https://github.com/developerHaoz/WatermelonDiaryNew) 欢迎大家 star 和 fork.

#### 本文的主要内容
- 日记的展示
- 悬浮菜单的实现
- 日记增删改的实现

先来一波日记的展示吧，虽然内容比较简单，但还是设计的非常用心的，因此这款 APP 还是非常简洁和优雅的

![DiaryLICE.gif](http://upload-images.jianshu.io/upload_images/4334738-2844d5478c509396.gif?imageMogr2/auto-orient/strip)

## 一、日记的展示
#### 1、伪日记的处理
可以看到刚开始进入主页面，显示的是 `今天，你什么都没写下...` 这个伪日记，其实只要是某一天没有写日记的话，界面最上面显示的就是这个，当我们写了日记之后，这个伪日记便会消失，讲道理一开始实现这个还真花了我不少心思，本来的思路是将这个伪日记作为 RecyclerView 的第一个Item，如果当天有写日记了，就将它隐藏起来，等到了第二天再重新显示，但是感觉实现起来会很麻烦，后来想了想只要将这个伪日记，直接写在主页面的布局中，到时候如果检索到数据库里面，有某篇日记的日期跟当天的日期一致的话，就将伪日记从布局中 remove 掉就行了
```
 if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                // 这是我自己写的一个获取当天日期的一个方法
                String dateSystem = GetDate.getDate().toString();
                if (date.equals(dateSystem)) {
                    mMainLlMain.removeView(mItemFirst);
                    break;
                }
            } while (cursor.moveToNext());
        }
```

#### 2、使用 RecyclerView 展示日记
因为我是打算以事件线的形式来展示我们所写的日记，因此使用 RecyclerView 也算是比较合适的了。这里附上一篇将 RecyclerView 讲的很不错的博客 [RecyclerView 使用详解（一）](http://frank-zhu.github.io/android/2015/01/16/android-recyclerview-part-1/)

要想使用 RecyclerView来实现我们想要实现的效果，先让我们建立一个`item_rv_diary`作为 RecyclerView 的子布局
```
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                  android:id="@+id/item_ll"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:orientation="vertical"
                  android:paddingRight="10dp"
                  android:background="@color/white"
        >
    
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="25dp"
            android:orientation="horizontal"
            android:paddingLeft="10.8dp"
            android:background="#ffffff"
            >

            <ImageView
                android:id="@+id/main_iv_circle"
                android:paddingTop="2dp"
                android:layout_width="22dp"
                android:layout_height="22dp"
                android:src="@drawable/circle"
                android:layout_gravity="center_vertical"
                />

            <TextView
                android:id="@+id/main_tv_date"
                android:layout_width="0dp"
                android:layout_weight="1"
                android:layout_height="25dp"
                android:gravity="center_vertical"
                android:paddingLeft="4dp"
                android:paddingTop="1dp"
                android:text="2017年01月18日"
                android:textSize="14sp"
                />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            >

            <LinearLayout
                android:layout_width="23.3dp"
                android:layout_height="match_parent"
                android:background="@drawable/linear_style"
                >
            </LinearLayout>

            <LinearLayout
                android:id="@+id/item_ll_control"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                >

                <LinearLayout
                    android:id="@+id/main_ll_title"
                    android:layout_width="match_parent"
                    android:layout_height="35dp"
                    android:orientation="horizontal"
                    android:background="#ffffff"
                    >

                    <TextView
                        android:paddingTop="3dp"
                        android:background="#ffffff"
                        android:id="@+id/main_tv_title"
                        android:layout_width="match_parent"
                        android:layout_height="32dp"
                        android:gravity="center_vertical"
                        android:paddingLeft="16dp"
                        android:text="哈哈哈今天傻逼了"
                        android:textColor="@color/black"
                        android:textSize="19sp"
                        />
                </LinearLayout>

                <TextView
                    android:paddingTop="2dp"
                    android:background="#ffffff"
                    android:id="@+id/main_tv_content"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:lineSpacingExtra="4dp"
                    android:paddingLeft="33dp"
                    android:paddingRight="15dp"
                    android:text="         在这里写些什么在这里写些什么在这里写些什么在这里写些什么在这里写些什么在这里写些什么在这里写些什么在这里写些什么在这里写些什么在这里 写些什么在这里写些什么在这里写些什么在这里写些什么在这里写些什么在这里写些什么在这里写些什么"
                    android:textColor="@color/black"
                    android:textSize="16sp"
                    />

                <RelativeLayout
                    android:id="@+id/item_rl_edit"
                    android:layout_width="match_parent"
                    android:layout_height="40dp"
                    android:paddingRight="5dp"
                    android:background="#ffffff"
                    >

                    <ImageView
                        android:id="@+id/main_iv_edit"
                        android:layout_width="30dp"
                        android:layout_height="30dp"
                        android:layout_alignParentRight="true"
                        android:layout_centerInParent="true"
                        android:src="@drawable/edit"
                        />

                </RelativeLayout>

                <LinearLayout
                    android:background="#ffffff"
                    android:layout_width="match_parent"
                    android:layout_height="20dp">
                </LinearLayout>

            </LinearLayout>

        </LinearLayout>

    </LinearLayout>
```
布局还是比较简单的，比较难实现的应该是左边的那条竖线，其实，一开始并没有什么思路，因为
 shape 中的 line 只能画横线，而画不了竖线，最后在 Google 的帮助下，终于找到了实现这个竖线的思路，我是这样处理的，定义一个 layer-list 设置在 TextView 中，将 TextView 的右边框进行描绘
```
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 边框颜色值 --><item>
    <shape>
        <solid android:color="#8a8a8a" />
    </shape>
</item>
    <!-- 主体背景颜色值 -->
    <item android:right="1.5dp">
        <shape>
            <solid android:color="#ffffff" />
            <padding android:bottom="10dp"
                     android:left="10dp"
                     android:right="10dp"
                     android:top="10dp" />
        </shape>
    </item>
</layer-list>
```

写好子布局之后，再让我们来实现 RecyclerView 的 Adapter，首先定义了一个 DiaryViewHolder 继承自  RecyclerView.ViewHolder，传入一个保存日记信息的List，然后通过 `onCreateViewHolder` 来创建布局，通过 `onBindViewHolder` 将数据绑定到对应的Item上面，这里我使用了 [EventBus](https://github.com/greenrobot/EventBus) 通过点击编辑按钮打开修改日记的界面，  EventBus 是一款针对Android优化的发布/订阅事件总线，使用也是非常简单的，可以当作一个轻量级的BroadCastReceiver 来使用，有兴趣可以看看这篇文章                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       [EventBus 使用详解(一)——初步使用 EventBus](http://blog.csdn.net/harvic880925/article/details/40660137)
```
public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<DiaryBean> mDiaryBeanList;

    public DiaryAdapter(Context context, List<DiaryBean> mDiaryBeanList){
        mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mDiaryBeanList = mDiaryBeanList;
    }
    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiaryViewHolder(mLayoutInflater.inflate(R.layout.item_rv_diary, parent, false));
    }

    @Override
    public void onBindViewHolder(final DiaryViewHolder holder, final int position) {

        String dateSystem = GetDate.getDate().toString();

        /**
         * 如果该日记是当天写的，则将日期左边的圆圈设置成橙色的
         */
        if(mDiaryBeanList.get(position).getDate().equals(dateSystem)){
            holder.mIvCircle.setImageResource(R.drawable.circle_orange);
        }

        holder.mTvDate.setText(mDiaryBeanList.get(position).getDate());
        holder.mTvTitle.setText(mDiaryBeanList.get(position).getTitle());
        holder.mTvContent.setText(mContext.getString(R.string.spaces) + mDiaryBeanList.get(position).getContent());
        holder.mIvEdit.setVisibility(View.INVISIBLE);

        /**
         * 当点击日记的内容时候，则显示出编辑按钮
         */
        holder.mLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mIvEdit.getVisibility() == View.INVISIBLE) {
                    holder.mIvEdit.setVisibility(View.VISIBLE);
                }else {
                    holder.mIvEdit.setVisibility(View.INVISIBLE);
                }
            }
        });

        /**
         * 使用 EventBus 来打开修改日记的界面，事件接收函数载 MainActivity 中
         */
        holder.mIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartUpdateDiaryEvent(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiaryBeanList.size();
    }

     static class DiaryViewHolder extends RecyclerView.ViewHolder{

        TextView mTvDate;
        TextView mTvTitle;
        TextView mTvContent;
        ImageView mIvEdit;
        LinearLayout mLlTitle;
        LinearLayout mLl;
        ImageView mIvCircle;
        LinearLayout mLlControl;
        RelativeLayout mRlEdit;

        DiaryViewHolder(View view){
            super(view);
            mIvCircle = (ImageView) view.findViewById(R.id.main_iv_circle);
            mTvDate = (TextView) view.findViewById(R.id.main_tv_date);
            mTvTitle = (TextView) view.findViewById(R.id.main_tv_title);
            mTvContent = (TextView) view.findViewById(R.id.main_tv_content);
            mIvEdit = (ImageView) view.findViewById(R.id.main_iv_edit);
            mLlTitle = (LinearLayout) view.findViewById(R.id.main_ll_title);
            mLl = (LinearLayout) view.findViewById(R.id.item_ll);
            mLlControl = (LinearLayout) view.findViewById(R.id.item_ll_control);
            mRlEdit = (RelativeLayout) view.findViewById(R.id.item_rl_edit);
        }
    }
}
```

最后在 MainActivity 中将 RecyclerView 进行处理就行了
```
mMainRvShowDiary.setLayoutManager(new LinearLayoutManager(this));
mMainRvShowDiary.setAdapter(new DiaryAdapter(this, mDiaryBeanList));
```

#### 二、悬浮菜单的实现
悬浮菜单看起来逼格还是挺高的，  而且观赏性也算是比较高，我是从 Github 找的一个库，来实现这个悬浮菜单的，不得不说，搞这个悬浮菜单真的花了我不少时间，  有些库要么不能调节菜单的大小，要么不能调节菜单图案，找了好久才找到这个让我比较满意的库[FloatingActionButton](https://github.com/trity1993/FloatingActionButton)                                                                                                                                                                                                                             

虽然逼格挺高的，但使用起来却是相当的方便，先在build.grade中添加
```
dependencies {
    compile 'cc.trity.floatingactionbutton:library:1.0.0'
}
```
然后在布局中设置我们想要的颜色和图案，最后在 Activity 中进行悬浮按钮点击事件的处理就行了
```
       <cc.trity.floatingactionbutton.FloatingActionsMenu
           android:id="@+id/right_labels"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_alignParentBottom="true"
           android:layout_alignParentLeft="true"
           android:layout_alignParentStart="true"
           app:fab_expandDirection="right"
           app:fab_addButtonSize="mini"
           >

           <cc.trity.floatingactionbutton.FloatingActionButton
               android:id="@+id/update_diary_fab_back"
               android:layout_width="40dp"
               android:layout_height="40dp"
               app:fab_size="normal"
               app:fab_icon = "@drawable/delete_new"
               app:fab_colorNormal="#c8180e"
               />

           <cc.trity.floatingactionbutton.FloatingActionButton
               android:id="@+id/update_diary_fab_add"
               android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:background="@drawable/save"
               app:fab_size="normal"
               app:fab_title="FAB 2"
               app:fab_icon = "@drawable/save_new"
               app:fab_colorNormal="#24d63c"

               />

           <cc.trity.floatingactionbutton.FloatingActionButton
               android:id="@+id/update_diary_fab_delete"
               android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:background="@drawable/delete"
               app:fab_colorNormal="#d92410"
               app:fab_icon = "@drawable/back_new"
               app:fab_size="normal"
               app:fab_title="FAB 2"

               />

       </cc.trity.floatingactionbutton.FloatingActionsMenu>
```

#### 三、日记增删改的实现

日记的信息，我是使用 Android 自带的 SQLite 数据库进行保存的，做法也是比较简单的，这里附上一篇讲解 SQLite 的博客 [Android中SQLite应用详解](http://blog.csdn.net/liuhe688/article/details/6715983)，先建立一个 DiaryDatabaseHelper 作为我们进行数据库操作的帮助类，因为日记的内容比较简单，  因此，我只建了一张表
```
public class DiaryDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_DIARY = "create table Diary("
            + "id integer primary key autoincrement, "
            + "date text, "
            + "title text, "
            + "content text)";

    private Context mContext;

    public DiaryDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Diary");
        onCreate(db);
    }
}
```

**1、日记的添加**
获取添加日记界面中日记的日期、标题以及具体的内容，然后将这些信息添加到数据库中
```  
 String date = GetDate.getDate().toString();
        String title = mAddDiaryEtTitle.getText().toString() + "";
        String content = mAddDiaryEtContent.getText().toString() + "";
        if (!title.equals("") || !content.equals("")) {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("date", date);
            values.put("title", title);
            values.put("content", content);
            db.insert("Diary", null, values);
            values.clear();
```        

**2、日记的删除**
在这里我为了防止日记被误删，就做了一个对话框，当点击删除按钮的时候，便会跳出这个对话框询问用户是否真的要删除该日记
```
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("确定要删除该日记吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String title = mUpdateDiaryEtTitle.getText().toString();
                        SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                        dbDelete.delete("Diary", "title = ?", new String[]{title});
                        MainActivity.startActivity(UpdateDiaryActivity.this);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
```

**3、日记的修改**

```
                SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                ContentValues valuesUpdate = new ContentValues();
                String title = mUpdateDiaryEtTitle.getText().toString();
                String content = mUpdateDiaryEtContent.getText().toString();
                valuesUpdate.put("title", title);
                valuesUpdate.put("content", content);
                dbUpdate.update("Diary", valuesUpdate, "title = ?", new String[]{title});
                dbUpdate.update("Diary", valuesUpdate, "content = ?", new String[]{content});
```


以上便是我写这个 APP 的具体实现思路，以及踩过的一些坑，记录下来，给大家看看，最后附上这个 APP 的 Github 地址 [WatermelonDiaryNew](https://github.com/developerHaoz/WatermelonDiaryNew) 欢迎大家 star 和 fork，如果有什么想法或者建议，非常欢迎大家来讨论

-----
### 猜你喜欢
- [手把手教你从零开始做一个好看的 APP](http://www.jianshu.com/p/8d2d74d6046f)
- [Android 能让你少走弯路的干货整理](http://www.jianshu.com/p/514656c383a2)
- [Android 撸起袖子，自己封装 DialogFragment](http://www.jianshu.com/p/c9f20ec7277a)
