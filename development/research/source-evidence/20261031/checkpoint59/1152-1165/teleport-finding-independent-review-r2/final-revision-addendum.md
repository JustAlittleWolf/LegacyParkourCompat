# Final revision source-line check

This addendum supplements the source review in `review.md` (SHA-256 `98C7F4335FCFD04361E933667EA7D46AC8911A62B17A79AEDB4A5BA6F9E7A071`). It binds the accepted source finding to the final draft at `.native-source/20261030/1152-1165-finish43/canonical-findings/1.16.1--1.16.2/client-teleport-near-coordinate-fallback.md`, SHA-256 `FFEEA964AF3EBABD175D78B657EABEBAC82F311E04026EFC031B9583957AD4D3`.

I checked the exact 1.16.1 `LivingEntity.java` line windows against the canonical source (file SHA-256 `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`):

- `aiStep` interpolation gate and position update are lines 2376–2385 (`lerpSteps > 0`, target interpolation, then `setPos`).
- `lerpTo` target-field assignments are lines 2583–2590 (`lerpX/Y/Z`, rotation targets, and `lerpSteps`; no immediate `setPos`).

The final draft’s 1.16.1 line references are exact. The previously reviewed 1.16.2 ranges remain accurate: `aiStep` 2378–2387 and `lerpTo` 2585–2592. These source-line corrections do not change the reviewed behavior or boundary. **Verdict: ACCEPTED** for the final draft SHA above, with the nonblocking boolean-phrasing note already recorded in `review.md`.
