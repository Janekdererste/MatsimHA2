package preparation;

import java.util.ArrayList;
import java.util.List;

import org.matsim.core.utils.io.tabularFileParser.TabularFileHandler;
import org.matsim.core.utils.io.tabularFileParser.TabularFileParser;
import org.matsim.core.utils.io.tabularFileParser.TabularFileParserConfig;

import structures.Commuter;

public class CommuterParser implements TabularFileHandler {

	private ArrayList<Commuter> commuters;
	
	public List<Commuter> getCommuters()
	{
		return commuters;
	}

	public void readData(String filename) {
		this.commuters = new ArrayList<Commuter>();
		TabularFileParserConfig config = new TabularFileParserConfig();
		config.setDelimiterRegex(";");
		config.setFileName(filename);
		config.setCommentRegex("#");
		System.out.println("Start reading file: " + filename);
		new TabularFileParser().parse(config, this);
	}

	public void startRow(String[] row) {
		// row should be 14 entries long
		if (row.length != 14)
			return;

		Commuter cc = new Commuter();
		cc.setId(row[0]);
		cc.setBirthYear(row[1]);
		cc.setSex(row[2]);
		cc.setIncome(row[3]);
		cc.setLivingZone(row[4]);
		cc.setWorkingZone(row[5]);
		cc.setMunicipalityID(row[6]);
		cc.setHasDriversLiscence(row[7]);
		cc.setHousingtype(row[8]);
		cc.setHhChildren(row[9]);
		cc.setHhAdults(row[10]);
		// don't know what HH_lic is so we skip it
		cc.setCarAvailable(row[12]);
		cc.setHhCars(row[13]);

		this.commuters.add(cc);
	}

}
