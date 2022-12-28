import java.util.ArrayList;
import java.util.Arrays;

public class PlaylistNodePrimaryIndex extends PlaylistNode {
	private ArrayList<Integer> audioIds;
	private ArrayList<PlaylistNode> children;
	
	public PlaylistNodePrimaryIndex(PlaylistNode parent) {
		super(parent);
		audioIds = new ArrayList<Integer>();
		children = new ArrayList<PlaylistNode>();
		this.type = PlaylistNodeType.Internal;
	}
	
	public PlaylistNodePrimaryIndex(PlaylistNode parent, ArrayList<Integer> audioIds, ArrayList<PlaylistNode> children) {
		super(parent);
		this.audioIds = audioIds;
		this.children = children;
		this.type = PlaylistNodeType.Internal;
	}
	
	// GUI Methods - Do not modify
	public ArrayList<PlaylistNode> getAllChildren()
	{
		return this.children;
	}
	
	public PlaylistNode getChildrenAt(Integer index) {return this.children.get(index); }
	
	public Integer audioIdCount()
	{
		return this.audioIds.size();
	}
	public Integer audioIdAtIndex(Integer index) {
		if(index >= this.audioIdCount() || index < 0) {
			return -1;
		}
		else {
			return this.audioIds.get(index);
		}
	}

	
	public ArrayList<PlaylistNode> addSong(CengSong song) {
		// This function returns null if song is added successfully. Otherwise, it splits the node and returns the pushed-up node.
		
		int insertIndex = -(Arrays.binarySearch(audioIds.toArray(), song.audioId())+1);

		PlaylistNode child = getChildrenAt(insertIndex);
		if (child instanceof PlaylistNodePrimaryIndex) {
			PlaylistNodePrimaryIndex index = (PlaylistNodePrimaryIndex) child;
			index.addSong(song);
			if (index.audioIdCount() == 2 * PlaylistNode.order) {
				PlaylistNodePrimaryIndex leftIndex = new PlaylistNodePrimaryIndex(this);
				PlaylistNodePrimaryIndex rightIndex = new PlaylistNodePrimaryIndex(this);
				int i;
				for (i = 0; i < PlaylistNode.order; i++) {
					leftIndex.audioIds.add(audioIds.get(i));
					leftIndex.children.add(children.get(i));
				}
				for (; i < 2 * PlaylistNode.order; i++) {
					rightIndex.audioIds.add(audioIds.get(i));
					rightIndex.children.add(children.get(i));
				}
				child = leftIndex;
				children.add(insertIndex + 1, rightIndex);
				audioIds.add(insertIndex, rightIndex.audioIds.get(0));
			}
		} else {
			PlaylistNodePrimaryLeaf leaf = (PlaylistNodePrimaryLeaf) child;
			insertIndex = -(Arrays.binarySearch(leaf.getSongs().toArray(), song)+1);
			leaf.addSong(insertIndex, song);
			if (leaf.songCount() == 2 * PlaylistNode.order) {
				PlaylistNodePrimaryLeaf leftLeaf = new PlaylistNodePrimaryLeaf(this);
				PlaylistNodePrimaryLeaf rightLeaf = new PlaylistNodePrimaryLeaf(this);
				int i;
				for (i = 0; i < PlaylistNode.order; i++) {
					leftLeaf.addSong(i, leaf.getSongs().get(i));
				}
				for (; i < 2 * PlaylistNode.order; i++) {
					rightLeaf.addSong(i - PlaylistNode.order, leaf.getSongs().get(i));
				}
				child = leftLeaf;
				children.add(insertIndex + 1, rightLeaf);
				audioIds.add(insertIndex, rightLeaf.getSongs().get(0).audioId());
			}
		}
		return null;
	}
}