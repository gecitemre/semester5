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

	
	public PlaylistNode addSong(CengSong song) {
		// This function returns null if song is added successfully. Otherwise, it splits the node and returns the pushed-up node.
		int low = 0;
		int high = this.audioIdCount();
		int mid = 0;
		while (low < high) {
			mid = (low + high) / 2;
			if (song.audioId() > this.audioIdAtIndex(mid)) {
				low = mid + 1;
			} else {
				high = mid;
			}
		}

		PlaylistNode child = this.getChildrenAt(low);
		if (child instanceof PlaylistNodePrimaryIndex) {
			PlaylistNodePrimaryIndex index = (PlaylistNodePrimaryIndex) child;
			if (index.audioIdCount() < 2 * PlaylistNode.order) {
				index.addSong(song);
				return null;	
			}
			// bisect
		} else {
			PlaylistNodePrimaryLeaf leaf = (PlaylistNodePrimaryLeaf) child;
			// bisect
			leaf.addSong(low, song);
		}
		return null;
	}
}
