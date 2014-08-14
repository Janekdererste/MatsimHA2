package startMatSIM;

import org.matsim.core.controler.Controler;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.Config;

public class StartMatsim {

	private static final String DEFAULT_CONFIG = "input/config.xml";
	
	public static void main(String[] args) {
		
		String path;
		if(args.length == 2)
		{
			if(args[0].equalsIgnoreCase("-config"))
				path = args[1];
			else
				path = DEFAULT_CONFIG;				
		}
		else
			path = DEFAULT_CONFIG;		
		
		Config config = ConfigUtils.loadConfig(path);
		Controler controler = new Controler(config);
		controler.setOverwriteFiles(true);
		controler.run();

	}

}
