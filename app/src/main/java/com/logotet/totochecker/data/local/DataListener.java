package com.logotet.totochecker.data.local;

import java.util.List;

public interface DataListener {
    void onDataReceived(List<NumbersEntity> values);
}
