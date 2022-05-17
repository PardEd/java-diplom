import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {

	private Map<String, ArrayList<PageEntry>> wordList = new HashMap<>();

	public BooleanSearchEngine(File pdfsDir) throws IOException {
		if (pdfsDir.isDirectory()) {
			for (File item : Objects.requireNonNull(pdfsDir.listFiles())) {
				if (!item.isDirectory()) {
					var doc = new PdfDocument(new PdfReader(item));
					for (int page = 1; page < doc.getNumberOfPages(); page++) {
						Map<String, Integer> freqs = new HashMap<>();
						var text = PdfTextExtractor.getTextFromPage(doc.getPage(page));
						var words = text.split("\\P{IsAlphabetic}+");
						for (var word : words) {
							if (word.isEmpty()) {
								continue;
							}
							freqs.put(word.toLowerCase(), freqs.getOrDefault(word, 0) + 1);
						}
						for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
							if (wordList.containsKey(entry.getKey())) {
								wordList.get(entry.getKey())
									.add(new PageEntry(item.getName(), page, entry.getValue()));
							} else {

								wordList.put(entry.getKey(), new ArrayList<>());
								wordList.get(entry.getKey())
									.add(new PageEntry(item.getName(), page, entry.getValue()));
							}
						}
					}
				}
			}
		} else {
			System.out.println("Folder does not exist");
		}
	}

	@Override
	public List<PageEntry> search(String word) {
		List<PageEntry> result = null;
		if (wordList.containsKey(word)) {
			result = wordList.get(word)
				.stream()
				.sorted(Comparator.comparing(PageEntry::getCount))
				.collect(Collectors.toList());
		}
		return result;
	}
}
