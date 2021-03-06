package tech.ankainn.edanapplication.binding;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

import tech.ankainn.edanapplication.R;

public class BindingAdapters {

    @BindingAdapter(value = "visibleGone")
    public static void showHide(View view, boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        if(view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    @BindingAdapter(value = "textDropDown")
    public static void setTextDropDown(AutoCompleteTextView view, String value) {

        String oldValue = view.getText().toString();

        if (TextUtils.isEmpty(oldValue) && TextUtils.isEmpty(value)) return;
        if (Objects.equals(oldValue, value)) return;

        view.setText(null);
        view.postDelayed(() -> view.setText(value, false), 100L);
    }

    @InverseBindingAdapter(attribute = "textDropDown", event = "android:textAttrChanged")
    public static String getTextDropDown(AutoCompleteTextView view) {
        return view.getText().toString();
    }

    @BindingAdapter(value = {"itemPosition", "onItemClickListener", "itemPositionAttrChanged"}, requireAll = false)
    public static void setItemPosition(AutoCompleteTextView view, Integer pos, final OnItemClickListener listener, final InverseBindingListener inverseListener) {

        if (pos != null) {
            view.setTag(R.id.autocompletetextview_pos, pos);
        }

        view.setOnItemClickListener((parent, view1, position, id) -> {

            view.setTag(R.id.autocompletetextview_pos, position);

            if (listener != null) {
                listener.onItemClick(parent, view1, position, id);
            }
            if (inverseListener != null) {
                inverseListener.onChange();
            }
        });
    }

    @InverseBindingAdapter(attribute = "itemPosition")
    public static int getItemPosition(AutoCompleteTextView view) {
        Object temp = view.getTag(R.id.autocompletetextview_pos);
        return temp != null ? (int) temp : -1;
    }

    @BindingAdapter(value = "dropdown")
    public static void setDropdown(AutoCompleteTextView textView, String[] array) {
        if (array == null) return;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        textView.getContext(),
                        R.layout.layout_dropdown_menu_item,
                        array);
        textView.setAdapter(adapter);
    }

    @BindingAdapter(value = "listDropdown")
    public static void setDropdown2(AutoCompleteTextView textView, List<?> array) {
        if (array == null) return;

        ArrayAdapter<?> adapter = new ArrayAdapter<>(
                textView.getContext(),
                R.layout.layout_dropdown_menu_item,
                array);
        textView.setAdapter(adapter);
    }

    @BindingAdapter(value = "imageUri")
    public static void setImageUri(ImageView imageView, String uri) {
        Context context = imageView.getContext().getApplicationContext();
        Glide.with(context).load(uri).into(imageView);
    }
}
