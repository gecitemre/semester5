import java.util.ArrayList;

class AudioIdPlaylistNodePrimaryPair {
	public Integer audioId;
	public PlaylistNode node;

	public AudioIdPlaylistNodePrimaryPair(Integer audioId, PlaylistNode node) {
		this.audioId = audioId;
		this.node = node;
	}
}

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

	public AudioIdPlaylistNodePrimaryPair addSong(CengSong song) {
		if (audioIdCount() == 0) {
			audioIds.add(song.audioId());
			PlaylistNodePrimaryLeaf leaf = new PlaylistNodePrimaryLeaf(this);
			children.add(leaf);
			leaf.addSong(0, song);
			return null;
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
				AudioIdPlaylistNodePrimaryPair pair = ((PlaylistNodePrimaryIndex) node).addSong(song);
				if (pair != null) {
					audioIds.add(nodeIndex, pair.audioId);
					children.add(nodeIndex + 1, pair.node);
				}
				break;
			case Leaf:
				PlaylistNodePrimaryLeaf leaf = (PlaylistNodePrimaryLeaf) node;
				int songIndex;
				for (songIndex = 0; songIndex < leaf.songCount(); songIndex++) {
					if (leaf.audioIdAtIndex(songIndex) > song.audioId()) {
						break;
					}
				}
				leaf.addSong(songIndex, song);
				if (leaf.songCount() > 2 * order) {
					// split the leaf
					PlaylistNodePrimaryLeaf newLeaf = new PlaylistNodePrimaryLeaf(this);
					for (int i = 2 * order; i >= order; i--) {
						newLeaf.addSong(0, leaf.songAtIndex(i));
					}
					for (int i = 2 * order; i >= order; i--) {
						leaf.getSongs().remove(i);
					}
					audioIds.add(nodeIndex, newLeaf.audioIdAtIndex(0));
					children.add(nodeIndex + 1, newLeaf);
				}
		}

		// split the node if necessary
		if (audioIdCount() > 2 * order) {
			// split the node
			PlaylistNodePrimaryIndex newIndex = new PlaylistNodePrimaryIndex(this.getParent());
			int r = audioIdAtIndex(order);
			for (int i = order + 1; i < audioIdCount(); i++) {
				newIndex.audioIds.add(audioIds.get(i));
				newIndex.children.add(children.get(i));
			}
			newIndex.children.add(children.get(audioIdCount()));
			for (int i = audioIdCount(); i > order; i--) {
				audioIds.remove(i - 1);
				children.remove(i);
			}
			return new AudioIdPlaylistNodePrimaryPair(r, newIndex);
		}
		return null;
	}

	public CengSong searchSong(Integer audioId, int depth) {
		indent(depth);
		System.out.println("<index>");
		int index;
		for (index = 0; index < audioIdCount(); index++) {
			indent(depth + 1);
			System.out.println("<node>" + audioIdAtIndex(index) + "</node>");
			if (audioIdAtIndex(index) > audioId) {
				break;
			}
		}
		indent(depth);
		System.out.println("</index>");
		PlaylistNode node = children.get(index);
		switch (node.type) {
			case Internal:
				return ((PlaylistNodePrimaryIndex) node).searchSong(audioId, depth + 1);
			case Leaf:
				PlaylistNodePrimaryLeaf leaf = (PlaylistNodePrimaryLeaf) node;
				System.out.println("<data>");
				for (int i = 0; i < leaf.songCount(); i++) {
					System.out.println("<record>"+leaf.songAtIndex(i).fullName()+"</record>");
					if (leaf.audioIdAtIndex(i) == audioId) {
						System.out.println("</data>");
						return leaf.songAtIndex(index);
					}
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
		System.out.println("<index>");
		for (int i = 0; i < audioIdCount(); i++) {
			indent(depth + 1);
			System.out.println(audioIds.get(i));
		}
		indent(depth);
		System.out.println("</index>");
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
			}
		}
	}
}