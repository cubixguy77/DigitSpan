package com.mastersofmemory.flashnumbers;

import android.os.Bundle;

public interface SaveInstanceStateListener {

    void onRestoreInstanceState(Bundle inState);
    void onSaveInstanceState(Bundle outState);

}
