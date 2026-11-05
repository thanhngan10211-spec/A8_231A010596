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

public class MainActivity extends AppCompatActivity implements NewsListFragment.OnNewsSelectedListener {

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
                doiTab(ProfileFragment.newInstance("Nguyễn Văn A", "2201234567"));
            }
            return true;
        });

        if (savedInstanceState == null) {
            boolean isTablet = findViewById(R.id.detailContainer) != null;
            if (isTablet) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.listContainer, new NewsListFragment())
                        .replace(R.id.detailContainer, NewsDetailFragment.newInstance("Chọn một tin để xem", "Nội dung chi tiết sẽ hiển thị ở đây."))
                        .commit();
                bottomNav.setSelectedItemId(R.id.tab_news);
            } else {
                bottomNav.setSelectedItemId(R.id.tab_home);
            }
        }
    }

    private void doiTab(Fragment f) {
        Log.d(TAG, "Chuyển tab sang: " + f.getClass().getSimpleName());
        boolean isTablet = findViewById(R.id.detailContainer) != null;

        if (isTablet) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.listContainer, f)
                    .setReorderingAllowed(true)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, f)
                    .setReorderingAllowed(true)
                    .commit();
        }
    }

    // 4. Xử lý sự kiện nhận dữ liệu từ NewsListFragment (Thực thi NC2)
    @Override
    public void onNewsSelected(String title, String content) {
        boolean isTablet = findViewById(R.id.detailContainer) != null;

        NewsDetailFragment detailFragment = NewsDetailFragment.newInstance(title, content);

        if (isTablet) {
            // Trên TABLET: Cập nhật trực tiếp nội dung chi tiết vào cột bên phải (detailContainer)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detailContainer, detailFragment)
                    .setReorderingAllowed(true)
                    .commit();
        } else {
            // Trên ĐIỆN THOẠI: Thay thế vào khung chính (fragmentContainer) và cho phép bấm Back quay lại
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, detailFragment)
                    .addToBackStack(null)
                    .setReorderingAllowed(true)
                    .commit();
        }
    }
}