import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmaker.presentation.fragments.FavoriteFragment
import com.example.playlistmaker.presentation.states.StateMediatekaFragment
import com.example.playlistmaker.presentation.fragments.PlaylistsFragment

class NumbersViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FavoriteFragment.newInstance()
            1 -> PlaylistsFragment.newInstance(StateMediatekaFragment.PLAYLISTS)
            else -> PlaylistsFragment.newInstance(StateMediatekaFragment.DEFAULT)
        }
    }
}