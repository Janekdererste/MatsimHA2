package preperation;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.matsim.core.utils.io.tabularFileParser.TabularFileHandler;
import org.matsim.core.utils.io.tabularFileParser.TabularFileParser;
import org.matsim.core.utils.io.tabularFileParser.TabularFileParserConfig;

public class StockholmReadPendler {

	public static void main(String[] args) {
		StockholmReadPendler rp = new StockholmReadPendler();
		rp.run();
	}

	private void run() {

		PendlerParser pp = new PendlerParser();
		read("rawdata/synpop.csv", pp);
		pp.printRows();

	}

	private void read(String fileName, TabularFileHandler handler) {
		TabularFileParserConfig config = new TabularFileParserConfig();
		config.setDelimiterRegex(";");
		config.setFileName(fileName);
		config.setCommentRegex("#");
		new TabularFileParser().parse(config, handler);

	}
}

class PendlerParser implements TabularFileHandler {
	
	String currentFrom = "";
	String currentTo = "";

	Map<String, Integer> relations;

	PendlerParser() {
		this.relations = new TreeMap<String, Integer>();
	}	
	

	@Override
	public void startRow(String[] row) {		
		
		currentFrom = row[4];
		currentTo = row[5];

		String key = currentFrom + "-" + currentTo;
		String driverslicense = row[7];
		// System.out.println(driverslicense);

		if (Integer.parseInt(driverslicense) == 1) {
			if (this.relations.containsKey(key)) {
				int p = this.relations.get(key);
				p += 1;
				this.relations.put(key, p);
			} else {
				this.relations.put(key, 1);
				// System.out.println(key);
			}
		}
	}
	

	void printLines() {
		for (Integer i : relations.values()) {
			System.out.println(i);
		}

	}

	void printRows() {
		for (Entry<String, Integer> e : this.relations.entrySet()) {
			System.out.println(e.getKey() + "\t" + e.getValue());
		}
	}

}
