import java.util.ArrayList;

class AudioIdPlaylistNodeSecondaryPair {
	public String genre;
	public PlaylistNode node;
	AudioIdPlaylistNodeSecondaryPair(String genre, PlaylistNode node) {
		this.genre = genre;
		this.node = node;
	}
}

public class PlaylistNodeSecondaryIndex extends PlaylistNode {
	private ArrayList<String> genres;
	private ArrayList<PlaylistNode> children;

	public PlaylistNodeSecondaryIndex(PlaylistNode parent) {
		super(parent);
		genres = new ArrayList<String>();
		children = new ArrayList<PlaylistNode>();
		this.type = PlaylistNodeType.Internal;
	}

	public PlaylistNodeSecondaryIndex(PlaylistNode parent, ArrayList<String> genres, ArrayList<PlaylistNode> children) {
		super(parent);
		this.genres = genres;
		this.children = children;
		this.type = PlaylistNodeType.Internal;
	}

	// GUI Methods - Do not modify
	public ArrayList<PlaylistNode> getAllChildren() {
		return this.children;
	}

	public PlaylistNode getChildrenAt(Integer index) {

		return this.children.get(index);
	}

	public Integer genreCount() {
		return this.genres.size();
	}

	public String genreAtIndex(Integer index) {
		if (index >= this.genreCount() || index < 0) {
			return "Not Valid Index!!!";
		} else {
			return this.genres.get(index);
		}
	}

	public AudioIdPlaylistNodeSecondaryPair addSong(CengSong song) {
		if (genreCount() == 0) {
			genres.add(song.genre());
			PlaylistNodeSecondaryLeaf leaf = new PlaylistNodeSecondaryLeaf(this);
			children.add(leaf);
			leaf.addSong(0, song);
			return null;
		}
		int nodeIndex;
		for (nodeIndex = 0; nodeIndex < genreCount(); nodeIndex++) {
			int compareResult = song.genre().compareTo(genreAtIndex(nodeIndex));
			if (compareResult <= 0) {
				if (compareResult == 0) {
					nodeIndex++;
				}
				break;
			}
		}
		PlaylistNode node = children.get(nodeIndex);
		switch (node.type) {
			case Internal:
				AudioIdPlaylistNodeSecondaryPair pair = ((PlaylistNodeSecondaryIndex) node).addSong(song);
				if (pair != null) {
					genres.add(nodeIndex, pair.genre);
					children.add(nodeIndex + 1, pair.node);
				}
				break;
			case Leaf:
				PlaylistNodeSecondaryLeaf leaf = (PlaylistNodeSecondaryLeaf) node;
				int genreIndex;
				for (genreIndex = 0; genreIndex < leaf.genreCount(); genreIndex++) {
					int compareResult = song.genre().compareTo(leaf.genreAtIndex(genreIndex));
					if (compareResult <= 0) {
						break;
					}
				}
				leaf.addSong(genreIndex, song);
				if (leaf.genreCount() > 2 * order) {
					PlaylistNodeSecondaryLeaf newLeaf = new PlaylistNodeSecondaryLeaf(this);
					for (int i = 0; i <= order; i++) {
						newLeaf.getSongBucket().add(i, leaf.songsAtIndex(i + order));
					}
					for (int i = 2 * order; i >= order; i--) {
						leaf.getSongBucket().remove(i);
					}
					genres.add(nodeIndex, newLeaf.genreAtIndex(0));
					children.add(nodeIndex + 1, newLeaf);
				}
		}

		// Split if necessary
		if (genreCount() > 2 * order) {
			PlaylistNodeSecondaryIndex newIndex = new PlaylistNodeSecondaryIndex(getParent());
			String r = genreAtIndex(order);
			
			for (int i = order + 1; i < genreCount(); i++) {
				newIndex.genres.add(genres.get(i));
				newIndex.children.add(children.get(i));
			}
			newIndex.children.add(children.get(genreCount()));
			for (int i = genreCount(); i > order; i--) {
				genres.remove(i-1);
				children.remove(i);
			}
			return new AudioIdPlaylistNodeSecondaryPair(r, newIndex);
		}
		return null;
	}

	private void indent(int level) {
		for (int i = 0; i < level; i++) {
			System.out.print("\t");
		}
	}

	public void print(int level) {
		indent(level);

		System.out.println("<index>");
		for (int i = 0; i < genres.size(); i++) {
			indent(level);
			System.out.println(genres.get(i));
		}
		indent(level);
		System.out.println("</index>");
		for (PlaylistNode child : children) {
			switch (child.type) {
				case Internal:
					((PlaylistNodeSecondaryIndex) child).print(level + 1);
					break;
				case Leaf:
					PlaylistNodeSecondaryLeaf leaf = (PlaylistNodeSecondaryLeaf) child;
					indent(level + 1);
					System.out.println("<data>");
					for (int i = 0; i < leaf.genreCount(); i++) {
						ArrayList<CengSong> songs = leaf.songsAtIndex(i);
						indent(level + 1);
						System.out.println(leaf.genreAtIndex(i));
						for (CengSong song : songs) {
							indent(level + 2);
							System.out.println("<record>" + song.fullName() + "</record>");
						}
					}
					indent(level + 1);
					System.out.println("</data>");
			}
		}
	}
}
