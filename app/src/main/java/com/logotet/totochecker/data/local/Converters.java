package com.logotet.totochecker.data.local;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Converters {

    @TypeConverter
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String fromList(List<String> numbers) {
        return String.join(" ", numbers);
    }

    @TypeConverter
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> fromStringToCategory(String value) {
        return Arrays.stream(value.split(" ")).collect(Collectors.toList());
    }
}
