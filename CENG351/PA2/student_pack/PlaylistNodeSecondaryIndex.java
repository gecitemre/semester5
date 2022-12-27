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
	public ArrayList<PlaylistNode> getAllChildren()
	{
		return this.children;
	}
	
	public PlaylistNode getChildrenAt(Integer index) {
		
		return this.children.get(index);
	}
	

	public Integer genreCount()
	{
		return this.genres.size();
	}
	
	public String genreAtIndex(Integer index) {
		if(index >= this.genreCount() || index < 0) {
			return "Not Valid Index!!!";
		}
		else {
			return this.genres.get(index);
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


}
