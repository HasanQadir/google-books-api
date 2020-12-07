package com.example.googlebooksapi.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.googlebooksapi.R;
import com.example.googlebooksapi.interfaces.APIResponseListener;
import com.example.googlebooksapi.interfaces.FetchDataOnSuccessListener;
import com.example.googlebooksapi.models.Book;
import com.example.googlebooksapi.models.JsonFeed;
import com.example.googlebooksapi.server.IWebService;
import com.example.googlebooksapi.server.ServiceGenerator;
import com.example.googlebooksapi.utils.Constants;
import com.example.googlebooksapi.utils.SharedPreferencesHelper;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;


public class FetchJsonFragment extends Fragment implements APIResponseListener {

    Button fetchData;
    ArrayList<Book> books;
    private APIResponseListener apiResponseListener;
    private FetchDataOnSuccessListener fetchDataOnSuccessListener;

    public FetchJsonFragment() {
        // Required empty public constructor
    }

    public static FetchJsonFragment newInstance() {
        FetchJsonFragment fragment = new FetchJsonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        books = new ArrayList<>();
        apiResponseListener = this;
    }

    public void setFetchDataOnSuccessListener(FetchDataOnSuccessListener listener){
        this.fetchDataOnSuccessListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fetch_json, container, false);
        fetchData = (Button) view.findViewById(R.id.fetch_books_data);

        // Attach & handle onClickListeners events
        fetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableFetchDataButton();
                getBookVolume(getString(R.string.harry_potter));
            }
        });

        return view;
    }

    // Get BookVolumes and show it in ListView
    private void getBookVolume(String bookTitle){
        IWebService service = ServiceGenerator.getService(IWebService.class);
        final Call<JsonFeed> bookVolume = service.getBookVolume(bookTitle, Constants.MAX_RESULTS);
        bookVolume.enqueue(new Callback<JsonFeed>() {
            @Override
            public void onResponse(Call<JsonFeed> call, Response<JsonFeed> response) {
                if (response.isSuccessful()) {
                    JsonFeed jsonData = response.body();
                    apiResponseListener.onAPISuccessfulResponse(jsonData);
                }
            }

            @Override
            public void onFailure(Call<JsonFeed> call, Throwable t) {
                apiResponseListener.onAPIFailureResponse(t.getMessage());
            }
        });
    }

    private void makeButtonInvisible(){
        fetchData.setVisibility(View.GONE);
    }

    private void disableFetchDataButton(){
        fetchData.setClickable(false);
        fetchData.setBackgroundColor(getResources().getColor(R.color.disabled_bg_color));
    }

    private void safeBookListInSharedPreferences(){
        SharedPreferencesHelper.putBookListData("bookList", books);
    }

    private void addItemsInBookList(JsonFeed jsonFeed){
        for (LinkedTreeMap map: jsonFeed.getItems()){
            books.add(new Book(map));
        }
    }

    @Override
    public void onAPISuccessfulResponse(JsonFeed jsonFeed) {
        makeButtonInvisible();
        addItemsInBookList(jsonFeed);
        safeBookListInSharedPreferences();
        fetchDataOnSuccessListener.onSuccessfulResponse();
    }

    @Override
    public void onAPIFailureResponse(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}