package structures;

import java.util.HashMap;
import java.util.Map;

import org.matsim.core.utils.gis.ShapeFileReader;
import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.WKTReader;

public class ShapeReader {

	public static Map<Integer, Geometry> readShapeFile(String filename, String attrString)
	{
		Map<Integer, Geometry> shapeMap = new HashMap<Integer, Geometry>();
		
		System.out.println("ShapeReader: Start reading file: " + filename + "with key: " + attrString);
		
		for(SimpleFeature ft : ShapeFileReader.getAllFeatures(filename))
		{
			GeometryFactory geomFact = new GeometryFactory();
			WKTReader reader = new WKTReader(geomFact);
			Geometry g; 
			
			try
			{
				g = reader.read(ft.getAttribute("the_geom").toString());
				String idStr = ft.getAttribute(attrString).toString();
				int id = (int)Double.parseDouble(idStr);
				shapeMap.put(id, g);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
		System.out.println("ShapeReader: Finished reading file: " + filename);
		return shapeMap;
	}
}
