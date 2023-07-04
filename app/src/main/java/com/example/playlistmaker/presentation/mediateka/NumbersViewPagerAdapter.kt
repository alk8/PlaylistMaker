import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmaker.domain.models.states.StateMediatekaFragment
import com.example.playlistmaker.presentation.fragments.EmptyMediatekaFragment

class NumbersViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> EmptyMediatekaFragment(StateMediatekaFragment.FAVORITE)
            1 -> EmptyMediatekaFragment(StateMediatekaFragment.PLAYLISTS)
            else -> EmptyMediatekaFragment(StateMediatekaFragment.DEFAULT)
        }
    }
}