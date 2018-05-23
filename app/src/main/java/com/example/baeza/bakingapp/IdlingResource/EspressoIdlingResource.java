package com.example.baeza.bakingapp.IdlingResource;

import android.support.test.espresso.IdlingResource;

public class EspressoIdlingResource {

    private static final String RESOURCE = "RESOURCE";

    private static SimpleCountingIdlingResource mCountingIdlingResource =
            new SimpleCountingIdlingResource(RESOURCE);

    public static void increment(){
        mCountingIdlingResource.increment();
    }

    public static void decrement(){
        mCountingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource(){
        return  mCountingIdlingResource;
    }
}
