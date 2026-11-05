package vn.edu.vhu.ltdd.a8fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // TODO: Thay 2201234567 bằng MSSV của bạn
    private static final String TAG = "A8_2201234567";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.tab_home) {
                doiTab(new HomeFragment());
            } else if (id == R.id.tab_news) {
                doiTab(new NewsListFragment());
            } else if (id == R.id.tab_profile) {
                doiTab(ProfileFragment.newInstance("Võ Phan Thanh Ngân", "231A010596"));
            }
            return true;   // BẮT BUỘC trả về true để tô sáng tab được chọn
        });

        // Chỉ nạp Fragment mặc định ở lần tạo đầu tiên để tránh bị nạp chồng khi xoay màn hình
        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.tab_home);
        }
    }

    private void doiTab(Fragment f) {
        Log.d(TAG, "Chuyển tab sang: " + f.getClass().getSimpleName());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, f)
                // KHÔNG gọi addToBackStack cho các tab chính để bấm Back thoát app luôn
                .setReorderingAllowed(true)
                .commit();
    }
}