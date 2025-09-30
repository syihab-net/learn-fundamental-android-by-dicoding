package com.ahmad.dicodingevent.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ahmad.dicodingevent.R;
import com.ahmad.dicodingevent.data.response.Event;
import com.ahmad.dicodingevent.databinding.ActivityDetailBinding;
import com.bumptech.glide.Glide;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    public static final String EXTRA_EVENT_ID = "extra_event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DetailViewModel mModel = new ViewModelProvider(this).get(DetailViewModel.class);

        if (mModel.getEvent().getValue() == null) {
            Integer eventId = getIntent().getIntExtra(EXTRA_EVENT_ID, 0);
            mModel.loadDetailEvent(eventId);
        }

        mModel.getEvent().observe(this, this::setDetailData);
        mModel.isLoading().observe(this, this::showLoading);
    }

    private void setDetailData(Event event) {
        if (event == null) {
            binding.mainContainer.setVisibility(View.GONE);
            binding.tvEmpty.setText(R.string.failed_to_fetch_detail);
            binding.tvEmpty.setVisibility(View.VISIBLE);
            return;
        }

        binding.tvEmpty.setVisibility(View.GONE);
        binding.mainContainer.setVisibility(View.VISIBLE);

        String html = event.getDescription();
        String cleanHtml = html.replaceAll("(?i)<img[^>]*>", "");
        CharSequence spanned = HtmlCompat.fromHtml(cleanHtml, HtmlCompat.FROM_HTML_MODE_LEGACY);
        Integer availableQuota = event.getQuota() - event.getRegistrants();

        Glide.with(binding.ivCover)
                .load(event.getMediaCover())
                .into(binding.ivCover);
        binding.tvEventName.setText(event.getName());
        binding.tvOwnerName.setText(String.format("Di selenggarakan oleh: %s", event.getOwnerName()));
        binding.tvDateTime.setText(formatWib(event.getBeginTime()));
        binding.tvAvailableQuota.setText(String.format("Sisa kuota: %s", availableQuota));
        binding.tvDescription.setText(spanned);
        binding.tvDescription.setMovementMethod(LinkMovementMethod.getInstance());

        binding.btnRegister.setOnClickListener(view -> {
            String registerLink = event.getLink();
            if (registerLink != null && !registerLink.isEmpty()) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(registerLink));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Tidak dapat membuka link", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Link tidak tersedia", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            binding.mainContainer.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
        } else {
            binding.mainContainer.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private String formatWib(String beginTime) {
        if (beginTime == null || beginTime.isEmpty()) return "-";
        try {
            DateTimeFormatter inFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
            LocalDateTime ldt = LocalDateTime.parse(beginTime, inFmt);
            DateTimeFormatter outFmt = DateTimeFormatter.ofPattern("d MMM yyyy, HH.mm 'WIB'", new Locale("id", "ID"));
            return ldt.atZone(ZoneId.of("Asia/Jakarta")).format(outFmt);
        } catch (Exception e) {
            return "-";
        }
    }

}