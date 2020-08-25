package tech.ankainn.edanapplication;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import tech.ankainn.edanapplication.databinding.ActivityFragmentContainerBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityFragmentContainerBinding binding =
                ActivityFragmentContainerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fragmentContainer.postDelayed(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enableNavigationBarLightMode();
            }
        }, 200L);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void enableNavigationBarLightMode() {
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorDefaultBackground));
        boolean lightMode = getResources().getBoolean(R.bool.light_mode);
        if (lightMode) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Cache cache = Cache.getInstance();
            FormOneData formOneData = cache.getFormOneData().getValue();
            Timber.tag(Tagger.DUMPER)
                    .d("onKeyDown: %s", formOneData == null ? "no form one data in cache" : new Gson().toJson(formOneData));
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }*/
}
