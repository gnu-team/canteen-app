package org.canteen_water.model.api;

public class ApiError {
    private String detail;

    public String getDetail() {
        StringBuilder sb = new StringBuilder();

        if (detail != null) {
            sb.append(detail);
        }

        // Allow subclasses to add errors for other fields
        fieldErrors(sb);

        return (sb.length() > 0) ? sb.toString() : null;
    }

    protected void fieldErrors(StringBuilder sb) {
        // Nothing to add
    }

    protected void listReasons(StringBuilder dest, String label, String[] reasons) {
        if (reasons != null && reasons.length > 0) {
            // If we've already added some reasons for a different
            // input, start the reasons for this new input on a new line
            if (dest.length() > 0) {
                dest.append("\n");
            }

            dest.append(label);
            dest.append(": ");

            for (int i = 0; i < reasons.length; i++) {
                dest.append(reasons[i]);

                if (i < reasons.length - 1) {
                    dest.append("; ");
                }
            }
        }
    }
}
