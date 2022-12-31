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
		return;
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
