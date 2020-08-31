package de.davelee.statsres.main;

import java.util.List;

import de.davelee.statsres.main.StatsresProg;
import de.davelee.statsres.main.StatsresSettings;

public class StatsresProgMock extends StatsresProg {
	
	 public boolean saveContent ( final List<String> content, final String location, final String fileExtension ) {
		 return location.contentEquals("status.srs");
	 }
	 
	 public StatsresSettings loadSettingsFile ( String location ) {
		 if ( location.contentEquals("status.srs") ) {
			 return StatsresSettings.createDefaultSettings(location);
		 }
		 return null;
	 }
	 
	 public String loadOutputFile ( String location ) {
		 if ( location.contentEquals("output.sro") ) {
			 return "myOutput";
		 }
		 return "";
	 }

}
