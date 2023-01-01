import java.util.ArrayList;

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

	public void addSong(CengSong song) {
		if (genreCount() == 0) {
			genres.add(song.genre());
			PlaylistNodeSecondaryLeaf leaf = new PlaylistNodeSecondaryLeaf(this);
			children.add(leaf);
			leaf.addSong(0, song);
			return;
		}
		int low = 0;
		int high = genreCount() - 1;
		int mid;
		while (low < high) {
			mid = (low + high) / 2;
			if (genres.get(mid).compareTo(song.genre()) < 0) {
				low = mid + 1;
			} else if (genres.get(mid).compareTo(song.genre()) > 0) {
				high = mid;
			} else {
				((PlaylistNodeSecondaryLeaf) children.get(mid)).addSong(0, song);
				return;
			}
		}
		if (genres.get(low).compareTo(song.genre()) < 0) {
			genres.add(low + 1, song.genre());
			PlaylistNodeSecondaryLeaf leaf = new PlaylistNodeSecondaryLeaf(this);
			children.add(low + 1, leaf);
			leaf.addSong(0, song);
		} else if (genres.get(low).compareTo(song.genre()) > 0) {
			genres.add(low, song.genre());
			PlaylistNodeSecondaryLeaf leaf = new PlaylistNodeSecondaryLeaf(this);
			children.add(low, leaf);
			leaf.addSong(0, song);
		} else {
			((PlaylistNodeSecondaryLeaf) children.get(low)).addSong(0, song);
		}
	}

	private void indent(int depth) {
		for (int i = 0; i < depth; i++) {
			System.out.print("\t");
		}
	}

	public void print(int depth) {
		indent(depth);
		System.out.println("<Index>");
		for (int i = 0; i < genres.size(); i++) {
			indent(depth + 1);
			System.out.println(genres.get(i));
		}
		for (PlaylistNode child : children) {
			switch (child.type) {
				case Internal:
					((PlaylistNodeSecondaryIndex) child).print(depth + 1);
					break;
				case Leaf:
					PlaylistNodeSecondaryLeaf leaf = (PlaylistNodeSecondaryLeaf) child;
					indent(depth + 1);
					System.out.println(leaf.genreAtIndex(0));
					indent(depth + 1);
					System.out.println("<data>");
					for (ArrayList<CengSong> songs : leaf.getSongBucket()) {
						for (CengSong song : songs) {
							indent(depth + 3);
							System.out.println("<record>" + song.audioId() + "</record>");
						}
						indent(depth + 2);
						System.out.println("</data>");
					}
					break;
			}
		}
		indent(depth);
		System.out.println("</Index>");
	}
}
