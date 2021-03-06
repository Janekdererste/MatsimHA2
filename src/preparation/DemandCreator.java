package preparation;

import java.util.List;
import java.util.Map;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.core.basic.v01.IdImpl;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordImpl;
import org.matsim.core.utils.geometry.geotools.MGC;

import structures.Commuter;
import structures.ShapeReader;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;

public class DemandCreator {
	
	private final String NETWORKFILE = "rawdata/network_cleaned_simplified.xml.gz";
	private final String COMMUTER = "rawdata/synpop/synpop.csv";
	private final String COUNTIES = "rawdata/stockholm_county_shp/stockholm_county.shp";
	private final String PLANS_OUTPUT = "input02/plans.xml";
	private final double SCALEFACTOR = 0.01;
	private final double PUBLIC_TRANSPORT_SHARE = 0.35;
	
	private Map<Integer, Geometry> counties;
	private Scenario scenario;
	private double[] startPercentages;
	
	
	public DemandCreator()
	{
		this.scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
		new MatsimNetworkReader(scenario).readFile(NETWORKFILE);
		counties = ShapeReader.readShapeFile(COUNTIES, "ZONE");
		startPercentages = new double[] { 0.075, 0.203, 0.199, 0.187, 0.17, 0.166 };		
	}	
	
	public void createDemand()
	{
		CommuterParser parser = new CommuterParser();
		parser.readData(COMMUTER);
		List<Commuter> commuters = parser.getCommuters();
		
		int counter = 0;		
		int arrCounter = 0;
		int ptCounter = 0;
		double timeRoot = 4.5; //alle die zwischen 5 und 6 gez�hlt werden fahren bestimmt zwischen 4:30 und 5:30 los.
		double perc = startPercentages[arrCounter];
		String mode;

		while(counter <= commuters.size() * SCALEFACTOR)
		{
			int i = (int) (Math.random() * commuters.size());			
			Commuter commuter = commuters.get(i);
			if(!commuter.isCarAvailable() || !commuter.isHasDriversLiscence())
				continue;
			
			if(counter >= commuters.size() * SCALEFACTOR * perc)
			{
				arrCounter++;
				perc += startPercentages[arrCounter];
				timeRoot = timeRoot + 1;
				ptCounter = 0; //reset pt counter, so it start for a new time slice
			}
			
			//each time slice should have a certain number of pt riders
			if(PUBLIC_TRANSPORT_SHARE > (ptCounter / (commuters.size() * SCALEFACTOR * startPercentages[arrCounter])))
			{
				mode = "pt";
				System.out.println(mode + ", " + timeRoot + ", " + ptCounter + ", " + startPercentages[arrCounter]);
			}
			else
			{
				mode = "car";
				System.out.println(mode + ", " + timeRoot + ", " + ptCounter + ", " + startPercentages[arrCounter]);
			}
			
			Person person = createMatsimPerson(commuter, timeRoot, mode);
			if(person == null)
				continue;
			try
			{
				scenario.getPopulation().addPerson(person);
			}
			catch(IllegalArgumentException ex)
			{
				System.out.println("Person already inserted");
				continue;
			}
			System.out.println("Person added");
			counter++;
			ptCounter++;
		}
		
		System.out.println("finished creating population");
		System.out.println("Population size: " + scenario.getPopulation().getPersons().size());
		System.out.println("Counter: " + counter);
		
		PopulationWriter pw = new PopulationWriter(scenario.getPopulation(), scenario.getNetwork());
		pw.write(PLANS_OUTPUT);
	}
	
	private Person createMatsimPerson(Commuter commuter, double timeRoot, String mode)
	{
		//person is not part of the workforce, if there is no workingplace
		Coord homeCoord;
		Coord workCoord;
		try
		{
			homeCoord = createCoordFromShapeId(commuter.getLivingZone());
			workCoord = createCoordFromShapeId(commuter.getWorkingZone());
		}
		catch(Exception e)
		{
			return null;
		}
		
		PopulationFactory pf = scenario.getPopulation().getFactory();
		Person person = pf.createPerson(new IdImpl(commuter.getId()));
		Plan plan = pf.createPlan();
		Activity home = pf.createActivityFromCoord("home", homeCoord);
		
		// create random time to leave home
		double start = Math.random() + timeRoot; // starting time 
		double end = Math.random() * 3 + 7; // end time between 7 and 10 hours
											// later than start

		end = end + start;
		home.setEndTime(start * 60 * 60);
		plan.addActivity(home);
		
		Leg leg1 = pf.createLeg(mode);
		plan.addLeg(leg1);
		
		Activity work = pf.createActivityFromCoord("work", workCoord);
		work.setStartTime(start * 60 * 60);
		work.setEndTime(end * 60 * 60);
		plan.addActivity(work);
		Leg leg2 = pf.createLeg(mode);
		plan.addLeg(leg2);
		Activity home2 = pf.createActivityFromCoord("home", homeCoord);
		plan.addActivity(home2);
		person.addPlan(plan);
		return person;
	}
	
	private Coord createCoordFromShapeId(int id)
	{
		Geometry county = counties.get(id);
		Envelope envelope = county.getEnvelopeInternal();
		
		while (true) {
			double x = createRandomNumber(envelope.getMinX(),
					envelope.getMaxX());
			double y = createRandomNumber(envelope.getMinY(),
					envelope.getMaxY());

			if (county.contains(MGC.xy2Point(x, y)))
				return new CoordImpl(x, y);
		}		
	}
	
	private double createRandomNumber(double min, double max)
	{
		double range = max - min;
		double rnd = Math.random();
		
		return range * rnd + min;
	}
	
		
		
	

}
