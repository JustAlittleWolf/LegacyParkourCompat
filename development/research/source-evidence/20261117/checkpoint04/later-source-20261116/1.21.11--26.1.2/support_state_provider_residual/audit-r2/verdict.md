# Independent audit — support-state provider residual r2

## Verdict

**ACCEPT** the citation-only r2 correction for the bounded support-state provider residual report. Report SHA-256: `560302F10614C0BBB95F1B1A631B7D2DA5237AC124F08B2FCB2E8A1540E6800B`.

The previous audit, `audit/verdict.md` SHA-256 `A68527DABAC5CEC60CC42E251924F45E9C943AD5996E7C7DBAE9A9745ECF1B58`, requested only immutable bindings for three reused accepted packets. The r2 appendix supplies all six report and independent ACCEPT-verdict hashes, and each hash matches the exact file at the cited path:

- `entity_move_collision/source-report.md`: `3F44A596DD8F9D8885C565CB2269355B5B6503D0B4EB3898FACD6EBCD77F73B7`.
- `entity_move_collision_audit/audit.md`: `5F80FEB0CA4138EDB835A56A8A11CAA86FB558DE819BCE31915BDB3021FA109E`.
- `core_shape_providers_revision_r2/coverage-r2.md`: `1DEA060FEEFE1812E68CC6D6A33D70EA32672948088B238BCDCE4B9123266393`.
- `core_shape_providers_r2_audit/verdict-r2.md`: `19D0E14E6F5388910EB0E31D14EBC4C3C29ADEE66E700E07C7B9CF9309B35B72`.
- `collision_provider_residual/collision-provider-residual.md`: `E313150BEE1C14E9BA91A6FB6F0C97B796F82D399DBD290792759D8292AFBA3F`.
- `collision_provider_residual/audit/verdict.md`: `693EC589325F8599E9373AACD961DB474920F6A1B153A36573B906B3E6DCB557`.

The original r1 report SHA-256 is `8ABADB281AC6D9EB275357466FBD49D5D57E0FF73ADE6625D988376335B29330`. Its 9,336 bytes are an exact prefix of r2; the added appendix changes no technical claim or residual boundary. The source/provider checks and limitations from the prior independent audit are reused unchanged.

This acceptance is limited to the citation correction. Provider method behavior, state registrations/tags, neighbor-update scheduling, support-state population, and full-pair coverage remain open. No pair freeze or runtime conclusion is made.
