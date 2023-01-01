import java.util.ArrayList;

public class PlaylistNodePrimaryIndex extends PlaylistNode {
	private ArrayList<Integer> audioIds;
	private ArrayList<PlaylistNode> children;

	public PlaylistNodePrimaryIndex(PlaylistNode parent) {
		super(parent);
		audioIds = new ArrayList<Integer>();
		children = new ArrayList<PlaylistNode>();
		this.type = PlaylistNodeType.Internal;
	}

	public PlaylistNodePrimaryIndex(PlaylistNode parent, ArrayList<Integer> audioIds,
			ArrayList<PlaylistNode> children) {
		super(parent);
		this.audioIds = audioIds;
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

	public Integer audioIdCount() {
		return this.audioIds.size();
	}

	public Integer audioIdAtIndex(Integer index) {
		if (index >= this.audioIdCount() || index < 0) {
			return -1;
		} else {
			return this.audioIds.get(index);
		}
	}

	public void addSong(CengSong song) {
		if (audioIdCount() == 0) {
			audioIds.add(song.audioId());
			PlaylistNodePrimaryLeaf leaf = new PlaylistNodePrimaryLeaf(this);
			children.add(leaf);
			leaf.addSong(0, song);
			return;
		}
		int nodeIndex;
		for (nodeIndex = 0; nodeIndex < audioIdCount(); nodeIndex++) {
			if (audioIdAtIndex(nodeIndex) > song.audioId()) {
				break;
			}
		}
		PlaylistNode node = children.get(nodeIndex);
		switch (node.type) {
			case Internal:
				((PlaylistNodePrimaryIndex) node).addSong(song);
				break;
			case Leaf:
				PlaylistNodePrimaryLeaf leaf = (PlaylistNodePrimaryLeaf) node;
				int songIndex;
				for (songIndex = 0; songIndex < leaf.songCount(); songIndex++) {
					if (leaf.songAtIndex(songIndex).audioId() > song.audioId()) {
						break;
					}
				}
				leaf.addSong(songIndex, song);
				if (leaf.songCount() > 2 * order) {
					// split
					ArrayList<CengSong> songs = new ArrayList<CengSong>() {
						{
							for (int i = PlaylistNode.order; i < leaf.songCount(); i++) {
								add(leaf.songAtIndex(i));
							}
						}
					};
					for (int i = 2 * PlaylistNode.order; i >= PlaylistNode.order; i--) {
						leaf.getSongs().remove(i);
					}
					PlaylistNodePrimaryLeaf newLeaf = new PlaylistNodePrimaryLeaf(leaf.getParent(), songs);
					ArrayList<PlaylistNode> children = new ArrayList<PlaylistNode>() {
						{
							add(leaf);
							add(newLeaf);
						}
					};
					ArrayList<Integer> audioIds = new ArrayList<Integer>() {
						{
							add(newLeaf.songAtIndex(0).audioId());
						}
					};
					this.children.set(nodeIndex, new PlaylistNodePrimaryIndex(this, audioIds, children));
				}
		}

	}

	public CengSong searchSong(Integer audioId) {
		// find the index of the first element that is greater than or equal to audioId
		int low = 0;
		int high = audioIdCount() - 1;
		int mid;
		while (low < high) {
			mid = (low + high) / 2;
			if (audioIds.get(mid) < audioId) {
				low = mid + 1;
			} else if (audioIds.get(mid) > audioId) {
				high = mid;
			}
		}
		PlaylistNode node = children.get(high);
		switch (node.type) {
			case Internal:
				return ((PlaylistNodePrimaryIndex) node).searchSong(audioId);
			case Leaf:
				PlaylistNodePrimaryLeaf leaf = (PlaylistNodePrimaryLeaf) node;
				low = 0;
				high = leaf.songCount() - 1;
				while (low < high) {
					mid = (low + high) / 2;
					if (leaf.songAtIndex(mid).audioId() < audioId) {
						low = mid + 1;
					} else if (leaf.songAtIndex(mid).audioId() > audioId) {
						high = mid;
					}
				}
				if (leaf.songAtIndex(high).audioId() == audioId) {
					return leaf.songAtIndex(high);
				}
			default:
				System.out.println("Could not find " + audioId);
				return null;
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
		for (int i = 0; i < audioIdCount(); i++) {
			indent(depth + 1);
			System.out.println(audioIds.get(i));
		}
		for (PlaylistNode child : children) {
			switch (child.type) {
				case Internal:
					((PlaylistNodePrimaryIndex) child).print(depth + 1);
					break;
				case Leaf:
					PlaylistNodePrimaryLeaf leaf = (PlaylistNodePrimaryLeaf) child;
					indent(depth + 1);
					System.out.println("<data>");
					for (CengSong song : leaf.getSongs()) {
						indent(depth + 2);
						System.out.println("\t<record>" + song.fullName() + "</record>");
					}
					indent(depth + 1);
					System.out.println("</data>");
					break;
			}
		}
		indent(depth);
		System.out.println("</Index>");
	}
}