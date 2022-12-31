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
				primaryRootAsInternal.addSong(song);
				PlaylistNodeSecondaryIndex secondaryRootAsInternal = (PlaylistNodeSecondaryIndex) secondaryRoot;
				secondaryRootAsInternal.addSong(song);
				break;
			case Leaf:
				PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
				// convert to binary search later
				for (int i = 0; i < primaryRootAsLeaf.songCount(); i++) {
					if (primaryRootAsLeaf.songAtIndex(i).audioId() > song.audioId()) {
						primaryRootAsLeaf.addSong(i, song);
						break;
					}
				}
				if (primaryRootAsLeaf.songAtIndex(primaryRootAsLeaf.songCount() - 1).audioId() < song.audioId()) { // change with i later ?
					primaryRootAsLeaf.addSong(primaryRootAsLeaf.songCount(), song);
				}
				PlaylistNodeSecondaryLeaf secondaryRootAsLeaf = (PlaylistNodeSecondaryLeaf) secondaryRoot;
				for (int g = 0; g < secondaryRootAsLeaf.genreCount(); g++) {
					if (secondaryRootAsLeaf.genreAtIndex(g).equals(song.genre())) {
						secondaryRootAsLeaf.addSong(g, song);
						return;
					}
				}
				secondaryRootAsLeaf.addSong(secondaryRootAsLeaf.genreCount(), song);
		}
	}

	public CengSong searchSong(Integer audioId) {
		switch (primaryRoot.type) {
			case Internal:
				PlaylistNodePrimaryIndex primaryRootAsInternal = (PlaylistNodePrimaryIndex) primaryRoot;
				return primaryRootAsInternal.searchSong(audioId);
			case Leaf:
				PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
				for (int i = 0; i < primaryRootAsLeaf.getSongs().size(); i++) {
					if (primaryRootAsLeaf.songAtIndex(i).audioId() == audioId) {
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
					System.out.println("\t<record>" + song.audioId() + "</record>");
				}
				System.out.println("</data>");
				break;
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
				for (ArrayList<CengSong> songBucket : secondaryRootAsLeaf.getSongBucket()) {
					System.out.println(songBucket.get(0).genre());
					for (CengSong song : songBucket) {
						System.out.print("\t<record>" + song.fullName() + "</record>");
					}
				}
				System.out.println("</data>");
				break;
		}
	}

	// Extra functions if needed

}