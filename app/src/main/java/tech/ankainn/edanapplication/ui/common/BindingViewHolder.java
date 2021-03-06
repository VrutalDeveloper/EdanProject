package tech.ankainn.edanapplication.ui.common;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import tech.ankainn.edanapplication.util.ViewBindingUtil;

public class BindingViewHolder<T extends ViewBinding> extends RecyclerView.ViewHolder {

    public final T binding;

    public BindingViewHolder(@NonNull T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
