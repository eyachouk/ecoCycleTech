package tn.esprit.ecocycletech.Entity.Enumerations;

public enum EtatAppareil {
    NEUF,
    UTILISE,
    REPARABLE,
    HS,
    OCCASION,
    RECONDITIONNE;

    public static EtatAppareil fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null; // or return a default value like NEUF
        }
        try {
            return EtatAppareil.valueOf(value.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            // Handle invalid values by returning a default
            return null; // or return a default value like NEUF
        }
    }
}
