package myproject.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import myproject.util.Animator;

/**
 * An example to model for a simple visualization.
 * The model contains roads organized in a matrix.
 * See {@link #Model(AnimatorBuilder, int, int)}.
 */
public class Model extends Observable {
	private static List<Agent> agents;
	private Animator animator;
	private boolean disposed;
	private double time;

	/** Creates a model to be visualized using the <code>builder</code>.
	 *  If the builder is null, no visualization is performed.
	 *  The number of <code>rows</code> and <code>columns</code>
	 *  indicate the number of {@link Light}s, organized as a 2D
	 *  matrix.  These are separated and surrounded by horizontal and
	 *  vertical {@link Road}s.  For example, calling the constructor with 1
	 *  row and 2 columns generates a model of the form:
	 *  <pre>
	 *     |  |
	 *   --@--@--
	 *     |  |
	 *  </pre>
	 *  where <code>@</code> is a {@link Light}, <code>|</code> is a
	 *  vertical {@link Road} and <code>--</code> is a horizontal {@link Road}.
	 *  Each road has one {@link Car}.
	 *
	 *  <p>
	 *  The {@link AnimatorBuilder} is used to set up an {@link
	 *  Animator}.
	 *  {@link AnimatorBuilder#getAnimator()} is registered as
	 *  an observer of this model.
	 *  <p>
	 */
	public Model(AnimatorBuilder builder, int rows, int columns) {
		if (rows < 0 || columns < 0 || (rows == 0 && columns == 0)) {
			throw new IllegalArgumentException();
		}
		if (builder == null) {
			builder = new NullAnimatorBuilder();
		}
		Model.agents = new ArrayList<Agent>();
		setup(builder, rows, columns);
		this.animator = builder.getAnimator();
		super.addObserver(animator);
	}

	/**
	 * Run the simulation for <code>duration</code> model seconds.
	 */
	public void run(double duration) {
		if (disposed)
			throw new IllegalStateException();
		for (int i=0; i<duration; i++) {
			time++;
			// iterate through a copy because agents may change during iteration...
			for (Agent a : agents.toArray(new Agent[0])) {
				a.run(time); 
			}
			super.setChanged();
			super.notifyObservers();
		}
	}

	/**
	 * Throw away this model.
	 */
	public void dispose() {
		animator.dispose();
		disposed = true;
	}

	/**
	 * Construct the model, establishing correspondences with the visualizer.
	 */
	private void setup(AnimatorBuilder builder, int rows, int columns) {
		Light[][] lights = new Light[rows][columns];
		Road[][] horizontalRoads = new Road[rows][columns+1];
		Road[][] verticalRoads = new Road[rows+1][columns];
		
		// Add Horizontal Roads
		boolean eastToWest = false;
		for (int i=0; i<rows; i++) {
			for (int j=0; j<=columns; j++) {
				Road l = new Road(eastToWest, "H"+String.valueOf(i)+String.valueOf(j));
				builder.addHorizontalRoad(l, i, j, eastToWest);
				horizontalRoads[i][j] = l;
			}
			eastToWest = !eastToWest;
		}

		// Add Vertical Roads
		boolean southToNorth = false;
		for (int j=0; j<columns; j++) {
			for (int i=0; i<=rows; i++) {
				Road l = new Road(southToNorth, "V"+String.valueOf(i)+String.valueOf(j));
				builder.addVerticalRoad(l, i, j, southToNorth);
				verticalRoads[i][j] = l;
			}
			southToNorth = !southToNorth;
		}

		//Add Lights
		for (int i=0; i<rows;i++) {
			for (int j=0; j<columns; j++) {
				Light l = new Light(String.valueOf(i)+String.valueOf(j));
				lights[i][j] =  l;
				builder.addLight(lights[i][j], i, j);
				agents.add(lights[i][j]);
			}
		}
		
		//Horizontal connections
		//Add Junkyards at the end of horizontal roads
		boolean dir = false; // represents eastToWest
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				Road W = horizontalRoads[i][j];
				Road E = horizontalRoads[i][j+1];
				Light l = lights[i][j];
				System.out.printf("Connecting L%s horizontally: W=%s, E=%s\n", l, W, E);
				//System.out.printf("dir=%s", dir);
				if (dir == false) { // dir = west to east
					if (j==columns-1) {
						Junkyard jy = new Junkyard();
						E.connect(jy);
					} else if (j==0) {
						Dealership ds = new Dealership();
						ds.connectRoad(W);
						agents.add(ds);
					}
					l.connectRoad(W, true, true);
					l.connectRoad(E, true, false);
					System.out.printf("Connecting W-%s: L=%s\n", W, l);
					W.connect(l);
				} else {			// dir = east to west
					if (j==0) {
						Junkyard jy = new Junkyard();
						W.connect(jy);
					} else if (j==columns-1) {
						Dealership ds = new Dealership();
						ds.connectRoad(E);
						agents.add(ds);
					}
					l.connectRoad(W, true, false);
					l.connectRoad(E, true, true);
					System.out.printf("Connecting E-%s: L=%s\n", E, l);
					E.connect(l);
				}
			}
			dir = !dir;
		}
		
		//Vertical connections
		//Add Junkyards at the end of vertical roads
		dir = false; // represents SouthToNorth
		for (int j=0; j<columns; j++) {
			for (int i=0; i<rows; i++) {
				Road N = verticalRoads[i][j];
				Road S = verticalRoads[i+1][j];
				Light l = lights[i][j];
				System.out.printf("Connecting L%s vertically: N=%s, S=%s\n", l, N, S);
				//System.out.printf("dir=%s", dir);
				if (dir == false) { //dir = north to south
					if (i==rows-1) { //At the end of the column
						Junkyard jy = new Junkyard();
						S.connect(jy);
					} else if (i==0) { //At the beginning of the column
						Dealership ds = new Dealership();
						ds.connectRoad(N);
						agents.add(ds);
					}
					l.connectRoad(N, false, true);
					l.connectRoad(S, false, false);
					System.out.printf("Connecting N-%s: L=%s\n", N, l);
					N.connect(l);
				} else {			//dir = south to north
					if (i==0) { 	//At the beginning of column
						Junkyard jy = new Junkyard();
						N.connect(jy);
					} else if (i==rows-1) { //At the end of column
						Dealership ds = new Dealership();
						ds.connectRoad(S);
						agents.add(ds);
					}
					l.connectRoad(N, false, false);
					l.connectRoad(S, false, true);
					System.out.printf("Connecting S-%s: L=%s\n", S, l);
					S.connect(l);
				}
			}
			dir = !dir;
		}

		// Add Cars
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				Car car = new Car("H"+String.valueOf(i)+String.valueOf(j));
				car.initializeOnRoad(horizontalRoads[i][j]);
				horizontalRoads[i][j].accept(car);
				agents.add(car);
			}
		}
		for (int j=0; j<columns; j++) {
			for (int i=0; i<rows; i++) {
				Car car = new Car("V"+String.valueOf(i)+String.valueOf(j));
				car.initializeOnRoad(verticalRoads[i][j]);
				verticalRoads[i][j].accept(car);
				agents.add(car);
			}
		}

		System.out.println();
	}

	public static void addAgent(Agent a) {
		agents.add(a);
	}
}
