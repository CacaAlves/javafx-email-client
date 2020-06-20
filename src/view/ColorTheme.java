package view;

public enum ColorTheme {
	LIGHT, DEFAULT, DARK;

	public static String getCssPath(ColorTheme colorTheme) {
		String str = null;
		
		switch (colorTheme) {
		case LIGHT:
			str = "css/themeLight.css";
			break;
		case DEFAULT:
			str = "css/themeDefault.css";
			break;
		case DARK:
			str = "css/themeDark.css";
			break;
		default:
			break;
		}
		return str;
	}
}
