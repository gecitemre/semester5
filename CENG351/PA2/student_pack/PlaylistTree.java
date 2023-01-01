import java.util.ArrayList;
import java.util.stream.Collectors;

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
				break;
			case Leaf:
				PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
				// TODO: challenge: convert to binary search
				if (primaryRootAsLeaf.songCount() == 0) {
					primaryRootAsLeaf.addSong(0, song);
					break;
				}
				for (int i = 0; i < primaryRootAsLeaf.songCount(); i++) {
					if (primaryRootAsLeaf.songAtIndex(i).audioId() > song.audioId()) {
						primaryRootAsLeaf.addSong(i, song);
						break;
					}
				}
				if (primaryRootAsLeaf.songAtIndex(primaryRootAsLeaf.songCount() - 1).audioId() < song.audioId()) {
					primaryRootAsLeaf.addSong(primaryRootAsLeaf.songCount(), song);
				}
				if (primaryRootAsLeaf.songCount() > 2 * PlaylistNode.order) {
					// split
					ArrayList<CengSong> songs = new ArrayList<CengSong>() {
						{
							for (int i = PlaylistNode.order; i < primaryRootAsLeaf.songCount(); i++) {
								add(primaryRootAsLeaf.songAtIndex(i));
							}
						}
					};
					for (int i = 2 * PlaylistNode.order; i >= PlaylistNode.order; i--) {
						primaryRootAsLeaf.getSongs().remove(i);
					}
					PlaylistNodePrimaryLeaf newLeaf = new PlaylistNodePrimaryLeaf(primaryRootAsLeaf.getParent(), songs);
					ArrayList<PlaylistNode> children = new ArrayList<PlaylistNode>() {
						{
							add(primaryRootAsLeaf);
							add(newLeaf);
						}
					};
					ArrayList<Integer> audioIds = new ArrayList<Integer>() {
						{
							add(newLeaf.songAtIndex(0).audioId());
						}
					};
					primaryRoot = new PlaylistNodePrimaryIndex(null, audioIds, children);
				}
		}
		switch (secondaryRoot.type) {
			case Internal:
				PlaylistNodeSecondaryIndex secondaryRootAsInternal = (PlaylistNodeSecondaryIndex) secondaryRoot;
				secondaryRootAsInternal.addSong(song);
				break;
			case Leaf:
				PlaylistNodeSecondaryLeaf secondaryRootAsLeaf = (PlaylistNodeSecondaryLeaf) secondaryRoot;
				if (secondaryRootAsLeaf.genreCount() == 0) {
					secondaryRootAsLeaf.addSong(0, song);
					break;
				}
				for (int g = 0; g < secondaryRootAsLeaf.genreCount(); g++) {
					if (secondaryRootAsLeaf.genreAtIndex(g).equals(song.genre())) {
						secondaryRootAsLeaf.addSong(g, song);
						break;
					}
				}
				if (secondaryRootAsLeaf.genreAtIndex(secondaryRootAsLeaf.genreCount() - 1)
						.compareTo(song.genre()) < 0) {
					secondaryRootAsLeaf.addSong(secondaryRootAsLeaf.genreCount(), song);
				}
				if (secondaryRootAsLeaf.genreCount() > 2 * PlaylistNode.order) {
					// split
					secondaryRoot = new PlaylistNodeSecondaryIndex(null);
					PlaylistNodeSecondaryLeaf newLeaf = new PlaylistNodeSecondaryLeaf(secondaryRoot);
					for (int i = PlaylistNode.order; i < secondaryRootAsLeaf.genreCount(); i++) {
						newLeaf.getSongBucket().add(i - PlaylistNode.order, secondaryRootAsLeaf.getSongBucket().get(i));
					}
					for (int i = 2 * PlaylistNode.order; i >= PlaylistNode.order; i--) {
						secondaryRootAsLeaf.getSongBucket().remove(i);
					}
				}
		}
	}

	public CengSong searchSong(Integer audioId) {
		switch (primaryRoot.type) {
			case Internal:
				PlaylistNodePrimaryIndex primaryRootAsInternal = (PlaylistNodePrimaryIndex) primaryRoot;
				return primaryRootAsInternal.searchSong(audioId);
			case Leaf:
				PlaylistNodePrimaryLeaf primaryRootAsLeaf = (PlaylistNodePrimaryLeaf) primaryRoot;
				for (int i = 0; i < primaryRootAsLeaf.songCount(); i++) {
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
						System.out.println("\t<record>" + song.fullName() + "</record>");
					}
				}
				System.out.println("</data>");
				break;
		}
	}

	// Extra functions if needed

}