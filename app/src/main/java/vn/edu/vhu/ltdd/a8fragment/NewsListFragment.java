package vn.edu.vhu.ltdd.a8fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsListFragment extends Fragment {

    private static final String TAG = "A8_231A010596/List";
    public static final String REQUEST_KEY = "news_request";

    private static final String[] TIEU_DE = {
            "VHU khai giảng năm học mới",
            "Android Studio ra bản cập nhật",
            "Flutter 3.47 có gì mới?",
    };
    private static final String[] NOI_DUNG = {
            "Trường Đại học Văn Hiến tổ chức lễ khai giảng với sự tham dự của toàn thể sinh viên khóa mới…",
            "Bản cập nhật tập trung vào tốc độ build, trình soạn thảo layout và công cụ phân tích bộ nhớ…",
            "Phiên bản này bổ sung RadioGroup, cải tiến hiệu năng trên web và nhiều widget Material 3…",
    };

    private TextView tvDaDoc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDaDoc = view.findViewById(R.id.tvDaDoc);
        LinearLayout box = view.findViewById(R.id.boxTin);

        for (int i = 0; i < TIEU_DE.length; i++) {
            final int viTri = i;
            Button b = new Button(requireContext());
            b.setText(TIEU_DE[i]);
            b.setAllCaps(false);
            b.setOnClickListener(v -> moChiTiet(viTri));
            box.addView(b);
        }

        // Lắng nghe kết quả trả về từ NewsDetailFragment thông qua Fragment Result API
        getParentFragmentManager().setFragmentResultListener(
                REQUEST_KEY, getViewLifecycleOwner(),
                (requestKey, bundle) -> {
                    String tieuDe = bundle.getString(NewsDetailFragment.KEY_TIEU_DE, "");
                    Log.d(TAG, "Nhận kết quả: đã đọc " + tieuDe);
                    tvDaDoc.setText(getString(R.string.list_last_read, tieuDe));
                });
    }

    private void moChiTiet(int viTri) {
        NewsDetailFragment f = NewsDetailFragment.newInstance(TIEU_DE[viTri], NOI_DUNG[viTri]);
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, f)
                .addToBackStack("chi_tiet")   // Cho phép bấm Back quay lại danh sách
                .setReorderingAllowed(true)
                .commit();
    }
}