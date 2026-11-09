package Utils;

public final class Html {

    private Html() {
    }

    public static String escape(Object giaTri) {
        if (giaTri == null) {
            return "";
        }
        String chuoi = String.valueOf(giaTri);
        StringBuilder ketQua = new StringBuilder(chuoi.length());
        for (int i = 0; i < chuoi.length(); i++) {
            char c = chuoi.charAt(i);
            switch (c) {
                case '&':  ketQua.append("&amp;");  break;
                case '<':  ketQua.append("&lt;");   break;
                case '>':  ketQua.append("&gt;");   break;
                case '"':  ketQua.append("&quot;"); break;
                case '\'': ketQua.append("&#39;");  break;
                default:   ketQua.append(c);
            }
        }
        return ketQua.toString();
    }

    public static String esc(Object giaTri) {
        return escape(giaTri);
    }
}

