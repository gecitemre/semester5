import java.util.ArrayList;
import java.util.Arrays;

class CengSongTools {
	public static int bisectRight (ArrayList<CengSong> songs, Integer audioId) {
		int low = 0;
		int high = songs.size();
		int mid = 0;
		while (low < high) {
			mid = (low + high) / 2;
			if (audioId > songs.get(mid).audioId()) {
				low = mid + 1;
			} else {
				high = mid;
			}
		}
		return low;
	}
}

public class PlaylistTree {

	public PlaylistNode primaryRoot; // root of the primary B+ tree
	public PlaylistNode secondaryRoot; // root of the secondary B+ tree

	public PlaylistTree(Integer order) {
		PlaylistNode.order = order;
		primaryRoot = new PlaylistNodePrimaryLeaf(null);
		primaryRoot.level = 0;
		secondaryRoot = new PlaylistNodeSecondaryLeaf(null);
		secondaryRoot.level = 0;
	}

	public void addSong(CengSong song) {
		if (primaryRoot instanceof PlaylistNodePrimaryLeaf) {
			PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
			if (primaryRootAsLeaf.songCount() == 2 * PlaylistNode.order) {
				// split

			} else {
				ArrayList<CengSong> songs = primaryRootAsLeaf.getSongs();
				primaryRootAsLeaf.addSong(-(Arrays.binarySearch(songs.toArray(), song)+1), song);
			}
		} else {
			PlaylistNodePrimaryIndex primaryRootAsInternal = (PlaylistNodePrimaryIndex) primaryRoot;
			
		}

		if (secondaryRoot instanceof PlaylistNodeSecondaryLeaf) {
			PlaylistNodeSecondaryLeaf secondaryRootAsLeaf = (PlaylistNodeSecondaryLeaf) secondaryRoot;
			if (secondaryRootAsLeaf.genreCount() == 2 * PlaylistNode.order) {
				// split

			} else {
				ArrayList<ArrayList<CengSong>> songBucket = secondaryRootAsLeaf.getSongBucket();
				// insert song into songs using bisect right

				int low = 0;
				int high = songBucket.size();
				int mid = 0;
				while (low < high) {
					mid = (low + high) / 2;
					if (song.genre().compareTo(songBucket.get(mid).get(0).genre()) > 0) {
						low = mid + 1;
					} else {
						high = mid;
					}
				}
			}
		} else {
			PlaylistNodeSecondaryIndex secondaryRootAsInternal = (PlaylistNodeSecondaryIndex) secondaryRoot;
			
		}

	}

	public CengSong searchSong(Integer audioId) {
		if (primaryRoot instanceof PlaylistNodePrimaryLeaf) {
			PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
			// TODO
			return null;
		} else {
			PlaylistNodePrimaryIndex primaryRootAsInternal = (PlaylistNodePrimaryIndex) primaryRoot;
			return primaryRootAsInternal.searchSong(audioId);
		}
	}

	public void printPrimaryPlaylist() {
		// TODO: Implement this method
		// print the primary B+ tree in Depth-first order
		/*
		 * All non-leaf and leaf nodes should be printed with a proper indentation (with
		 * tabs) for each
		 * level in the tree.
		 * For non-leaf nodes, you should print the search key enclosed by <index> and
		 * </index> tags.
		 * For leaf nodes, you should print the content between <data> and </data> tags.
		 * Records
		 * should be printed between with <record> and </record> tags.
		 */

		if (primaryRoot instanceof PlaylistNodePrimaryLeaf) {
			PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
			// TODO
		} else {
			PlaylistNodePrimaryIndex primaryRootAsInternal = (PlaylistNodePrimaryIndex) primaryRoot;
			primaryRootAsInternal.print(0);
		}
	}

	public void printSecondaryPlaylist() {
		// TODO: Implement this method
		// print the secondary B+ tree in Depth-first order

		if (secondaryRoot instanceof PlaylistNodeSecondaryLeaf) {
			PlaylistNodeSecondaryLeaf secondaryRootAsLeaf = (PlaylistNodeSecondaryLeaf) secondaryRoot;
			// TODO
		} else {
			PlaylistNodeSecondaryIndex secondaryRootAsInternal = (PlaylistNodeSecondaryIndex) secondaryRoot;
			secondaryRootAsInternal.print(0);
		}
		return;
	}

	// Extra functions if needed

}