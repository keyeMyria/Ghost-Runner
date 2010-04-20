package at.ac.tuwien.hci.ghost.data.entities;

public class Goal extends Entity {
	public enum Period {
		WEEK, MONTH, YEAR;
		public static Period Int2Period(int value) {
			if (value == WEEK.ordinal())
				return WEEK;
			if (value == MONTH.ordinal())
				return MONTH;
			return YEAR;
		}
	};

	public enum Type {
		DISTANCE, CALORIES, RUNS;
		public static Type Int2Type(int value) {
			if (value == DISTANCE.ordinal())
				return DISTANCE;
			if (value == CALORIES.ordinal())
				return CALORIES;
			return RUNS;
		}
	};

	/** the id of the goal */
	private long id = 0;
	/** the progress of the goal, between 0 and 1 */
	private float progress = 0.f;
	/** the value of the goal */
	private float goalValue = 0.f;
	/** the period of the goal */
	private Period period = Period.MONTH;
	/** the type of the goal */
	private Type type = null;

	public Goal(long id, Type type, float goalValue, float progress) {
		this.id = id;
		this.type = type;
		this.goalValue = goalValue;
		this.progress = progress;
	}

	public Goal(long id, Type type, float goalValue) {
		this(id, type, goalValue, 0.f);
	}

	public Goal(long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public float getGoalValue() {
		return goalValue;
	}

	public void setGoalValue(float goalValue) {
		this.goalValue = goalValue;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public float getProgress() {
		return progress;
	}

	public float getProgressFraction() {
		return progress * goalValue;
	}

	public int getProgressPercentage() {
		return Math.round(progress * 100);
	}

	public boolean finished() {
		return progress >= 1.f;
	}

	@Override
	public long getID() {
		return id;
	}

	@Override
	public void setID(long id) {
		this.id = id;
	}

	public void print() {
		System.out.println(id + ";" + period + ";" + progress + ";" + type + ";" + goalValue);
	}
}
