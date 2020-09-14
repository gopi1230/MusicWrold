package com.wipro.musicworld.fragments;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wipro.musicworld.MainActivity;
import com.wipro.musicworld.R;
import com.wipro.musicworld.adapters.ArtistListRecyclerAdapter;
import com.wipro.musicworld.databinding.ArtistListFragmentBinding;
import com.wipro.musicworld.dialogs.CustomProgressDialogue;
import com.wipro.musicworld.entities.Artist;
import com.wipro.musicworld.entities.DataEntity;
import com.wipro.musicworld.entities.Results;
import com.wipro.musicworld.interfaces.OnItemClickListener;
import com.wipro.musicworld.utils.NetworkUtil;
import com.wipro.musicworld.viewmodels.ArtistListViewModel;
import com.wipro.musicworld.viewmodels.ArtistListViewModelFactory;

import java.util.ArrayList;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArtistListFragment extends Fragment  implements  SearchView.OnQueryTextListener , OnItemClickListener {

    ArtistListViewModel mViewModel;
    private ArrayList<Artist> artistArrayList=new ArrayList<>();
    private ArtistListFragmentBinding artistListFragmentBinding;
    private ArtistListRecyclerAdapter artistListRecyclerAdapter;
    private Dialog loadingView=null;

    @Inject
    ArtistListViewModelFactory factory;
    SearchView mSearchView;
     @Inject
    public  ArtistListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(getView()==null){
            mViewModel =  new ViewModelProvider(getActivity(),factory).get(ArtistListViewModel.class);
            artistListFragmentBinding = ArtistListFragmentBinding.inflate(inflater,container,false);
            setupRecyclerView();
        }
        return  artistListFragmentBinding.getRoot();
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem mSearchmenuItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) mSearchmenuItem.getActionView();
        mSearchView.setQueryHint(getString(R.string.search_title));
        mSearchView.setOnQueryTextListener(this  );
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void startSearchWithQuery(String queryString){
        mViewModel.getSearchArtistResults("artist.search",queryString,"cbaebad167e697788489d2b99992da60","json");
        mViewModel.getMutableLiveData().observe(this, new Observer<DataEntity>() {
            @Override
            public void onChanged(DataEntity results) {
                dismissProgressDialog();
                if(results!=null&&results.getResults()!=null&&results.getResults().getArtistmatches()!=null){
                    artistListFragmentBinding.messageView.setVisibility(View.GONE);
                    artistListFragmentBinding.artistListView.setVisibility(View.VISIBLE);
                    if(artistArrayList!=null&&artistArrayList.size()>0){
                        artistArrayList.clear();
                    }
                    artistArrayList.addAll(results.getResults().getArtistmatches().getArtist());
                    artistListRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void showProgressDialog(){
         loadingView= CustomProgressDialogue.getProgressDialogue(getActivity(),"");
         if(loadingView!=null) {
             loadingView.show();
         }
    }

    private void dismissProgressDialog(){
        if(loadingView!=null){
            loadingView.dismiss();
        }
    }


    /**
     * To initialize and setup adapter to recyclerview
     */
    private void setupRecyclerView(){
      if(artistListRecyclerAdapter==null){
          artistListRecyclerAdapter = new ArtistListRecyclerAdapter(getActivity(),artistArrayList,this);
          artistListFragmentBinding.artistListView.setLayoutManager(new LinearLayoutManager(getContext()));
          artistListFragmentBinding.artistListView.setAdapter(artistListRecyclerAdapter);
          artistListFragmentBinding.artistListView.setItemAnimator(new DefaultItemAnimator());
      }else {
          artistListRecyclerAdapter.notifyDataSetChanged();
      }

    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchView.clearFocus();
        if(!TextUtils.isEmpty(query)){
            if(NetworkUtil.isConnectingToInternet(getActivity())){
                showProgressDialog();
                startSearchWithQuery(query);
            }else {
                Toast.makeText(getActivity(),getString(R.string.network_problem),Toast.LENGTH_SHORT).show();
            }
        }else {
             Toast.makeText(getActivity(),getString(R.string.query_string_empty),Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void OnItemClick(int position) {
        ((MainActivity)getActivity()).showArtistDetailsFragment(artistArrayList.get(position));
    }
}