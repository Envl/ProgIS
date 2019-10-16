package exercise.Lab3;


public class ColorModel {

	private Integer value;

	public ColorModel(Integer value) {
		this.value = value;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public String toHex() {
		return toHex(0);
	}

	public String toHex(Integer minLength) {
		String hex = Integer.toHexString(getValue());
		while (hex.length() < minLength) {
			hex = "0" + hex;
		}
		return hex;
	}
}
