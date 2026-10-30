# Erratum: prior source-only records

This note corrects the provenance paragraph in `report.md`; the original report remains unchanged. Its SHA-256 is `D9AD35BDA17618F3844D4FEAB8D9D8EC5DEF19A519BDC01E273C9DEDBABEBCDA`.

I had **not read or reused** any of the four permitted prior records when drafting `report.md`. My filename-only search missed them because each supplied record is a directory containing `report.md` or `audit.md`, rather than a file named after the record. The statement that the records were not found was incorrect.

After drafting, I read the permitted records under `.native-source/20261028/frontier-c/`. Their overlapping claims are:

- `1165-1171-ordinary-tick-closure/report.md` and `1165-1171-jump-push-audit/audit.md` document the Jump-effect addition-precision candidate; the audit leaves an amplifier-specific numeric witness open.
- `1170-1171-full-inventory/report.md` and `1170-1171-full-audit/audit.md` document the Jump-boost precision and swimming-entry WATER-predicate candidates; the independent audit confirms the source predicates but leaves the concrete WATER-tag precondition open.

The two candidates in my original report were retraced directly against the 1.16.5 and 1.17.1 endpoint Java sources before I read those records. Thus the original source comparison did not reuse their claims. The 1.17.1 swimming predicate is the same operation documented in the 1.17.0 → 1.17.1 records; that adjacent endpoint context was not used in my original 1.16.5 → 1.17.1 analysis.
