package tech.ankainn.edanapplication.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment implements ViewFinder {

    /*protected boolean isClickable() {
        return false;
    }*/

    @NonNull
    protected abstract View makeView(LayoutInflater inflater, ViewGroup container);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*view.setClickable(isClickable());
        view.setFocusableInTouchMode(isClickable());*/
        return makeView(inflater, container);
    }

    protected void hideSoftKeyboard(@NonNull Activity activity, @NonNull View view) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Nullable
    @Override
    public View rootView() {
        return getView();
    }
}
