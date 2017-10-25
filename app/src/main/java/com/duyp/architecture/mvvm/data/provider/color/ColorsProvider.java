package com.duyp.architecture.mvvm.data.provider.color;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.data.model.LanguageColorModel;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.helper.RxHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Kosh on 27 May 2017, 9:50 PM
 */

public class ColorsProvider {

    private static List<String> POPULAR_LANG = Stream.of("Java", "Kotlin", "JavaScript", "Python", "CSS", "PHP",
            "Ruby", "C++", "C", "GO", "Swift").toList();//predefined languages.

    private static Map<String, LanguageColorModel> colors = new LinkedHashMap<>();

    public static void load() {
        if (colors.isEmpty()) {
            RxHelper.safeObservable(Observable
                    .create(observableEmitter -> {
                        try {
                            Type type = new TypeToken<Map<String, LanguageColorModel>>() {}.getType();
                            try (InputStream stream = App.getInstance().getAssets().open("colors.json")) {
                                Gson gson = new Gson();
                                try (JsonReader reader = new JsonReader(new InputStreamReader(stream))) {
                                    colors.putAll(gson.fromJson(reader, type));
                                    observableEmitter.onNext("");
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            observableEmitter.onError(e);
                        }
                        observableEmitter.onComplete();
                    }))
                    .subscribe(s -> {/**/}, Throwable::printStackTrace);
        }
    }

    @NonNull public static ArrayList<String> languages() {
        ArrayList<String> lang = new ArrayList<>();
        lang.addAll(Stream.of(colors)
                .filter(value -> value != null && !InputHelper.isEmpty(value.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new)));
        lang.add(0, "All Languages");
        lang.addAll(1, POPULAR_LANG);
        return lang;
    }

    @Nullable public static LanguageColorModel getColor(@NonNull String lang) {
        return colors.get(lang);
    }

    @ColorInt public static int getColorAsColor(@NonNull String lang, @NonNull Context context) {
        LanguageColorModel color = getColor(lang);
        int langColor = ColorGenerator.getColor(context, lang);
        if (color != null && !InputHelper.isEmpty(color.getColor())) {
            try {langColor = Color.parseColor(color.getColor());} catch (Exception ignored) {}
        }
        return langColor;
    }
}
