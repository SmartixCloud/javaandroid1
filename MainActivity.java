package com.delta.fragments;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity implements HeadlinesFragment.OnHeadlineSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check if the activity is using the layout version
        //with the FrameLayout.  if so, we have to add the fragment
        //(it wont be done automatically )
        if(findViewById(R.id.container) != null){

            //However if were being restored from a previous state,
            //then dont do anything
            if(savedInstanceState != null){
                return;
            }

            //Crate an instance of the Headline Fragment
            HeadlinesFragment headlinesFragment = new HeadlinesFragment();

            //In the case this activity was started with special instructions from an Intent,
            //pass the Intent's extras to the fragment as arguments
            headlinesFragment.setArguments(getIntent().getExtras());

            //Ask the Fragment manager to add it to the FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.container,headlinesFragment)
                    .commit();


        }

    }

    @Override
    public void onArticleSelected(int position) {
        //Capture the article fragment from the activity's dual-pane layout
        ArticleFragment articleFragment = (ArticleFragment) getFragmentManager().findFragmentById(R.id.article_fragment);

        //if we dont find one, we must not be in two pane mode
        //lets swap the Fragments instead
        if(articleFragment != null){

            //we must be in two pane layout
            articleFragment.updateArticleView(position);

        }else{
            //we must be in one pane layout

            //Create Fragment and give it an arguement for the selected article right away
            ArticleFragment swapFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION,position);
            swapFragment.setArguments(args);

            //now that the Fragment is prepared, swap it

            getFragmentManager().beginTransaction()
                    .replace(R.id.container, swapFragment)
                    .addToBackStack(null)
                    .commit();


        }
    }
}
