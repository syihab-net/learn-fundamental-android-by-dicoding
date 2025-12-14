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
import com.ahmad.dicodingevent.data.local.entity.EventEntity;
import com.ahmad.dicodingevent.databinding.ActivityDetailBinding;
import com.ahmad.dicodingevent.helper.ViewModelFactory;
import com.bumptech.glide.Glide;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private DetailViewModel viewModel;
    public static final String EXTRA_EVENT_ID = "extra_event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewModelFactory factory = new ViewModelFactory(this, "");
        viewModel = new ViewModelProvider(this, factory).get(DetailViewModel.class);

        int eventId = getIntent().getIntExtra(EXTRA_EVENT_ID, 0);
        viewModel.getDetailEvent(eventId).observe(this, this::setDetailData);
    }

    private void setDetailData(EventEntity eventItem) {
        if (eventItem == null) {
            binding.mainContainer.setVisibility(View.GONE);
            binding.tvEmpty.setText(R.string.failed_to_fetch_detail);
            binding.tvEmpty.setVisibility(View.VISIBLE);
            return;
        }

        binding.tvEmpty.setVisibility(View.GONE);
        binding.mainContainer.setVisibility(View.VISIBLE);

        String html = eventItem.getDescription();
        String cleanHtml = html.replaceAll("(?i)<img[^>]*>", "");
        CharSequence spanned = HtmlCompat.fromHtml(cleanHtml, HtmlCompat.FROM_HTML_MODE_LEGACY);
        Integer availableQuota = eventItem.getQuota() - eventItem.getRegistrants();

        Glide.with(binding.ivCover)
                .load(eventItem.getMediaCover())
                .into(binding.ivCover);
        binding.tvEventName.setText(eventItem.getName());
        binding.tvOwnerName.setText(String.format("Di selenggarakan oleh: %s", eventItem.getOwnerName()));
        binding.tvDateTime.setText(formatWib(eventItem.getBeginTime()));
        binding.tvAvailableQuota.setText(String.format("Sisa kuota: %s", availableQuota));
        binding.tvDescription.setText(spanned);
        binding.tvDescription.setMovementMethod(LinkMovementMethod.getInstance());

        if (eventItem.isFavorite()) {
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_outline_favorite_24);
        }

        binding.btnRegister.setOnClickListener(view -> {
            String registerLink = eventItem.getLink();
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

        binding.fabFavorite.setOnClickListener(view -> {
            if (eventItem.isFavorite()) {
                binding.fabFavorite.setImageResource(R.drawable.ic_outline_favorite_24);
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
            }

            eventItem.setFavorite(!eventItem.isFavorite());
            viewModel.updateEvent(eventItem);
        });
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