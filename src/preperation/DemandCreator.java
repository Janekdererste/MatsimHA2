package preperation;

import java.util.List;
import java.util.Map;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;

import structures.Commuter;

import com.vividsolutions.jts.geom.Geometry;

public class DemandCreator {
	
	private final String NETWORKFILE = "rawdata/network_cleaned_simplified.xml.gz";
	private final String COMMUTER = "rawdata/synpop/synpop.csv";
	private final String COUNTIES = "rawdata/stockholm_county.shp";
	private final String PLANS_OUTPUT = "input_nullfall/plans.xml";
	private final double SCALEFACTOR = 0.01;
	
	private Map<String, Geometry> counties;
	private Scenario scenario;
	
	public DemandCreator()
	{
		this.scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
		new MatsimNetworkReader(scenario).readFile(NETWORKFILE);
	}
	
	
	public void CreateDemand()
	{
		CommuterParser parser = new CommuterParser();
		parser.readData(COMMUTER);
		List<Commuter> commuters = parser.getCommuters();
	}

}
