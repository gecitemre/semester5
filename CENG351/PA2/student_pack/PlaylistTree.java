import java.util.ArrayList;

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
		switch (primaryRoot.type) {
			case Internal:
				PlaylistNodePrimaryIndex primaryRootAsInternal = (PlaylistNodePrimaryIndex) primaryRoot;
				AudioIdPlaylistNodePrimaryPair pair = primaryRootAsInternal.addSong(song);
				if (pair != null) {

					// split the node

					// children
					ArrayList<PlaylistNode> children = new ArrayList<PlaylistNode>() {
						{
							add(primaryRootAsInternal);
							add(pair.node);
						}
					};

					// audioIds
					ArrayList<Integer> audioIds = new ArrayList<Integer>() {
						{
							add(pair.audioId);
						}
					};

					primaryRoot = new PlaylistNodePrimaryIndex(null, audioIds, children);
				}
				break;
			case Leaf:
				PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
				// TODO: challenge: convert to binary search
				if (primaryRootAsLeaf.songCount() == 0) {
					primaryRootAsLeaf.addSong(0, song);
					break;
				}
				int i;
				for (i = 0; i < primaryRootAsLeaf.songCount(); i++) {
					if (primaryRootAsLeaf.audioIdAtIndex(i) > song.audioId()) {
						break;
					}
				}
				primaryRootAsLeaf.addSong(i, song);
				if (primaryRootAsLeaf.songCount() > 2 * PlaylistNode.order) {
					// split
					ArrayList<CengSong> songs = new ArrayList<CengSong>() {
						{
							for (int i = PlaylistNode.order; i < primaryRootAsLeaf.songCount(); i++) {
								add(primaryRootAsLeaf.songAtIndex(i));
							}
						}
					};
					for (int j = 2 * PlaylistNode.order; j >= PlaylistNode.order; j--) {
						primaryRootAsLeaf.getSongs().remove(j);
					}
					PlaylistNodePrimaryLeaf newLeaf = new PlaylistNodePrimaryLeaf(primaryRootAsLeaf.getParent(), songs);
					ArrayList<PlaylistNode> children2 = new ArrayList<PlaylistNode>() {
						{
							add(primaryRootAsLeaf);
							add(newLeaf);
						}
					};
					ArrayList<Integer> audioIds2 = new ArrayList<Integer>() {
						{
							add(newLeaf.audioIdAtIndex(0));
						}
					};
					primaryRoot = new PlaylistNodePrimaryIndex(null, audioIds2, children2);
				}
		}
		switch (secondaryRoot.type) {
			case Internal:
				PlaylistNodeSecondaryIndex secondaryRootAsInternal = (PlaylistNodeSecondaryIndex) secondaryRoot;
				AudioIdPlaylistNodeSecondaryPair pair = secondaryRootAsInternal.addSong(song);
				if (pair != null) {
					ArrayList<PlaylistNode> children = new ArrayList<PlaylistNode>() {
						{
							add(secondaryRootAsInternal);
							add(pair.node);
						}
					};

					// genres
					ArrayList<String> genres = new ArrayList<String>() {
						{
							add(pair.genre);
						}
					};

					secondaryRoot = new PlaylistNodeSecondaryIndex(null, genres, children);
				}
				break;
			case Leaf:
				PlaylistNodeSecondaryLeaf secondaryRootAsLeaf = (PlaylistNodeSecondaryLeaf) secondaryRoot;
				
				if (secondaryRootAsLeaf.genreCount() == 0) {
					secondaryRootAsLeaf.addSong(0, song);
					break;
				}
				int i;
				for (i = 0; i < secondaryRootAsLeaf.genreCount(); i++) {
					int compareResult = secondaryRootAsLeaf.genreAtIndex(i).compareTo(song.genre());
					if (compareResult >= 0) {
						break;
					}
				}
				secondaryRootAsLeaf.addSong(i, song);
				if (secondaryRootAsLeaf.genreCount() > 2 * PlaylistNode.order) {
					// split
					ArrayList<ArrayList<CengSong>> songBucket = new ArrayList<ArrayList<CengSong>>() {
						{
							for (int i = PlaylistNode.order; i < secondaryRootAsLeaf.genreCount(); i++) {
								add(secondaryRootAsLeaf.songsAtIndex(i));
							}
						}
					};
					for (int j = 2 * PlaylistNode.order; j >= PlaylistNode.order; j--) {
						secondaryRootAsLeaf.getSongBucket().remove(j);
					}
					PlaylistNodeSecondaryLeaf newLeaf = new PlaylistNodeSecondaryLeaf(secondaryRootAsLeaf.getParent(), songBucket);
					ArrayList<PlaylistNode> children2 = new ArrayList<PlaylistNode>() {
						{
							add(secondaryRootAsLeaf);
							add(newLeaf);
						}
					};
					ArrayList<String> genres2 = new ArrayList<String>() {
						{
							add(newLeaf.genreAtIndex(0));
						}
					};
					secondaryRoot = new PlaylistNodeSecondaryIndex(null, genres2, children2);
				}

		}
	}

	public CengSong searchSong(Integer audioId) {
		switch (primaryRoot.type) {
			case Internal:
				PlaylistNodePrimaryIndex primaryRootAsInternal = (PlaylistNodePrimaryIndex) primaryRoot;
				return primaryRootAsInternal.searchSong(audioId, 0);
			case Leaf:
				PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
				for (int i = 0; i < primaryRootAsLeaf.songCount(); i++) {
					System.out.println("<data>");
					System.out.println("<record>" + primaryRootAsLeaf.songAtIndex(i).fullName() + "</record>");
					if (primaryRootAsLeaf.audioIdAtIndex(i) == audioId) {
						System.out.println("</data>");
						return primaryRootAsLeaf.songAtIndex(i); 
					}
				}
			default:
				System.out.println("Could not find " + audioId);
				return null;
		}
	}

	public void printPrimaryPlaylist() {
		switch (primaryRoot.type) {
			case Internal:
				PlaylistNodePrimaryIndex primaryRootAsInternal = (PlaylistNodePrimaryIndex) primaryRoot;
				primaryRootAsInternal.print(0);
				break;
			case Leaf:
				PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
				System.out.println("<data>");
				for (CengSong song : primaryRootAsLeaf.getSongs()) {
					System.out.println("<record>" + song.fullName() + "</record>");
				}
				System.out.println("</data>");
		}
	}

	public void printSecondaryPlaylist() {
		switch (secondaryRoot.type) {
			case Internal:
				PlaylistNodeSecondaryIndex secondaryRootAsInternal = (PlaylistNodeSecondaryIndex) secondaryRoot;
				secondaryRootAsInternal.print(0);
				break;
			case Leaf:
				PlaylistNodeSecondaryLeaf secondaryRootAsLeaf = (PlaylistNodeSecondaryLeaf) secondaryRoot;
				System.out.println("<data>");
				for (int i = 0; i < secondaryRootAsLeaf.genreCount(); i++) {
					ArrayList<CengSong> songs = secondaryRootAsLeaf.songsAtIndex(i);
					System.out.println(secondaryRootAsLeaf.genreAtIndex(i));
					for (CengSong song : songs) {
						System.out.println("\t<record>" + song.fullName() + "</record>");
					}
				}
				System.out.println("</data>");
		}
	}

	// Extra functions if needed

}