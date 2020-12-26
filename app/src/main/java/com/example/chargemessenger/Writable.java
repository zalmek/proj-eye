package com.example.chargemessenger;

import android.content.Context;
import android.widget.EditText;

public interface Writable {
    void write(Context context, String filename, EditText obj);
}
