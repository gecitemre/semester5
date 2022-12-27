import java.util.ArrayList;

public class PlaylistTree {
	
	public PlaylistNode primaryRoot;		//root of the primary B+ tree
	public PlaylistNode secondaryRoot;	//root of the secondary B+ tree
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

			}
			else {
				ArrayList<CengSong> songs = primaryRootAsLeaf.getSongs();
				// insert song into songs using bisect right

				int low = 0;
				int high = songs.size();
				int mid = 0;
				while (low < high) {
					mid = (low + high) / 2;
					if (song.audioId() > songs.get(mid).audioId()) {
						low = mid + 1;
					}
					else {
						high = mid;
					}
				}
				primaryRootAsLeaf.addSong(low, song);
			}
		}
		else {
			PlaylistNodePrimaryIndex primaryRootAsInternal = (PlaylistNodePrimaryIndex) primaryRoot;
			primaryRootAsInternal.addSong(song);
		}
	}
	
	public CengSong searchSong(Integer audioId) {
		if (primaryRoot instanceof PlaylistNodePrimaryLeaf) {
			PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
			return primaryRootAsLeaf.searchSong(audioId);
		}
		else {
			PlaylistNodePrimaryIndex primaryRootAsInternal = (PlaylistNodePrimaryIndex) primaryRoot;
			return primaryRootAsInternal.searchSong(audioId);
		}
	}
	
	
	public void printPrimaryPlaylist() {
		// TODO: Implement this method
		// print the primary B+ tree in Depth-first order
		/*All non-leaf and leaf nodes should be printed with a proper indentation (with tabs) for each
		level in the tree.
		For non-leaf nodes, you should print the search key enclosed by <index> and </index> tags.
		For leaf nodes, you should print the content between <data> and </data> tags. Records
		should be printed between with <record> and </record> tags. */

		if (primaryRoot instanceof PlaylistNodePrimaryLeaf) {
			PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
			primaryRootAsLeaf.print(0);
		}
		else {
			PlaylistNodePrimaryIndex primaryRootAsInternal = (PlaylistNodePrimaryIndex) primaryRoot;
			primaryRootAsInternal.print(0);
		}
	}
	
	public void printSecondaryPlaylist() {
		// TODO: Implement this method
		// print the secondary B+ tree in Depth-first order

		if (secondaryRoot instanceof PlaylistNodeSecondaryLeaf) {
			PlaylistNodeSecondaryLeaf secondaryRootAsLeaf = (PlaylistNodeSecondaryLeaf) secondaryRoot;
			secondaryRootAsLeaf.print(0);
		}
		else {
			PlaylistNodeSecondaryIndex secondaryRootAsInternal = (PlaylistNodeSecondaryIndex) secondaryRoot;
			secondaryRootAsInternal.print(0);
		}
		return;
	}
	
	// Extra functions if needed

}