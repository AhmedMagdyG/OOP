package rail;

import java.util.ArrayList;
import java.util.List;

import model.Util;

public class RailsContainer {
	private List<Rail> rails;

	public RailsContainer() {
		rails = new ArrayList<Rail>();
	}

	public void addRail(Rail rail) {
		rails.add(rail);
	}

	/**
	 * @return A random rail if exists else returns null.
	 */
	public Rail getRandomRail() {
		if (rails.isEmpty()) {
			return null;
		} else {
			return rails.get(Util.RANDOM_GENERATOR.nextInt(rails.size()));
		}
	}

	public List<Rail> getRails() {
		return rails;
	}
}
