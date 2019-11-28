package yangWork;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class HexagonProgressBarFactory {

	private int baseEdgeLen, widthOfProgressBar, percentage;
	private List<Number> interval;
	private List<List<Double>> base;

	public HexagonProgressBarFactory(int baseEdgeLen, int widthOfProgressBar, int percentage) {
		this.baseEdgeLen = baseEdgeLen;
		this.widthOfProgressBar = widthOfProgressBar;
		this.percentage = percentage;
		this.interval = getInterval();
		this.base = generateCoordinateOfBase();
	}

	private static final double SQRT3 = Math.sqrt(3);

	private List<List<Double>> generateCoordinateOfBase() {
		List<List<Double>> coordinateOfBase = new LinkedList<List<Double>>();
		coordinateOfBase.add(Arrays.asList(SQRT3 / 2 * baseEdgeLen, (double) 0));
		coordinateOfBase.add(Arrays.asList(SQRT3 * baseEdgeLen, (double) baseEdgeLen / 2));
		coordinateOfBase.add(Arrays.asList(SQRT3 * baseEdgeLen, (double) baseEdgeLen * 1.5));
		coordinateOfBase.add(Arrays.asList(SQRT3 / 2 * baseEdgeLen, (double) baseEdgeLen * 2));
		coordinateOfBase.add(Arrays.asList((double) 0, (double) 1.5 * baseEdgeLen));
		coordinateOfBase.add(Arrays.asList((double) 0, (double) 0.5 * baseEdgeLen));

		return coordinateOfBase;
	}

	private List<List<Double>> generateCoordinateOfInnerProgressBar() {
		List<List<Double>> coordinate = new LinkedList<List<Double>>();
		double halfWidth = widthOfProgressBar / 2;

		coordinate.add(Arrays.asList(base.get(0).get(0), base.get(0).get(1) + halfWidth));
		coordinate.add(Arrays.asList(base.get(1).get(0) - SQRT3 / 2 * halfWidth, base.get(1).get(1) + halfWidth / 2));
		coordinate.add(Arrays.asList(base.get(2).get(0) - SQRT3 / 2 * halfWidth, base.get(2).get(1) - halfWidth / 2));
		coordinate.add(Arrays.asList(base.get(3).get(0), base.get(3).get(1) - halfWidth));
		coordinate.add(Arrays.asList(base.get(4).get(0) + SQRT3 / 2 * halfWidth, base.get(4).get(1) - halfWidth / 2));
		coordinate.add(Arrays.asList(base.get(5).get(0) + SQRT3 / 2 * halfWidth, base.get(4).get(1) + halfWidth / 2));

		// for calculate interval use
		coordinate.add(Arrays.asList(base.get(0).get(0), base.get(0).get(1) + halfWidth));

		return generateCoordinateOfProgressBar(coordinate);
	}

	private List<List<Double>> generateCoordinateOfOuterProgressBar() {
		List<List<Double>> coordinate = new LinkedList<List<Double>>();
		double halfWidth = widthOfProgressBar / 2;

		coordinate.add(Arrays.asList(base.get(0).get(0), base.get(0).get(1) - halfWidth));
		coordinate.add(Arrays.asList(base.get(1).get(0) + SQRT3 / 2 * halfWidth, base.get(1).get(1) - halfWidth / 2));
		coordinate.add(Arrays.asList(base.get(2).get(0) + SQRT3 / 2 * halfWidth, base.get(2).get(1) + halfWidth / 2));
		coordinate.add(Arrays.asList(base.get(3).get(0), base.get(3).get(1) + halfWidth));
		coordinate.add(Arrays.asList(base.get(4).get(0) - SQRT3 / 2 * halfWidth, base.get(4).get(1) + halfWidth / 2));
		coordinate.add(Arrays.asList(base.get(5).get(0) - SQRT3 / 2 * halfWidth, base.get(4).get(1) - halfWidth / 2));

		// for calculate interval use
		coordinate.add(Arrays.asList(base.get(0).get(0), base.get(0).get(1) - halfWidth));

		return generateCoordinateOfProgressBar(coordinate);
	}

	private List<List<Double>> generateCoordinateOfProgressBar(List<List<Double>> coordinate) {
		List<List<Double>> rtn = new LinkedList<List<Double>>();
		if (Double.parseDouble(interval.get(0) + "") != 0) {
			rtn.add(coordinate.get(0));
			for (int i = 0; i < interval.size(); i++) {
				Double thisInterval = Double.parseDouble(interval.get(i) + "");
				if (thisInterval == 1) {
					rtn.add(coordinate.get(i + 1));
				} else if (thisInterval > 0) {
					Double oriX = coordinate.get(i).get(0);
					Double oriY = coordinate.get(i).get(1);
					Double nextX = coordinate.get(i + 1).get(0);
					Double nextY = coordinate.get(i + 1).get(1);
					Double diffX = nextX - oriX;
					Double diffY = nextY - oriY;
					rtn.add(Arrays.asList(oriX + thisInterval * diffX, oriY + thisInterval * diffY));
				}
			}
		}
		return rtn;
	}

	private List<Number> getInterval() {
		// percentage : 17 16 17 17 16 17
		List<Number> interval = new LinkedList<>();
		if (percentage == 0) {
			interval = Arrays.asList(0, 0, 0, 0, 0, 0);
		} else if (percentage <= 17) {
			interval = Arrays.asList((double) percentage / 17, 0, 0, 0, 0, 0);
		} else if (percentage <= 33) {
			interval = Arrays.asList(1, (double) (percentage - 17) / 16, 0, 0, 0, 0);
		} else if (percentage <= 50) {
			interval = Arrays.asList(1, 1, (double) (percentage - 33) / 17, 0, 0, 0);
		} else if (percentage <= 67) {
			interval = Arrays.asList(1, 1, 1, (double) (percentage - 50) / 17, 0, 0);
		} else if (percentage <= 83) {
			interval = Arrays.asList(1, 1, 1, 1, (double) (percentage - 67) / 16, 0);
		} else if (percentage <= 100) {
			interval = Arrays.asList(1, 1, 1, 1, 1, (double) (percentage - 83) / 17);
		}
		return interval;
	}

	private String format(List<List<Double>> coordinate) {
		String svgString = "";
		for (List<Double> eachCoordinate : coordinate) {
			svgString += eachCoordinate.get(0) + "," + eachCoordinate.get(1) + " ";
		}
		return svgString.trim();
	}

	private String formatT(List<List<Double>> coordinate) {
		String svgString = "";
		for (List<Double> eachCoordinate : coordinate) {
			svgString = eachCoordinate.get(0) + "," + eachCoordinate.get(1) + " " + svgString;
		}
		return svgString.trim();
	}

	void print() {
		String baseCoordinate = format(base);
		String innerCoordinate = format(generateCoordinateOfInnerProgressBar());
		String outerCoordinate = formatT(generateCoordinateOfOuterProgressBar());
		System.out.println(interval);
		System.out.println("Base:" + baseCoordinate);
		System.out.println("Progress bar:" + innerCoordinate + " " + outerCoordinate);
	}

	public static void main(String[] args) {
		HexagonProgressBarFactory svg = new HexagonProgressBarFactory(30, 4, 36);
		svg.print();
	}
}