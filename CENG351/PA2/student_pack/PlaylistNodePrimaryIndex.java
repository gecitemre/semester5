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
	
	public CengSong searchSong(Integer audioId) {
		// find the greatest audioId that is smaller than the given audioId
		int low = 0;
		int high = audioIds.size() - 1;
		int mid = 0;
		while (low <= high) {
			mid = (low + high) / 2;
			if (audioId < audioIds.get(mid)) {
				high = mid - 1;
			}
			else {
				low = mid + 1;
			}
		}
		// if the audioId is not found, return null
		if (low == 0) {
			return null;
		}
		PlaylistNode node = children.get(low - 1);
		if (node instanceof PlaylistNodePrimaryLeaf) {
			PlaylistNodePrimaryLeaf leaf = (PlaylistNodePrimaryLeaf) node;
			return leaf.searchSong(audioId);
		}
		else {
			PlaylistNodePrimaryIndex index = (PlaylistNodePrimaryIndex) node;
			return index.searchSong(audioId);
		}
	}

	public void indent(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("\t");
        }
    }

	public void print(int depth) {
		indent(depth);
		System.out.println("<Index>");
		for (PlaylistNode node : children) {
			if (node instanceof PlaylistNodePrimaryLeaf) {
				PlaylistNodePrimaryLeaf leaf = (PlaylistNodePrimaryLeaf) node;
				leaf.print(depth + 1);
			}
			else {
				PlaylistNodePrimaryIndex index = (PlaylistNodePrimaryIndex) node;
				index.print(depth + 1);
			}
		}
		indent(depth);
		System.out.println("</Index>");
	}

	public void addSong(CengSong song) {
		// find the greatest audioId that is smaller than the given audioId
		int low = 0;
		int high = audioIds.size() - 1;
		int mid = 0;
		while (low <= high) {
			mid = (low + high) / 2;
			if (song.audioId() < audioIds.get(mid)) {
				high = mid - 1;
			}
			else {
				low = mid + 1;
			}
		}
		// if the audioId is not found, return null
		if (low == 0) {
			return;
		}
		PlaylistNode node = children.get(low - 1);
		if (node instanceof PlaylistNodePrimaryLeaf) {
			PlaylistNodePrimaryLeaf leaf = (PlaylistNodePrimaryLeaf) node;
			leaf.addSong(song);
		}
		else {
			PlaylistNodePrimaryIndex index = (PlaylistNodePrimaryIndex) node;
			index.addSong(song);
		}
	}
}
