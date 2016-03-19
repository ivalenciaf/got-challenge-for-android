package es.npatarino.android.gotchallenge.view;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.adapter.CharacterAdapter;
import es.npatarino.android.gotchallenge.model.GoTCharacter;

/**
 * Custom search filter for the characters adapter.
 */
public class CharacterSearchFilter extends Filter {
    private CharacterAdapter mAdapter;
    private List<GoTCharacter> mCharacters;
    private List<GoTCharacter> mFiltered;

    public CharacterSearchFilter(CharacterAdapter adapter, List<GoTCharacter> characters) {
        this.mAdapter = adapter;
        this.mCharacters = characters;
        mFiltered = new ArrayList<>();
    }

    public void setCharacters(List<GoTCharacter> characters) {
        this.mCharacters = characters;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        mFiltered.clear();
        final FilterResults results = new FilterResults();

        if (constraint.length() == 0) {
            mFiltered.addAll(mCharacters);
        } else {
            final String filterPattern = constraint.toString().toLowerCase().trim();

            String name;
            for (final GoTCharacter character : mCharacters) {
                name = character.getName().toLowerCase();
                if (name.contains(filterPattern)) {
                    mFiltered.add(character);
                }
            }
        }
        results.values = mFiltered;
        results.count = mFiltered.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        mAdapter.clear();
        mAdapter.addAll((List<GoTCharacter>) results.values);
        mAdapter.notifyDataSetChanged();
    }
}
