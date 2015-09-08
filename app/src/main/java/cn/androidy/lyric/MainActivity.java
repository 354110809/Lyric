package cn.androidy.lyric;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.androidy.lyric.data.LyricRow;
import cn.androidy.lyric.view.LyricView;

public class MainActivity extends AppCompatActivity {
    private LyricView mLyricView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLyricView = (LyricView) findViewById(R.id.lyricView);

        List<LyricRow> list = new ArrayList<>();
        list.add(new LyricRow("风吹雨成花", 0, 1000,8000));
        list.add(new LyricRow("时间追不上白马", 1000, 2000,8000));
        list.add(new LyricRow("你年少掌心的梦话", 2000, 3000,8000));
        list.add(new LyricRow("依然紧握着吗", 3000, 4000,8000));
        list.add(new LyricRow("云翻涌成夏", 4000, 5000,8000));
        list.add(new LyricRow("眼泪被岁月蒸发", 5000, 6000,8000));
        list.add(new LyricRow("这条路上的你我她", 6000, 7000,8000));
        list.add(new LyricRow("有谁迷路了吗", 7000, 8000,8000));
        mLyricView.playLyric(list);
    }

    public void startPlay(View view) {
        mLyricView.startOrPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
