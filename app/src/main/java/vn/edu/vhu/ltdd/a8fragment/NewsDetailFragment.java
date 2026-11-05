package vn.edu.vhu.ltdd.a8fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/** Màn hình chi tiết tin, nhận dữ liệu qua arguments (Bundle). */
public class NewsDetailFragment extends Fragment {

    // Khai báo public static để NewsListFragment có thể truy cập qua NewsDetailFragment.KEY_TIEU_DE
    public static final String KEY_TIEU_DE = "key_tieu_de";
    private static final String KEY_NOI_DUNG = "key_noi_dung";

    public NewsDetailFragment() {
        // Required empty public constructor
    }

    public static NewsDetailFragment newInstance(String tieuDe, String noiDung) {
        NewsDetailFragment f = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TIEU_DE, tieuDe);
        args.putString(KEY_NOI_DUNG, noiDung);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_news_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = requireArguments();
        String tieuDe = args.getString(KEY_TIEU_DE, "");
        String noiDung = args.getString(KEY_NOI_DUNG, "");

        ((TextView) view.findViewById(R.id.tvTieuDe)).setText(tieuDe);
        ((TextView) view.findViewById(R.id.tvNoiDung)).setText(noiDung);

        Button btnDaDoc = view.findViewById(R.id.btnDaDoc);
        if (btnDaDoc != null) {
            btnDaDoc.setOnClickListener(v -> {
                // Gửi kết quả về Fragment đang lắng nghe rồi tự đóng
                Bundle kq = new Bundle();
                kq.putString(KEY_TIEU_DE, tieuDe);
                getParentFragmentManager().setFragmentResult(NewsListFragment.REQUEST_KEY, kq);
                getParentFragmentManager().popBackStack();
            });
        }
    }
}