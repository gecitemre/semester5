import java.util.ArrayList;
import java.util.Arrays;

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
				primaryRootAsLeaf.addSong(1 - (Arrays.binarySearch(primaryRootAsLeaf.getSongs().toArray(), song)),
						song);
				PlaylistNodeSecondaryLeaf secondaryRootAsLeaf = (PlaylistNodeSecondaryLeaf) secondaryRoot;
				secondaryRootAsLeaf
						.addSong(1 - (Arrays.binarySearch(secondaryRootAsLeaf.getSongBucket().toArray(), song)), song);
				break;
		}
	}

	public CengSong searchSong(Integer audioId) {
		switch (primaryRoot.type) {
			case Internal:
				PlaylistNodePrimaryIndex primaryRootAsInternal = (PlaylistNodePrimaryIndex) primaryRoot;
				return primaryRootAsInternal.searchSong(audioId);
			case Leaf:
				PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
				int index = Arrays.binarySearch(primaryRootAsLeaf.getSongs().toArray(),
						new CengSong(audioId, null, null, null));
				if (index >= 0) {
					return primaryRootAsLeaf.getSongs().get(index);
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