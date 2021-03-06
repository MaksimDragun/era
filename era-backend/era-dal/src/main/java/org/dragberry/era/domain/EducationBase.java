package org.dragberry.era.domain;

public enum EducationBase implements BaseEnum<String> {

	L9("09"), L11("11");
	
	public final String value;
	
	private EducationBase(String value) {
		this.value = value;
	}
	
	public static EducationBase resolve(String value) {
		if (value == null) {
			throw BaseEnum.npeException(EducationBase.class);
		}
		for (EducationBase base : EducationBase.values()) {
			if (value.equals(base.value)) {
				return base;
			}
		}
		throw BaseEnum.unknownValueException(EducationBase.class, value);
	}
	
	@Override
	public String getValue() {
		return value;
	}
}
