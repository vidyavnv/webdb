package advanceddb2;

import java.util.ArrayList;
import java.util.List;

/*
 * Utility class to return queries corresponding to each categories
 */
public class Queries {
	private String rootQueries = "Computers cpu,Computers java,Computers module,Computers multimedia,Computers perl,Computers vb,Computers agp card,Computers application windows,Computers applet code,Computers array code,Computers audio file,Computers avi file,Computers bios,Computers buffer code,Computers bytes code,Computers shareware,Computers card drivers,Computers card graphics,Computers card pc,Computers pc windows,Health acupuncture,Health aerobic,Health aerobics,Health aids,Health cancer,Health cardiology,Health cholesterol,Health diabetes,Health diet,Health fitness,Health hiv,Health insulin,Health nurse,Health squats,Health treadmill,Health walkers,Health calories fat,Health carb carbs,Health doctor health,Health doctor medical,Health eating fat,Health fat muscle,Health health medicine,Health health nutritional,Health hospital medical,Health hospital patients,Health medical patient,Health medical treatment,Health patients treatment,Sports laker,Sports ncaa,Sports pacers,Sports soccer,Sports teams,Sports wnba,Sports nba,Sports avg league,Sports avg nba,Sports ball league,Sports ball referee,Sports ball team,Sports blazers game,Sports championship team,Sports club league,Sports fans football,Sports game league";
	private String computerQueries = "Hardware bios ,Hardware motherboard ,Hardware board fsb ,Hardware board overclocking ,Hardware fsb overclocking ,Hardware bios controller ide ,Hardware cables drive floppy ,Programming actionlistener ,Programming algorithms ,Programming alias ,Programming alloc ,Programming ansi ,Programming api ,Programming applet ,Programming argument ,Programming array ,Programming binary ,Programming boolean ,Programming borland ,Programming char ,Programming class ,Programming code ,Programming compile ,Programming compiler ,Programming component ,Programming container ,Programming controls ,Programming cpan ,Programming java ,Programming perl";
	private String healthQueries = "Diseases aids ,Diseases cancer ,Diseases dental ,Diseases diabetes ,Diseases hiv ,Diseases cardiology ,Diseases aspirin cardiologist ,Diseases aspirin cholesterol ,Diseases blood heart ,Diseases blood insulin ,Diseases cholesterol doctor ,Diseases cholesterol lipitor ,Diseases heart surgery ,Diseases radiation treatment ,Fitness aerobic ,Fitness fat ,Fitness fitness ,Fitness walking ,Fitness workout ,Fitness acid diets ,Fitness bodybuilding protein ,Fitness calories protein ,Fitness calories weight ,Fitness challenge walk ,Fitness dairy milk ,Fitness eating protein ,Fitness eating weight ,Fitness exercise protein ,Fitness exercise weight";
	private String sportsQueries = "Soccer uefa,Soccer leeds,Soccer bayern,Soccer bundesliga,Soccer premiership,Soccer lazio,Soccer mls,Soccer hooliganism,Soccer juventus,Soccer liverpool,Soccer fifa,Basketball nba,Basketball pacers,Basketball kobe,Basketball laker,Basketball shaq ,Basketball blazers,Basketball knicks,Basketball sabonis,Basketball shaquille,Basketball laettner,Basketball wnba,Basketball rebounds,Basketball dunks";
	
	private final List<String> rootList;
	private final List<String> computerList;
	private final List<String> healthList;
	private final List<String> sportsList;
	
	public Queries() {
		String [] rootArr = rootQueries.split(",");
		rootList = new ArrayList<String>();
		for(String s: rootArr) {
			rootList.add(s.trim());
		}
		
		String [] computerArr = computerQueries.split(",");
		computerList = new ArrayList<String>();
		for(String s: computerArr) {
			computerList.add(s.trim());
		}
		
		String [] healthArr = healthQueries.split(",");
		healthList = new ArrayList<String>();
		for(String s: healthArr) {
			healthList.add(s.trim());
		}
		
		String [] sportsArr = sportsQueries.split(",");
		sportsList = new ArrayList<String>();
		for(String s: sportsArr) {
			sportsList.add(s.trim());
		}
	}
	
	public List<String> getRootQueries() {
		return rootList;
	}
	
	public List<String> getComputersQueries() {
		return computerList;
	}
	
	public List<String> getHealthQueries() {
		return healthList;
	}
	
	public List<String> getSportsQueries() {
		return sportsList;
	}
}
