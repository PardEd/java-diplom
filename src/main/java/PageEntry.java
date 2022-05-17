import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PageEntry implements Comparable<PageEntry> {

	private final String pdfName;
	private final int page;
	private final int count;


	public PageEntry(String pdfName, int page, int count) {
		this.pdfName = pdfName;
		this.page = page;
		this.count = count;
	}

	public String getPdfName() {
		return pdfName;
	}

	public int getPage() {
		return page;
	}

	public int getCount() {
		return count;
	}

	@Override
	public int compareTo(PageEntry o) {
		return Integer.compare(this.count, o.count);
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
