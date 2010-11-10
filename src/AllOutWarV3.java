import java.util.Iterator;
import java.util.List;


public class AllOutWarV3 implements Strategy {

	@Override
	public void doTurn(Planet m, PlanetWars pw) {
		List<Planet> closests = pw.listClosestNotMyPlanets(m);
		Iterator<Planet> it = closests.iterator();

		while (it.hasNext() && m.numShips > 0) {
			Planet c = it.next();
			int d = pw.getDistance(m.planetID, c.planetID);

			// planet hasn't been captured yet
			if (c.availableShips >= 0) {
				// calculate the bare minimum of ships to capture the closest planet
				int ships = c.availableShips + 1 + ((c.owner == 0) ? 0 : d * c.growthRate);

				// if we have enough ship...
				if (m.numShips >= ships) {
					// ... send the fleet
					if (pw.issueOrder(m, c, ships)) {
						// account for ships that just left...
						m.removeShips(ships);
						// ... and ships that will arrive
						c.availableShips -= ships;
					}
				}
			}
		}
	}

}
