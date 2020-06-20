package view;

public enum FontSize {
	SMALL, MEDIUM, BIG;

	public static String getCssPath(FontSize fontSize) {
		String str = null;
		
		switch (fontSize) {
		case SMALL:
			str = "css/fontSmall.css";
			break;
		case MEDIUM:
			str = "css/fontMedium.css";
			break;
		case BIG:
			str = "css/fontBig.css";
			break;
		default:
			break;
		}
		return str;
	}
}
