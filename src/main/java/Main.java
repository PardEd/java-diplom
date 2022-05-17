import java.io.File;

public class Main {

	public static void main(String[] args) throws Exception {
		BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
		TodoServer server = new TodoServer(8989, engine);
		server.start();

		System.out.println(engine.search("бизнес"));
	}
}