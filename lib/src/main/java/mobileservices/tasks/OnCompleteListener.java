package mobileservices.tasks;

import androidx.annotation.NonNull;

public interface OnCompleteListener<TResult> {
    void onComplete(@NonNull Task<TResult> var1);
}